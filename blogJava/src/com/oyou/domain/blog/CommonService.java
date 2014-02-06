package com.oyou.domain.blog;

import java.io.Serializable;
import java.util.List;


import com.oyou.common.exception.BusinessException;
import com.oyou.common.spring.SpringService;
import com.oyou.domain.blog.orm.BlogGroup;
import com.oyou.domain.blog.orm.BlogMessage;
import com.oyou.domain.blog.orm.BlogReplyMessage;
import com.oyou.domain.blog.orm.BlogUser;

public interface CommonService extends SpringService {

	public void delete(Object obj) throws BusinessException;

	public Object get(Class cls, Serializable id) throws BusinessException;

	public BlogUser getBlogUserByID(Long id) throws BusinessException;

	public List listObjects(Class cls) throws BusinessException;

	public void saveOrUpdate(Object obj) throws BusinessException;

	public void saveOrUpdateBlogGroup(BlogGroup blogGroup) throws BusinessException;

	public void saveOrUpdateBlogMessage(BlogMessage blogMessage) throws BusinessException;
		
	public void saveOrUpdateBlogReplyMessage(BlogReplyMessage blogMessage) throws BusinessException;

	public void saveOrUpdateBlogUser(BlogUser blogUser) throws BusinessException;

	public Long getObjectMaxID(Class cls) throws BusinessException;
	
}
