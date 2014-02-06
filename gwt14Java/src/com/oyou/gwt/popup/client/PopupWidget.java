package com.oyou.gwt.popup.client;


import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.oyou.gwt.popup.client.PopupDataProvider.InformationAcceptor;

/**
 * 
 * @author	Owen Ou
 * @version $Id: PopupWidget.java,v 1.1 2008/06/29 14:23:36 oyou Exp $
 * Nov 5, 2007
 */
public class PopupWidget extends Composite implements ClickListener {

	private static class ErrorDialog extends DialogBox implements ClickListener {
		private HTML error = new HTML("", true);

		public ErrorDialog() {
			setStylePrimaryName("Error-Dialog");
			setText("ERROR");
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
			error.setHTML(err);
		}
	}

	private static class PopupDialog extends DialogBox implements ClickListener {
		private HTML popupMsg = new HTML("", true);
		public PopupDialog(String msgTitle) {
			setStylePrimaryName("Popup-Dialog");
			setText(msgTitle);
			Button closeButton = new Button("Close", this);
			DockPanel dock = new DockPanel();
			dock.setSpacing(4);
			popupMsg.setWidth("640px");
			dock.add(closeButton, DockPanel.SOUTH);
			dock.add(popupMsg, DockPanel.CENTER);
			dock.setCellHorizontalAlignment(closeButton, DockPanel.ALIGN_RIGHT);
			dock.setWidth("100%");
			setWidget(dock);
		}

		public void onClick(Widget sender) {
			hide();
		}

		public void setMsg(String msg) {
			popupMsg.setHTML(msg);
		}
	}

	private static class PopupFrame extends PopupPanel {
		private HTML contents = new HTML("", true);

		public PopupFrame() {
			super(true);
			setContents("Click anywhere outside this popup to make it disappear.");
			contents.setWidth("128px");
			setWidget(contents);
			setStyleName("Popup-Popup");
		}

		public void setContents(String msg) {
			contents.setHTML(msg);
		}

	}

	private class InformationAcceptorImpl implements InformationAcceptor {
		public void accept(String[] datas) {
			String data = "";
			for (int i = 0; i < datas.length; i++) {
				data += datas[i];
			}
			popDialog.setMsg(data);
			//popFrame.setContents(data);
		}

		public void failed(Throwable caught) {
			if (errorDialog == null) {
				errorDialog = new ErrorDialog();
			}
			if (caught instanceof InvocationException) {
				errorDialog.setError(NO_CONNECTION_MESSAGE);
			} else {
				errorDialog.setError(caught.getMessage());
			}
			errorDialog.center();
		}
	}

	private static final String NO_CONNECTION_MESSAGE = "Can't get RPC connection!";
	private final InformationAcceptor acceptor = new InformationAcceptorImpl();
	private PopupDialog popDialog = null;
	private PopupFrame popFrame = null;
	private final PopupDataProvider provider;
	private ErrorDialog errorDialog = null;
	private String msgId;
	private String msgTitle;
	private Hyperlink msgLink = new Hyperlink();
	private Image helpImage = new Image("images/icon-leaf-tiny-red.png");

	public PopupWidget(PopupDataProvider provider, String msgId, String msgTitle) {
		this.msgTitle = msgTitle;
		popDialog = new PopupDialog(this.msgTitle);
		popFrame = new PopupFrame();
		popFrame.setContents(this.msgTitle);
		HorizontalPanel panel = new HorizontalPanel();
		panel.add(helpImage);
		panel.add(new Label(" "));
		panel.add(msgLink);
		this.msgId = msgId;
		this.msgTitle = msgTitle;
		helpImage.addClickListener(this);
		msgLink.setText(msgTitle);
		msgLink.addClickListener(this);
		this.provider = provider;
		initWidget(panel);
	}

	public void refresh() {
		provider.updateInformations(msgId, acceptor);
	}

	public void onClick(Widget sender) {
		if (sender == msgLink) {
			popDialog.center();
			popDialog.show();
		} if (sender == helpImage) {
			int left = sender.getAbsoluteLeft() + 10;
			int top = sender.getAbsoluteTop() + 10;
			popFrame.setPopupPosition(left, top);
			popFrame.show();
		}
	}

}
