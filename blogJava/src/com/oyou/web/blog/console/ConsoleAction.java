package com.oyou.web.blog.console;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.oyou.common.exception.BusinessException;
import com.oyou.common.spring.SpringService;
import com.oyou.domain.blog.BlogService;
import com.oyou.domain.blog.CommonService;
import com.oyou.domain.blog.UserService;
import com.oyou.web.struts.StrutsAction;

public abstract class ConsoleAction extends StrutsAction {
	private static final Log log = LogFactory.getLog(ConsoleAction.class);
	protected static final String DB_PROD = "prod";
	protected static final String DB_BACKUP = "backup";
    protected static final int BUFFER = 5000;
   
	private BlogService blogService;

	private BlogService backupBlogService;

	private UserService userService;

	private UserService backupUserService;
	
	private CommonService commonService;
	
	private CommonService backupCommonService;
	
	public BlogService getBackupBlogService() {
		if (backupBlogService == null) {
			Object obj = getService(SpringService.BACKUP_BLOG_SERVICE);
			if (obj != null) {
				backupBlogService = (BlogService) obj;
			}
		}
		return backupBlogService;
	}

	public BlogService getBlogService() {
		if (blogService == null) {
			Object obj = getService(SpringService.BLOG_SERVICE);
			if (obj != null) {
				blogService = (BlogService) obj;
			}
		}
		return blogService;
	}

	 public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		 return super.unspecified(mapping, form, request, response);
	 }

	public CommonService getBackupCommonService() {
		if (backupCommonService == null) {
			Object obj = getService(SpringService.BACKUP_COMMON_SERVICE);
			if (obj != null) {
				backupCommonService = (CommonService) obj;
			}
		}
		return backupCommonService;
	}

	public CommonService getCommonService() {
		if (commonService == null) {
			Object obj = getService(SpringService.COMMON_SERVICE);
			if (obj != null) {
				commonService = (CommonService) obj;
			}
		}
		return commonService;
	}

	protected Map getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.executeSQL", "executeSQL");
		map.put("button.execute", "executeSQL");
		map.put("button.send", "send");
		map.put("button.update", "update");
		map.put("button.executeHQL", "executeHQL");
		return map;
	}

	public UserService getBackupUserService() {
		if (backupUserService == null) {
			Object obj = getService(SpringService.BACKUP_USER_SERVICE);
			if (obj != null) {
				backupUserService = (UserService) obj;
			}
		}
		return backupUserService;
	}

	public UserService getUserService() {
		if (userService == null) {
			Object obj = getService(SpringService.USER_SERVICE);
			if (obj != null) {
				userService = (UserService) obj;
			}
		}
		return userService;
	}

	public void setBackupBlogService(BlogService backupBlogService) {
		this.backupBlogService = backupBlogService;
	}

	public void setBackupCommonService(CommonService backupCommonService) {
		this.backupCommonService = backupCommonService;
	}

	public void setBackupUserService(UserService backupUserService) {
		this.backupUserService = backupUserService;
	}

	public void setBlogService(BlogService blogService) {
		this.blogService = blogService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}


}
