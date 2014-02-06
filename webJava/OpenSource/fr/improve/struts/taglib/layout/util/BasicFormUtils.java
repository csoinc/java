/*
 * (c) Copyright 2001 Improve SA.
 * All Rights Reserved.
 */
package fr.improve.struts.taglib.layout.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.struts.action.ActionForm;

import fr.improve.struts.taglib.layout.field.AbstractModeFieldTag;

/**
 * Utility methods to deal with form and field.
 * 
 * @version 	1.0
 * @author
 */
public class BasicFormUtils implements FormUtilsInterface {
	
	/* (non-Javadoc)
	 * @see fr.improve.struts.taglib.layout.util.FormUtilsInterface#computeFieldDisplayMode(javax.servlet.jsp.PageContext, java.lang.String)
	 */
	public short computeFieldDisplayMode(AbstractModeFieldTag in_tag) {
		return computeFieldDisplayMode(in_tag.getPageContext(), in_tag.getMode());
	}
	public short computeFieldDisplayMode(PageContext in_pg, String in_mode) {
		return FormUtils.computeFieldDisplayMode(in_pg, in_mode);
	}

	/* (non-Javadoc)
	 * @see fr.improve.struts.taglib.layout.util.FormUtilsInterface#getFieldDisplayMode(javax.servlet.http.HttpServletRequest, org.apache.struts.action.ActionForm, java.lang.String)
	 */
	public Integer getFieldDisplayMode(
		HttpServletRequest in_request,
		ActionForm in_form,
		String in_fieldName) {
		return FormUtils.getFieldDisplayMode(in_request, in_form, in_fieldName);
	}

	/* (non-Javadoc)
	 * @see fr.improve.struts.taglib.layout.util.FormUtilsInterface#getFieldDisplayMode(javax.servlet.jsp.PageContext, java.lang.String)
	 */
	public Integer getFieldDisplayMode(
		PageContext in_page,
		String in_fieldName) {		
		return FormUtils.getFieldDisplayMode(in_page, in_fieldName);
	}

	/* (non-Javadoc)
	 * @see fr.improve.struts.taglib.layout.util.FormUtilsInterface#getFieldStyle(javax.servlet.jsp.PageContext, java.lang.String)
	 */
	public String getFieldStyle(
		PageContext in_pageContext,
		String in_fieldName) {	
		return FormUtils.getFieldStyle(in_pageContext, in_fieldName);
	}

	/* (non-Javadoc)
	 * @see fr.improve.struts.taglib.layout.util.FormUtilsInterface#getFieldStyleClass(javax.servlet.jsp.PageContext, java.lang.String)
	 */
	public String getFieldStyleClass(AbstractModeFieldTag in_tag) {		
		return FormUtils.getFieldStyleClass(in_tag.getPageContext(), in_tag.getProperty());
	}

	/* (non-Javadoc)
	 * @see fr.improve.struts.taglib.layout.util.FormUtilsInterface#getFieldValueStyle(javax.servlet.jsp.PageContext)
	 */
	public String getFieldValueStyle(PageContext in_pageContext) {		
		return FormUtils.getFieldValueStyle(in_pageContext);
	}

	/* (non-Javadoc)
	 * @see fr.improve.struts.taglib.layout.util.FormUtilsInterface#getFormDisplayMode(javax.servlet.http.HttpServletRequest, org.apache.struts.action.ActionForm)
	 */
	public int getFormDisplayMode(
		HttpServletRequest in_request,
		ActionForm in_form) {		
		return FormUtils.getFormDisplayMode(in_request, in_form);
	}

	/* (non-Javadoc)
	 * @see fr.improve.struts.taglib.layout.util.FormUtilsInterface#getFormDisplayMode(javax.servlet.jsp.PageContext)
	 */
	public int getFormDisplayMode(PageContext in_page) {		
		return FormUtils.getFormDisplayMode(in_page);
	}

}
