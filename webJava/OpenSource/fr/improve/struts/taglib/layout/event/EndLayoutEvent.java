package fr.improve.struts.taglib.layout.event;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import fr.improve.struts.taglib.layout.LayoutTag;
import fr.improve.struts.taglib.layout.util.ParentFinder;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * @author jnribette
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class EndLayoutEvent extends AbstractLayoutEvent {
	public EndLayoutEvent(LayoutTag in_tag, Object in_value) {
		super(in_tag, in_value);	
	}
	/**
	 * @see fr.improve.struts.taglib.layout.event.AbstractLayoutEvent#send()
	 */
	public Object send() throws JspException {
		LayoutEventListener lc_listener = (LayoutEventListener) ParentFinder.findLayoutTag(source, LayoutEventListener.class);
				if (lc_listener!=null) {
					return lc_listener.processEndLayoutEvent(this);
				} else {
					return Boolean.FALSE;
				}
	}
	public Object sendToParent(LayoutTag in_tag) throws JspException {
		LayoutEventListener lc_listener = (LayoutEventListener) ParentFinder.findLayoutTag(in_tag, LayoutEventListener.class);
				if (lc_listener!=null) {
					return lc_listener.processEndLayoutEvent(this);
				} else {
					return Boolean.FALSE;
				}
	}
	public Boolean consume(PageContext in_context, String in_end) throws JspException {
	  // Do write with the source tag pageContext : in the case of an include,  
    // writing with the target tag pageContext mess the HTML. 
		if (value!=null) {			
			TagUtils.write(source.getPageContext(), value.toString());
		}
		if (in_end!=null) {
			TagUtils.write(source.getPageContext(), in_end + "\n");
		}
		return Boolean.TRUE;
	}

}
