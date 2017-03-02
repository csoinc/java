package com.oyou.web.blog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.oyou.common.exception.BusinessException;
import com.oyou.web.blog.util.ConfigHelper;
import com.oyou.web.blog.util.StrutsHelper;

public class LogoutAction extends UserAction {
	private static final Log log = LogFactory.getLog(LogoutAction.class);
	private String LOGOUT_URL; //"http://localhost:6080/cas/logout";

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
    	log.debug("==logout()");
    	StrutsHelper.removeBlogUser(request);
//		Cookie[] cookies = request.getCookies();
//		for (int i = 0; i < cookies.length; i++) {
//			Cookie cookie = cookies[i];
//			if (StrutsHelper.COOKIE_LOGIN_USER_ID.equals(cookie.getName())) {
//				cookie.setMaxAge(0);
//				cookie.setPath("/");
//				cookie.setDomain("");			
//				response.addCookie(cookie);
//		    	log.info("==remove cookie: " + StrutsHelper.COOKIE_LOGIN_USER_ID);
//			} else if (StrutsHelper.COOKIE_LOGIN_USER_PWD.equals(cookie.getName())) {
//				cookie.setMaxAge(0);
//				cookie.setPath("/");
//				cookie.setDomain("");			
//				response.addCookie(cookie);
//		    	log.info("==remove cookie: " + StrutsHelper.COOKIE_LOGIN_USER_PWD);
//			}
//		}
    	ActionMessages messages = new ActionMessages();
    	ActionMessage message = new ActionMessage("message.logout.confirmed");
		messages.add(ActionMessages.GLOBAL_MESSAGE, message);
		request.setAttribute(Globals.MESSAGE_KEY, messages);
		try {
			LOGOUT_URL = this.getBlogService().getBlogProperties(ConfigHelper.SSO_LOGOUT).getValue();
			response.sendRedirect(LOGOUT_URL);
		} catch (Exception e) {
			
		}
    	return super.unspecified(mapping, form, request, response);

    }
	
}
