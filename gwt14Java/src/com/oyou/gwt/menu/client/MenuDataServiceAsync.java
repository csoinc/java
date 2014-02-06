package com.oyou.gwt.menu.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author	Owen Ou
 * @version $Id: MenuDataServiceAsync.java,v 1.1 2008/06/29 14:24:37 oyou Exp $
 * Nov 5, 2007
 */
public interface MenuDataServiceAsync {

	void getMenus(AsyncCallback callback);
	
}
