package com.oyou.gwt.blog.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * @author	Owen Ou
 * @version $Id: MyAlbumDetail.java,v 1.1 2008/06/29 14:23:45 oyou Exp $
 * @since Dec 5, 2007
 */
public class MyAlbumDetail extends Composite {

	private VerticalPanel vpFooter = new VerticalPanel();

	private HTML hAuthor = new HTML();

	private HTML hComment = new HTML();

	private Image iPhoto = new Image("./images/icon-leaf-red.png");

	private ScrollPanel spPhoto = new ScrollPanel(iPhoto);

	private StatusBall sbStatus = new StatusBall();
	
	public MyAlbumDetail() {
		iPhoto.setVisibleRect(0, 0, 800, 600);
		vpFooter.add(hComment);
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
		hComment.setStyleName("album-Comment");
		iPhoto.setStyleName("album-DetailBody");
		iPhoto.setVisible(true);
	}

	public void setPhoto(IsPicture photo) {
		hComment.setHTML(photo.getComment());
		hAuthor.setHTML("Posted by " + photo.getNickname() + " on " + photo.getTime());
		String moduleRelativeURL = GWT.getModuleBaseURL();
		if (Blog.DEBUG) {
			iPhoto.setUrl(moduleRelativeURL + "./" + photo.getImage());
		} else {
			iPhoto.setUrl(moduleRelativeURL + "../../upload/" + photo.getImage());
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
	
}
