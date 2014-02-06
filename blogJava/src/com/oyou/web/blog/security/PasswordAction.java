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
import com.oyou.common.util.StrutsSession;
import com.oyou.domain.blog.orm.BlogUser;
import com.oyou.web.blog.util.StrutsHelper;

public class PasswordAction extends UserAction {
	private static final Log log = LogFactory.getLog(PasswordAction.class);

	public ActionForward changePassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug(">>changePassword()");
		PasswordForm passwordForm = (PasswordForm) form;
		ActionMessages messages = new ActionMessages();
		if (!passwordForm.getLoginPassword().equals(passwordForm.getConfirmPassword())) {
			ActionMessage message = new ActionMessage("error.password.newconfirm.mismatch");
			messages.add(ActionMessages.GLOBAL_MESSAGE, message);
			request.setAttribute(Globals.MESSAGE_KEY, messages);
			return mapping.findForward(FORWARD_FAILED);
		} else {
			BlogUser user = (BlogUser)request.getSession().getAttribute(StrutsSession.KEY_USER);
			if (user != null) {
				//if (!user.getLoginName().equalsIgnoreCase(BlogUserType.USER_ROLE) && !user.getLoginName().equalsIgnoreCase(BlogUserType.GUEST_ROLE)) {
					this.getUserService().changePassword(user.getId(), passwordForm.getLoginPassword());
					getUserService().increaseUserUpdateTimes(user.getId());
					ActionMessage message = new ActionMessage("message.password.changed");
					messages.add(ActionMessages.GLOBAL_MESSAGE, message);
					request.setAttribute(Globals.MESSAGE_KEY, messages);
				//} 
				return mapping.findForward(FORWARD_GROUP);
			} 
			else {
				ActionMessage message = new ActionMessage("error.login.valid.info.required");
				messages.add(ActionMessages.GLOBAL_MESSAGE, message);
				request.setAttribute(Globals.MESSAGE_KEY, messages);
				return mapping.findForward(FORWARD_FAILED);
			}
		}
	}

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		PasswordForm passwordForm = (PasswordForm) form;
		BlogUser user = StrutsHelper.getBlogUser(request);
		passwordForm.reset();
		passwordForm.setLoginName(user.getLoginName());
		//passwordForm.setLoginPassword(user.getLoginPassword());
		//passwordForm.setConfirmPassword(user.getLoginPassword());
    	return super.unspecified(mapping, passwordForm, request, response);
    }
	
}
