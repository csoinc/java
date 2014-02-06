package com.oyou.web.blog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.oyou.common.exception.BusinessException;
import com.oyou.common.util.DateHelper;
import com.oyou.domain.blog.orm.BlogUser;

public class RegisterAction extends UserAction {
	private static final Log log = LogFactory.getLog(RegisterAction.class);

	public ActionForward register(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug(">>register");
		RegisterForm userForm = (RegisterForm) form;
		ActionMessages messages = new ActionMessages();
		BlogUser user = this.getBBSUser(userForm);
		getUserService().register(user, request.getRemoteAddr());
		ActionMessage message = new ActionMessage("message.register.created");
		messages.add(ActionMessages.GLOBAL_MESSAGE, message);
		this.saveMessages(request, messages);
		userForm.reset();
		log.debug("<<register");
		return mapping.findForward(FORWARD_SUCCESS);
	}

	protected BlogUser getBBSUser(RegisterForm userForm) {
		BlogUser user = new BlogUser();
		user.setCreateTime(DateHelper.getCurrentTimestamp());
		user.setAccessTime(DateHelper.getCurrentTimestamp());
		user.setCity(userForm.getCity());
		user.setCountry(userForm.getCountry());
		user.setEmail(userForm.getEmail());
		user.setFirstname(userForm.getFirstname());
		user.setLastname(userForm.getLastname());
		user.setNickname(userForm.getNickname());
		user.setLoginName(userForm.getLoginName());
		user.setPhoneCell(userForm.getPhoneCell());
		user.setPhoneHome(userForm.getPhoneHome());
		user.setProvince(userForm.getProvince());
		user.setStatus(true);
		user.setStreet(userForm.getStreet());
		user.setUnit(userForm.getUnit());
		user.setUpdateTime(DateHelper.getCurrentTimestamp());
		return user;
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug(">>unspecified");
		RegisterForm userForm = (RegisterForm) form;
		userForm.reset();
		log.debug("<<unspecified");
		return mapping.findForward(FORWARD_SUCCESS);
    }

}
