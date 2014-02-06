package fr.improve.struts.taglib.layout.event;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.LayoutTag;
import fr.improve.struts.taglib.layout.util.ParentFinder;

/**
 * Allow a tag to print static code to be included only once 
 * even if the current tag is nested in a hidden layer.
 * 
 * @author JN Ribette
 */
public class StaticCodeIncludeLayoutEvent extends AbstractLayoutEvent {
	public StaticCodeIncludeLayoutEvent(LayoutTag in_tag, Object in_value) {
		super(in_tag, in_value);	
	}

	/**
	 * The tag calling this method should print the Object returned to its pageContext.
	 * @see fr.improve.struts.taglib.layout.event.AbstractLayoutEvent#send()
	 */
	public Object send() throws JspException {
		return sendToParent(source);
	}
	
	public Object sendToParent(LayoutTag in_tag) throws JspException {
		StaticCodeIncludeListener lc_listener = (StaticCodeIncludeListener) ParentFinder.findLayoutTag(in_tag, StaticCodeIncludeListener.class);
		if (lc_listener!=null) {
			return lc_listener.processStaticCodeIncludeEvent(this);
		} else {
			return value;
		}
	}

}
