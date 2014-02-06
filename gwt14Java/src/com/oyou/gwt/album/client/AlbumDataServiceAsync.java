package com.oyou.gwt.album.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author	Owen Ou
 * @version $Id: AlbumDataServiceAsync.java,v 1.1 2008/06/29 14:24:23 oyou Exp $
 * Nov 5, 2007
 */
public interface AlbumDataServiceAsync {

	void getPhotos(String cid, String gid, String uid, AsyncCallback callback);

	void getAlbums(String cid, AsyncCallback callback);

	void getCategories(AsyncCallback callback);
	
}
