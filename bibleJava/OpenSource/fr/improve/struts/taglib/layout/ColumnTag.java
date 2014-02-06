package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.LayoutEventListener;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * @author jnribette, F. BELLINGARD
 */
public class ColumnTag extends LayoutTagSupport implements LayoutEventListener {
	private String styleClass;
    private String width;
    private String styleId;
    
    private String style;
    private String jspStyle;
	
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
        if (styleId!=null) {
        	lc_td.append("\" id=\"");
        	lc_td.append(styleId);
        }
        if (style!=null) {
        	lc_td.append("\" style=\"");
        	lc_td.append(style);
        }
		lc_td.append("\">");
		new StartLayoutEvent(this, lc_td.toString()).send();	
		TagUtils.write(pageContext, "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		return EVAL_BODY_INCLUDE;
	}
	public int doEndLayoutTag() throws JspException {
		TagUtils.write(pageContext, "</table>");
		new EndLayoutEvent(this, "</td>").send();
		return EVAL_PAGE;
		
	} 
	public Object processStartLayoutEvent(StartLayoutEvent in_event) throws JspException {
		return in_event.consume(pageContext, "<tr>");
	}
	public Object processEndLayoutEvent(EndLayoutEvent in_event) throws JspException {
		return in_event.consume(pageContext, "</tr>");		
	}
	
	public void release() {
		super.release();
		styleClass = null;
        width = null;
        style = null;
        styleId = null;
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
	public void setStyle(String style) {
		this.style = style;
	}
	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}
	protected void initDynamicValues() {
		super.initDynamicValues();
		jspStyle = style;
		style = Expression.evaluate(style, pageContext);
	}
	protected void reset() {
		super.reset();
		style = jspStyle;
	}
}
