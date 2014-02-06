package com.oyou.gwt.banner.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;

/**
 * 
 * @author	Owen Ou
 * @version $Id: BannerMessageWidget.java,v 1.1 2008/06/29 14:24:39 oyou Exp $
 * Nov 5, 2007
 */
public class BannerMessageWidget extends Composite {
	private static boolean DEBUG = false;

	public class MessageProvider implements BannerDataProvider {
		private final BannerMessageServiceAsync calService;
		//private IsMessage[] messages;
		public MessageProvider() {
			calService = (BannerMessageServiceAsync) GWT.create(BannerMessageService.class);
			ServiceDefTarget target = (ServiceDefTarget) calService;
			String moduleRelativeURL = GWT.getModuleBaseURL();
			//Window.alert(moduleRelativeURL); 
			if (moduleRelativeURL != null && !"".equals(moduleRelativeURL)) {
				if (DEBUG)
					moduleRelativeURL = "http://localhost:8080/blogger2/blogajax"; //Debug
				else 
					moduleRelativeURL += "../../blogajax"; //JSP 
			} else {
				if (DEBUG)
					moduleRelativeURL = "http://localhost:8080/blogger2/blogajax"; //Debug
				else 
					moduleRelativeURL += "../../blogajax"; //JSP 
			}
			target.setServiceEntryPoint(moduleRelativeURL);
		}

		public void updateMessages(String msgId, final MessageAcceptor acceptor) {
			calService.getMessages(msgId, new AsyncCallback() {
				public void onFailure(Throwable caught) {
					acceptor.failed(caught);
				}

				public void onSuccess(Object result) {
					IsMessage[] messages = (IsMessage[]) result;
					pushResults(acceptor, messages);
				}
			});
		}

		public void updateAnnouncement(final MessageAcceptor acceptor) {
			calService.getAnnouncement(new AsyncCallback() {
				public void onFailure(Throwable caught) {
					acceptor.failed(caught);
				}

				public void onSuccess(Object result) {
					IsMessage[] messages = (IsMessage[]) result;
					pushResults(acceptor, messages);
				}
			});
		}

		private void pushResults(MessageAcceptor acceptor, IsMessage[] messages) {
//			String[] results = new String[messages.length];
//			for (int i = 0; i < messages.length; i++) {
//				IsMessage message = messages[i];
//				results[i] = message.getMessage();
//			}
			acceptor.accept(messages);
		}
	}
	
	private final MessageProvider messageProvider = new MessageProvider();
	private final BannerWidget banner;

	public BannerMessageWidget() {
		this("0", "Banner");
	}
	
	public BannerMessageWidget(String msgId, String msgTitle) {
		banner = new BannerWidget(messageProvider, msgId, msgTitle);
		initWidget(banner);
	}

	protected void onLoad() {
		banner.refresh();
	}
	
}
