package com.oyou.domain.blog;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.common.exception.BusinessException;
import com.oyou.common.exception.ErrorKeys;
import com.oyou.common.spring.SpringServiceImpl;
import com.oyou.common.util.DateHelper;
import com.oyou.common.util.SecurityHelper;
import com.oyou.domain.blog.orm.BlogUser;
import com.oyou.domain.blog.orm.BlogUserStatistic;
import com.oyou.domain.blog.orm.BlogUserType;
import com.oyou.web.blog.util.ConfigHelper;

/**
 * 
 * @author	Owen Ou
 * @since 	Nov 3, 2007
 * Update on Oct 12, 2011 for send email part
 */
abstract class UserServiceImpl extends SpringServiceImpl implements UserService {
	private static final Log log = LogFactory.getLog(UserServiceImpl.class);
	
	public final String USER_REGISTER = "REGISTER";
	public final String USER_LOGIN = "LOGIN";
	public final String USER_PASSWORD = "PASSWORD";

	protected UserDAO userDAO;

	protected EmailManager emailManager;
	
	protected BlogService blogService;

	public void changePassword(Long id, String password) throws BusinessException {
		log.debug(">>changePassword()");
		BusinessException be = new BusinessException();
		BlogUser user = (BlogUser)getUserDAO().load(BlogUser.class, id);
		if (user == null) {
			be.addError(ErrorKeys.ERROR_PASSWORD_PROFILE_INVALID, id);
		} else {
			user.setLoginPassword(SecurityHelper.encode(password));
			user.setUpdateTime(DateHelper.getCurrentTimestamp());
			getUserDAO().saveOrUpdate(user);
		}
		if (be.hasErrors()) {
			throw be;
		}
		log.debug("<<changePassword()");
	}

	public void forgotPassword(String phoneHome, String email, String remoteAddr) throws BusinessException {
		log.debug(">>forgotPassword()");
		BusinessException be = new BusinessException();
		BlogUser user = (BlogUser)getUserDAO().getBlogUserByHomePhoneAndEmail(phoneHome, email);
		if (user == null) {
			be.addError(ErrorKeys.ERROR_PHONE_AND_EMAIL_INVALID);
		} else {
			String password = user.getEmail().substring(0, user.getEmail().indexOf("@"));
			user.setLoginPassword(SecurityHelper.encode(password));
			getUserDAO().saveOrUpdate(user);
			this.sendSimpleMailMessage(user.getEmail(), user.getFirstname(), password, this.getFromEmail(), remoteAddr, USER_PASSWORD);
		}
		if (be.hasErrors()) {
			throw be;
		}
		log.debug("<<forgotPassword()");
	}
	
	public BlogUser getBlogUserByID(Long id) throws BusinessException {
		return userDAO.getBlogUserByID(id);
	}

	public List<BlogUser> getBlogUsersByUserType(Long userType) throws BusinessException {
		return userDAO.getBlogUsersByUserType(userType);
	}
	
