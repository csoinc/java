package com.oyou.web.blog;

import java.util.ArrayList;
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
import com.oyou.domain.blog.MessagePageList;
import com.oyou.domain.blog.orm.BlogGroup;
import com.oyou.domain.blog.orm.BlogGroupType;
import com.oyou.domain.blog.orm.BlogMessage;
import com.oyou.domain.blog.orm.BlogMessageType;
import com.oyou.domain.blog.orm.BlogUser;
import com.oyou.domain.blog.orm.BlogUserType;
import com.oyou.web.blog.util.StrutsHelper;
import com.oyou.web.view.Selection;

public class MessageAction extends BlogAction {
	private static final Log log = LogFactory.getLog(MessageAction.class);

	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug(">>create()");
		MessageForm messageForm = (MessageForm) form;
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
		BlogMessage blogMessage = new BlogMessage();
		blogMessage.setLinkURL(messageForm.getLinkURL());
		uTypeId = this.getUploadFileType(messageForm.getUploadFile());
		if (!uTypeId.equals(BlogMessageType.TEXT)) {
			String uploadFilename = messageForm.getUploadFile().getFileName();
			ActionMessage message = new ActionMessage("message.upload.created.confirmed", uploadFilename);
			uploadName = this.saveUploadFile(messageForm.getUploadFile(), uTypeId, messageForm.getTitle());
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
		if (StringHelper.isNotEmpty(statusOpt) && MessageForm.UPDATE_PUBLISHED.equals(statusOpt)) {
			blogMessage.setStatus(true);
		} else {
			blogMessage.setStatus(false);
		}
		blogMessage.setUpdateTime(DateHelper.getCurrentTimestamp());
		blogMessage.setTitle(messageForm.getTitle());
		blogMessage.setSummary(HTMLHelper.formatToHTML(messageForm.getSummary()));
		blogMessage.setMessage(HTMLHelper.formatToHTML(messageForm.getMessage()));
		blogMessage.setBlogUser(blogUser);
		blogMessage.setBlogGroup(messageForm.getBlogGroup());
		
		getBlogService().createBlogMessage(blogMessage);
		log.debug(DebugHelper.getJSONString(blogMessage));
		MessagePageList pageList = messageForm.getPageList();
		pageList = getBlogService().listBlogMessagePageList(pageList);
		messageForm.reset();
		messageForm.setPageList(pageList);
		ActionMessage message = new ActionMessage("message.message.created");
		messages.add(ActionMessages.GLOBAL_MESSAGE, message);
		this.saveMessages(request, messages);
		log.debug("<<create()");
		return mapping.findForward(FORWARD_SUCCESS);
	}

	@SuppressWarnings("unchecked")
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug(">>list()");
		MessageForm messageForm = (MessageForm) form;
		MessagePageList pageList = messageForm.getPageList();
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
		log.debug("==maxLine=" + maxLinesOpt);
		pageList.setSize(messageForm.getPageMaxLines());
		pageList = getBlogService().listBlogMessagePageList(pageList);
		messageForm.reset();
		messageForm.setPageList(pageList);

