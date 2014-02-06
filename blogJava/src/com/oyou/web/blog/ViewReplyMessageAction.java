package com.oyou.web.blog;

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
import com.oyou.domain.blog.orm.BlogReplyMessage;

public class ViewReplyMessageAction extends BlogAction {
	private static final Log log = LogFactory.getLog(ViewReplyMessageAction.class);

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug(">>view()");
		ViewReplyMessageForm messageForm = (ViewReplyMessageForm) form;
		ActionMessages messages = new ActionMessages();
		String id = request.getParameter("rid");
		if (id == null || id.equals("")) {
			log.debug("Can't get reply message id.");
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.system"));
			messageForm.setMethod(null);
			return mapping.findForward(FORWARD_LOGIN);
		} else {
			BlogReplyMessage blogMessage = getBlogService().getBlogReplyMessageByID(Long.valueOf(id));
			messageForm.setBlogReplyMessage(blogMessage);
		}
		this.saveMessages(request, messages);
		log.debug("<<view()");
		return mapping.findForward(FORWARD_SUCCESS);
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		ViewReplyMessageForm messageForm = (ViewReplyMessageForm) form;
		return view(mapping, messageForm, request, response);
	}

}
