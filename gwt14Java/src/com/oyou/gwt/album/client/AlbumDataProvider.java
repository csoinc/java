package com.oyou.gwt.album.client;

/**
 * 
 * @author	Owen Ou
 * @version $Id: AlbumDataProvider.java,v 1.1 2008/06/29 14:24:24 oyou Exp $
 * @since Nov 14, 2007
 */
public interface AlbumDataProvider {

	interface AlbumDataAcceptor {
		void accept(IsPhoto[] datas);
		void acceptAlbums(IsAlbum[] datas);
		void acceptCategories(IsCategory[] datas);
		void failed(Throwable caught);
	}

	void updatePhotos(String cid, String gid, AlbumDataAcceptor acceptor);

	void getAlbums(String cid, AlbumDataAcceptor acceptor);

	void getCategories(AlbumDataAcceptor acceptor);
	
}
