package fr.improve.struts.taglib.layout.event;

import javax.servlet.jsp.JspException;

/**
 * @author jnribette
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface ErrorEventListener {
	public void processErrorEvent(ErrorEvent in_event) throws JspException;
}
