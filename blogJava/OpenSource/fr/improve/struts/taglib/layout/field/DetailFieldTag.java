/*
 * Created on 30 mars 2004
 *
 * Copyright Improve SA 2004.
 * All rights reserved.
 */
package fr.improve.struts.taglib.layout.field;

import javax.servlet.jsp.JspException;

/**
 * @author jnribette
 */
public class DetailFieldTag extends TextFieldTag {

	public DetailFieldTag() {
		setMode("R,R,R");
	}
	
	protected Object getFieldValue() throws JspException {
		// No initial value.
		return "";
	}
	
	public void release() {
		setMode("R,R,R");
	}
}
