package com.oyou.gwt.blog.client;


import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.oyou.gwt.blog.client.BlogDataProvider.BlogDataAcceptor;

public class ArticleList extends Composite implements TableListener, ChangeListener, ClickListener {
	
	private class BlogDataAcceptorImpl implements BlogDataAcceptor {
		public void acceptErrorCode(IsErrorCode data) {
			//
		}

		public void acceptArticles(IsArticle[] datas) {
			setArticles(datas);
		}
		public void acceptComments(IsComment[] datas) {
			setComments(datas);
		}

		public void acceptPhotos(IsPicture[] datas) {
			//
		}

		public void acceptAlbums(IsGroup[] datas) {
			setAlbums(datas);
		}

		public void acceptGroups(IsGroup[] datas) {
			setGroups(datas);
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

	private static final int VISIBLE_ARTICLE_ROW = 21;

	private static final int VISIBLE_ARTICLE_COL = 1;

	private HTML statusButton = new HTML();
	private HTML hSelection = new HTML("Blogger2<br>OYOU 2010<br>文章Article选择Selection", true);
	private HTML hLoading = new HTML("Blogger2<br>OYOU 2010<br><font color=ff0000><blink>Loading......</blink></font>", true);

	private HTML countLabel = new HTML();

	private HTML newerButton = new HTML("<a href='javascript:;'>&lt;&lt;</a>", true);

	private HTML olderButton = new HTML("<a href='javascript:;'>&gt;&gt;</a>", true);

	private int startIndex, selectedRow = -1, selectedCell = -1; 

	private FlexTable table = new FlexTable();

	private HorizontalPanel navBar = new HorizontalPanel();

	private BlogDataAcceptor acceptor = new BlogDataAcceptorImpl();

	private BlogDataProvider provider = null;

	private ErrorDialog errorDialog = null;

	private IsArticle[] articles = null;

	private IsComment[] comments = null;
	
	private IsGroup[] groups = null;

	private IsGroupType[] categories = null;
	
	private ListBox lbGroup = new ListBox();

	private ListBox lbCategory = new ListBox();

	private StatusBall sbStatus = new StatusBall();
	
	public ArticleList(BlogDataProvider provider) {
		this.provider = provider;

		// Setup the table.
		table.setCellSpacing(0);
		table.setCellPadding(0);
		table.setWidth("200px");

		// Hook up events.
		table.addTableListener(this);
		newerButton.addClickListener(this);
		olderButton.addClickListener(this);
		lbGroup.addChangeListener(this);
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
		selectionNavBar.add(lbGroup);
		lbCategory.setWidth("200px");
		lbGroup.setWidth("200px");
		
		// Create the 'navigation' bar at the upper-center.
		HorizontalPanel innerNavBar = new HorizontalPanel();
		navBar.setStyleName("album-ListNavBar");
		lbGroup.setVisibleItemCount(1);
		innerNavBar.add(newerButton);
		innerNavBar.add(countLabel);
		innerNavBar.add(olderButton);
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
		table.getFlexCellFormatter().setColSpan(0, 0, VISIBLE_ARTICLE_COL);

		// Initialize the rest of the rows.
		for (int i = 0; i < VISIBLE_ARTICLE_ROW; ++i) {
			for (int j = 0; j < VISIBLE_ARTICLE_COL; ++j) {
				table.setText(i + 1, j, "");
				table.getCellFormatter().setWordWrap(i + 1, j, true);
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
			// Move forward a page.
			startIndex += VISIBLE_ARTICLE_ROW * VISIBLE_ARTICLE_COL;
			if (startIndex >= articles.length) {
				startIndex -= VISIBLE_ARTICLE_ROW * VISIBLE_ARTICLE_COL;
			} else {
				styleRow(selectedRow, selectedCell, false);
				selectedRow = -1;
				update();
			}
		} else if (sender == newerButton) {
			Blog.get().stopSlide();
			// Move back a page.
			startIndex -= VISIBLE_ARTICLE_ROW * VISIBLE_ARTICLE_COL;
			if (startIndex < 0) {
				startIndex = 0;
			} else {
				styleRow(selectedRow, selectedCell, false);
				selectedRow = -1;
				update();
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

		int index = startIndex + VISIBLE_ARTICLE_COL * row + cell;
		if (index <= articles.length) {
			IsArticle article = articles[index];
			if (article != null) {
				Blog.get().displayArticle(article);
			}
		}
	}

	private void setArticles(IsArticle[] articles) {
		this.articles = articles;
		update();
	}

	private void setComments(IsComment[] comments) {
		this.comments = comments;
		update();
	}
	
	private void setAlbums(IsGroup[] albums) {
		this.setGroups(albums);
	}

	private void setGroups(IsGroup[] groups) {
		this.groups = groups;
		lbGroup.clear();
		for (int i = 0; i < groups.length; i++) {
			IsGroup group = groups[i];
			lbGroup.addItem(group.getName());
		}
		startIndex = 0;
		selectedRow = -1;
		selectedCell = -1; 
		lbGroup.addItem("Dummy Group");
		int index = Random.nextInt(groups.length - 1);
		lbGroup.setItemSelected(index, true);
		lbGroup.setSelectedIndex(index);
		provider.updateArticles("", groups[index].getId().toString(), "", acceptor);
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
		provider.getGroups(categories[index].getId().toString(), acceptor);
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
		int count = articles.length;
		int max = startIndex + VISIBLE_ARTICLE_ROW * VISIBLE_ARTICLE_COL;
		if (max > count) {
			max = count;
		}

		statusButton.setHTML(hSelection.getHTML());
		newerButton.setVisible(startIndex != 0);
		olderButton.setVisible(startIndex + VISIBLE_ARTICLE_ROW * VISIBLE_ARTICLE_COL < count);
		countLabel.setText("" + (startIndex + 1) + "-" + max + "/" + count);

		for (int i = 0; i < VISIBLE_ARTICLE_ROW; ++i) {
			for (int j = 0; j < VISIBLE_ARTICLE_COL; ++j) {
				if (startIndex + i * VISIBLE_ARTICLE_COL + j >= articles.length) {
					table.setHTML(i + 1, j, "&nbsp;");
				} else {
					IsArticle article = articles[startIndex + i * VISIBLE_ARTICLE_COL + j];
					HTML hLine = new HTML("", true);
					hLine.setText(article.getTitle());
					table.setWidget(i + 1, j, hLine);
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
			provider.getGroups(category.getId().toString(), acceptor);
		} else if (sender == lbGroup) {
			startIndex = 0;
			selectedRow = -1;
			selectedCell = -1; 
			statusButton.setHTML(hLoading.getHTML());
			int cIndex = lbCategory.getSelectedIndex();
			IsGroupType category = categories[cIndex];
			int aIndex = lbGroup.getSelectedIndex();
			if (aIndex >= groups.length) {
				provider.updateArticles(category.getId().toString(), "", "", acceptor);
			} else {
				IsGroup group = groups[aIndex];
				provider.updateArticles(category.getId().toString(), group.getId().toString(), "", acceptor);
			}
		}
	}	

}
