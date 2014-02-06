package com.oyou.web.blog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
import com.oyou.common.util.DateHelper;
import com.oyou.common.util.DebugHelper;
import com.oyou.common.util.StringHelper;
import com.oyou.common.util.StrutsSession;
import com.oyou.domain.blog.orm.BlogGroup;
import com.oyou.domain.blog.orm.BlogGroupType;
import com.oyou.domain.blog.orm.BlogLanguageType;
import com.oyou.domain.blog.orm.BlogUser;
import com.oyou.domain.blog.orm.BlogUserType;
import com.oyou.web.blog.resources.CNWords;
import com.oyou.web.blog.resources.TWWords;
import com.oyou.web.blog.resources.Words;
import com.oyou.web.blog.util.StrutsHelper;
import com.oyou.web.view.Selection;

public class GroupAction extends BlogAction {
	private static final Log log = LogFactory.getLog(GroupAction.class);
	private static final String ENTRY_ACTION = "group";

	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws BusinessException {
		log.debug(">>create()");
		GroupForm groupForm = (GroupForm) form;
		ActionMessages messages = new ActionMessages();
		BlogUser blogUser = StrutsHelper.getBlogUser(request);
		
		if (groupForm.getGroupName() == null || "".equals(groupForm.getGroupName())) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.required", "Group Name"));
			StrutsHelper.setPathInfo(request, ENTRY_ACTION);
			request.setAttribute(Globals.MESSAGE_KEY, messages);
			return list(mapping, groupForm, request, response);
			//return mapping.findForward(ENTRY_ACTION);
		}

