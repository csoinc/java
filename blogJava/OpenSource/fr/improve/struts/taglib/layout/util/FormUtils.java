/*
 * (c) Copyright 2001 Improve SA.
 * All Rights Reserved.
 */
package fr.improve.struts.taglib.layout.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.apache.struts.action.ActionForm;
import org.apache.struts.taglib.html.Constants;

import fr.improve.struts.taglib.layout.field.AbstractModeFieldTag;

/**
 * Utility methods to deal with form and field.
 * 
 * @version 	1.0
 * @author
 */
public class FormUtils {
	/**
	 * Key of the map storing the form display modes.
	 */
	private static final String FORM_MODE_KEY = "fr.improve.struts.taglib.layout.util.FormUtils.FORM_MODE_KEY";
	
	/**
	 * Key of the map storing the user form modes.
	 */
	private static final String USER_FORM_MODE_KEY = "fr.improve.struts.taglib.layout.util.FormUtils.USER_FORM_MODE_KEY";

	public static final int CREATE_MODE = 0;
	public static final int EDIT_MODE = 1;	
	public static final int INSPECT_MODE = 2;
	
	/**
	 * Key of the map storing the field styleClasses.
	 */
	private static final String FIELD_STYLECLASS_KEY = "fr.improve.struts.taglib.layout.util.FormUtils.FIELD_STYLECLASS_KEY";

	/**
	 * Key of the map storing the field mode display.
	 */
	private static final String FIELD_MODE_KEY = "fr.improve.struts.taglib.layout.util.FormUtils.FIELD_MODE_KEY";

	/**
	 * Key of the map storing the label field styles.
	 */
	private static final String FIELD_STYLE_KEY = "fr.improve.struts.taglib.layout.util.FormUtils.FIELD_STYLE_KEY";
	
	/**
	 * Key of the list storing the value field styles.
	 */
	private static final String FIELD_VALUES_STYLE_KEY = "fr.improve.struts.taglib.layout.util.FormUtils.FIELD_VALUES_STYLE_KEY";

	/**
	 * Utility method to get the form display modes.
	 */	
	private static Map getFormModes(HttpSession in_session) {
		if (in_session==null) {
			return new HashMap();
		}
		Map lc_map = (Map) in_session.getAttribute(FORM_MODE_KEY);
		if (lc_map==null) {
			lc_map = new HashMap();
			in_session.setAttribute(FORM_MODE_KEY, lc_map);
		}
		return lc_map;
	}
	
	/**
	 * Utility method to get the field display modes.
	 */
	private static Map getFieldModes(HttpSession in_session, ActionForm in_form) {
		Map lc_mapForm = null;
		if (in_session!=null) {
			lc_mapForm = (Map) in_session.getAttribute(FIELD_MODE_KEY);
		}
		if (lc_mapForm == null) {
			lc_mapForm = new HashMap();
			if (in_session!=null) {
				in_session.setAttribute(FIELD_MODE_KEY, lc_mapForm);
			}
		}
		Map lc_mapField = (Map) lc_mapForm.get(in_form.getClass());
		if (lc_mapField == null) {
			lc_mapField = new HashMap();
			lc_mapForm.put(in_form.getClass(), lc_mapField);
		}
		return lc_mapField;
	}
	
	/**
	 * Set the form display mode.
	 * Used in user's Action class.
	 */
	public static void setFormDisplayMode(HttpServletRequest in_request, ActionForm in_form, int in_mode) {		
		if (in_form==null) {
			throw new IllegalArgumentException("in_form cannot be null");
		}
		Map lc_map = getFormModes(in_request.getSession());
		lc_map.put(in_form.getClass(), new Integer(in_mode));
		
		//in_request.getSession().setAttribute(FORM_MODE_KEY,new Integer(in_mode));
	}
	
	/**
	 * Set theform display mode to a user defined mode.
	 *
	 */
	public static void setFormDisplayMode(HttpServletRequest in_request, ActionForm in_form, String in_mode) {
		if (in_form==null) {
			throw new IllegalArgumentException("in_form cannot be null");
		}
		if (in_mode==null || in_mode.trim().length()==0) {
			throw new IllegalArgumentException("in_mode cannot be null or empty");
		}
		Map lc_map = getFormModes(in_request.getSession());
		Integer lc_int = getUserFormMode(in_request, in_mode);
		lc_map.put(in_form.getClass(), lc_int);
	}
	
