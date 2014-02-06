package com.oyou.domain.blog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.common.exception.BusinessException;
import com.oyou.common.spring.SpringServiceImpl;
import com.oyou.common.util.StringHelper;
import com.oyou.domain.blog.orm.BlogGroup;
import com.oyou.domain.blog.orm.BlogGroupType;
import com.oyou.domain.blog.orm.BlogInformation;
import com.oyou.domain.blog.orm.BlogLanguageType;
import com.oyou.domain.blog.orm.BlogMessage;
import com.oyou.domain.blog.orm.BlogMessageStatistic;
import com.oyou.domain.blog.orm.BlogMessageType;
import com.oyou.domain.blog.orm.BlogProperties;
import com.oyou.domain.blog.orm.BlogReplyMessage;
import com.oyou.domain.blog.orm.BlogReplyMessageStatistic;
import com.oyou.domain.blog.orm.BlogUser;
import com.oyou.domain.blog.orm.BlogUserGroup;
import com.oyou.domain.blog.orm.BlogUserType;

public class BlogServiceImpl extends SpringServiceImpl implements BlogService {
	private static final Log log = LogFactory.getLog(BlogServiceImpl.class);

	private BlogDAO blogDAO;

	public void createBlogGroup(BlogGroup blogGroup, BlogUser blogUser) throws BusinessException {
		log.debug("==createBlogGroup()");
		BusinessException be = new BusinessException();
		blogDAO.insert(blogGroup);
		BlogUserGroup userGroup = new BlogUserGroup();
		userGroup.setBlogGroup(blogGroup);
		userGroup.setBlogUser(blogUser);
		userGroup.setCreateTime(blogGroup.getCreateTime());
		userGroup.setUpdateTime(blogGroup.getUpdateTime());
		userGroup.setStatus(true);
		userGroup.setGroupOwner(true);
		blogDAO.saveOrUpdate(userGroup);
		if (be.hasErrors()) {
			throw be;
		}
	}

	public void createBlogMessage(BlogMessage blogMessage) throws BusinessException {
		log.debug("==createBlogMessage()");
		BusinessException be = new BusinessException();
		if (StringHelper.isEmpty(blogMessage.getMessage())) {
			blogMessage.setMessage(BlogMessage.MESSAGE_DEFAULT_VALUE);
		}
		blogDAO.insert(blogMessage);
		if (be.hasErrors()) {
			throw be;
		}
	}

	public void createBlogReplyMessage(BlogReplyMessage blogMessage) throws BusinessException {
		log.debug("==createBlogMessage()");
		BusinessException be = new BusinessException();
		if (StringHelper.isEmpty(blogMessage.getMessage())) {
			blogMessage.setMessage(BlogReplyMessage.MESSAGE_DEFAULT_VALUE);
		}
		blogDAO.insert(blogMessage);
		if (be.hasErrors()) {
			throw be;
		}
	}

	public Object executeSQLScript(String sql) throws SQLException {
		return blogDAO.executeSQL(sql);
	}

	public List<Object> executeHQL(String hql) throws SQLException {
		return blogDAO.executeHQL(hql);
	}

	public BlogDAO getBlogDAO() {
		return blogDAO;
	}

	public BlogGroup getBlogGroupByID(Long id) throws BusinessException {
		return blogDAO.getBlogGroupByID(id);
	}

	public BlogLanguageType getBlogLanguageTypeByID(Long id) throws BusinessException {
		return blogDAO.getBlogLanguageTypeByID(id);
	}

	public BlogGroupType getBlogGroupTypeByID(Long id) throws BusinessException {
		return blogDAO.getBlogGroupTypeByID(id);
	}

	public BlogMessage getBlogMessageByID(Long id) throws BusinessException {
		return blogDAO.getBlogMessageByID(id);
	}

	public BlogMessage getBlogMessageByUploadName(String uploadName) {
		return blogDAO.getBlogMessageByUploadName(uploadName);
	}

	public BlogMessageType getBlogMessageTypeByID(Long id) throws BusinessException {
		return blogDAO.getBlogMessageTypeByID(id);
	}

	public BlogReplyMessage getBlogReplyMessageByID(Long id) throws BusinessException {
		return blogDAO.getBlogReplyMessageByID(id);
	}

