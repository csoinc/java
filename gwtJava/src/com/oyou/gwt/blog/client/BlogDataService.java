package com.oyou.gwt.blog.client;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * 
 * @author	Owen Ou
 * @version $Id: BlogDataService.java,v 1.1 2008/06/29 14:23:44 oyou Exp $
 * @since Dec 5, 2007
 */
public interface BlogDataService extends RemoteService {

	IsPicture[] getPhotos(String cid, String gid, String uid) throws BlogException;

	IsGroup[] getAlbums(String cid) throws BlogException;

	IsGroupType[] getCategories() throws BlogException;

	IsArticle[] getArticles(String cid, String gid, String uid) throws BlogException;

	IsGroup[] getGroups(String cid) throws BlogException;

	IsComment[] getComments(String mid, String uid) throws BlogException;

	IsErrorCode updatePhoto(String mid, String comment) throws BlogException;

}
