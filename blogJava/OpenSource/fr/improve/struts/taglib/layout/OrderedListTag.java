package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.LayoutEventListener;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Ordered list tag for Struts-Layout
 * @author Jean-Noel Ribette
 */
public class OrderedListTag extends LayoutTagSupport implements LayoutEventListener {
	private String styleClass;
    private String width;
	
	public int doStartLayoutTag() throws JspException {
		StringBuffer lc_td = new StringBuffer("<td colspan=\"");
		lc_td.append(LayoutUtils.getSkin(pageContext.getSession()).getFieldInterface().getColumnNumber());
		if (styleClass!=null) {
			lc_td.append("\" class=\"");
			lc_td.append(styleClass);
		}
        if (width!=null) {
            lc_td.append("\" width=\"");
            lc_td.append(width);
        }
		lc_td.append("\">");
		new StartLayoutEvent(this, lc_td.toString()).send();	
		TagUtils.write(pageContext, "<ol");
		if (styleClass!=null) {
			TagUtils.write(pageContext, " class=\"");
			TagUtils.write(pageContext, styleClass);
			TagUtils.write(pageContext, "\"");			
		}
		TagUtils.write(pageContext, ">");
		return EVAL_BODY_INCLUDE;
	}
	public int doEndLayoutTag() throws JspException {
		TagUtils.write(pageContext, "</ol>");
		new EndLayoutEvent(this, "</td>").send();
		return EVAL_PAGE;
		
	} 
	public Object processStartLayoutEvent(StartLayoutEvent in_event) throws JspException {
		TagUtils.write(in_event.getSource().getPageContext(), "<li>");
		return Boolean.FALSE;
	}
	public Object processEndLayoutEvent(EndLayoutEvent in_event) throws JspException {
		TagUtils.write(in_event.getSource().getPageContext(), "</li>");
		return Boolean.FALSE;		
	}
	
	public void release() {
		super.release();
		styleClass = null;
        width = null;
	}

	/**
	 * Sets the styleClass.
	 * @param styleClass The styleClass to set
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	/**
	 * @param width The width to set.
	 */
	public final void setWidth(String width)
	{
		this.width = width; 
	}
}
