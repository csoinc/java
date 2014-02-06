package fr.improve.struts.taglib.layout.field;

import java.util.Collection;

import javax.servlet.jsp.JspException;

/**
 * The area field tag is a tag with no specific user input field.
 * The field area must be defined by nested tags.
 * 
 * @author JN RIBETTE
 */
public class AreaFieldTag extends AbstractLayoutFieldTag {
	protected int doStartEditField() throws JspException {
		return EVAL_BODY_INCLUDE;
	}

	protected int doEndEditField() throws JspException {
		return EVAL_PAGE;
	}
	
	protected int doStartInspectField() throws JspException {
		return EVAL_BODY_INCLUDE;
	}	

	protected int doEndInspectField() throws JspException {
		return EVAL_PAGE;
	}

	protected Object getFieldValue() throws JspException {
		return null;
	}

	protected void printIndexedHiddenValue(Collection lc_collection) throws JspException {
		// Do nothing : we don't have any property and value. 
	}

	protected void printSimpleHiddenValue(Object in_value) throws JspException {
		// Do nothing :we d'ont have any property and value.
	}

	
}
