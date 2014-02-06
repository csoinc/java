package fr.improve.struts.taglib.layout.event;

import javax.servlet.jsp.JspException;

/**
 * @author JN Ribette
 */
public interface StaticCodeIncludeListener {
	public Object processStaticCodeIncludeEvent(StaticCodeIncludeLayoutEvent in_event) throws JspException;
}
