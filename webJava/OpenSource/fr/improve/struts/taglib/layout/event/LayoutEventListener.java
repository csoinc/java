package fr.improve.struts.taglib.layout.event;

import javax.servlet.jsp.JspException;

/**
 * Interface implemented by struts-layout tags that want to collaborate.
 * @author jnribette
 */
public interface LayoutEventListener {
	/**
	 * Process a StartLayoutEvent.
	 * If the listener generates <tr> tag it must also write the event value to the out flow and return Boolean.TRUE
	 * However it must return Boolean.FALSE
	 */
	public Object processStartLayoutEvent(StartLayoutEvent in_event) throws JspException;
	/**
	 * Process a EndLayoutEvent.
	 * If the listener generates </tr> tag it must also write the event value to the out flow and return Boolean.TRUE
	 * However it must return Boolean.FALSE
	 */
	public Object processEndLayoutEvent(EndLayoutEvent in_event) throws JspException;
}
