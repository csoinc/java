package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.LayoutEventListener;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;
/**
 * Allow to align more than one field on a line.
 * @author: JeanNoel Ribette
 */
public class RowTag extends LayoutTagSupport implements LayoutEventListener {
	boolean first = true;
	protected boolean space = true;
	protected String width;
	protected String styleClass;
	
	/**
	 * End a line of field.
	 */
	public int doEndLayoutTag() throws JspException {
		TagUtils.write(pageContext, "</tr></table>");
		new EndLayoutEvent(this, "</td>").send();
		first = true;
		return EVAL_PAGE;
	}
	
	/**
	 * Print an empty td (if space is set to true)
	 */
	public void doPrintSeparator(StringBuffer buffer) {
		if (space) {
			buffer.append("<td>&nbsp;&nbsp;</td>\n");
		}
	}
	/**
	 * Start a line of field.
	 */
	public int doStartLayoutTag() throws JspException {
		int lc_span = LayoutUtils.getSkin(pageContext.getSession()).getFieldInterface().getColumnNumber();
		new StartLayoutEvent(this, "<td colspan=\"" + lc_span + "\">").send();
		TagUtils.write(pageContext, "<table");
		if (styleClass==null) {
			TagUtils.write(pageContext, " border=\"0\"");
		}
		if (width!=null) {
			TagUtils.write(pageContext, " width=\"");
			TagUtils.write(pageContext, width);
			TagUtils.write(pageContext, "\"");
		}
		if (styleClass!=null) {
			TagUtils.write(pageContext, " class=\"");
			TagUtils.write(pageContext, styleClass);
			TagUtils.write(pageContext, "\"");
		}
		TagUtils.write(pageContext, "><tr>");
		return EVAL_BODY_INCLUDE;
	}
	public Object processStartLayoutEvent(StartLayoutEvent in_event) throws JspException {
		StringBuffer lc_buffer = new StringBuffer("");
		if (first) {
			first = false;
		} else {
			doPrintSeparator(lc_buffer);
		}
		return in_event.consume(pageContext, lc_buffer.toString());
	}
	public Object processEndLayoutEvent(EndLayoutEvent in_event) throws JspException {		
		return in_event.consume(pageContext, "");
	}
	
	public void release() {
		space = true;
		width = null;
		styleClass = null;
	}
	
	public void setSpace(boolean in_space) {
		space = in_space;
	}

	public void setWidth(String width) {
		this.width = width;
	}
	
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
}
