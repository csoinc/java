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
import com.oyou.common.util.StringHelper;
import com.oyou.domain.blog.orm.BlogUser;
import com.oyou.domain.blog.orm.BlogUserType;
import com.oyou.web.blog.util.StrutsHelper;

public class LoginAction extends UserAction {
	private static final Log log = LogFactory.getLog(LoginAction.class);

    public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
    	log.debug("==login()");
    	LoginForm loginForm = (LoginForm) form;
    	BlogUser user = getUserService().login(loginForm.getLoginName(), loginForm.getLoginPassword(), request.getRemoteAddr());
		ActionMessages messages = new ActionMessages();
		if (user != null) {
			getUserService().increaseUserUpdateTimes(user.getId());
			StrutsHelper.setBlogUser(request, user);
			//StrutsHelper.setBlogUserCookie(response, user, getUserService().getSiteDomain());
			ActionMessage message = new ActionMessage("message.login.confirmed");
			messages.add(ActionMessages.GLOBAL_MESSAGE, message);
			request.setAttribute(Globals.MESSAGE_KEY, messages);
			if (user.getBlogUserType().getId().longValue() == BlogUserType.GUEST) {
				return mapping.findForward(FORWARD_SEARCH);
			} else {
				String forward = StrutsHelper.getPathInfo(request);
				if (StringHelper.isEmpty(forward)) {
					return mapping.findForward(FORWARD_GROUP);
				} else {
					StrutsHelper.setPathInfo(request, null);
					return mapping.findForward(forward);
				}
			}
		} else {
			ActionMessage message = new ActionMessage("error.login.valid.info.required");
			messages.add(ActionMessages.GLOBAL_MESSAGE, message);
			request.setAttribute(Globals.MESSAGE_KEY, messages);
			return mapping.findForward(FORWARD_FAILED);
		}
	}
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
    	LoginForm loginForm = (LoginForm) form;
    	loginForm.reset();
		return login(mapping, form, request, response);
    }
	
}
