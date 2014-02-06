package com.oyou.domain.blog;

import java.sql.SQLException;
import java.util.List;


import com.oyou.common.exception.BusinessException;
import com.oyou.common.spring.SpringService;
import com.oyou.domain.blog.orm.BlogGroup;
import com.oyou.domain.blog.orm.BlogGroupType;
import com.oyou.domain.blog.orm.BlogInformation;
import com.oyou.domain.blog.orm.BlogLanguageType;
import com.oyou.domain.blog.orm.BlogMessage;
import com.oyou.domain.blog.orm.BlogMessageType;
import com.oyou.domain.blog.orm.BlogProperties;
import com.oyou.domain.blog.orm.BlogReplyMessage;
import com.oyou.domain.blog.orm.BlogUser;

public interface BlogService extends SpringService {

	public void createBlogGroup(BlogGroup blogGroup, BlogUser blogUser) throws BusinessException;

	public void createBlogMessage(BlogMessage blogMessage) throws BusinessException;

	public void createBlogReplyMessage(BlogReplyMessage blogMessage) throws BusinessException;

	public Object executeSQLScript(String sql) throws SQLException;

	public List<Object> executeHQL(String hql) throws SQLException;

	public BlogGroup getBlogGroupByID(Long id) throws BusinessException;

	public BlogLanguageType getBlogLanguageTypeByID(Long id) throws BusinessException;

	public BlogGroupType getBlogGroupTypeByID(Long id) throws BusinessException;

	public BlogMessage getBlogMessageByID(Long id) throws BusinessException;

	public BlogMessage getBlogMessageByUploadName(String uploadName);

	public BlogMessageType getBlogMessageTypeByID(Long id) throws BusinessException;

	public BlogReplyMessage getBlogReplyMessageByID(Long id) throws BusinessException;

	public BlogReplyMessage getBlogReplyMessageByUploadName(String uploadName);

	public BlogUser getBlogUserByID(Long id) throws BusinessException;

	public BlogInformation getBlogInformationByID(Long id) throws BusinessException;

	public boolean isBlogGroupOwner(Long userId, Long groupId) throws BusinessException;

	public List<Object> listBlogGroups() throws BusinessException;

	public List<Object> listBlogGroupTypes() throws BusinessException;

	public List<Object> listBlogGroupsByGroupTypeID(Long groupTypeId) throws BusinessException;

	public List<Object> listBlogGroupsByUserID(Long userId, Long groupTypeId) throws BusinessException;

	public List<Object> getBlogMessagePhotos(String cid, String gid, String uid) throws BusinessException;

	public List<Object> getBlogReplyMessagePhotos(String cid, String gid, String uid) throws BusinessException;

	public List<Object> getBlogMessageArticles(String cid, String gid, String uid) throws BusinessException;

	public List<Object> getBlogReplyMessageArticles(String cid, String gid, String uid) throws BusinessException;

	public List<Object> getBlogMessageComments(String mid, String uid) throws BusinessException;

	public List<Object> searchBlogMessages(String word, boolean orderByTimes) throws BusinessException;

	public SearchPageList searchBlogMessagePageList(SearchPageList pageList, boolean orderByTimes) throws BusinessException;

	public List<Object> listBlogMessages(Long groupId) throws BusinessException;

	public MessagePageList listBlogMessagePageList(MessagePageList pageList) throws BusinessException;

	public List<Object> listBlogReplyMessages(Long messageId) throws BusinessException;

	public void updateBlogGroup(BlogGroup blogGroup) throws BusinessException;

	public void updateBlogMessage(BlogMessage blogMessage) throws BusinessException;

	public void updateBlogReplyMessage(BlogReplyMessage blogMessage) throws BusinessException;

	public void increaseMessageViewTimes(Long messageId) throws BusinessException;

	public void increaseMessageUpdateTimes(Long messageId) throws BusinessException;

	public void increaseReplyMessageViewTimes(Long replyMessageId) throws BusinessException;

	public void increaseReplyMessageUpdateTimes(Long replyMessageId) throws BusinessException;

	public BlogProperties getBlogProperties(String key);

	public List<BlogMessage> filterBlogMessages(List<Object> messages, BlogUser user) throws BusinessException;

	public List<BlogReplyMessage> filterBlogReplyMessages(List<Object> messages, BlogUser user) throws BusinessException;
	
}
