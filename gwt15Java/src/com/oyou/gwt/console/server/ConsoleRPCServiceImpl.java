package com.oyou.gwt.console.server;

import java.sql.Timestamp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.oyou.domain.blog.BlogService;
import com.oyou.domain.blog.CommonService;
import com.oyou.domain.blog.UserService;
import com.oyou.domain.blog.orm.BlogUser;
import com.oyou.domain.blog.orm.BlogUserType;
import com.oyou.gwt.console.client.Console;
import com.oyou.gwt.console.client.ConsoleException;
import com.oyou.gwt.console.client.ErrorConstants;
import com.oyou.gwt.console.client.IsError;
import com.oyou.web.blog.util.AlbumHelper;
import com.oyou.web.blog.util.StrutsHelper;
import com.oyou.web.blog.util.UploadHelper;

/**
 * 
 * @author Owen Ou
 * @version $Id: BlogAjaxServiceImpl.java,v 1.1 2008/06/29 14:24:37 oyou Exp $
 * @since Nov 6, 2007
 */
public class ConsoleRPCServiceImpl extends RemoteServiceServlet implements ConsoleRPCService {
	static final long serialVersionUID = 1;
	protected static final String IMAGE_ORIGINAL_RELATIVE_PATH = "org";
	protected static final String IMAGE_ICON_RELATIVE_PATH = "icon";
	protected static final String ALBUM_CREATE_MODE = "U";
	private static final Log log = LogFactory.getLog(ConsoleRPCServiceImpl.class);
	private ApplicationContext applicationContext = null;
	
	public ApplicationContext getApplicationContext() {
		if (applicationContext == null) {
			applicationContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		}
		return applicationContext;
	}

	public BlogService getBlogService() {
		return (BlogService)this.getApplicationContext().getBean(BLOG_SERVICE);
	}

	public CommonService getCommonService() {
		return (CommonService)this.getApplicationContext().getBean(COMMON_SERVICE);
	}

	public UserService getUserService() {
		return (UserService)this.getApplicationContext().getBean(USER_SERVICE);
	}
	

	public IsError importPhotos(String photoPath, String albumId, String comment) throws ConsoleException {
		IsError error = new IsError();
		if (Console.DEBUG) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException ie) {
				;
			}
			log.debug("=Success");
			error.setMessage(IsError.SUCCESS);
			return error;
		}
		BlogUser blogUser = StrutsHelper.getBlogUser(this.getThreadLocalRequest());
		if (blogUser == null || BlogUserType.GUEST.equals(blogUser.getBlogUserType().getId())) {
			ConsoleException exception = new ConsoleException(ErrorConstants.error_message_authority);
			log.debug("==" + ErrorConstants.error_message_authority);
			throw exception;
		}
		try {
			AlbumHelper.getInstance().createAlbumByPhotos(photoPath, albumId, comment, ALBUM_CREATE_MODE, blogUser, getBlogService(), getCommonService(), getServletContext());
		} catch (Exception e) {
			//ConsoleException exception = new ConsoleException(e.getMessage());
			log.debug("==" + e.getMessage());
			//throw exception;
			error.setMessage(e.getMessage());
			return error;
		}
		error.setMessage(IsError.SUCCESS);
		return error;
	}
	
	
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.applicationContext = ctx;

	}

	/**
	 * 
	 * @param mType
	 * @param updateTime
	 * @return
	 */
	protected String getUploadPath(Long mType, Timestamp updateTime) {
		return UploadHelper.getInstance().getUploadRelativePath(mType, updateTime);
	}

	/**
	 * 
	 * @param mType
	 * @param updateTime
	 * @return
	 */
	protected String getIconUploadPath(Long mType, Timestamp updateTime) {
		String uploadPath = this.getUploadPath(mType, updateTime);
		return uploadPath + "/" + IMAGE_ICON_RELATIVE_PATH;
	}

	
}
