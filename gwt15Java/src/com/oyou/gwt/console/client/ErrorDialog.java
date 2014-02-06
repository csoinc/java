package com.oyou.gwt.console.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author	Owen Ou
 * @version $Id: ErrorDialog.java,v 1.1 2008/06/29 14:24:30 oyou Exp $
 * @since Nov 14, 2007
 */
public class ErrorDialog extends DialogBox implements ClickListener {
	private HTML error = new HTML("", true);
	private static String INFO = "INFO";
	private static String ERROR = "ERROR";
	private static String DEBUG = "DEBUG";

	public ErrorDialog() {
		setStylePrimaryName("Error-Dialog");
		setText(INFO);
		Button closeButton = new Button("Close", this);
		VerticalPanel panel = new VerticalPanel();
		panel.setSpacing(4);
		error.setWidth("256px");
		panel.add(error);
		panel.add(closeButton);
		panel.setCellHorizontalAlignment(closeButton, VerticalPanel.ALIGN_RIGHT);
		setWidget(panel);
	}

	public void onClick(Widget sender) {
		hide();
	}

	public void setError(String err) {
		error.setText(ERROR);
		error.setHTML(err);
		center();
	}

	public void setInfo(String info) {
		error.setText(INFO);
		error.setHTML(info);
		center();
	}

	public void setDebug(String debug) {
		error.setText(DEBUG);
		error.setHTML(debug);
		center();
	}
	
}
