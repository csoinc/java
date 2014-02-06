package com.oyou.gwt.banner.client;


import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DisclosureEvent;
import com.google.gwt.user.client.ui.DisclosureHandler;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.oyou.gwt.banner.client.BannerDataProvider.MessageAcceptor;

/**
 * 
 * @author	Owen Ou
 * @version $Id: BannerWidget.java,v 1.1 2008/06/29 14:24:40 oyou Exp $
 * Nov 5, 2007
 */
public class BannerWidget extends Composite implements DisclosureHandler {
	/**
	 * A dialog box for displaying an error.
	 */
	private static class ErrorDialog extends DialogBox implements ClickListener {
		private HTML body = new HTML("");

		public ErrorDialog() {
			setStylePrimaryName("Banner-ErrorDialog");
			Button closeButton = new Button("Close", this);
			VerticalPanel panel = new VerticalPanel();
			panel.setSpacing(4);
			panel.add(body);
			panel.add(closeButton);
			panel.setCellHorizontalAlignment(closeButton, VerticalPanel.ALIGN_CENTER);
			setWidget(panel);
		}

		public String getBody() {
			return body.getHTML();
		}

		public void onClick(Widget sender) {
			hide();
		}

		public void setBody(String html) {
			body.setHTML(html);
		}
	}

	private class MessageAcceptorImpl implements MessageAcceptor {
		public void accept(IsMessage[] datas) {
			String data = "";
			for (int i = 0; i < datas.length; i++) {
				data += datas[i].getMessage();
			}
			HTML content = new HTML(data);
			panel.setContent(content);
		}

		public void failed(Throwable caught) {
			if (errorDialog == null) {
				errorDialog = new ErrorDialog();
			}
			if (caught instanceof InvocationException) {
				errorDialog.setText("The RPC server");
				errorDialog.setBody(NO_CONNECTION_MESSAGE);
			} else {
				errorDialog.setText("Unexcepted Error processing remote call");
				errorDialog.setBody(caught.getMessage());
			}
			errorDialog.center();
		}
	}

	private static final String NO_CONNECTION_MESSAGE = "The RPC server is not available right now, please try later......";
	private final MessageAcceptor acceptor = new MessageAcceptorImpl();
	private final DisclosurePanel panel = new DisclosurePanel("", false);
	private final BannerDataProvider provider;
	private ErrorDialog errorDialog = null;
	private String msgId; 
	private String msgTitle; 

	public BannerWidget(BannerDataProvider provider, String msgId, String msgTitle) {
		this.msgId = msgId;
		this.msgTitle = msgTitle;
		panel.getHeaderTextAccessor().setText(msgTitle);
		panel.addEventHandler(this);
		this.provider = provider;
		initWidget(panel);
	}

	public void refresh() {
		provider.updateMessages(msgId, acceptor);
	}

	public void onOpen(DisclosureEvent event) {
		panel.getHeaderTextAccessor().setText("Close " + msgTitle);
	}

	public void onClose(DisclosureEvent event) {
		panel.getHeaderTextAccessor().setText(msgTitle);
	}

	
}
