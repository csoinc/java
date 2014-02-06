package com.oyou.gwt.blog.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author	Owen Ou
 * @version $Id: MessageDialog.java,v 1.1 2008/06/29 14:23:45 oyou Exp $
 * @since Feb 25, 2008
 */
public class MessageDialog extends DialogBox implements ClickListener {
	private HTML message = new HTML("", true);

	public MessageDialog() {
		setStylePrimaryName("Popup-Dialog");
		setText("MESSAGE");
		Button closeButton = new Button("Close", this);
		VerticalPanel panel = new VerticalPanel();
		panel.setSpacing(4);
		message.setWidth("256px");
		panel.add(message);
		panel.add(closeButton);
		panel.setCellHorizontalAlignment(closeButton, VerticalPanel.ALIGN_RIGHT);
		setWidget(panel);
	}

	public void onClick(Widget sender) {
		hide();
	}

	public void setMessage(String msg) {
		message.setHTML(msg);
	}

}
