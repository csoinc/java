package fr.improve.struts.taglib.layout.event;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import fr.improve.struts.taglib.layout.LayoutTag;
import fr.improve.struts.taglib.layout.util.ParentFinder;
import fr.improve.struts.taglib.layout.util.TagUtils;


/**
 * This event is send by struts-layout tags to their parent tags.<br/>
 * When a parent tag process this event, he must make sure that the last HTML code that was generated is a &lt;tr&gt; tag.
 * Th layout tag sending this event must write its output in two columns.
 * 
 * @author jnribette
 */
public class StartLayoutEvent extends AbstractLayoutEvent {
	/**
	 * Construct a new StartLayout event.
	 * @param in_tag the tag source of the event.
	 * @param in_value HTML code to write to the out flow if the source is nested in a layout tag.
	 */
	public StartLayoutEvent(LayoutTag in_tag, Object in_value) {
		super(in_tag, in_value);	
	}
	/**
	 * Send the event to the parent layout tag of the event tag source.
	 * If the event was succesfully processed by a parent layout tag, the method returns Boolean.TRUE.
	 */
	public Object send() throws JspException {
		return sendToParent(source);
	}
	public Object sendToParent(LayoutTag in_tag) throws JspException {
		LayoutEventListener lc_listener = (LayoutEventListener) ParentFinder.findLayoutTag(in_tag, LayoutEventListener.class);   
		if (lc_listener!=null) {
			return lc_listener.processStartLayoutEvent(this);
		} else {
			return Boolean.FALSE;
		}
	}
	public Boolean consume(PageContext in_context, String in_start) throws JspException {
    // Do write with the source tag pageContext : in the case of an include,  
    // writing with the target tag pageContext may mess the HTML. 
		if (in_start!=null) {
			TagUtils.write(source.getPageContext(), in_start);
		}
		if (value!=null) {
			TagUtils.write(source.getPageContext(),value.toString());
		}
		return Boolean.TRUE;
	}
	
}
