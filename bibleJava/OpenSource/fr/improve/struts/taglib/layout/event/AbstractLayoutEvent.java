package fr.improve.struts.taglib.layout.event;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.LayoutTag;


/**
 * This class defines the struts-layout event. It can be used to easier communication between layout tags.
 * 
 * @author jnribette
 */
public abstract class AbstractLayoutEvent {
	/**
	 * The layout tag that created the event.
	 */
	protected LayoutTag source;
	
	/**
	 * The event associated value.
	 */
	protected Object value;   
		
	protected AbstractLayoutEvent(LayoutTag in_source, Object in_value) {
		source = in_source;
		value = in_value;	
	}
	public LayoutTag getSource() {
		return source;	
	}
	public Object getValue() {
		return value;	
	}
	/**
	 * Send the event.
	 * Depends of the event implementation.
	 */
	public abstract Object send() throws JspException;
}
