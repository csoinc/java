package com.oyou.gwt.popup.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;

/**
 * 
 * @author	Owen Ou
 * @version $Id: PopupInformationWidget.java,v 1.1 2008/06/29 14:23:37 oyou Exp $
 * Nov 5, 2007
 */
public class PopupInformationWidget extends Composite {
	private static boolean DEBUG = false;

	public class InformationProvider implements PopupDataProvider {
		private final PopupInformationServiceAsync calService;

		private Information[] informations;

		public InformationProvider() {
			calService = (PopupInformationServiceAsync) GWT.create(PopupInformationService.class);
			ServiceDefTarget target = (ServiceDefTarget) calService;
			String moduleRelativeURL = GWT.getModuleBaseURL();
			if (moduleRelativeURL != null && !"".equals(moduleRelativeURL)) {
				if (DEBUG) {
					moduleRelativeURL = "http://localhost:8080/blogger2/blogajax"; //Debug
				} else {
					moduleRelativeURL += "../../blogajax"; //JSP - OYOSOFT
				}
			} else {
				if (DEBUG) {
					moduleRelativeURL = "http://localhost:8080/blogger2/blogajax"; //Debug
				} else {
					moduleRelativeURL += "../../blogajax"; //JSP - OYOSOFT
				}
			}
			//Window.alert(moduleRelativeURL);
			target.setServiceEntryPoint(moduleRelativeURL);
		}

		public void updateInformations(String infoId, final InformationAcceptor acceptor) {
			calService.getInformations(infoId, new AsyncCallback() {
				public void onFailure(Throwable caught) {
					acceptor.failed(caught);
				}

				public void onSuccess(Object result) {
					informations = (Information[]) result;
					pushResults(acceptor, informations);
				}
			});
		}

		private void pushResults(InformationAcceptor acceptor, Information[] informations) {
			String[] results = new String[informations.length];
			for (int i = 0; i < informations.length; i++) {
				Information info = informations[i];
				results[i] = info.getInformation();
			}
			acceptor.accept(results);
		}
	}

	private final InformationProvider infoProvider = new InformationProvider();

	private final PopupWidget popup;

	public PopupInformationWidget() {
		this("0", "Popup");
	}

	public PopupInformationWidget(String infoId, String infoTitle) {
		popup = new PopupWidget(infoProvider, infoId, infoTitle);
		initWidget(popup);
	}

	protected void onLoad() {
		popup.refresh();
	}

}
