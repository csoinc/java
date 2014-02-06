package com.oyou.gwt.menu.client;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * 
 * @author	Owen Ou
 * @version $Id: MenuDataService.java,v 1.1 2008/06/29 14:24:36 oyou Exp $
 * Nov 5, 2007
 */
public interface MenuDataService extends RemoteService {

	IsMenu[] getMenus();
	
}
