package com.oyou.web.layout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.oyou.web.util.StrutsHelper;

import fr.improve.struts.taglib.layout.sort.SortAction;

public class PageSortAction extends SortAction {

	public PageSortAction() {
		super();
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String path = request.getRequestURI();
		StrutsHelper.setLayoutEncoding(request, response, path);
		ActionForward forward = super.execute(mapping, form, request, response);
		return forward;
	}

}
