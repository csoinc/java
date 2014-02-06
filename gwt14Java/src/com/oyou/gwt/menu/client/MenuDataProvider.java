package com.oyou.gwt.menu.client;

/**
 * 
 * @author	Owen Ou
 * @version $Id: MenuDataProvider.java,v 1.1 2008/06/29 14:24:36 oyou Exp $
 * @since Nov 14, 2007
 */
public interface MenuDataProvider {

	interface MenuDataAcceptor {
		void acceptMenus(IsMenu[] datas);
		void failed(Throwable caught);
	}

	void getMenus(MenuDataAcceptor acceptor);
	
}
