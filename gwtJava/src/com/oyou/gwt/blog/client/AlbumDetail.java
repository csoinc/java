package com.oyou.gwt.blog.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.oyou.gwt.blog.client.BlogDataProvider.BlogDataAcceptor;

/**
 * 
 * @author	Owen Ou
 * @version $Id: AlbumDetail.java,v 1.1 2008/06/29 14:23:49 oyou Exp $
 * @since Dec 5, 2007
 */
public class AlbumDetail extends Composite implements ClickListener {
	private static final String SUCCESS = "0";
	private static final String FAIL = "-1";

	private class BlogDataAcceptorImpl implements BlogDataAcceptor {
		public void acceptErrorCode(IsErrorCode data) {
			if (data != null && data.getId().equals(SUCCESS)) {
				showMessageDialog(data.getId() + " - " + data.getMessage());
			} else {
				showErrorDialog(data.getId() + " - " + data.getMessage());
			}
		}
		public void acceptArticles(IsArticle[] datas) {
			//
		}
		public void acceptComments(IsComment[] datas) {
			//
		}

		public void acceptPhotos(IsPicture[] datas) {
			//
		}

		public void acceptGroups(IsGroup[] datas) {
			//
		}
		
		public void acceptAlbums(IsGroup[] datas) {
			//
		}

		public void acceptCategories(IsGroupType[] datas) {
			//
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

		private void showMessageDialog(String msg) {
			if (messageDialog == null) {
				messageDialog = new MessageDialog();
			}
			messageDialog.setMessage(msg);
			messageDialog.center();
		}
		
	}

	private static final String NO_CONNECTION_MESSAGE = "Unable to connect RPC server!";

	private ErrorDialog errorDialog = null;

	private MessageDialog messageDialog = null;
	
	private VerticalPanel vpFooter = new VerticalPanel();

	private HTML hAuthor = new HTML();

	private HTML hSummary = new HTML();

	private TextArea taComment = new TextArea(); 

	private Image iPhoto = new Image("./images/icon-leaf-red.png");

	private ScrollPanel spPhoto = new ScrollPanel(iPhoto);

	private StatusBall sbStatus = new StatusBall();
	
	private IsPicture ipCurrent;
	
	private Button btnUpdate = new Button("Update");

	private BlogDataAcceptor acceptor = new BlogDataAcceptorImpl();
	
	private BlogDataProvider provider = null;
	
	public AlbumDetail(BlogDataProvider provider) {
		this.provider = provider;
		HorizontalPanel hpComment = new HorizontalPanel();
		taComment.setCharacterWidth(80);
		taComment.setVisibleLines(2);

		hpComment.add(taComment);
		hpComment.add(btnUpdate);
		btnUpdate.addClickListener(this);
		
		iPhoto.setVisibleRect(0, 0, 800, 600);
		vpFooter.add(hSummary);
		vpFooter.add(hpComment);
		vpFooter.add(hAuthor);
		vpFooter.setWidth("800px");

		DockPanel dpInner = new DockPanel();
		dpInner.add(spPhoto, DockPanel.CENTER);
		dpInner.add(vpFooter, DockPanel.SOUTH);

		dpInner.setCellHorizontalAlignment(spPhoto, HorizontalPanel.ALIGN_CENTER);
		dpInner.setCellHeight(spPhoto, "620px");

		spPhoto.setSize("100%", "100%");
		spPhoto.setAlwaysShowScrollBars(false);
		initWidget(dpInner);

		setStyleName("album-Detail");
		spPhoto.setStyleName("album-DetailBody");
		vpFooter.setStyleName("album-DetailHeader");
		dpInner.setStyleName("album-DetailInner");
		hAuthor.setStyleName("album-Comment");
		hSummary.setStyleName("album-Comment");
		iPhoto.setStyleName("album-DetailBody");
		iPhoto.setVisible(true);
	}

	public void setPhoto(IsPicture photo) {
		ipCurrent = photo;
		hSummary.setHTML(photo.getSummary());
		taComment.setText(photo.getComment());
		hAuthor.setHTML("Posted by " + photo.getNickname() + " on " + photo.getTime());
		String moduleRelativeURL = GWT.getModuleBaseURL();
		if (Blog.DEBUG) {
			iPhoto.setUrl(moduleRelativeURL + "./" + photo.getImage());
		} else {
			iPhoto.setUrl(moduleRelativeURL + "../../upload/" + photo.getImage());
			//iPhoto.setUrl(moduleRelativeURL + "../../image.do?mid=" + photo.getId());
		}

	}

	public void start() {
		sbStatus.start();
	}

	public void stop() {
		sbStatus.stop();
	}

	/**
	 * Adjusts the widget's size such that it fits within the window's client
	 * area.
	 */
	public void adjustSize(int windowWidth, int windowHeight) {
		int scrollWidth = windowWidth - spPhoto.getAbsoluteLeft() - 9;
		if (scrollWidth < 1) {
			scrollWidth = 1;
		}

		int scrollHeight = windowHeight - spPhoto.getAbsoluteTop() - 9;
		if (scrollHeight < 1) {
			scrollHeight = 1;
		}

		iPhoto.setVisibleRect(0, 0, scrollWidth, scrollHeight);
		vpFooter.setWidth("" + scrollWidth);
		spPhoto.setSize("" + scrollWidth, "" + scrollHeight);
	}

	public void onClick(Widget sender) {
		if (sender == btnUpdate) {
			ipCurrent.setComment(taComment.getText());
			this.provider.updatePhoto(ipCurrent.getId(), taComment.getText(), acceptor);
		}
	}
	
	
}
