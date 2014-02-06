package com.oyou.gwt.album.client;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * 
 * @author	Owen Ou
 * @version $Id: AlbumDataService.java,v 1.1 2008/06/29 14:24:24 oyou Exp $
 * Nov 5, 2007
 */
public interface AlbumDataService extends RemoteService {

	IsPhoto[] getPhotos(String cid, String gid, String uid) throws AlbumException;

	IsAlbum[] getAlbums(String cid) throws AlbumException;

	IsCategory[] getCategories() throws AlbumException;
	
}
