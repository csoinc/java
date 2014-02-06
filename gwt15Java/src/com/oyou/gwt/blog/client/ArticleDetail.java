package com.oyou.gwt.blog.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * @author	Owen Ou
 * @version $Id: ArticleDetail.java,v 1.1 2008/06/29 14:23:45 oyou Exp $
 * @since Dec 11, 2007
 */
public class ArticleDetail extends Composite {

	private VerticalPanel vpHeader = new VerticalPanel();

	private HTML hTitle = new HTML("", true);

	private HTML hSummary = new HTML("", true);
	
	private HTML hAuthor = new HTML("", true);

	private HTML hContent = new HTML("", true);

	private ScrollPanel spContent = new ScrollPanel(hContent);

	public ArticleDetail() {
		vpHeader.add(hTitle);
		vpHeader.add(hAuthor);
		vpHeader.add(hSummary);
		vpHeader.setWidth("800px");

		DockPanel dpInner = new DockPanel();
		dpInner.add(vpHeader, DockPanel.NORTH);
		dpInner.add(spContent, DockPanel.CENTER);

		dpInner.setCellHorizontalAlignment(spContent, HorizontalPanel.ALIGN_LEFT);
		dpInner.setCellHeight(spContent, "538px");

		spContent.setSize("100%", "100%");
		spContent.setAlwaysShowScrollBars(false);
		initWidget(dpInner);

		setStyleName("article-Detail");
		spContent.setStyleName("article-Body");
		vpHeader.setStyleName("article-Detail");
		dpInner.setStyleName("article-Detail");
		hTitle.setStyleName("article-Title");
		hSummary.setStyleName("article-Summary");
		hAuthor.setStyleName("article-Author");
		hContent.setStyleName("article-Body");
	}

	public void setArticle(IsArticle article) {
		hTitle.setHTML(article.getTitle());
		hAuthor.setHTML(article.getNickname() + "&nbsp;&nbsp;" + article.getTime());
		hSummary.setHTML(article.getSummary());
		hContent.setHTML(article.getContent());
	}


	/**
	 * Adjusts the widget's size such that it fits within the window's client
	 * area.
	 */
	public void adjustSize(int windowWidth, int windowHeight) {
		int scrollWidth = windowWidth - spContent.getAbsoluteLeft() - 9;
		if (scrollWidth < 1) {
			scrollWidth = 1;
		}

		int scrollHeight = windowHeight - spContent.getAbsoluteTop() - 9;
		if (scrollHeight < 1) {
			scrollHeight = 1;
		}

		vpHeader.setWidth("" + scrollWidth);
		spContent.setSize("" + scrollWidth, "" + scrollHeight);
	}

}
