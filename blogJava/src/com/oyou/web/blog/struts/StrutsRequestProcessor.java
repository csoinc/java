package com.oyou.web.blog.struts;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.springframework.web.context.WebApplicationContext;

import com.oyou.common.exception.BusinessException;
import com.oyou.common.spring.SpringProcessor;
import com.oyou.common.spring.SpringService;
import com.oyou.common.util.StringHelper;
import com.oyou.domain.blog.UserService;
import com.oyou.domain.blog.orm.BlogUser;
import com.oyou.domain.blog.orm.BlogUserType;
import com.oyou.web.blog.util.StrutsHelper;

public class StrutsRequestProcessor extends SpringProcessor {
	private static final Log log = LogFactory.getLog(StrutsRequestProcessor.class);
	public static boolean SYSTEM_DEBUG_MODE = false;
	private static String[] logonRequiredPaths = { "password", "profile", "message", "replyMessage", "sqlCommander",
			"emailGroup", "blogSearch", "bibleSearch", "bibleList", "bibleTree", "Many" };
	private static String[] logonNotRequiredPaths = { "group", "logout", "login", "language", "register", "forgotPassword",
			"citySuggests", "provinceSuggests", "countrySuggests" };
	private String LOGON_PAGE = "/tiles/security/login.jsp";

	public StrutsRequestProcessor() {
		super();
	}

	protected boolean processRoles(HttpServletRequest request, HttpServletResponse response, ActionMapping mapping)
			throws IOException, ServletException {
		log.info(">>processRoles()");
		String pathInfo = request.getRequestURI();
		log.debug("==PathInfo: " + pathInfo);
		if (this.isLogonRequired(pathInfo)) {
			ActionMessages messages = new ActionMessages();
			BlogUser user = StrutsHelper.getBlogUser(request);
			if (user == null) {
				try {
					String luid = "", lupwd = "";
					Cookie[] cookies = request.getCookies();
					if (cookies != null) {
						for (int i = 0; i < cookies.length; i++) {
							Cookie cookie = cookies[i];
							if (StrutsHelper.COOKIE_LOGIN_USER_ID.equals(cookie.getName())) {
								luid = cookie.getValue().trim();
								log.info("== Get luid: " + luid);
								break;
							} 
						}
						for (int i = 0; i < cookies.length; i++) {
							Cookie cookie = cookies[i];
							if (StrutsHelper.COOKIE_LOGIN_USER_PWD.equals(cookie.getName())) {
								lupwd = cookie.getValue().trim();
								log.info("== Get lupwd: " + lupwd);
								break;
							}
						}
					}
					if (StringHelper.isNotEmpty(luid) && StringHelper.isNotEmpty(lupwd)) {
						user = this.getUserService().login(luid, lupwd, request.getRemoteAddr());
					}
					// default guest login solution start
					if (user == null) {
						user = this.getUserService().login("guest", "guest", request.getRemoteAddr());
					}
				} catch (BusinessException be) {
					try {
						user = this.getUserService().login("guest", "guest", request.getRemoteAddr());
					} catch (BusinessException be2) {
						log.info("<<processRoles(): Guest login false");
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.login.required"));
						StrutsHelper.setPathInfo(request, this.getLogonRequiredAction(pathInfo));
						showLogonPage(request, response, messages);
						return false;
					}
				} finally {
					if (user != null)
						StrutsHelper.setBlogUser(request, user);
				}
			}
			if (user != null) {
				if (user.getBlogUserStatistic() != null) {
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.login.viewed", user.getNickname(),
							user.getBlogUserStatistic().getViewTimes()));
				} else {
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.login.viewed", user.getNickname(), 0));
				}
				request.setAttribute(Globals.MESSAGE_KEY, messages);
				String[] roles = mapping.getRoleNames();
				if (roles != null && roles.length > 0) {
					Long uType = user.getBlogUserType().getId();
					for (int i = 0; i < roles.length; i++) {
						Long type = BlogUserType.getTypeFromRole(roles[i]);
						log.debug("==Role: " + type.toString());
						if (uType != null && uType.equals(type)) {
							log.info("<<processRoles(): user role OK");
							return true;
						}
					}
				} else {
					log.info("<<processRoles(): login OK");
					return true;
				}
				log.info("<<processRoles(): user role false");
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.function.authority"));
				StrutsHelper.setPathInfo(request, this.getLogonRequiredAction(pathInfo));
				showLogonPage(request, response, messages);
				return false;
			} else {
				log.info("<<processRoles(): login false");
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.login.required"));
				StrutsHelper.setPathInfo(request, this.getLogonRequiredAction(pathInfo));
				showLogonPage(request, response, messages);
				return false;
			}
		} else {
			log.info("<<processRoles(): path OK");
			return true;
		}
	}

	protected void showLogonPage(HttpServletRequest request, HttpServletResponse response, ActionMessages messages)
			throws ServletException, IOException {
		request.setAttribute(Globals.MESSAGE_KEY, messages);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(LOGON_PAGE);
		dispatcher.forward(request, response);
	}

	protected String getLogonRequiredAction(String path) {
		log.debug("==logonRequiredAction() " + path);
		if (StringHelper.isEmpty(path))
			return null;
		for (int i = 0; i < logonRequiredPaths.length; i++) {
			String inPath = logonRequiredPaths[i];
			if (path.indexOf(inPath) != -1) {
				log.debug("==Logon required action " + inPath);
				return inPath;
			}
		}
		return null;
	}

	protected boolean isLogonRequired(String path) {
		if (SYSTEM_DEBUG_MODE)
			return false;
		log.debug("==pathInfoFilter " + path);
		if (path == null || path.equals(""))
			return false;
		for (int i = 0; i < logonNotRequiredPaths.length; i++) {
			String inPath = logonNotRequiredPaths[i];
			if (path.indexOf(inPath) != -1) {
				return false;
			}
		}
		return true;
	}

	protected void processLocale(HttpServletRequest request, HttpServletResponse response) {
		log.debug("==processLocale()");
		StrutsHelper.processEncoding(request, response);
		super.processLocale(request, response);
	}

	protected void processException(HttpServletRequest request, HttpServletResponse response) {
	}

	public UserService getUserService() {
		UserService userService = null;
		try {
			WebApplicationContext ctx = getWebApplicationContext();
			if (ctx != null && ctx.containsBean(SpringService.USER_SERVICE)) {
				Object obj = ctx.getBean(SpringService.USER_SERVICE);
				if (obj != null)
					userService = (UserService) obj;
			}
			if (userService == null) {
				log.fatal(">>Can't get userService from Application Context");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("Exception on get context " + e.getMessage());
		}
		return userService;
	}

}
