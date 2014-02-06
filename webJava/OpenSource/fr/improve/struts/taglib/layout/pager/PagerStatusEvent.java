package fr.improve.struts.taglib.layout.pager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import fr.improve.struts.taglib.layout.LayoutTag;
import fr.improve.struts.taglib.layout.event.AbstractLayoutEvent;

/**
 * @author jnribette
 */
public class PagerStatusEvent extends AbstractLayoutEvent {
	public PagerStatusEvent(LayoutTag in_source, Object in_value) {
		super(in_source, in_value);	
	}
	/**
	 * Send a PagerStatus event.<br/>
	 * <br/>
	 * Return the pager status under an Integer[4] array. The first int is the current page number, 
	 * the second is the total number of page,
	 * the third is the total number of items,
	 * the fourth is the number of items per page.
	 */
	public Object send() throws JspException {
		PagerStatusListener lc_listener = (PagerStatusListener) TagSupport.findAncestorWithClass(source, PagerStatusListener.class);
		if (lc_listener==null) {
			return null;	
		} else {
			return lc_listener.processPagerStatusEvent(this)	;
		}
	}

}