		if (blogUser == null) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.login.required"));
			StrutsHelper.setPathInfo(request, ENTRY_ACTION);
			request.setAttribute(Globals.MESSAGE_KEY, messages);
			return mapping.findForward(FORWARD_LOGIN);
		}
		if (!BlogUserType.ADMIN.equals(blogUser.getBlogUserType().getId())
				&& !BlogUserType.LEADER.equals(blogUser.getBlogUserType().getId())) {
			log.debug("error.message.authority");
			messages.add(StrutsSession.KEY_BUSINESS, new ActionMessage("error.function.authority"));
			this.saveMessages(request, messages);
			return mapping.findForward(FORWARD_FAILED);
		}
		BlogGroup blogGroup = this.getBlogGroup(groupForm);
		String languageOpt = groupForm.getLanguageOpt();
		if (StringHelper.isNotEmpty(languageOpt)) {
			groupForm.setLanguage(Long.parseLong(languageOpt));
		} else {
			groupForm.setLanguage(BlogLanguageType.CANTONESE);
		}
		BlogLanguageType lType = getBlogService().getBlogLanguageTypeByID(groupForm.getLanguage());
		blogGroup.setBlogLanguageType(lType);
		String groupTypeOpt = groupForm.getGroupTypeOpt();
		if (StringHelper.isNotEmpty(groupTypeOpt)) {
			groupForm.setGroupType(Long.parseLong(groupTypeOpt));
		} else {
			groupForm.setGroupType(BlogGroupType.GROUP_HOT);
		}
		BlogGroupType gType = getBlogService().getBlogGroupTypeByID(groupForm.getGroupType());
		blogGroup.setBlogGroupType(gType);
		getBlogService().createBlogGroup(blogGroup, blogUser);
		log.debug(DebugHelper.getJSONString(blogGroup));
		List groups = getBlogService().listBlogGroupsByGroupTypeID(groupForm.getGroupType());
		groupForm.reset();
		groupForm.setMethod("create");
		groupForm.setGroups(groups);
		ActionMessage message = null;
		message = new ActionMessage("message.group.created", groupForm.getGroupName());
		messages.add(ActionMessages.GLOBAL_MESSAGE, message);
		this.saveMessages(request, messages);
		log.debug("<<create()");
		return mapping.findForward(FORWARD_SUCCESS);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws BusinessException {
		log.debug(">>list()");
		GroupForm groupForm = (GroupForm) form;
		String typeId = (request.getParameter("typeid") == null ? request.getParameter("tid") : request.getParameter("typeid"));
		List groups = null;
		if (StringHelper.isEmpty(typeId)) {
			typeId = StrutsHelper.getCategory(request.getSession());
			if (StringHelper.isEmpty(typeId)) {
				long maxId = this.getCommonService().getObjectMaxID(BlogGroupType.class);
				Random random = new Random();
				int i = random.nextInt(Integer.parseInt(Long.toString(maxId - 1)));
				typeId = Integer.toString(i + 1);
				StrutsHelper.setCategory(request, typeId);
			} else {
				StrutsHelper.setCategory(request, typeId);
			}
		} else {
			StrutsHelper.setCategory(request, typeId);
		}
		groups = getBlogService().listBlogGroupsByGroupTypeID(Long.parseLong(typeId));
		BlogUser blogUser = StrutsHelper.getBlogUser(request);
		if (blogUser == null) {
			groupForm.setMethod("login");
		} else {
			groupForm.setMethod("create");
		}
		groupForm.setGroups(groups);
		log.debug("<<list()");
		return mapping.findForward(FORWARD_SUCCESS);
	}

	public ActionForward updateList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws BusinessException {
		log.debug(">>updateList()");
		GroupForm groupForm = (GroupForm) form;
		ActionMessages messages = new ActionMessages();
		String id = request.getParameter("id");
		if (id == null || id.equals("")) {
			messages.add(StrutsSession.KEY_BUSINESS, new ActionMessage("error.system"));
			return mapping.findForward(FORWARD_LOGIN);
		} else {
			BlogUser blogUser = StrutsHelper.getBlogUser(request);
			if (blogUser == null) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.login.required"));
				StrutsHelper.setPathInfo(request, ENTRY_ACTION);
				request.setAttribute(Globals.MESSAGE_KEY, messages);
				return mapping.findForward(FORWARD_LOGIN);
			}
			if (BlogUserType.ADMIN.equals(blogUser.getBlogUserType().getId())
					|| getBlogService().isBlogGroupOwner(blogUser.getId(), Long.valueOf(id))
					|| BlogUserType.LEADER.equals(blogUser.getBlogUserType().getId())) {
				BlogGroup blogGroup = getBlogService().getBlogGroupByID(Long.valueOf(id));
				groupForm.setGroupName(blogGroup.getGroupName());
				groupForm.setDescription(blogGroup.getDescription());
				groupForm.setId(Long.valueOf(id));
				groupForm.setBlogGroup(blogGroup);
				groupForm.setGroups(null);
				groupForm.setLanguage(blogGroup.getBlogLanguageType().getId());
				groupForm.setLanguageOpt(blogGroup.getBlogLanguageType().getId().toString());
				groupForm.setGroupType(blogGroup.getBlogGroupType().getId());
				groupForm.setGroupTypeOpt(blogGroup.getBlogGroupType().getId().toString());
				groupForm.setUpdateOpt(GroupForm.UPDATE_MESSAGE);
				groupForm.setMethod("edit");
			} else {
				messages.add(StrutsSession.KEY_BUSINESS, new ActionMessage("error.group.authority"));
				groupForm.setId(null);
				groupForm.setGroupName(null);
				groupForm.setDescription(null);
				groupForm.setMethod("create");
			}
		}
		this.saveMessages(request, messages);
		log.debug("<<updateList()");
		return mapping.findForward(FORWARD_SUCCESS);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws BusinessException {
		log.debug(">>update()");
		GroupForm groupForm = (GroupForm) form;
		ActionMessages messages = new ActionMessages();
		Long id = groupForm.getId();
		if (id == null) {
			log.error("Can't get group id.");
			messages.add(StrutsSession.KEY_BUSINESS, new ActionMessage("error.system"));
			return mapping.findForward(FORWARD_LOGIN);
		} else {
			BlogGroup blogGroup = getBlogService().getBlogGroupByID(id);
			String updateOpt = groupForm.getUpdateOpt();
			log.debug("==update option: " + updateOpt);
			if (StringHelper.isNotEmpty(updateOpt) && GroupForm.UPDATE_ALL.equals(updateOpt)) {
				String languageOpt = groupForm.getLanguageOpt();
				if (StringHelper.isNotEmpty(languageOpt)) {
					groupForm.setLanguage(Long.parseLong(languageOpt));
				} else {
					groupForm.setLanguage(BlogLanguageType.CANTONESE);
				}
				BlogLanguageType lType = getBlogService().getBlogLanguageTypeByID(groupForm.getLanguage());
				blogGroup.setBlogLanguageType(lType);

				String groupTypeOpt = groupForm.getGroupTypeOpt();
				if (StringHelper.isNotEmpty(groupTypeOpt)) {
					groupForm.setGroupType(Long.parseLong(groupTypeOpt));
				} else {
					groupForm.setGroupType(BlogGroupType.GROUP_HOT);
				}
				BlogGroupType gType = getBlogService().getBlogGroupTypeByID(groupForm.getGroupType());
				blogGroup.setBlogGroupType(gType);
				blogGroup.setGroupName(groupForm.getGroupName());
				blogGroup.setUpdateTime(DateHelper.getCurrentTimestamp());
			}
			blogGroup.setDescription(groupForm.getDescription());
			log.debug(DebugHelper.getJSONString(blogGroup));
			getBlogService().updateBlogGroup(blogGroup);
		}
		List groups = getBlogService().listBlogGroupsByGroupTypeID(groupForm.getGroupType());
		groupForm.reset();
		groupForm.setMethod("create");
		groupForm.setGroups(groups);
		this.saveMessages(request, messages);
		log.debug("<<update()");
		return mapping.findForward(FORWARD_SUCCESS);
	}

	protected BlogGroup getBlogGroup(GroupForm groupForm) {
		BlogGroup blogGroup = new BlogGroup();
		blogGroup.setCreateTime(DateHelper.getCurrentTimestamp());
		blogGroup.setStatus(true);
		blogGroup.setUpdateTime(DateHelper.getCurrentTimestamp());
		blogGroup.setGroupName(groupForm.getGroupName());
		blogGroup.setDescription(groupForm.getDescription());
		return blogGroup;
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws BusinessException {
		GroupForm groupForm = (GroupForm) form;
		groupForm.reset();
		String lang = StrutsHelper.getLanguage(request);
		if (StringHelper.isEmpty(lang))
			StrutsHelper.setLanguage(request, StrutsSession.LANGUAGE_EN);
		groupForm.setRegion(lang.toUpperCase());
		groupForm.initImageAsIconOptList(lang);
		groupForm.initLanguageOptList(lang);
		groupForm.initOrderByOptList(lang);
		groupForm.initPageMaxLinesOptList(lang);
		List<Selection> list = new ArrayList<Selection>();
		Selection sel;
		if (StrutsSession.LANGUAGE_EN.equalsIgnoreCase(lang)) {
			sel = new Selection("", Words.SELECTION);
		} else if (StrutsSession.LANGUAGE_CN.equalsIgnoreCase(lang)) {
			sel = new Selection("", CNWords.SELECTION);
		} else {
			sel = new Selection("", TWWords.SELECTION);
		}
		list.add(sel);
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
		groupForm.setGroupType(BlogGroupType.GROUP_HOT);
		groupForm.setGroupTypeOpt(BlogGroupType.GROUP_HOT.toString());
		groupForm.setGroupTypeOptList(list);
		return list(mapping, groupForm, request, response);
	}

}
