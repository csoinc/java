package fr.improve.struts.taglib.layout.event;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.LayoutTag;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.ParentFinder;


/**
 * This event is send by struts-layout tags to compute the number of td 
 * requires to span a component on multiple columns<br/>
 * 
 * @author jnribette
 */
public class ComputeLayoutSpanEvent extends AbstractLayoutEvent {
	private int col;
	
	public ComputeLayoutSpanEvent(LayoutTag in_tag, int in_colspan) {
		super(in_tag, null);
		col = in_colspan;
	}
	/**
	 * Send the event to the parent layout tag of the event tag source.
	 * Return the td span value as an Integer.
	 */
	public Object send() throws JspException {
		return sendToParent(source);
	}
	
	public Object sendToParent(LayoutTag in_tag) throws JspException {
		ComputeLayoutSpanEventListener lc_listener = (ComputeLayoutSpanEventListener) ParentFinder.findLayoutTag(in_tag, ComputeLayoutSpanEventListener.class);
  
		if (lc_listener!=null) {
			return lc_listener.computeColspan(this);
		} else {
			return consume(in_tag);
		}
	}
	
	public int getColspan() {
		return col;
	}
	
	public Object consume(LayoutTag in_tag) throws JspException {
		int lc_colspan = LayoutUtils.getSkin(in_tag.getPageContext().getSession()).getFieldInterface().getColumnNumber() * col;
		return new Integer(lc_colspan);
	}
}
