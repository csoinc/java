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
import com.oyou.common.util.DebugHelper;
import com.oyou.domain.blog.orm.BlogUser;
import com.oyou.domain.blog.orm.BlogUserType;
import com.oyou.web.blog.util.StrutsHelper;

public class ProfileAction extends UserAction {
	private static final Log log = LogFactory.getLog(RegisterAction.class);

	public ActionForward updateProfile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug(">>updateProfile");
		ProfileForm userForm = (ProfileForm) form;
		ActionMessages messages = new ActionMessages();
		BlogUser user = StrutsHelper.getBlogUser(request);
		if (user != null) {
			if (!user.getLoginName().equalsIgnoreCase(BlogUserType.USER_ROLE) && !user.getLoginName().equalsIgnoreCase(BlogUserType.GUEST_ROLE)) {
				this.setBBSUser(user, userForm);
				getUserService().updateProfile(user);
				getUserService().increaseUserUpdateTimes(user.getId());
				log.debug(DebugHelper.getJSONString(user));
				ActionMessage message = new ActionMessage("message.profile.updated");
				messages.add(ActionMessages.GLOBAL_MESSAGE, message);
				this.saveMessages(request, messages);
			}
		}
		log.debug("<<updateProfile");
		return mapping.findForward(FORWARD_GROUP);
	}

	protected void setBBSUser(BlogUser user, ProfileForm userForm) {
		user.setUpdateTime(DateHelper.getCurrentTimestamp());
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
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		ProfileForm userForm = (ProfileForm) form;
		BlogUser user = StrutsHelper.getBlogUser(request);
		userForm.setLoginName(user.getLoginName());
		userForm.setCity(user.getCity());
		userForm.setCountry(user.getCountry());
		userForm.setEmail(user.getEmail());
		userForm.setFirstname(user.getFirstname());
		userForm.setLastname(user.getLastname());
		userForm.setNickname(user.getNickname());
		userForm.setLoginName(user.getLoginName());
		userForm.setPhoneCell(user.getPhoneCell());
		userForm.setPhoneHome(user.getPhoneHome());
		userForm.setProvince(user.getProvince());
		userForm.setStreet(user.getStreet());
		userForm.setUnit(user.getUnit());
    	return super.unspecified(mapping, userForm, request, response);
    }
	

}