	public BlogReplyMessage getBlogReplyMessageByUploadName(String uploadName) {
		return blogDAO.getBlogReplyMessageByUploadName(uploadName);
	}

	public BlogUser getBlogUserByID(Long id) throws BusinessException {
		return blogDAO.getBlogUserByID(id);
	}

	public BlogInformation getBlogInformationByID(Long id) throws BusinessException {
		return blogDAO.getBlogInformationByID(id);
	}

	public boolean isBlogGroupOwner(Long userId, Long groupId) throws BusinessException {
		BlogUserGroup blogUserGroup = blogDAO.getBlogUserGroupByUserIDAndGroupID(userId, groupId);
		if (blogUserGroup != null)
			return blogUserGroup.isGroupOwner();
		return false;
	}

	public List<Object> listBlogGroups() throws BusinessException {
		log.debug("==listBlogGroups()");
		return blogDAO.getBlogGroups();
	}

	public List<Object> listBlogGroupTypes() throws BusinessException {
		log.debug("==listBlogGroupTypes()");
		return blogDAO.getBlogGroupTypes();
	}

	public List<Object> listBlogGroupsByGroupTypeID(Long groupTypeId) throws BusinessException {
		return blogDAO.getBlogGroupsByGroupTypeID(groupTypeId);
	}

	public List<Object> listBlogGroupsByUserID(Long userId, Long groupTypeId) throws BusinessException {
		return blogDAO.getBlogGroupsByUserID(userId, groupTypeId);
	}

	public List<Object> getBlogMessagePhotos(String cid, String gid, String uid) throws BusinessException {
		log.debug("==getBlogMessagePhotos()");
		return blogDAO.getBlogMessagePhotos(cid, gid, uid);
	}

	public List<Object> getBlogReplyMessagePhotos(String cid, String gid, String uid) throws BusinessException {
		log.debug("==getBlogReplyMessagePhotos()");
		return blogDAO.getBlogReplyMessagePhotos(cid, gid, uid);
	}

	public List<Object> getBlogMessageArticles(String cid, String gid, String uid) throws BusinessException {
		log.debug("==getBlogMessageArticles()");
		return blogDAO.getBlogMessageArticles(cid, gid, uid);
	}

	public List<Object> getBlogReplyMessageArticles(String cid, String gid, String uid) throws BusinessException {
		log.debug("==getBlogReplyMessageArticles()");
		return blogDAO.getBlogReplyMessageArticles(cid, gid, uid);
	}

	public List<Object> getBlogMessageComments(String mid, String uid) throws BusinessException {
		log.debug("==getBlogMessageComments()");
		return blogDAO.getBlogMessageComments(mid, uid);
	}

	public List<Object> searchBlogMessages(String word, boolean orderByTimes) throws BusinessException {
		log.debug("==searchBlogMessages()");
		return blogDAO.searchBlogMessages(word, orderByTimes);
	}

	public SearchPageList searchBlogMessagePageList(SearchPageList pageList, boolean orderByTimes) throws BusinessException {
		log.debug("==searchBlogMessagePageList()");
		return blogDAO.searchBlogMessagePageList(pageList, orderByTimes);
	}

	public List<Object> listBlogMessages(Long groupId) throws BusinessException {
		log.debug("==listBlogMessages()");
		return blogDAO.getBlogMessagesByGroupID(groupId);
	}

	public MessagePageList listBlogMessagePageList(MessagePageList pageList) throws BusinessException {
		log.debug("==listBlogMessagePageList()");
		return blogDAO.getBlogMessagePageList(pageList);
	}

	public List<Object> listBlogReplyMessages(Long messageId) throws BusinessException {
		log.debug("==listBlogReplyMessages()");
		return blogDAO.getBlogReplyMessagesByMessageID(messageId);
	}

	public void setBlogDAO(BlogDAO blogDAO) {
		this.blogDAO = blogDAO;
	}

	public void updateBlogGroup(BlogGroup blogGroup) throws BusinessException {
		log.debug("==updateBlogGroup()");
		BusinessException be = new BusinessException();
		blogDAO.update(blogGroup);
		if (be.hasErrors()) {
			throw be;
		}
	}

	public void updateBlogMessage(BlogMessage blogMessage) throws BusinessException {
		log.debug("==updateBlogMessage()");
		BusinessException be = new BusinessException();
		if (StringHelper.isEmpty(blogMessage.getMessage())) {
			blogMessage.setMessage(BlogMessage.MESSAGE_DEFAULT_VALUE);
		}
		blogDAO.update(blogMessage);
		if (be.hasErrors()) {
			throw be;
		}
	}

