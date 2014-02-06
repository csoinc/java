package com.oyou.gwt.console.client;

/**
 * 
 * @author	Owen Ou
 * @version $Id: ConsoleDataProvider.java,v 1.1 2008/06/29 14:24:28 oyou Exp $
 * @since Nov 26, 2007
 */
public interface ConsoleDataProvider {

	interface ConsoleDataAcceptor {
		void accept(IsError data);
		void failed(Throwable caught);
	}

	void importPhotos(String photoPath, String albumId, String comment, ConsoleDataAcceptor acceptor);

}
