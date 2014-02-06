package com.oyou.web.layout;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.oyou.web.util.StrutsHelper;

import fr.improve.struts.taglib.layout.treeview.TreeviewAction;

public class MenuTreeAction extends TreeviewAction {
	
	public MenuTreeAction() {
		super();
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
     throws IOException, ServletException {
		String path = request.getRequestURI();
		StrutsHelper.setLayoutEncoding(request, response, path);
		ActionForward forward = super.execute(mapping, form, request, response);
		return forward;
	}


}
