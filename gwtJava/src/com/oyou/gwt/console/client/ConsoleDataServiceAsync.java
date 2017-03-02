package com.oyou.gwt.console.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author	Owen Ou
 * @version $Id: ConsoleDataServiceAsync.java,v 1.1 2008/06/29 14:24:28 oyou Exp $
 * @since Nov 26, 2007
 */
public interface ConsoleDataServiceAsync {

	void importPhotos(String photoPath, String albumId, String comment, AsyncCallback callback);

}