	private static Integer getUserFormMode(HttpServletRequest in_request, String in_mode) {
		Map lc_map = (Map) in_request.getSession().getAttribute(USER_FORM_MODE_KEY);
		if (lc_map==null) {
			lc_map = new HashMap();
			lc_map.put("create", new Integer(CREATE_MODE));
			lc_map.put("edit", new Integer(EDIT_MODE));
			lc_map.put("inspect", new Integer(INSPECT_MODE));
			in_request.getSession().setAttribute(USER_FORM_MODE_KEY, lc_map);
		}
		Integer lc_int = (Integer) lc_map.get(in_mode);
		if (lc_int==null) {
			lc_int = new Integer(lc_map.size());
			lc_map.put(in_mode, lc_int);
		}
		return lc_int;
	}
	 	
	/**
	 * Set the field display mode.
	 * Used in user's Action class.
	 */
	public static void setFieldDisplayMode(HttpServletRequest in_request, ActionForm in_form, String in_fieldName, int in_mode) {		
		if (in_form == null) {
			throw new IllegalArgumentException("in_form cannot be null");
		}
		if (in_fieldName == null) {
			throw new IllegalArgumentException("in_fieldName cannot be null");
		}
		Map lc_field = getFieldModes(in_request.getSession(), in_form);
		lc_field.put(in_fieldName, new Integer(in_mode));
	}
	
	/**
	 * Get the form display mode
	 */	
	public static int getFormDisplayMode(PageContext in_page) {
		Object lc_form = in_page.findAttribute(Constants.BEAN_KEY);
		if (lc_form == null) {
			// defaut display mode is EDIT.
			return EDIT_MODE;	
		}
		return getFormDisplayMode(in_page.getSession(), (ActionForm) lc_form);
	}
	
	/**
	 * Get the field display mode
	 */	
	public static Integer getFieldDisplayMode(PageContext in_page, String in_fieldName) {
		Object lc_form = in_page.findAttribute(Constants.BEAN_KEY);
		if (lc_form == null) {
			// no default value
			return null;	
		}
		return getFieldDisplayMode(in_page.getSession(), (ActionForm) lc_form, in_fieldName);
	}
	
	/**
	 * Get the form display mode
	 */	
	public static int getFormDisplayMode(HttpServletRequest in_request, ActionForm in_form) {
		return getFormDisplayMode(in_request.getSession(), in_form);
	}
	
	/**
	 * Get the field display mode
	 */	
	public static Integer getFieldDisplayMode(HttpServletRequest in_request, ActionForm in_form, String in_fieldName) {
		return getFieldDisplayMode(in_request.getSession(), in_form, in_fieldName);
	}
	
	/**
	 * Get the form display mode
	 */	
	protected static int getFormDisplayMode(HttpSession in_session, ActionForm in_form) {
		Map lc_map = getFormModes(in_session);
		if (in_form==null) {
			// defaut display mode is EDIT.
			return EDIT_MODE;	
		}
		Integer lc_mode = (Integer) lc_map.get(in_form.getClass());
		if (lc_mode==null) {
			// defaut display mode is EDIT.
			return EDIT_MODE;	
		}
		return lc_mode.intValue();
	}