	public void updateBlogReplyMessage(BlogReplyMessage blogMessage) throws BusinessException {
		log.debug("==updateBlogReplyMessage()");
		BusinessException be = new BusinessException();
		if (StringHelper.isEmpty(blogMessage.getMessage())) {
			blogMessage.setMessage(BlogReplyMessage.MESSAGE_DEFAULT_VALUE);
		}
		blogDAO.update(blogMessage);
		if (be.hasErrors()) {
			throw be;
		}
	}

	public void increaseMessageViewTimes(Long messageId) throws BusinessException {
		BlogMessage blogMessage = blogDAO.getBlogMessageByID(messageId);
		if (blogMessage != null) {
			BlogMessageStatistic statistic = blogMessage.getBlogMessageStatistic();
			if (statistic == null) {
				statistic = new BlogMessageStatistic();
				statistic.setStatisticId(blogMessage.getId());
				statistic.setViewTimes(1);
				statistic.setUpdateTimes(0);
				statistic.setStatus(true);
			} else {
				statistic.setViewTimes(statistic.getViewTimes() + 1);
			}
			blogDAO.saveOrUpdate(statistic);
			blogMessage.setBlogMessageStatistic(statistic);
		}
	}

	public void increaseMessageUpdateTimes(Long messageId) throws BusinessException {
		BlogMessage blogMessage = blogDAO.getBlogMessageByID(messageId);
		if (blogMessage != null) {
			BlogMessageStatistic statistic = blogMessage.getBlogMessageStatistic();
			if (statistic == null) {
				statistic = new BlogMessageStatistic();
				statistic.setStatisticId(blogMessage.getId());
				statistic.setViewTimes(0);
				statistic.setUpdateTimes(1);
				statistic.setStatus(true);
			} else {
				statistic.setUpdateTimes(statistic.getUpdateTimes() + 1);
			}
			blogDAO.saveOrUpdate(statistic);
			blogMessage.setBlogMessageStatistic(statistic);
		}
	}

	public void increaseReplyMessageViewTimes(Long replyMessageId) throws BusinessException {
		BlogReplyMessage blogMessage = blogDAO.getBlogReplyMessageByID(replyMessageId);
		if (blogMessage != null) {
			BlogReplyMessageStatistic statistic = blogMessage.getBlogReplyMessageStatistic();
			if (statistic == null) {
				statistic = new BlogReplyMessageStatistic();
				statistic.setStatisticId(blogMessage.getId());
				statistic.setViewTimes(1);
				statistic.setUpdateTimes(0);
				statistic.setStatus(true);
			} else {
				statistic.setViewTimes(statistic.getViewTimes() + 1);
			}
			blogDAO.saveOrUpdate(statistic);
			blogMessage.setBlogReplyMessageStatistic(statistic);
		}
	}

	public void increaseReplyMessageUpdateTimes(Long replyMessageId) throws BusinessException {
		BlogReplyMessage blogMessage = blogDAO.getBlogReplyMessageByID(replyMessageId);
		if (blogMessage != null) {
			BlogReplyMessageStatistic statistic = blogMessage.getBlogReplyMessageStatistic();
			if (statistic == null) {
				statistic = new BlogReplyMessageStatistic();
				statistic.setStatisticId(blogMessage.getId());
				statistic.setViewTimes(0);
				statistic.setUpdateTimes(1);
				statistic.setStatus(true);
			} else {
				statistic.setUpdateTimes(statistic.getUpdateTimes() + 1);
			}
			blogDAO.saveOrUpdate(statistic);
			blogMessage.setBlogReplyMessageStatistic(statistic);
		}
	}

	public BlogProperties getBlogProperties(String key) {
		return blogDAO.getBlogProperties(key);
	}

	public List<BlogMessage> filterBlogMessages(List<Object> messages, BlogUser user) throws BusinessException {
		return blogDAO.filterBlogMessages(messages, user);
	}

	public List<BlogReplyMessage> filterBlogReplyMessages(List<Object> messages, BlogUser user) throws BusinessException {
		return blogDAO.filterBlogReplyMessages(messages, user);
	}
	
}
