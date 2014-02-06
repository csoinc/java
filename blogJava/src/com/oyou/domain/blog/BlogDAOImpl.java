package com.oyou.domain.blog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;

import com.oyou.common.hibernate.dao.CommonDAOImpl;
import com.oyou.common.hibernate.util.SessionConstants;
import com.oyou.common.util.StringHelper;
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
import com.oyou.domain.blog.orm.BlogUserType;

/**
 * 
 * @author Owen Ou May 8, 2007
 */
public class BlogDAOImpl extends CommonDAOImpl implements BlogDAO {
	private static final Log log = LogFactory.getLog(BlogDAOImpl.class);

	public BlogGroup getBlogGroupByID(Serializable id) {
		return (BlogGroup) load(BlogGroup.class, id);
	}

	public List<Object> getBlogGroups() {
		StringBuffer sb = new StringBuffer();
		sb.append("select bg from BlogGroup as bg");
		sb.append(" order by size(bg.blogMessages) desc, bg.updateTime desc");
		Query query = getSession().createQuery(sb.toString());
		return executeHQ(query);
	}

	public List<Object> getBlogGroupTypes() {
		StringBuffer sb = new StringBuffer();
		sb.append("select bgt from BlogGroupType as bgt");
		sb.append(" order by bgt.id");
		Query query = getSession().createQuery(sb.toString());
		
		return executeHQ(query);
	}

	public List<Object> getBlogGroupsByGroupTypeID(Long groupTypeId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bg from BlogGroup as bg");
		sb.append(" where bg.blogGroupType.id = :groupTypeId or bg.blogGroupType.id = 0");
		sb.append(" order by size(bg.blogMessages) desc, bg.updateTime desc");
		Query query = getSession().createQuery(sb.toString());
		query.setLong("groupTypeId", groupTypeId.longValue());
		return executeHQ(query);
	}

	public List<Object> getBlogGroupsByUserID(Long userId, Long groupTypeId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bug.blogGroup from BlogUserGroup as bug");
		sb.append(" where bug.blogUser.id = :userId");
		sb.append(" and bug.blogGroup.blogGroupType.id = :groupTypeId");
		Query query = getSession().createQuery(sb.toString());
		query.setLong("userId", userId.longValue());
		query.setLong("groupTypeId", groupTypeId.longValue());
		return executeHQ(query);
	}

	public BlogLanguageType getBlogLanguageTypeByID(Serializable id) {
		return (BlogLanguageType) load(BlogLanguageType.class, id);
	}

	public BlogGroupType getBlogGroupTypeByID(Serializable id) {
		return (BlogGroupType) load(BlogGroupType.class, id);
	}

	public BlogMessage getBlogMessageByID(Serializable id) {
		BlogMessage bm = (BlogMessage) load(BlogMessage.class, id);
		Hibernate.initialize(bm.getBlogGroup());
		Hibernate.initialize(bm.getBlogUser());
		return bm;
	}