		List<BlogMessage> messages = pageList.getResultSet();		
		for (Iterator iterator = messages.iterator(); iterator.hasNext();) {
			BlogMessage blogMessage = (BlogMessage) iterator.next();
			getBlogService().increaseMessageViewTimes(blogMessage.getId());
		} 		
		log.debug("<<list()");
		return mapping.findForward(FORWARD_SUCCESS);
	}

	public ActionForward updateList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug(">>updateList()");
		MessageForm messageForm = (MessageForm) form;
		ActionMessages messages = new ActionMessages();
		String id = request.getParameter("id");
		if (id == null || id.equals("")) {
			Long mid = messageForm.getId();
			if (mid != null) id = mid.toString();
		}
		if (id == null || id.equals("")) {
			log.debug("Can't get message id.");
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.system"));
			messageForm.setMethod(null);
			return mapping.findForward(FORWARD_LOGIN);
		} else {
			messageForm.getPageList().setResultSet(null);
			BlogUser blogUser = StrutsHelper.getBlogUser(request);
			BlogMessage blogMessage = getBlogService().getBlogMessageByID(Long.valueOf(id));
			if (getBlogService().isBlogGroupOwner(blogUser.getId(), blogMessage.getBlogGroup().getId()) 
					|| BlogUserType.ADMIN.equals(blogUser.getBlogUserType().getId()) 
					|| (blogMessage.getBlogUser().getId()).equals(blogUser.getId())
					|| BlogUserType.LEADER.equals(blogUser.getBlogUserType().getId())) {
				messageForm.setTitle(blogMessage.getTitle());
				messageForm.setSummary(HTMLHelper.formatToText(blogMessage.getSummary()));
				messageForm.setMessage(HTMLHelper.formatToText(blogMessage.getMessage()));
				messageForm.setLinkURL(blogMessage.getLinkURL());
				messageForm.setUploadName(null);
				messageForm.setUploadFile(null);
				messageForm.setId(Long.valueOf(id));
				messageForm.setUpdateOpt(MessageForm.UPDATE_MESSAGE);
				messageForm.setBlogMessage(blogMessage);
				messageForm.setBlogGroup(blogMessage.getBlogGroup());
				if (blogMessage.isStatus()) messageForm.setStatusOpt(MessageForm.UPDATE_PUBLISHED);
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
		MessageForm messageForm = (MessageForm) form;
		ActionMessages messages = new ActionMessages();
		Long id = messageForm.getId();
		if (id == null) {
			log.error("Can't get message id.");
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.system"));
			return mapping.findForward(FORWARD_LOGIN);
		} else {
			BlogMessage blogMessage = getBlogService().getBlogMessageByID(id);
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
					uploadName = this.saveUploadFile(messageForm.getUploadFile(), uTypeId, messageForm.getTitle());
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
			
			blogMessage.setTitle(messageForm.getTitle());
			blogMessage.setSummary(HTMLHelper.formatToHTML(messageForm.getSummary()));
			blogMessage.setMessage(HTMLHelper.formatToHTML(messageForm.getMessage()));
			
			BlogGroup blogGroup = getBlogService().getBlogGroupByID(Long.valueOf(messageForm.getGroupOpt()));
			blogMessage.setBlogGroup(blogGroup);
			
			getBlogService().updateBlogMessage(blogMessage);
			getBlogService().increaseMessageUpdateTimes(blogMessage.getId());
			log.debug(DebugHelper.getJSONString(blogMessage));
		}
		MessagePageList pageList = messageForm.getPageList();
		pageList = getBlogService().listBlogMessagePageList(pageList);
		messageForm.reset();
		messageForm.setPageList(pageList);
		this.saveMessages(request, messages);
		log.debug("<<update()");
		return mapping.findForward(FORWARD_SUCCESS);
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug(">>unspecified()");
		MessageForm messageForm = (MessageForm) form;
		String lang = StrutsHelper.getLanguage(request);
		messageForm.setRegion(lang.toUpperCase());
		ActionMessages messages = new ActionMessages();

		String groupId = request.getParameter("gid");
		if (groupId == null) {
			BlogGroup blogGroup = messageForm.getBlogGroup();
			if (blogGroup == null) {
				log.error("Can't get group id.");
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.system"));
				this.saveMessages(request, messages);
				return mapping.findForward(FORWARD_LOGIN);
			} else {
				String groupTypeId = messageForm.getGroupType().toString();
				String groupTypeOptId = messageForm.getGroupTypeOpt();
				if (!groupTypeId.equals(groupTypeOptId)) {
					List<Selection> list = new ArrayList<Selection>();
					List<Object> groups = getBlogService().listBlogGroupsByGroupTypeID(Long.valueOf(groupTypeOptId));
					if (groups.size() > 0) {
						BlogGroup group = (BlogGroup)groups.get(0);
						messageForm.setGroup(group.getId());
						messageForm.setGroupOpt(group.getId().toString());
					}
					Selection sel;
					for (Iterator<Object> iter = groups.iterator(); iter.hasNext();) {
						BlogGroup group = (BlogGroup) iter.next();
						sel = new Selection(group.getId().toString(), group.getGroupName());
						list.add(sel);
					}
					messageForm.setGroupOptList(list);
					log.debug("<<unspecified()");
					return updateList(mapping, messageForm, request, response);
				}
			}
		} else {
			messageForm.reset();
			BlogGroup blogGroup = getBlogService().getBlogGroupByID(Long.valueOf(groupId));
			messageForm.setBlogGroup(blogGroup);
			messageForm.setGroupType(blogGroup.getBlogGroupType().getId());
			messageForm.setGroupTypeOpt(blogGroup.getBlogGroupType().getId().toString());
			messageForm.setGroup(blogGroup.getId());
			messageForm.setGroupOpt(blogGroup.getId().toString());

			List<Selection> list = new ArrayList<Selection>();
			Selection sel;
			List<Object> gTypes = getBlogService().listBlogGroupTypes();
			for (Iterator<Object> iter = gTypes.iterator(); iter.hasNext();) {
				BlogGroupType gType = (BlogGroupType) iter.next();
				if (StrutsSession.LANGUAGE_EN.equalsIgnoreCase(lang)) {
					sel = new Selection(gType.getId().toString(), gType.getName());
				} else if (StrutsSession.LANGUAGE_CN.equalsIgnoreCase(lang)) {
					sel = new Selection(gType.getId().toString(), gType.getNameCN());
				} else {
					sel = new Selection(gType.getId().toString(), gType.getNameTW());
				}
				list.add(sel);
			}
			messageForm.setGroupType(blogGroup.getBlogGroupType().getId());
			messageForm.setGroupTypeOpt(blogGroup.getBlogGroupType().getId().toString());
			messageForm.setGroupTypeOptList(list);
			
			list = new ArrayList<Selection>();
			List<Object> groups = getBlogService().listBlogGroupsByGroupTypeID(blogGroup.getBlogGroupType().getId());
			for (Iterator<Object> iter = groups.iterator(); iter.hasNext();) {
				BlogGroup group = (BlogGroup) iter.next();
				sel = new Selection(group.getId().toString(), group.getGroupName());
				list.add(sel);
			}
			messageForm.setGroupOptList(list);
		}
		MessagePageList pageList = messageForm.getPageList();
		if (pageList == null) {
			pageList = new MessagePageList();
		}
		String pageId = request.getParameter("page");
		if (pageId == null) pageList.setNumber(1);
		else pageList.setNumber(Integer.parseInt(pageId));
		pageList.setSize(messageForm.getPageMaxLines());
		pageList.setGroupId(messageForm.getBlogGroup().getId());
		pageList.setBlogUser(StrutsHelper.getBlogUser(request));
        messageForm.setPageList(pageList);
		log.debug("<<unspecified()");
		return list(mapping, messageForm, request, response);
	}
}
