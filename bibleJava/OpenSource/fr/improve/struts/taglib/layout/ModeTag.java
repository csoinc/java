package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.util.FormUtilsInterface;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
/**
 * Evaluate the content of the tag in function of the form mode.
 * @author: Jean-Noël Ribette
 */
public class ModeTag extends javax.servlet.jsp.tagext.TagSupport {
	private String value ="";
public int doStartTag() throws JspException {
	int lc_mode = LayoutUtils.getSkin(pageContext.getSession()).getFormUtils().getFormDisplayMode(pageContext);
	boolean lc_include = false;
	switch (lc_mode) {
		case FormUtilsInterface.CREATE_MODE:
			if (value.indexOf("create")!=-1) {
				lc_include = true;
			}
			break;				
		case FormUtilsInterface.EDIT_MODE:
			if (value.indexOf("edit")!=-1) {
				lc_include = true;
			}	
			break;				
		case FormUtilsInterface.INSPECT_MODE:
			if (value.indexOf("inspect")!=-1) {
				lc_include = true;
			}				
			break;				
	}
	
	if (lc_include) {
		return EVAL_BODY_INCLUDE;
	} else {
		return SKIP_BODY;
	}
}
/**
 * Insert the method's description here.
 * Creation date: (02/12/2001 22:53:50)
 * @return java.lang.String
 */
public java.lang.String getValue() {
	return value;
}
	public void release() {
		super.release();
		value = "";
	}
/**
 * Evaluate the body tag if the form mode is in the specified form mode.
 * @param newValue java.lang.String create / edit / inspect.
 */
public void setValue(java.lang.String newValue) {
	if (value!=null) {
		value = newValue.toLowerCase();
	} else {
		value = "";
	}
}
}
