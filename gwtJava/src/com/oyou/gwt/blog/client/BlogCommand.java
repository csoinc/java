package com.oyou.gwt.blog.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;

/**
 * 
 * @author	Owen Ou
 * @version $Id: BlogCommand.java,v 1.1 2008/06/29 14:23:46 oyou Exp $
 * @since Dec 5, 2007
 */
public class BlogCommand implements Command {
	public static final String CMD_EXIT = "exit";
	public static final String CMD_LOGIN = "login";
	public static final String CMD_LOGOUT = "logout";
	public static final String CMD_REGISTER = "register";
	public static final String CMD_UPDATE_PASSWORD = "updatePassword";
	public static final String CMD_FORGOT_PASSWORD = "forgotPassword";
	public static final String CMD_UPDATE_PROFILE = "updateProfile";
	public static final String CMD_NEW_PHOTO = "newPhoto";
	public static final String CMD_NEW_ARTICLE = "newArticle";
	public static final String CMD_VIEW_PHOTO = "viewPhoto";
	public static final String CMD_VIEW_ARTICLE = "viewArticle";
	public static final String CMD_VIEW_MYPHOTO = "viewMyPhoto";
	public static final String CMD_VIEW_MYARTICLE = "viewMyArticle";
	public static final String CMD_BIBLE_INDEX = "bibleIndex";
	public static final String CMD_BIBLE_SELECTED = "bibleSelected";
	public static final String CMD_BIBLE_SEARCH = "bibleSearch";
	public static final String CMD_BLOG_SEARCH = "blogSearch";
	public static final String CMD_GROUP_EMAIL = "emailGroup";
	
	private String action = null;
	
	public BlogCommand() {
	}

	public BlogCommand(String action) {
		this.action = action;
	}
	
	public void execute() {
		String moduleRelativeURL = GWT.getModuleBaseURL();
		if (action == null || "".equals(action)) {
			Window.alert("Coming soon......");
		} else if (CMD_EXIT.equals(action)) {
			this.redirect(moduleRelativeURL + "../../blogger/group.do");
		} else if (CMD_LOGIN.equals(action)) {
			this.redirect(moduleRelativeURL + "../../blogger/login.do");
		} else if (CMD_LOGOUT.equals(action)) {
			this.redirect(moduleRelativeURL + "../../blogger/logout.do");
		} else if (CMD_REGISTER.equals(action)) {
			this.redirect(moduleRelativeURL + "../../blogger/register.do");
		} else if (CMD_FORGOT_PASSWORD.equals(action)) {
			this.redirect(moduleRelativeURL + "../../blogger/forgotPassword.do");
		} else if (CMD_UPDATE_PASSWORD.equals(action)) {
			this.redirect(moduleRelativeURL + "../../blogger/password.do");
		} else if (CMD_UPDATE_PROFILE.equals(action)) {
			this.redirect(moduleRelativeURL + "../../blogger/profile.do");
		} else if (CMD_BIBLE_INDEX.equals(action)) {
			this.redirect(moduleRelativeURL + "../../bible/bibleTree.do");
		} else if (CMD_BIBLE_SELECTED.equals(action)) {
			this.redirect(moduleRelativeURL + "../../bible/bibleList.do");
		} else if (CMD_BIBLE_SEARCH.equals(action)) {
			this.redirect(moduleRelativeURL + "../../bible/bibleSearch.do");
		} else if (CMD_BLOG_SEARCH.equals(action)) {
			this.redirect(moduleRelativeURL + "../../blogger/blogSearch.do");
		} else if (CMD_GROUP_EMAIL.equals(action)) {
			this.redirect(moduleRelativeURL + "../../blogger/emailGroup.do");
		} else if (CMD_NEW_PHOTO.equals(action)) {
			this.redirect(moduleRelativeURL + "../../blogger/group.do");
		} else if (CMD_NEW_ARTICLE.equals(action)) {
			this.redirect(moduleRelativeURL + "../../blogger/group.do");
		} else if (CMD_VIEW_ARTICLE.equals(action)) {
			Blog.get().setSelectedTab(CMD_VIEW_ARTICLE);
		} else if (CMD_VIEW_PHOTO.equals(action)) {
			Blog.get().setSelectedTab(CMD_VIEW_PHOTO);
		} else if (CMD_VIEW_MYARTICLE.equals(action)) {
			Blog.get().setSelectedTab(CMD_VIEW_MYARTICLE);
		} else if (CMD_VIEW_MYPHOTO.equals(action)) {
			Blog.get().setSelectedTab(CMD_VIEW_MYPHOTO);
		} else {
			Window.alert("The " + action + " will be coming soon......");
		}	
	}

	public String getAction() {
		return action;
	}

	public native void redirect(String url)/*-{
	 	$wnd.location = url;
	}-*/;

	public void setAction(String action) {
		this.action = action;
	}

}