	public BlogMessage getBlogMessageByUploadName(String uploadName) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bm from BlogMessage as bm");
		sb.append(" where bm.uploadFile = :uploadName");
		Query query = getSession().createQuery(sb.toString());
		query.setString("uploadName", uploadName);
		return (BlogMessage) executeHQObject(query);
	}

	public List<Object> getBlogMessagePhotos(String cid, String gid, String uid) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bm from BlogMessage as bm inner join fetch bm.blogGroup inner join fetch bm.blogGroup.blogGroupType");
		sb.append(" where bm.blogMessageType.id = " + BlogMessageType.IMAGE);
		if (StringHelper.isNotEmpty(gid)) {
			sb.append(" and bm.blogGroup.id = :gid");
		}
		if (StringHelper.isNotEmpty(cid)) {
			sb.append(" and bm.blogGroup.blogGroupType.id = :cid");
		}
		if (StringHelper.isNotEmpty(uid)) {
			sb.append(" and bm.blogUser.id = :uid");
		}
		sb.append(" order by bm.updateTime desc");
		Query query = getSession().createQuery(sb.toString());
		if (StringHelper.isNotEmpty(gid)) {
			query.setLong("gid", Long.parseLong(gid));
		}
		if (StringHelper.isNotEmpty(cid)) {
			query.setLong("cid", Long.parseLong(cid));
		}
		if (StringHelper.isNotEmpty(uid)) {
			query.setLong("uid", Long.parseLong(uid));
		}
		query.setCacheable(true);
		return executeHQ(query, SessionConstants.HIBERNATE_QUERY_MAX_ROWS);
	}

	public List<Object> getBlogReplyMessagePhotos(String cid, String gid, String uid) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bm from BlogReplyMessage as bm inner join fetch bm.blogMessage.blogGroup inner join fetch bm.blogMessage.blogGroup.blogGroupType");
		sb.append(" where bm.blogMessageType.id = " + BlogMessageType.IMAGE);
		if (StringHelper.isNotEmpty(gid)) {
			sb.append(" and bm.blogMessage.blogGroup.id = :gid");
		}
		if (StringHelper.isNotEmpty(cid)) {
			sb.append(" and bm.blogMessage.blogGroup.blogGroupType.id = :cid");
		}
		if (StringHelper.isNotEmpty(uid)) {
			sb.append(" and bm.blogUser.id = :uid");
		}
		sb.append(" order by bm.updateTime desc");
		Query query = getSession().createQuery(sb.toString());
		if (StringHelper.isNotEmpty(gid)) {
			query.setLong("gid", Long.parseLong(gid));
		}
		if (StringHelper.isNotEmpty(cid)) {
			query.setLong("cid", Long.parseLong(cid));
		}
		if (StringHelper.isNotEmpty(uid)) {
			query.setLong("uid", Long.parseLong(uid));
		}
		query.setCacheable(true);
		return executeHQ(query, SessionConstants.HIBERNATE_QUERY_MAX_ROWS);
	}

	public List<Object> getBlogMessageArticles(String cid, String gid, String uid) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bm from BlogMessage as bm inner join fetch bm.blogGroup inner join fetch bm.blogGroup.blogGroupType");
		sb.append(" where bm.blogMessageType.id != " + BlogMessageType.IMAGE);
		if (StringHelper.isNotEmpty(gid)) {
			sb.append(" and bm.blogGroup.id = :gid");
		}
		if (StringHelper.isNotEmpty(cid)) {
			sb.append(" and bm.blogGroup.blogGroupType.id = :cid");
		}
		if (StringHelper.isNotEmpty(uid)) {
			sb.append(" and bm.blogUser.id = :uid");
		}
		sb.append(" order by bm.updateTime desc");
		Query query = getSession().createQuery(sb.toString());
		if (StringHelper.isNotEmpty(gid)) {
			query.setLong("gid", Long.parseLong(gid));
		}
		if (StringHelper.isNotEmpty(cid)) {
			query.setLong("cid", Long.parseLong(cid));
		}
		if (StringHelper.isNotEmpty(uid)) {
			query.setLong("uid", Long.parseLong(uid));
		}
		query.setCacheable(true);
		return executeHQ(query, SessionConstants.HIBERNATE_QUERY_MAX_ROWS);
	}

	public List<Object> getBlogReplyMessageArticles(String cid, String gid, String uid) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bm from BlogReplyMessage as bm inner join fetch bm.blogMessage.blogGroup inner join fetch bm.blogMessage.blogGroup.blogGroupType");
		sb.append(" where bm.blogMessageType.id != " + BlogMessageType.IMAGE);
		if (StringHelper.isNotEmpty(gid)) {
			sb.append(" and bm.blogMessage.blogGroup.id = :gid");
		}
		if (StringHelper.isNotEmpty(cid)) {
			sb.append(" and bm.blogMessage.blogGroup.blogGroupType.id = :cid");
		}
		if (StringHelper.isNotEmpty(uid)) {
			sb.append(" and bm.blogUser.id = :uid");
		}
		sb.append(" order by bm.updateTime desc");
		Query query = getSession().createQuery(sb.toString());
		if (StringHelper.isNotEmpty(gid)) {
			query.setLong("gid", Long.parseLong(gid));
		}
		if (StringHelper.isNotEmpty(cid)) {
			query.setLong("cid", Long.parseLong(cid));
		}
		if (StringHelper.isNotEmpty(uid)) {
			query.setLong("uid", Long.parseLong(uid));
		}
		query.setCacheable(true);
		return executeHQ(query, SessionConstants.HIBERNATE_QUERY_MAX_ROWS);
	}

	public List<Object> getBlogMessageComments(String mid, String uid) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bm from BlogReplyMessage as bm");
		sb.append(" where bm.blogMessageType.id != " + BlogMessageType.IMAGE);
		if (StringHelper.isNotEmpty(mid)) {
			sb.append(" and bm.blogMessage.id = :mid");
		}
		if (StringHelper.isNotEmpty(uid)) {
			sb.append(" and bm.blogUser.id = :uid");
		}
		sb.append(" order by bm.updateTime desc");
		Query query = getSession().createQuery(sb.toString());
		if (StringHelper.isNotEmpty(mid)) {
			query.setLong("mid", Long.parseLong(mid));
		}
		if (StringHelper.isNotEmpty(uid)) {
			query.setLong("uid", Long.parseLong(uid));
		}
		query.setCacheable(true);
		return executeHQ(query, SessionConstants.HIBERNATE_QUERY_MAX_ROWS);
	}
	
	public List<Object> searchBlogMessages(String word, boolean orderByTimes) {
		log.debug("==search " + word);
		StringBuffer sb = new StringBuffer();
		if (StringHelper.isNotEmpty(word)) {
			sb.append("select bm from BlogMessage as bm inner join fetch bm.blogGroup inner join fetch bm.blogMessageStatistic");
			sb.append(" where bm.message like :word");
		} else {
			sb.append("select bm from BlogMessage as bm inner join fetch bm.blogGroup inner join fetch bm.blogMessageStatistic");
			sb.append(" where bm.blogGroup.blogGroupType.id = " + BlogGroupType.GROUP_HOT.longValue());
		}
		if (orderByTimes)
			sb.append(" order by bm.blogMessageStatistic.viewTimes desc, bm.updateTime desc");
		else
			sb.append(" order by bm.updateTime desc");
		Query query = getSession().createQuery(sb.toString());
		if (StringHelper.isNotEmpty(word)) {
			word = StringHelper.patternValue(word);
			query.setString("word", word).setCacheable(false);
		}
		return executeHQ(query, SessionConstants.HIBERNATE_QUERY_MAX_ROWS);
	}

	public SearchPageList searchBlogMessagePageList(SearchPageList pageList, boolean orderByTimes) {
		StringBuffer sb = new StringBuffer();
		if (StringHelper.isNotEmpty(pageList.getWord())) {
			sb.append("select bm from BlogMessage as bm inner join fetch bm.blogGroup inner join fetch bm.blogMessageStatistic");
			sb.append(" where bm.message like :word");
		} else {
			sb.append("select bm from BlogMessage as bm inner join fetch bm.blogGroup inner join fetch bm.blogMessageStatistic");
			sb.append(" where bm.blogGroup.blogGroupType.id = " + BlogGroupType.GROUP_HOT.longValue());
		}
		if (orderByTimes)
			sb.append(" order by bm.blogMessageStatistic.viewTimes desc, bm.updateTime desc");
		else
			sb.append(" order by bm.updateTime desc");
		Query query = getSession().createQuery(sb.toString());
		if (StringHelper.isNotEmpty(pageList.getWord())) {
			String word = StringHelper.patternValue(pageList.getWord());
			query.setString("word", word);
			log.debug("==search: " + word);
		}
		List<Object> list = executeHQ(query, SessionConstants.HIBERNATE_QUERY_MAX_ROWS);
		List<BlogMessage> filteredList = this.filterBlogMessages(list, pageList.getBlogUser());		
		List<BlogMessage> subList = this.getSearchPageList(filteredList, pageList);
		pageList.setResultSet(subList);
		return pageList;
	}

	public List<Object> getBlogMessagesByGroupID(Long groupId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bm from BlogMessage as bm");
		sb.append(" where bm.blogGroup.id = :groupId");
		sb.append(" order by bm.updateTime desc");
		Query query = getSession().createQuery(sb.toString());
		query.setLong("groupId", groupId.longValue());
		return executeHQ(query);
	}

	public MessagePageList getBlogMessagePageList(MessagePageList pageList) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bm from BlogMessage as bm");
		sb.append(" where bm.blogGroup.id = :groupId");
		sb.append(" order by bm.updateTime desc");
		Query query = getSession().createQuery(sb.toString());
		query.setLong("groupId", pageList.getGroupId().longValue());
		List<Object> list = executeHQ(query, SessionConstants.HIBERNATE_QUERY_MAX_ROWS);
		List<BlogMessage> filteredList = this.filterBlogMessages(list, pageList.getBlogUser());		
		List<BlogMessage> subList = this.getMessagePageList(filteredList, pageList);
		pageList.setResultSet(subList);
		return pageList;
	}
	
	public List<Object> getBlogMessagesByUserID(Long userId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bm from BlogMessage as bm");
		sb.append(" where bm.blogUser.id = :userId");
		sb.append(" order by bm.updateTime desc");
		Query query = getSession().createQuery(sb.toString());
		query.setLong("userId", userId.longValue());
		return executeHQ(query);
	}

	public BlogMessageType getBlogMessageTypeByID(Serializable id) {
		return (BlogMessageType) load(BlogMessageType.class, id);
	}

	public BlogReplyMessage getBlogReplyMessageByID(Serializable id) {
		BlogReplyMessage bm = (BlogReplyMessage) load(BlogReplyMessage.class, id);
		Hibernate.initialize(bm.getBlogMessage());
		Hibernate.initialize(bm.getBlogUser());
		return bm;
	}

	public BlogReplyMessage getBlogReplyMessageByUploadName(String uploadName) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bm from BlogReplyMessage as bm");
		sb.append(" where bm.uploadFile = :uploadName");
		Query query = getSession().createQuery(sb.toString());
		query.setString("uploadName", uploadName);
		return (BlogReplyMessage) executeHQObject(query);
	}

	public List<Object> getBlogReplyMessagesByMessageID(Long messageId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bm from BlogReplyMessage as bm");
		sb.append(" where bm.blogMessage.id = :messageId");
		sb.append(" order by bm.updateTime desc");
		Query query = getSession().createQuery(sb.toString());
		query.setLong("messageId", messageId.longValue());
		return executeHQ(query);
	}

	public List<Object> getBlogReplyMessagesByUserID(Long userId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bm from BlogReplyMessage as bm");
		sb.append(" where bm.blogUser.id = :userId");
		sb.append(" order by bm.updateTime desc");
		Query query = getSession().createQuery(sb.toString());
		query.setLong("userId", userId.longValue());
		return executeHQ(query);
	}

	public BlogUser getBlogUserByID(Serializable id) {
		return (BlogUser) load(BlogUser.class, id);
	}

	public BlogInformation getBlogInformationByID(Serializable id) {
		return (BlogInformation) load(BlogInformation.class, id);
	}

	public BlogUserGroup getBlogUserGroupByUserIDAndGroupID(Long userId, Long groupId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bug from BlogUserGroup as bug");
		sb.append(" where bug.blogUser.id = :userId");
		sb.append(" and bug.blogGroup.id = :groupId");
		sb.append(" and bug.groupOwner = true");
		Query query = getSession().createQuery(sb.toString());
		query.setLong("userId", userId.longValue());
		query.setLong("groupId", groupId.longValue());
		return (BlogUserGroup) executeHQObject(query);
	}

	public List<Object> getBlogUsersByGroupID(Long groupId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bug.blogUser from BlogUserGroup as bug");
		sb.append(" where bug.blogGroup.id = :groupId");
		Query query = getSession().createQuery(sb.toString());
		query.setLong("groupId", groupId.longValue());
		return executeHQ(query);
	}

	public List<Object> getBlogGroupsByUserID(Long userId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bug.blogGroup from BlogUserGroup as bug");
		sb.append(" where bug.blogUser.id = :userId");
		Query query = getSession().createQuery(sb.toString());
		query.setLong("userId", userId.longValue());
		return executeHQ(query);
	}

	public BlogProperties getBlogProperties(String key) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bp from BlogProperties as bp");
		sb.append(" where bp.key = :key");
		Query query = getSession().createQuery(sb.toString());
		query.setString("key", key);
		return (BlogProperties) executeHQObject(query);
	}

	public List<BlogMessage> filterBlogMessages(List<Object> messages, BlogUser user) {
		List<BlogMessage> filteredMessages = new ArrayList<BlogMessage>();
		for (Iterator<Object> iterator = messages.iterator(); iterator.hasNext();) {
			BlogMessage blogMessage = (BlogMessage) iterator.next();
			if (blogMessage.isStatus()) {
				filteredMessages.add(blogMessage);
			} else {
				long blogUserId = blogMessage.getBlogUser().getId();
				long loginUserId = (user == null? -1 : user.getId());
				if (blogUserId == loginUserId) {
					filteredMessages.add(blogMessage);
				} else {
					if (user != null) {
						long loginUserTypeId = (user.getBlogUserType() == null? -1 : user.getBlogUserType().getId());
						if (BlogUserType.ADMIN == loginUserTypeId || BlogUserType.LEADER == loginUserTypeId) {
							filteredMessages.add(blogMessage);
						}					
					}
				}
			}
		}
		
		return filteredMessages;
	}

	public List<BlogReplyMessage> filterBlogReplyMessages(List<Object> messages, BlogUser user) {
		List<BlogReplyMessage> filteredMessages = new ArrayList<BlogReplyMessage>();
		for (Iterator<Object> iterator = messages.iterator(); iterator.hasNext();) {
			BlogReplyMessage blogMessage = (BlogReplyMessage) iterator.next();
			if (blogMessage.isStatus()) {
				filteredMessages.add(blogMessage);
			} else {
				long blogUserId = blogMessage.getBlogUser().getId();
				long loginUserId = (user == null? -1 : user.getId());
				if (blogUserId == loginUserId) {
					filteredMessages.add(blogMessage);
				} else {
					if (user != null) {
						long loginUserTypeId = (user.getBlogUserType() == null? -1 : user.getBlogUserType().getId());
						if (BlogUserType.ADMIN == loginUserTypeId || BlogUserType.LEADER == loginUserTypeId) {
							filteredMessages.add(blogMessage);
						}					
					}
				}
			}
		}
		return filteredMessages;
	}

	public List<BlogMessage> getMessagePageList(List<BlogMessage> list, MessagePageList pageList) {
		int listSize = list.size();
		pageList.setTotal((int)Math.ceil(listSize/pageList.getSize()) + 1);
		List<BlogMessage> subList = new ArrayList<BlogMessage>();
		int max = (listSize > pageList.getSize() * pageList.getNumber())?pageList.getSize() * pageList.getNumber():listSize;
		int min = pageList.getSize() * (pageList.getNumber() - 1);
		for (int i = min; i < max; i++) {
			subList.add(list.get(i));
		}
		return subList;
	}
	
	public List<BlogMessage> getSearchPageList(List<BlogMessage> list, SearchPageList pageList) {
		int listSize = list.size();
		pageList.setTotal((int)Math.ceil(listSize/pageList.getSize()) + 1);
		List<BlogMessage> subList = new ArrayList<BlogMessage>();
		int max = (listSize > pageList.getSize() * pageList.getNumber())?pageList.getSize() * pageList.getNumber():listSize;
		int min = pageList.getSize() * (pageList.getNumber() - 1);
		for (int i = min; i < max; i++) {
			subList.add(list.get(i));
		}
		return subList;
	}
	
}
