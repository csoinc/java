package com.oyou.gwt.blog.client;

/**
 * 
 * @author	Owen Ou
 * @version $Id: BlogDataProvider.java,v 1.1 2008/06/29 14:23:44 oyou Exp $
 * @since Dec 5, 2007
 */
public interface BlogDataProvider {

	interface BlogDataAcceptor {
		void acceptPhotos(IsPicture[] datas);
		void acceptAlbums(IsGroup[] datas);
		void acceptCategories(IsGroupType[] datas);
		void acceptGroups(IsGroup[] datas);
		void acceptArticles(IsArticle[] datas);
		void acceptComments(IsComment[] datas);
		void acceptErrorCode(IsErrorCode data);
		void failed(Throwable caught);
	}

	void updatePhotos(String cid, String gid, String uid, BlogDataAcceptor acceptor);

	void updateArticles(String cid, String gid, String uid, BlogDataAcceptor acceptor);

	void getAlbums(String cid, BlogDataAcceptor acceptor);

	void getGroups(String cid, BlogDataAcceptor acceptor);

	void getCategories(BlogDataAcceptor acceptor);

	void updatePhoto(String mid, String comment, BlogDataAcceptor acceptor);
	
}
