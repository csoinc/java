package com.oyou.gwt.console.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;

public class ConsoleCommand implements Command {
	public static final String CMD_EXIT = "exit";
	public static final String CMD_LOGIN = "login";
	public static final String CMD_LOGOUT = "logout";
	public static final String CMD_NEW_PHOTO = "newPhoto";
	public static final String CMD_NEW_ARTICLE = "newArticle";
	public static final String CMD_VIEW_PHOTO = "viewPhoto";
	public static final String CMD_VIEW_ARTICLE = "viewArticle";
	public static final String CMD_VIEW_MYPHOTO = "viewMyPhoto";
	public static final String CMD_VIEW_MYARTICLE = "viewMyArticle";
	
	private String action = null;
	
	public ConsoleCommand() {
	}

	public ConsoleCommand(String action) {
		this.action = action;
	}
	
	public void execute() {
		String moduleRelativeURL = GWT.getModuleBaseURL();
		if (action == null || "".equals(action)) {
			Window.alert("Coming soon......");
		} else if (CMD_EXIT.equals(action)) {
			this.redirect(moduleRelativeURL + "../../group.do");
		} else if (CMD_LOGIN.equals(action)) {
			this.redirect(moduleRelativeURL + "../../login.do");
		} else if (CMD_LOGOUT.equals(action)) {
			this.redirect(moduleRelativeURL + "../../logout.do");
		} else {
			Window.alert(action + " will be coming soon......");
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
