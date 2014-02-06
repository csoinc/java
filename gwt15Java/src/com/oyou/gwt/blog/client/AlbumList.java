package com.oyou.gwt.blog.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.oyou.gwt.blog.client.BlogDataProvider.BlogDataAcceptor;

/**
 * 
 * @author	Owen Ou
 * @version $Id: AlbumList.java,v 1.1 2008/06/29 14:23:45 oyou Exp $
 * @since Dec 5, 2007
 */
public class AlbumList extends Composite implements TableListener, ChangeListener, ClickListener {
	
	private class BlogDataAcceptorImpl implements BlogDataAcceptor {
		public void acceptErrorCode(IsErrorCode data) {
			//
		}
		public void acceptArticles(IsArticle[] datas) {
			//
		}
		public void acceptComments(IsComment[] datas) {
			//
		}

		public void acceptPhotos(IsPicture[] datas) {
			setPhotos(datas);
		}

		public void acceptGroups(IsGroup[] datas) {
			setAlbums(datas);
		}
		
		public void acceptAlbums(IsGroup[] datas) {
			setAlbums(datas);
		}

		public void acceptCategories(IsGroupType[] datas) {
			setCategories(datas);
		}
		
		public void failed(Throwable caught) {
			if (caught instanceof InvocationException) {
				showErrorDialog(NO_CONNECTION_MESSAGE);
			} if (caught instanceof BlogException) {
				//process business exception here
				if (ErrorConstants.error_message_authority.equals(caught.getMessage())) {
					Blog.get().login();
				} else {
					showErrorDialog(caught.getMessage());
				}
			} else {
				showErrorDialog(caught.getMessage());
			}
		}
		
		private void showErrorDialog(String msg) {
			if (errorDialog == null) {
				errorDialog = new ErrorDialog();
			}
			errorDialog.setError(msg);
			errorDialog.center();
		}
	}

	private static final String NO_CONNECTION_MESSAGE = "Unable to connect RPC server!";

	private static final int VISIBLE_PHOTO_ROW = 8;

	private static final int VISIBLE_PHOTO_COL = 2;

	private HTML statusButton = new HTML();
	private HTML hSelection = new HTML("Blogger<br> OYOU 2010<br>相册Album选择Selection", true);
	private HTML hLoading = new HTML("Blogger<br>OYOU 2010<br><font color=ff0000><blink>Loading......</blink></font>", true);

	private HTML countLabel = new HTML();

	private HTML newerButton = new HTML("<a href='javascript:;'>&lt;&lt;</a>", true);

	private HTML olderButton = new HTML("<a href='javascript:;'>&gt;&gt;</a>", true);

	private HTML slideButton = new HTML("<a href='javascript:;'>slide</a>", true);
	
	private int startIndex, selectedRow = -1, selectedCell = -1; 

	private FlexTable table = new FlexTable();

	private HorizontalPanel navBar = new HorizontalPanel();

	private BlogDataAcceptor acceptor = new BlogDataAcceptorImpl();

	private BlogDataProvider provider = null;

	private ErrorDialog errorDialog = null;

	private IsPicture[] photos = null;

	private IsGroup[] albums = null;

	private IsGroupType[] categories = null;
	
	private ListBox lbAlbum = new ListBox();

	private ListBox lbCategory = new ListBox();

	private StatusBall sbStatus = new StatusBall();
	
