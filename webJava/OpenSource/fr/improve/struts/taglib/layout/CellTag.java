package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.event.ComputeLayoutSpanEvent;
import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.LayoutEventListener;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;

/**
 * Tag whose body content is developer written HTML code.
 * If another struts-layout tag is nested in this one, no layout code will be generated.
 * 
 * @author jnribette
 */
public class CellTag extends LayoutTagSupport implements LayoutEventListener {
	protected String styleClass;
	protected String width;
	protected String height;
	protected int colspan = 1;
	private String align;
	
	public int doStartLayoutTag() throws JspException {
		// Compute colspan value.
		int lc_colspan = ((Integer)new ComputeLayoutSpanEvent(this, colspan).send()).intValue();
		
		// Generate td.
		StringBuffer lc_buffer = new StringBuffer("<td colspan=\"");
		lc_buffer.append(lc_colspan);
		if (styleClass!=null) {
			lc_buffer.append("\" class=\"");
			lc_buffer.append(styleClass);
		}
		if (width!=null){
			lc_buffer.append("\" width=\"");
			lc_buffer.append(width);
		}
		if (height!=null){
			lc_buffer.append("\" height=\"");
			lc_buffer.append(height);
		}
		if (align!=null) {
			lc_buffer.append("\" align=\"");
			lc_buffer.append(align);
		}
		lc_buffer.append("\">");
		
		new StartLayoutEvent(this, lc_buffer.toString()).send();
		return EVAL_BODY_INCLUDE;
	}
	
	public int doEndLayoutTag() throws JspException {
		new EndLayoutEvent(this, "</td>").send();
		return EVAL_PAGE;
	}
	
	public void release() {
		styleClass = null;
		width = null;
		height = null;
		colspan = 1;
		align = null;
	}
	
	public void setStyleClass(String in_styleClass) {
		styleClass = in_styleClass;	
	}
	
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getStyleClass() {
		return styleClass;
	}
	public int getColspan() {
		return colspan;
	}
	public void setColspan(int colspan) {
		this.colspan = colspan;
	}
	/**
	 * @see fr.improve.struts.taglib.layout.event.LayoutEventListener#processEndLayoutEvent(EndLayoutEvent)
	 */
	public Object processEndLayoutEvent(EndLayoutEvent in_event) throws JspException {
		return Boolean.FALSE;
	}

	/**
	 * @see fr.improve.struts.taglib.layout.event.LayoutEventListener#processStartLayoutEvent(StartLayoutEvent)
	 */
	public Object processStartLayoutEvent(StartLayoutEvent in_event) throws JspException {
		return Boolean.FALSE;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}
}
