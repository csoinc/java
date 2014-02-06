package com.oyou.gwt.blog.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author	Owen Ou
 * @version $Id: BlogDataServiceAsync.java,v 1.1 2008/06/29 14:23:44 oyou Exp $
 * @since Dec 5, 2007
 */
public interface BlogDataServiceAsync {

	void getPhotos(String cid, String gid, String uid, AsyncCallback callback);

	void getAlbums(String cid, AsyncCallback callback);

	void getCategories(AsyncCallback callback);

	void getArticles(String cid, String gid, String uid, AsyncCallback callback);

	void getGroups(String cid, AsyncCallback callback);

	void getComments(String mid, String uid, AsyncCallback callback);

	void updatePhoto(String mid, String comment, AsyncCallback callback);

}