	public AlbumList(BlogDataProvider provider) {
		this.provider = provider;

		// Setup the table.
		table.setCellSpacing(0);
		table.setCellPadding(0);
		table.setWidth("200px");

		// Hook up events.
		table.addTableListener(this);
		newerButton.addClickListener(this);
		olderButton.addClickListener(this);
		slideButton.addClickListener(this);
		lbAlbum.addChangeListener(this);
		lbCategory.addChangeListener(this);

		HorizontalPanel statusNavBar = new HorizontalPanel();
		statusNavBar.setStyleName("album-ListNavBar");
		statusNavBar.add(sbStatus);
		statusNavBar.add(statusButton);
		statusNavBar.setWidth("200px");
		statusNavBar.setVerticalAlignment(VerticalPanel.ALIGN_BOTTOM);
		statusButton.setHTML(hLoading.getHTML());
		
		VerticalPanel selectionNavBar = new VerticalPanel();
		selectionNavBar.setStyleName("album-ListNavBar");
		selectionNavBar.add(lbCategory);
		selectionNavBar.add(lbAlbum);
		lbCategory.setWidth("200px");
		lbAlbum.setWidth("200px");
		
		// Create the 'navigation' bar at the upper-center.
		HorizontalPanel innerNavBar = new HorizontalPanel();
		navBar.setStyleName("album-ListNavBar");
		lbAlbum.setVisibleItemCount(1);
		innerNavBar.add(newerButton);
		innerNavBar.add(countLabel);
		innerNavBar.add(olderButton);
		innerNavBar.add(slideButton);
		innerNavBar.add(new HTML("&nbsp;&nbsp;"));

		navBar.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		navBar.add(innerNavBar);
		navBar.setWidth("200px");

		VerticalPanel vpList = new VerticalPanel();
		vpList.add(statusNavBar);
		vpList.add(selectionNavBar);
		vpList.add(table);
		vpList.setWidth("200px");
		initWidget(vpList);
		setStyleName("album-List");
		initTable();

	}

	private void initTable() {
		// Create the header row.
		//table.setWidget(0, 0, sbStatus);
		//table.setWidget(0, 1, statusButton);
		table.setWidget(0, 0, navBar);
		table.getRowFormatter().setStyleName(0, "album-ListHeader");
		table.getFlexCellFormatter().setColSpan(0, 0, VISIBLE_PHOTO_COL);

		// Initialize the rest of the rows.
		for (int i = 0; i < VISIBLE_PHOTO_ROW; ++i) {
			for (int j = 0; j < VISIBLE_PHOTO_COL; ++j) {
				table.setText(i + 1, j, "");
				table.getCellFormatter().setWordWrap(i + 1, j, false);
			}
		}
	}

	public void onCellClicked(SourcesTableEvents sender, int row, int cell) {
		//Window.alert("Clicked cell(index,row,cell): " + startIndex + "," + row + "," + cell);
		if (row > 0) {
			selectRow(row - 1, cell);
		}
	}

	public void onClick(Widget sender) {
		if (sender == olderButton) {
			Blog.get().stopSlide();
			// Move forward a page.
			startIndex += VISIBLE_PHOTO_ROW * VISIBLE_PHOTO_COL;
			if (startIndex >= photos.length) {
				startIndex -= VISIBLE_PHOTO_ROW * VISIBLE_PHOTO_COL;
			} else {
				styleRow(selectedRow, selectedCell, false);
				selectedRow = -1;
				update();
			}
		} else if (sender == newerButton) {
			Blog.get().stopSlide();
			// Move back a page.
			startIndex -= VISIBLE_PHOTO_ROW * VISIBLE_PHOTO_COL;
			if (startIndex < 0) {
				startIndex = 0;
			} else {
				styleRow(selectedRow, selectedCell, false);
				selectedRow = -1;
				update();
			}
		} 
		else if (sender == slideButton) {
			if ("slide".equals(slideButton.getText())) {
				int index = startIndex + VISIBLE_PHOTO_COL * selectedRow + selectedCell;
				if (index < 0) index = 0;
				Blog.get().slidePhotos(photos, index + 1);
			} else {
				Blog.get().stopSlide();
			}
		}
	}

	protected void onLoad() {
		refresh();
	}

	public void refresh() {
		provider.getCategories(acceptor);
	}

	private void selectRow(int row, int cell) {
		styleRow(selectedRow, selectedCell, false);
		styleRow(row, cell, true);

		selectedRow = row;
		selectedCell = cell;
		if ("stop".equals(slideButton.getText())) {
			slideButton.setText("slide");
			Blog.get().stopSlide();
		}

		int index = startIndex + VISIBLE_PHOTO_COL * row + cell;
		if (index <= photos.length) {
			IsPicture photo = photos[index];
			if (photo != null) {
				Blog.get().displayPhoto(photo);
			}
		}
	}

	private void setPhotos(IsPicture[] photos) {
		this.photos = photos;
		update();
	}

