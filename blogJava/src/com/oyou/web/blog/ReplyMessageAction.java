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
import com.oyou.common.util.DateHelper;
import com.oyou.common.util.DebugHelper;
import com.oyou.common.util.HTMLHelper;
import com.oyou.common.util.StringHelper;
import com.oyou.common.util.StrutsSession;
import com.oyou.domain.blog.orm.BlogMessage;
import com.oyou.domain.blog.orm.BlogMessageType;
import com.oyou.domain.blog.orm.BlogReplyMessage;
import com.oyou.domain.blog.orm.BlogUser;
import com.oyou.domain.blog.orm.BlogUserType;
import com.oyou.web.blog.util.StrutsHelper;

public class ReplyMessageAction extends BlogAction {
	private static final Log log = LogFactory.getLog(ReplyMessageAction.class);

	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug(">>create()");
		ReplyMessageForm messageForm = (ReplyMessageForm) form;
		ActionMessages messages = new ActionMessages();
		BlogUser blogUser = StrutsHelper.getBlogUser(request);
		if (blogUser == null || BlogUserType.GUEST.equals(blogUser.getBlogUserType().getId())) {
			log.debug("error.message.authority");
			messages.add(StrutsSession.KEY_BUSINESS, new ActionMessage("error.message.authority"));
			messageForm.setId(null);
			messageForm.setMethod(null);
			this.saveMessages(request, messages);
			log.debug("<<create()");
			return mapping.findForward(FORWARD_SUCCESS);
		}
		Long uTypeId = null;
		String uploadName = null;
		BlogReplyMessage blogMessage = new BlogReplyMessage();
		blogMessage.setLinkURL(messageForm.getLinkURL());
		uTypeId = this.getUploadFileType(messageForm.getUploadFile());
		if (!uTypeId.equals(BlogMessageType.TEXT)) {
			String uploadFilename = messageForm.getUploadFile().getFileName();
			ActionMessage message = new ActionMessage("message.upload.created.confirmed", uploadFilename);
			uploadName = this.saveUploadFile(messageForm.getUploadFile(), uTypeId, "comment");
			messages.add(ActionMessages.GLOBAL_MESSAGE, message);
		} else {
			if (StringHelper.isNotEmpty(messageForm.getLinkURL())) {
				uTypeId = BlogMessageType.LINK;
			} 
		}
		blogMessage.setUploadFile(uploadName);
		BlogMessageType blogMessageType = getBlogService().getBlogMessageTypeByID(uTypeId);
		blogMessage.setBlogMessageType(blogMessageType);
		blogMessage.setCreateTime(DateHelper.getCurrentTimestamp());
		String statusOpt = (String)request.getParameter("statusOpt");
		log.debug("==status option: " + statusOpt);	
		if (StringHelper.isNotEmpty(statusOpt) && ReplyMessageForm.UPDATE_PUBLISHED.equals(statusOpt)) {
			blogMessage.setStatus(true);
		} else {
			blogMessage.setStatus(false);
		}
		blogMessage.setUpdateTime(DateHelper.getCurrentTimestamp());
		blogMessage.setMessage(HTMLHelper.formatToHTML(messageForm.getMessage()));
		blogMessage.setBlogUser(blogUser);
		blogMessage.setBlogMessage(messageForm.getBlogMessage());
		getBlogService().createBlogReplyMessage(blogMessage);
		log.debug(DebugHelper.getJSONString(blogMessage));
		List<Object> blogMessages = getBlogService().listBlogReplyMessages(messageForm.getBlogMessage().getId());		
		List<BlogReplyMessage> filteredMessages = getBlogService().filterBlogReplyMessages(blogMessages, blogUser);		
		messageForm.reset();
		messageForm.setMessages(filteredMessages);
		ActionMessage message = new ActionMessage("message.message.reply.created");
		messages.add(ActionMessages.GLOBAL_MESSAGE, message);
		this.saveMessages(request, messages);
		log.debug("<<create()");
		return mapping.findForward(FORWARD_SUCCESS);
	}

	@SuppressWarnings("unchecked")
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug(">>list()");
		ReplyMessageForm messageForm = (ReplyMessageForm) form;
		List blogMessages = getBlogService().listBlogReplyMessages(messageForm.getBlogMessage().getId());
		List<BlogReplyMessage> filteredMessages = getBlogService().filterBlogReplyMessages(blogMessages, StrutsHelper.getBlogUser(request));
		messageForm.reset();
		messageForm.setMessages(filteredMessages);
		for (Iterator iterator = filteredMessages.iterator(); iterator.hasNext();) {
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

	public ActionForward updateList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug(">>updateList()");
		ReplyMessageForm messageForm = (ReplyMessageForm) form;
		ActionMessages messages = new ActionMessages();
		String id = request.getParameter("id");
		if (id == null || id.equals("")) {
			log.debug("Can't get reply message id.");
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.system"));
			messageForm.setMethod(null);
			return mapping.findForward(FORWARD_LOGIN);
		} else {
			messageForm.setMessages(null);
			BlogUser blogUser = StrutsHelper.getBlogUser(request);
			BlogReplyMessage blogMessage = getBlogService().getBlogReplyMessageByID(Long.valueOf(id));
			if (getBlogService().isBlogGroupOwner(blogUser.getId(), blogMessage.getBlogMessage().getBlogGroup().getId()) 
					|| BlogUserType.ADMIN.equals(blogUser.getBlogUserType().getId()) 
					|| (blogMessage.getBlogUser().getId()).equals(blogUser.getId())
					|| BlogUserType.LEADER.equals(blogUser.getBlogUserType().getId())) {
				messageForm.setMessage(HTMLHelper.formatToText(blogMessage.getMessage()));
				messageForm.setLinkURL(blogMessage.getLinkURL());
				messageForm.setUploadName(null);
				messageForm.setUploadFile(null);
				messageForm.setId(Long.valueOf(id));
				messageForm.setUpdateOpt(ReplyMessageForm.UPDATE_MESSAGE);
				messageForm.setBlogReplyMessage(blogMessage);	
				if (blogMessage.isStatus()) messageForm.setStatusOpt(ReplyMessageForm.UPDATE_PUBLISHED);
				else messageForm.setStatusOpt("");		
				messageForm.setMethod("edit");
			} else {
				log.debug("error.message.authority");
				messages.add(StrutsSession.KEY_BUSINESS, new ActionMessage("error.message.authority"));
				messageForm.setId(null);
				messageForm.setMethod(null);
			}
		}
		this.saveMessages(request, messages);
		log.debug("<<updateList()");
		return mapping.findForward(FORWARD_SUCCESS);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug(">>update()");
		ReplyMessageForm messageForm = (ReplyMessageForm) form;
		ActionMessages messages = new ActionMessages();
		Long id = messageForm.getId();
		if (id == null) {
			log.error("Can't get reply message id.");
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.system"));
			return mapping.findForward(FORWARD_LOGIN);
		} else {
			BlogReplyMessage blogMessage = getBlogService().getBlogReplyMessageByID(id);
			String updateOpt = messageForm.getUpdateOpt();
			log.debug("==update option: " + updateOpt);
			if (StringHelper.isNotEmpty(updateOpt) && MessageForm.UPDATE_ALL.equals(updateOpt)) {
				Long mTypeId = blogMessage.getBlogMessageType().getId();
				Long uTypeId = null;
				String uploadName = null;
				blogMessage.setLinkURL(messageForm.getLinkURL());
				uTypeId = this.getUploadFileType(messageForm.getUploadFile());
				if (StringHelper.isNotEmpty(blogMessage.getUploadFile())) {
					this.deleteUploadFile(mTypeId, blogMessage.getUpdateTime(), blogMessage.getUploadFile());
				}
				if (!uTypeId.equals(BlogMessageType.TEXT)) {
					String uploadFilename = messageForm.getUploadFile().getFileName();
					ActionMessage message = new ActionMessage("message.upload.created.confirmed", uploadFilename);
					uploadName = this.saveUploadFile(messageForm.getUploadFile(), uTypeId, "comment");
					messages.add(ActionMessages.GLOBAL_MESSAGE, message);
				} else {
					if (StringHelper.isNotEmpty(messageForm.getLinkURL())) {
						uTypeId = BlogMessageType.LINK;
					} 
				}
				blogMessage.setUploadFile(uploadName);
				BlogMessageType blogMessageType = getBlogService().getBlogMessageTypeByID(uTypeId);
				blogMessage.setBlogMessageType(blogMessageType);
				blogMessage.setUpdateTime(DateHelper.getCurrentTimestamp());
			}
			String statusOpt = (String) request.getParameter("statusOpt");		
			log.debug("==status option: " + statusOpt);
			if (StringHelper.isNotEmpty(statusOpt) && MessageForm.UPDATE_PUBLISHED.equals(statusOpt)) {
				blogMessage.setStatus(true);
			} else {
				blogMessage.setStatus(false);
			}		
			blogMessage.setMessage(HTMLHelper.formatToHTML(messageForm.getMessage()));
			getBlogService().updateBlogReplyMessage(blogMessage);
			getBlogService().increaseReplyMessageUpdateTimes(blogMessage.getId());
			log.debug(DebugHelper.getJSONString(blogMessage));
		}
		List<Object> blogMessages = getBlogService().listBlogReplyMessages(messageForm.getBlogMessage().getId());
		List<BlogReplyMessage> filteredMessages = getBlogService().filterBlogReplyMessages(blogMessages, StrutsHelper.getBlogUser(request));		
		messageForm.reset();
		messageForm.setMessages(filteredMessages);
		this.saveMessages(request, messages);
		log.debug("<<update()");
		return mapping.findForward(FORWARD_SUCCESS);
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		ReplyMessageForm messageForm = (ReplyMessageForm) form;
		String lang = StrutsHelper.getLanguage(request);
		messageForm.setRegion(lang.toUpperCase());
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
