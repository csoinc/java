package fr.improve.struts.taglib.layout.event;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.LayoutTag;
import fr.improve.struts.taglib.layout.util.ParentFinder;

/**
 * @author jnribette
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ErrorEvent extends AbstractLayoutEvent {
	public ErrorEvent(LayoutTag in_tag, Object in_value) {
		super(in_tag, in_value);	
	}
	public Object send() throws JspException {
		ErrorEventListener lc_listener = (ErrorEventListener) ParentFinder.findLayoutTag(source, ErrorEventListener.class);
		if (lc_listener!=null) {
			lc_listener.processErrorEvent(this);
			return lc_listener;
		} else {
			return null;
		}
	}
}