	public EmailManager getEmailManager() {
		return emailManager;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public BlogUser login(String name, String loginPassword, String remoteAddr) throws BusinessException {
		BlogUser user = userDAO.getBlogUserByLoginNameAndPassword(name, loginPassword);
		if (user == null) {
			user = userDAO.getBlogUserByLoginNameAndPassword(name, SecurityHelper.encode(loginPassword));
		}
		if (user == null) {
			user = userDAO.getBlogUserByEmailAndPassword(name, loginPassword);
			if (user == null) {
				user = userDAO.getBlogUserByEmailAndPassword(name, SecurityHelper.encode(loginPassword));
			}
		}
		if (user != null) {
			user.setAccessTime(DateHelper.getCurrentTimestamp());
			userDAO.saveOrUpdate(user);
			this.increaseUserViewTimes(user.getId());
			//this.sendSimpleMailMessage(user.getEmail(), user.getFirstname(), user.getLoginPassword(), this.getFromEmail(), remoteAddr, USER_LOGIN);
		}
		return user;
	}

	public BlogUser login(String loginName) throws BusinessException {
		BlogUser user = userDAO.getBlogUserByLoginName(loginName);
		if (user != null) {
			user.setAccessTime(DateHelper.getCurrentTimestamp());
			userDAO.saveOrUpdate(user);
			this.increaseUserViewTimes(user.getId());
		}
		return user;
	}
	
	public void register(BlogUser user, String remoteAddr) throws BusinessException {
		log.debug(">>register");
		BusinessException be = new BusinessException();
		if (getUserDAO().isNewBlogUserLoginName(user.getLoginName())) {
			log.debug(">>>>1. is new login name");
			if (getUserDAO().isNewBlogUserEmailAddress(user.getEmail())) {
				log.debug(">>>>2. is new email address");
				String password = user.getEmail().substring(0, user.getEmail().indexOf("@"));
				user.setLoginPassword(SecurityHelper.encode(password));
				BlogUserType blogUserType = getUserDAO().getBlogUserTypeByID(BlogUserType.USER);
				user.setBlogUserType(blogUserType);
				getUserDAO().saveOrUpdate(user);
				this.sendSimpleMailMessage(user.getEmail(), user.getFirstname(), password, this.getFromEmail(), remoteAddr, USER_REGISTER);
			} else {
				be.addError(ErrorKeys.ERROR_REGISTER_EMAIL_ADDRESS, user.getEmail());
			}
		} else {
			be.addError(ErrorKeys.ERROR_REGISTER_LOGIN_NAME, user.getLoginName());
		}
		if (be.hasErrors()) {
			throw be;
		}
		log.debug(">>register");
		
	}

	public void setEmailManager(EmailManager emailManager) {
		this.emailManager = emailManager;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public void updateProfile(BlogUser user) throws BusinessException {
		log.debug(">>updateProfile()");
		BusinessException be = new BusinessException();
		getUserDAO().saveOrUpdate(user);
		if (be.hasErrors()) {
			throw be;
		}
		log.debug("<<updateProfile()");
	}

	public void increaseUserViewTimes(Long userId) throws BusinessException {
		BlogUser blogUser = getUserDAO().getBlogUserByID(userId);
		if (blogUser != null) {
			BlogUserStatistic statistic = blogUser.getBlogUserStatistic();
			if (statistic == null) {
				statistic = new BlogUserStatistic();
				statistic.setStatisticId(blogUser.getId());
				statistic.setViewTimes(1);
				statistic.setUpdateTimes(0);
				statistic.setStatus(true);	
			} else {
				statistic.setViewTimes(statistic.getViewTimes() + 1);
			}
			getUserDAO().saveOrUpdate(statistic);
			blogUser.setBlogUserStatistic(statistic);
		}
	}

	public void increaseUserUpdateTimes(Long userId) throws BusinessException {
		BlogUser blogUser = getUserDAO().getBlogUserByID(userId);
		if (blogUser != null) {
			BlogUserStatistic statistic = blogUser.getBlogUserStatistic();
			if (statistic == null) {
				statistic = new BlogUserStatistic();
				statistic.setStatisticId(blogUser.getId());
				statistic.setViewTimes(0);
				statistic.setUpdateTimes(1);
				statistic.setStatus(true);	
			} else {
				statistic.setUpdateTimes(statistic.getUpdateTimes() + 1);
			}
			getUserDAO().saveOrUpdate(statistic);
			blogUser.setBlogUserStatistic(statistic);
		}
	}
	
	abstract void sendSimpleMailMessage(String toEmail, String toFirstname, String toPassword, String fromEmail, String remoteAddr, String emailType);

	public BlogService getBlogService() {
		return blogService;
	}

	public void setBlogService(BlogService blogService) {
		this.blogService = blogService;
	}

	public String getSiteDomain() {
		return this.getBlogService().getBlogProperties(ConfigHelper.SITE_DOMAIN).getValue();
	}

	public String getFromEmail() {
		return this.getBlogService().getBlogProperties(ConfigHelper.FROM_EMAIL).getValue();
	}

	public String getAdminEmail() {
		return this.getBlogService().getBlogProperties(ConfigHelper.ADMIN_EMAIL).getValue();
	}

	public String getAdminName() {
		return this.getBlogService().getBlogProperties(ConfigHelper.ADMIN_NAME).getValue();
	}
	
}