	private void setAlbums(IsGroup[] albums) {
		this.albums = albums;
		lbAlbum.clear();
		for (int i = 0; i < albums.length; i++) {
			IsGroup album = albums[i];
			lbAlbum.addItem(album.getName());
		}
		startIndex = 0;
		selectedRow = -1;
		selectedCell = -1; 
		lbAlbum.addItem("Dummy Album");
		int index = Random.nextInt(albums.length - 1);
		lbAlbum.setItemSelected(index, true);
		lbAlbum.setSelectedIndex(index);
		provider.updatePhotos("", albums[index].getId().toString(), "", acceptor);
	}

	private void setCategories(IsGroupType[] categories) {
		this.categories = categories;
		lbCategory.clear();
		for (int i = 0; i < categories.length; i++) {
			IsGroupType category = categories[i];
			lbCategory.addItem(category.getNameCN() + ":" + category.getName());
		}
		int index = Random.nextInt(categories.length - 1);
		lbCategory.setItemSelected(index, true);
		lbCategory.setSelectedIndex(index);
		provider.getAlbums(categories[index].getId().toString(), acceptor);
	}
	
	private void styleRow(int row, int col, boolean selected) {
		if (row != -1) {
			if (selected) {
				table.getCellFormatter().addStyleName(row + 1, col, "album-SelectedCell");
			} else {
				table.getCellFormatter().removeStyleName(row + 1, col, "album-SelectedCell");
			}
		}
	}

	private void update() {
		// Update the older/newer buttons & label.
		int count = photos.length;
		int max = startIndex + VISIBLE_PHOTO_ROW * VISIBLE_PHOTO_COL;
		if (max > count) {
			max = count;
		}

		statusButton.setHTML(hSelection.getHTML());
		newerButton.setVisible(startIndex != 0);
		olderButton.setVisible(startIndex + VISIBLE_PHOTO_ROW * VISIBLE_PHOTO_COL < count);
		countLabel.setText("" + (startIndex + 1) + "-" + max + "/" + count);

		String moduleRelativeURL = GWT.getModuleBaseURL();
		for (int i = 0; i < VISIBLE_PHOTO_ROW; ++i) {
			for (int j = 0; j < VISIBLE_PHOTO_COL; ++j) {
				if (startIndex + i * VISIBLE_PHOTO_COL + j >= photos.length) {
					table.setHTML(i + 1, j, "&nbsp;");
				} else {
					IsPicture photo = photos[startIndex + i * VISIBLE_PHOTO_COL + j];
					Image icon = new Image();
					if (Blog.DEBUG) {
						icon.setUrl(moduleRelativeURL + "./" + photo.getIcon());
					} else {
						icon.setUrl(moduleRelativeURL + "../../upload/" + photo.getIcon());
						//icon.setUrl(moduleRelativeURL + "../../icon.do?mid=" + photo.getId());
					}
					icon.setVisibleRect(0, 0, 99, 59); 
					table.setWidget(i + 1, j, icon);
				}
			}
		}

		// Select the first row,cell if none is selected.
		if (selectedRow == -1) {
			selectRow(0, 0);
		}
	}

	public void onChange(Widget sender) {
		if (sender == lbCategory) {
			statusButton.setHTML(hLoading.getHTML());
			int cIndex = lbCategory.getSelectedIndex();
			IsGroupType category = categories[cIndex];
			provider.getAlbums(category.getId().toString(), acceptor);
		} else if (sender == lbAlbum) {
			startIndex = 0;
			selectedRow = -1;
			selectedCell = -1; 
			statusButton.setHTML(hLoading.getHTML());
			int cIndex = lbCategory.getSelectedIndex();
			IsGroupType category = categories[cIndex];
			int aIndex = lbAlbum.getSelectedIndex();
			if (aIndex >= albums.length) {
				provider.updatePhotos(category.getId().toString(), "", "", acceptor);
			} else {
				IsGroup album = albums[aIndex];
				provider.updatePhotos(category.getId().toString(), album.getId().toString(), "", acceptor);
			}
		}
	}	

	public void start() {
		slideButton.setText("stop");
		sbStatus.start();
	}

	public void stop() {
		slideButton.setText("slide");
		sbStatus.stop();
	}

}
