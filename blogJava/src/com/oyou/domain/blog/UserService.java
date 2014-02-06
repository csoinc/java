package com.oyou.domain.blog;

import java.util.List;

import com.oyou.common.exception.BusinessException;
import com.oyou.common.spring.SpringService;
import com.oyou.domain.blog.orm.BlogUser;

/**
 * 
 * @author	Owen Ou
 * @since 	Nov 3, 2007
 * @version	$Id: UserService.java,v 1.1 2008/06/29 14:36:57 oyou Exp $
 */
public interface UserService extends SpringService {

	public void changePassword(Long id, String password) throws BusinessException;

	public void forgotPassword(String phoneHome, String email, String remoteAddr) throws BusinessException;

	public BlogUser getBlogUserByID(Long id) throws BusinessException;

	public List<BlogUser> getBlogUsersByUserType(Long userType) throws BusinessException;

	public EmailManager getEmailManager();

	public UserDAO getUserDAO();

	public BlogUser login(String loginName, String loginPassword, String remoteAddr) throws BusinessException;

	public BlogUser login(String loginName) throws BusinessException;

	public void register(BlogUser user, String remoteAddr) throws BusinessException;

	public void setEmailManager(EmailManager emailManager);
	
	public void setUserDAO(UserDAO userDAO);

	public void updateProfile(BlogUser user) throws BusinessException;

	public void increaseUserViewTimes(Long userId) throws BusinessException;

	public void increaseUserUpdateTimes(Long userId) throws BusinessException;
	
	public String getSiteDomain();

	public String getFromEmail();

	public String getAdminEmail();

	public String getAdminName();
	
}
