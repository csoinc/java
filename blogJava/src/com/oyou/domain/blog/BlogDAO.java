package com.oyou.domain.blog;

import java.io.Serializable;
import java.util.List;


import com.oyou.common.domain.PageList;
import com.oyou.common.exception.BusinessException;
import com.oyou.common.hibernate.dao.CommonDAO;
import com.oyou.domain.blog.orm.BlogGroup;
import com.oyou.domain.blog.orm.BlogGroupType;
import com.oyou.domain.blog.orm.BlogInformation;
import com.oyou.domain.blog.orm.BlogLanguageType;
import com.oyou.domain.blog.orm.BlogMessage;
import com.oyou.domain.blog.orm.BlogMessageType;
import com.oyou.domain.blog.orm.BlogProperties;
import com.oyou.domain.blog.orm.BlogReplyMessage;
import com.oyou.domain.blog.orm.BlogUser;
import com.oyou.domain.blog.orm.BlogUserGroup;

public interface BlogDAO extends CommonDAO {

	public BlogGroup getBlogGroupByID(Serializable id);

	public List<Object> getBlogGroups();

	public List<Object> getBlogGroupTypes();

	public List<Object> getBlogGroupsByGroupTypeID(Long groupTypeId);

	public List<Object> getBlogGroupsByUserID(Long userId, Long groupTypeId);

	public BlogLanguageType getBlogLanguageTypeByID(Serializable id);

	public BlogGroupType getBlogGroupTypeByID(Serializable id);

	public BlogMessage getBlogMessageByID(Serializable id);

	public BlogMessage getBlogMessageByUploadName(String uploadName);

	public List<Object> getBlogMessagePhotos(String cid, String gid, String uid);

	public List<Object> getBlogReplyMessagePhotos(String cid, String gid, String uid);

	public List<Object> getBlogMessageArticles(String cid, String gid, String uid);

	public List<Object> getBlogReplyMessageArticles(String cid, String gid, String uid);

	public List<Object> getBlogMessageComments(String mid, String uid);

	public List<Object> searchBlogMessages(String word, boolean orderByTimes);

	public SearchPageList searchBlogMessagePageList(SearchPageList pageList, boolean orderByTimes);

	public List<Object> getBlogMessagesByGroupID(Long groupId);

	public MessagePageList getBlogMessagePageList(MessagePageList pageList);

	public List<Object> getBlogMessagesByUserID(Long userId);

	public BlogMessageType getBlogMessageTypeByID(Serializable id);

	public BlogReplyMessage getBlogReplyMessageByID(Serializable id);

	public BlogReplyMessage getBlogReplyMessageByUploadName(String uploadName);

	public List<Object> getBlogReplyMessagesByMessageID(Long messageId);

	public List<Object> getBlogReplyMessagesByUserID(Long userId);

	public BlogUser getBlogUserByID(Serializable id);

	public BlogInformation getBlogInformationByID(Serializable id);

	public BlogUserGroup getBlogUserGroupByUserIDAndGroupID(Long userId, Long groupId);

	public List<Object> getBlogUsersByGroupID(Long groupId);

	public List<Object> getBlogGroupsByUserID(Long userId);
	
	public BlogProperties getBlogProperties(String key);

	public List<BlogMessage> filterBlogMessages(List<Object> messages, BlogUser user);

	public List<BlogReplyMessage> filterBlogReplyMessages(List<Object> messages, BlogUser user);

	public List<BlogMessage> getMessagePageList(List<BlogMessage> list, MessagePageList pageList);

	public List<BlogMessage> getSearchPageList(List<BlogMessage> list, SearchPageList pageList);
	
}