	/**
	 * Get the field display mode
	 */	
	protected static Integer getFieldDisplayMode(HttpSession in_session, ActionForm in_form, String in_fieldName) {
		if (in_form == null) {
			// no default value
			return null;	
		}
		if (in_fieldName == null) {
			// no default value
			return null;	
		}
		Map lc_map = getFieldModes(in_session, in_form);
		Integer lc_mode = (Integer) lc_map.get(in_fieldName);
		if (lc_mode == null) {
			// no default value
			return null;	
		}
		return lc_mode;
	}
	/**
	 * Compute a field display mode.
	 * Used in custom tags
	 */
	public static short computeFieldDisplayMode(PageContext in_pageContext, String in_mode) {
		int lc_formMode = getFormDisplayMode(in_pageContext);
		if (in_mode==null) {
			throw new IllegalArgumentException("The specified mode is null");
		}
		if (in_mode.indexOf(':')==-1) {
			// Old format create/edit/inspect
			if (lc_formMode>=3) {
				// Default mode is inspect for user defined mode.
				return AbstractModeFieldTag.MODE_INSPECT;
			} else {
				int lc_modePosition = lc_formMode*2;
				if (in_mode.length()<lc_modePosition) {
					throw new IllegalArgumentException("Bad form / field display mode (" + lc_formMode + ";" + in_mode);
				}
				char lc_char = in_mode.charAt(lc_modePosition);
				switch (Character.toUpperCase(lc_char)) {
					case 'E': return AbstractModeFieldTag.MODE_EDIT;
					case 'I': return AbstractModeFieldTag.MODE_INSPECT;
					case 'H': return AbstractModeFieldTag.MODE_HIDDEN;
					case 'N': return AbstractModeFieldTag.MODE_NODISPLAY;
					case 'S': return AbstractModeFieldTag.MODE_INSPECT_ONLY;
					case 'P': return AbstractModeFieldTag.MODE_INSPECT_PRESENT;
					case 'R': return AbstractModeFieldTag.MODE_READONLY;
					case 'D': return AbstractModeFieldTag.MODE_DISABLED;
					case 'C' : return AbstractModeFieldTag.MODE_CELL;
				}
				throw new IllegalArgumentException("Bad field display mode " + lc_char);
			}
		} else{
			// New format mode:value,mode:value,...
			StringTokenizer tokenizer = new StringTokenizer(in_mode, ",;:");
			while (tokenizer.hasMoreTokens()) {
				String lc_mode = tokenizer.nextToken();
				if (!tokenizer.hasMoreTokens()) {
					throw new IllegalArgumentException("The specified mode " + in_mode + " is invalid.");
				}
				String lc_fieldMode = tokenizer.nextToken();				
				if (((Integer)getUserFormMode((HttpServletRequest)in_pageContext.getRequest(), lc_mode)).intValue()==lc_formMode) { 
					switch (Character.toUpperCase(lc_fieldMode.charAt(0))) {
					case 'E': return AbstractModeFieldTag.MODE_EDIT;
					case 'I': return AbstractModeFieldTag.MODE_INSPECT;
					case 'H': return AbstractModeFieldTag.MODE_HIDDEN;
					case 'N': return AbstractModeFieldTag.MODE_NODISPLAY;
					case 'S': return AbstractModeFieldTag.MODE_INSPECT_ONLY;
					case 'P': return AbstractModeFieldTag.MODE_INSPECT_PRESENT;
					case 'R': return AbstractModeFieldTag.MODE_READONLY;
					case 'D': return AbstractModeFieldTag.MODE_DISABLED;
					case 'C' : return AbstractModeFieldTag.MODE_CELL;
				}
				}				
			}			
			// Mode is not specified, return correct default value.
			switch (lc_formMode) {
				case CREATE_MODE:
				case EDIT_MODE: 
					return AbstractModeFieldTag.MODE_EDIT;
				default:
					return AbstractModeFieldTag.MODE_INSPECT;
			}
		}
	}
	
	/**
	 * Compute a tag visibility.
	 * Used in custom tags.
	 */
	public static int computeVisibilityMode(PageContext in_pageContext, String in_mode) {
		int lc_formMode = getFormDisplayMode(in_pageContext);
		char lc_visible = 'N';
		if (in_mode == null) {
			throw new IllegalArgumentException("The specified mode is null");
		}
		if (in_mode.indexOf(':')==-1) {
			// Old format.
			if(lc_formMode>=3) {
				// Default mode is not visible.
				return AbstractModeFieldTag.MODE_NODISPLAY;
			} else {
				if (in_mode == null || in_mode.length() != 5) {
					throw new IllegalArgumentException(
						"The specified mode" + in_mode + " is invalid");
				}
				char lc_displayMode;
				switch (lc_formMode) {
					case FormUtilsInterface.CREATE_MODE :
						lc_displayMode = in_mode.charAt(0);
						break;
					case FormUtilsInterface.EDIT_MODE :
						lc_displayMode = in_mode.charAt(2);
						break;
					case FormUtilsInterface.INSPECT_MODE :
						lc_displayMode = in_mode.charAt(4);
						break;
					default :
						lc_displayMode = 'D';
				}
				lc_visible = lc_displayMode;
			}
		} else {
			// Get the current mode..
			StringTokenizer tokenizer = new StringTokenizer(in_mode, ",;:");
			while (tokenizer.hasMoreTokens()) {
				String lc_mode = tokenizer.nextToken();
				if (!tokenizer.hasMoreTokens()) {
					throw new IllegalArgumentException("The specified mode " + in_mode + " is invalid.");
				}
				String lc_visibility = tokenizer.nextToken();				
				if (((Integer)getUserFormMode((HttpServletRequest)in_pageContext.getRequest(), lc_mode)).intValue()==lc_formMode) { 
					lc_visible = lc_visibility.charAt(0);
					break;
				}				
			}
		}
		
		if (lc_visible=='D'||lc_visible=='d') {
			return AbstractModeFieldTag.MODE_EDIT;
		} else if (lc_visible=='F' || lc_visible=='f') {
			return AbstractModeFieldTag.MODE_DISABLED;
		} else if (lc_visible=='C' || lc_visible=='c') {
			return AbstractModeFieldTag.MODE_CELL;
		}
		return AbstractModeFieldTag.MODE_NODISPLAY;
	}
	
