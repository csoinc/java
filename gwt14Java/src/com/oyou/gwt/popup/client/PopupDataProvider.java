package com.oyou.gwt.popup.client;

/**
 * 
 * @author	Owen Ou
 * @version $Id: PopupDataProvider.java,v 1.1 2008/06/29 14:23:37 oyou Exp $
 * Nov 5, 2007
 */
public interface PopupDataProvider {
	interface InformationAcceptor {
		void accept(String[] information);

		void failed(Throwable caught);
	}

	void updateInformations(String infoId, InformationAcceptor acceptor);

}
