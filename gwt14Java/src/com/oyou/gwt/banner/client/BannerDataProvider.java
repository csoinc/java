package com.oyou.gwt.banner.client;

/**
 * 
 * @author	Owen Ou
 * @version $Id: BannerDataProvider.java,v 1.1 2008/06/29 14:24:40 oyou Exp $
 * Nov 5, 2007
 */
public interface BannerDataProvider {
	interface MessageAcceptor {
		void accept(IsMessage[] message);
		void failed(Throwable caught);
	}

	void updateMessages(String msgId, MessageAcceptor acceptor);

	void updateAnnouncement(MessageAcceptor acceptor);

}
