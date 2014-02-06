package com.oyou.web.blog;

import java.util.Iterator;
import java.util.List;

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
import com.oyou.common.util.StringHelper;
import com.oyou.common.util.StrutsSession;
import com.oyou.domain.blog.orm.BlogMessage;
import com.oyou.domain.blog.orm.BlogReplyMessage;

public class ReplyMessageSearchAction extends BlogAction {
	private static final Log log = LogFactory.getLog(ReplyMessageSearchAction.class);

	@SuppressWarnings("unchecked")
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug(">>list()");
		ReplyMessageSearchForm messageForm = (ReplyMessageSearchForm) form;
		List messages = getBlogService().listBlogReplyMessages(messageForm.getBlogMessage().getId());
		messageForm.reset();
		messageForm.setMessages(messages);
		for (Iterator iterator = messages.iterator(); iterator.hasNext();) {
			BlogReplyMessage blogReplyMessage = (BlogReplyMessage) iterator.next();
			getBlogService().increaseReplyMessageViewTimes(blogReplyMessage.getId());
		} 				
		String maxLinesOpt = messageForm.getPageMaxLinesOpt();
		String imageAsIconOpt = messageForm.getImageAsIconOpt();
		if (StringHelper.isNotEmpty(maxLinesOpt)) {
			messageForm.setPageMaxLines(Integer.parseInt(maxLinesOpt));
		}
		if (StringHelper.isNotEmpty(imageAsIconOpt)) {
			if (imageAsIconOpt.equals(StrutsSession.ICON)) {
				messageForm.setImageAsIcon(true);
			} else {
				messageForm.setImageAsIcon(false);
			}
		}
		log.debug("<<list()");
		return mapping.findForward(FORWARD_SUCCESS);
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		ReplyMessageSearchForm messageForm = (ReplyMessageSearchForm) form;
		ActionMessages messages = new ActionMessages();
		String messageId = request.getParameter("mid");
		if (messageId == null) {
			BlogMessage blogMessage = messageForm.getBlogMessage();
			if (blogMessage == null) {
				log.error("Can't get message id.");
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.system"));
				this.saveMessages(request, messages);
				return mapping.findForward(FORWARD_LOGIN);
			}
		} else {
			messageForm.reset();
			BlogMessage blogMessage = getBlogService().getBlogMessageByID(Long.valueOf(messageId));
			messageForm.setBlogMessage(blogMessage);
		}
		return list(mapping, messageForm, request, response);
	}
}