	private static Map getFieldStyleClass(HttpServletRequest in_request) {
		Map lc_map = (Map) in_request.getAttribute(FIELD_STYLECLASS_KEY);
		if (lc_map==null) {
			lc_map = new HashMap();
			in_request.setAttribute(FIELD_STYLECLASS_KEY, lc_map);
		}
		return lc_map;
	}
	
	
	private static Map getFieldStyle(HttpServletRequest in_request) {
		Map lc_map = (Map) in_request.getAttribute(FIELD_STYLE_KEY);
		if (lc_map==null) {
			lc_map = new HashMap();
			in_request.setAttribute(FIELD_STYLE_KEY, lc_map);
		}
		return lc_map;
	}	
	
	/**
	 * Override a field styleClass.
	 * Used in user's Action class.
	 */
	public static void setFieldStyleClass(HttpServletRequest in_request, String in_fieldName, String in_styleClass) {
		getFieldStyleClass(in_request).put(in_fieldName, in_styleClass);
	}
	
	/**
	 * Override a field style.
	 * Used in user's Action class.
	 */
	public static void setFieldStyle(HttpServletRequest in_request, String in_fieldName, String in_style) {
		getFieldStyle(in_request).put(in_fieldName, in_style);
	}	

	/**
	 * Get a field styleClass.
	 * Used in custom tags.
	 */
	public static String getFieldStyleClass(PageContext in_pageContext, String in_fieldName) {
		return (String) getFieldStyleClass((HttpServletRequest)in_pageContext.getRequest()).get(in_fieldName);
	}
	
	/**
	 * Get a field style.
	 * Used in custom tags.
	 */
	public static String getFieldStyle(PageContext in_pageContext, String in_fieldName) {
		return (String) getFieldStyle((HttpServletRequest)in_pageContext.getRequest()).get(in_fieldName);
	}
	
	/**
	 * Get the style for the current field value.
	 */
	public static String getFieldValueStyle(PageContext in_pageContext) {
		List lc_list = (List) in_pageContext.getRequest().getAttribute(FIELD_VALUES_STYLE_KEY);
		if (lc_list==null || lc_list.isEmpty()) {
			return ""; 	
		} else {
			StringBuffer lc_style = new StringBuffer();
			for (int i=0;i< lc_list.size();i++) {
					lc_style.append(lc_list.get(i));
			}
			return lc_style.toString();
		}
	}
	
	public static void addFieldValueStyle(PageContext in_pageContext, String in_style) {
		List lc_list = (List) in_pageContext.getRequest().getAttribute(FIELD_VALUES_STYLE_KEY);
		if (lc_list==null) {
			lc_list = new ArrayList();	
			in_pageContext.getRequest().setAttribute(FIELD_VALUES_STYLE_KEY, lc_list);
		}
		lc_list.add(in_style);
	}
	public static void removeFieldValueStyle(PageContext in_pageContext) {
		List lc_list = (List) in_pageContext.getRequest().getAttribute(FIELD_VALUES_STYLE_KEY);
		lc_list.remove(lc_list.size()-1);
	}

}
