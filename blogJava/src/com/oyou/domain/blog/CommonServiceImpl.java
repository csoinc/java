package com.oyou.domain.blog;

import java.io.Serializable;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.common.exception.BusinessException;
import com.oyou.common.hibernate.dao.CommonDAO;
import com.oyou.common.spring.SpringServiceImpl;
import com.oyou.domain.blog.orm.BlogGroup;
import com.oyou.domain.blog.orm.BlogMessage;
import com.oyou.domain.blog.orm.BlogReplyMessage;
import com.oyou.domain.blog.orm.BlogUser;

public class CommonServiceImpl extends SpringServiceImpl implements CommonService {
	private static final Log log = LogFactory.getLog(CommonServiceImpl.class);

	private CommonDAO commonDAO;
	private BlogDAO blogDAO;
	private UserDAO userDAO;

	public void delete(Object obj) throws BusinessException {
		log.debug("==delete()");
		commonDAO.delete(obj);
	}

	public Object get(Class cls, Serializable id) throws BusinessException {
		log.debug("==get()");
		return commonDAO.get(cls, id);
	}
	
	public BlogDAO getBlogDAO() {
		return blogDAO;
	}

	public BlogUser getBlogUserByID(Long id) throws BusinessException {
		log.debug("==getBlogUserByID()");
		return (BlogUser)commonDAO.load(BlogUser.class, id);
	}

	public CommonDAO getCommonDAO() {
		return commonDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public List listObjects(Class cls) throws BusinessException {
		log.debug("==listObjects()");
		return commonDAO.select(cls);
	}

	public void saveOrUpdate(Object obj) throws BusinessException {
		log.debug("==saveOrUpdate()");
		commonDAO.saveOrUpdate(obj);
	}
	
	public void saveOrUpdateBlogGroup(BlogGroup blogGroup) throws BusinessException {
		log.debug("==createBlogGroup()");
		BusinessException be = new BusinessException();
		this.saveOrUpdate(blogGroup);
		if (be.hasErrors()) {
			throw be;
		}
	}
	
	public void saveOrUpdateBlogMessage(BlogMessage blogMessage) throws BusinessException {
		log.debug("==createBlogMessage()");
		BusinessException be = new BusinessException();
		this.saveOrUpdate(blogMessage);
		if (be.hasErrors()) {
			throw be;
		}
	}

	public void saveOrUpdateBlogReplyMessage(BlogReplyMessage blogMessage) throws BusinessException {
		log.debug("==backupBlogReplyMessage()");
		BusinessException be = new BusinessException();
		this.saveOrUpdate(blogMessage);
		if (be.hasErrors()) {
			throw be;
		}
	}

	public void saveOrUpdateBlogUser(BlogUser blogUser) throws BusinessException {
		log.debug("==createBlogUser()");
		BusinessException be = new BusinessException();
		this.saveOrUpdate(blogUser);
		if (be.hasErrors()) {
			throw be;
		}
	}

	public void setBlogDAO(BlogDAO blogDAO) {
		this.blogDAO = blogDAO;
	}

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public Long getObjectMaxID(Class cls) throws BusinessException {
		BusinessException be = new BusinessException();
		Long id = this.commonDAO.getObjectMaxID(cls);
		if (be.hasErrors()) {
			throw be;
		}
		return id;
	}
	
}
