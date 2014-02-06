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

public class ForgotPasswordAction extends UserAction {
	private static final Log log = LogFactory.getLog(ForgotPasswordAction.class);

	public ActionForward forgotPassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug(">>forgotPassword()");
		ForgotPasswordForm passwordForm = (ForgotPasswordForm) form;
		ActionMessages messages = new ActionMessages();
		this.getUserService().forgotPassword(passwordForm.getPhoneHome(), passwordForm.getEmail(), request.getRemoteAddr());
		ActionMessage message = new ActionMessage("message.forgot.password.email.confirmed", passwordForm.getEmail());
		messages.add(ActionMessages.GLOBAL_MESSAGE, message);
		request.setAttribute(Globals.MESSAGE_KEY, messages);
		return mapping.findForward(FORWARD_SUCCESS);
	}

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		ForgotPasswordForm passwordForm = (ForgotPasswordForm) form;
		passwordForm.reset();
    	return super.unspecified(mapping, passwordForm, request, response);
    }
	
}
