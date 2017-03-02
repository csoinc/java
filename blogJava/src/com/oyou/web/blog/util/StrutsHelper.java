package com.oyou.web.blog.util;

import java.text.MessageFormat;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMessage;

import com.oyou.common.struts.MessagesFactory;
import com.oyou.common.util.StrutsSession;
import com.oyou.domain.blog.orm.BlogUser;

public class StrutsHelper extends com.oyou.web.util.StrutsHelper {
	private static final Log log = LogFactory.getLog(StrutsHelper.class);
	public static final String COOKIE_LOGIN_USER_ID = "luid";
	public static final String COOKIE_LOGIN_USER_PWD = "lupwd";
	public static final int SECONDS_PER_YEAR = 60*60*24*365;
	public static final int SECONDS_PER_MONTH = 60*60*2*365;
	
	public static BlogUser getBlogUser(HttpServletRequest request) {
		BlogUser user = (BlogUser) request.getSession().getAttribute(StrutsSession.KEY_USER);
		return user;
	}

	public static void setBlogUser(HttpServletRequest request, BlogUser user) {
		if (user == null) {
			request.getSession().removeAttribute(StrutsSession.KEY_USER);
		} else {
			request.getSession().setAttribute(StrutsSession.KEY_USER, user);
		}
	}

	private static void setBlogUserCookie(HttpServletResponse response, BlogUser user, String siteDomain) {
		if (user != null) {
			Cookie luid = new Cookie(COOKIE_LOGIN_USER_ID, user.getLoginName());
			luid.setDomain("localhost");
			//luid.setPath("/");
			luid.setMaxAge(SECONDS_PER_MONTH);
			
			Cookie lupwd = new Cookie(COOKIE_LOGIN_USER_PWD, user.getLoginPassword());
			lupwd.setDomain("localhost");
			//lupwd.setPath("/");
			lupwd.setMaxAge(SECONDS_PER_MONTH);
	
			response.addCookie(luid);
			response.addCookie(lupwd);
		}
	}
	
	public static void removeBlogUser(HttpServletRequest request) {
		log.debug("==removeBlogUser");
		request.getSession().removeAttribute(StrutsSession.KEY_USER);
	}

	public static String getFormattedMessage(ActionMessage message) {
		return MessageFormat.format(MessagesFactory.getInstance().getProperty(message.getKey()), message.getValues());
	}
	
}
