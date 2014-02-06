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

import com.oyou.common.exception.BusinessException;
import com.oyou.common.util.StringHelper;
import com.oyou.common.util.StrutsSession;
import com.oyou.domain.blog.SearchPageList;
import com.oyou.domain.blog.orm.BlogMessage;
import com.oyou.web.blog.util.StrutsHelper;

public class MessageSearchAction extends BlogAction {
	private static final Log log = LogFactory.getLog(MessageSearchAction.class);

	@SuppressWarnings("unchecked")
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug(">>list()");
		MessageSearchForm messageForm = (MessageSearchForm) form;
		SearchPageList pageList = messageForm.getPageList();
		if (pageList == null) {
			pageList = new SearchPageList();
		}
		if (pageList.getWord() == null) {
			pageList.setWord("");
		}
		String pageId = request.getParameter("page");
		if (pageId == null) pageList.setNumber(1);
		else pageList.setNumber(Integer.parseInt(pageId));

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
		pageList.setSize(messageForm.getPageMaxLines());
		pageList.setBlogUser(StrutsHelper.getBlogUser(request));
		log.debug("==maxLine=" + maxLinesOpt);
		String orderByOpt = messageForm.getOrderByOpt();
		if (StringHelper.isNotEmpty(orderByOpt)) {
			if (orderByOpt.equals(MessageSearchForm.ORDER_BY_VIEW_TIMES)) {
				messageForm.setOrderByTimes(true);
			} else {
				messageForm.setOrderByTimes(false);
			}
		}	
		pageList = getBlogService().searchBlogMessagePageList(pageList, messageForm.isOrderByTimes());
		messageForm.setPageList(pageList);
		
		List<BlogMessage> messages = pageList.getResultSet();		
		for (Iterator iterator = messages.iterator(); iterator.hasNext();) {
			BlogMessage blogMessage = (BlogMessage) iterator.next();
			getBlogService().increaseMessageViewTimes(blogMessage.getId());
		} 		
		
		log.debug("<<list()");
		return mapping.findForward(FORWARD_SUCCESS);
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug("==unspecified()");
		MessageSearchForm messageForm = (MessageSearchForm) form;
		return list(mapping, messageForm, request, response);
	}
	
}
