/*
 * Created on 17 oct. 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.improve.struts.taglib.layout.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.struts.action.ActionForm;

import fr.improve.struts.taglib.layout.field.AbstractModeFieldTag;

/**
 * @author slave06e
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface FormUtilsInterface {
	public static final int CREATE_MODE = 0;
	public static final int EDIT_MODE = 1;	
	public static final int INSPECT_MODE = 2;
	
	/**
	 * Get the form display mode
	 */
	public abstract int getFormDisplayMode(PageContext in_page);
	/**
	 * Get the field display mode
	 */
	public abstract Integer getFieldDisplayMode(
		PageContext in_page,
		String in_fieldName);
	/**
	 * Get the form display mode
	 */
	public abstract int getFormDisplayMode(
		HttpServletRequest in_request,
		ActionForm in_form);
	/**
	 * Get the field display mode
	 */
	public abstract Integer getFieldDisplayMode(
		HttpServletRequest in_request,
		ActionForm in_form,
		String in_fieldName);
	/**
	 * Compute a field display mode.
	 * Used in custom tags
	 */
	public abstract short computeFieldDisplayMode(AbstractModeFieldTag in_tag);
	public abstract short computeFieldDisplayMode(PageContext in_pg, String in_mode);
	
	/**
	 * Get a field styleClass.
	 * Used in custom tags.
	 */
	public abstract String getFieldStyleClass(AbstractModeFieldTag in_tag);
	/**
	 * Get a field style.
	 * Used in custom tags.
	 */
	public abstract String getFieldStyle(
		PageContext in_pageContext,
		String in_fieldName);
	/**
	 * Get the style for the current field value.
	 */
	public abstract String getFieldValueStyle(PageContext in_pageContext);
}