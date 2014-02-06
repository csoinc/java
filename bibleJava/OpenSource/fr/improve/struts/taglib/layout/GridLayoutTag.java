package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.event.ComputeLayoutSpanEvent;
import fr.improve.struts.taglib.layout.event.ComputeLayoutSpanEventListener;
import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.LayoutEventListener;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.event.StaticCodeIncludeLayoutEvent;
import fr.improve.struts.taglib.layout.event.StaticCodeIncludeListener;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * @author jnribette
 */
public class GridLayoutTag extends LayoutTagSupport implements LayoutEventListener, ComputeLayoutSpanEventListener, StaticCodeIncludeListener {
	protected String cols = "2";
	
	protected boolean space = true;
	
	protected String styleClass;
	protected String width;
	protected String align;
	
	/**
	 * IE does not allows to set this property with css :(
	 */
	protected int borderSpacing = 0;
		
	protected String id;
	protected String height;
	
	protected int currentCols = 1;
	protected int currentSpan = 1;
	protected String jspStyleClass;
	
	protected StringBuffer staticCode = new StringBuffer();
	
	protected void initDynamicValues() {
		jspStyleClass = styleClass;
		if (styleClass==null){
			styleClass = LayoutUtils.getSkin(pageContext.getSession()).getProperty("styleclass.grid",null);
		}
	}
	protected void reset() {
		styleClass = jspStyleClass;
		jspStyleClass = null;
		staticCode = new StringBuffer();
	}
	
	public int doStartLayoutTag() throws JspException {
		int lc_fieldColumnNumber = LayoutUtils.getSkin(pageContext.getSession()).getFieldInterface().getColumnNumber();
		new StartLayoutEvent(this, "<td colspan=\"" + lc_fieldColumnNumber + "\">").send();
		
		if (id!=null || height!=null) {
			TagUtils.write(pageContext, "<div style=\"");
			if (height!=null) {
				TagUtils.write(pageContext, "height:");
				TagUtils.write(pageContext, height);
				TagUtils.write(pageContext, ";");
			}
			TagUtils.write(pageContext,"overflow-y:scroll;overflow:-moz-scrollbars-vertical;");
			if (id!=null) {
				TagUtils.write(pageContext, "\" id=\"");
				TagUtils.write(pageContext, id);
			}
			TagUtils.write(pageContext, "\">");
		}
		
		TagUtils.write(pageContext, "<table border=\"0\" cellspacing=\"");
		TagUtils.write(pageContext, String.valueOf(borderSpacing));
		TagUtils.write(pageContext,"\" cellpadding=\"0\"");
		if (styleClass!=null) {
			TagUtils.write(pageContext, " class=\"");
			TagUtils.write(pageContext, styleClass);
			TagUtils.write(pageContext, "\"");
		}
		if (width!=null) {
			TagUtils.write(pageContext, " width=\"");
			TagUtils.write(pageContext, width);
			TagUtils.write(pageContext, "\"");
		}
		if (align!=null) {
			TagUtils.write(pageContext, " align=\"");
			TagUtils.write(pageContext, align);
			TagUtils.write(pageContext, "\"");
		}
		TagUtils.write(pageContext,"><tr>");
		return EVAL_BODY_INCLUDE;
	}
	public int doEndLayoutTag() throws JspException {
		currentCols = 1;
		TagUtils.write(pageContext, "</tr></table>");
		
		if (id!=null || height!=null) {
			TagUtils.write(pageContext, "</div>");
		}
		if (staticCode.length()!=0) {
			TagUtils.write(pageContext, staticCode.toString());
		}
		
		new EndLayoutEvent(this, "</td>").send();
		return EVAL_PAGE;
	}
	public void release() {
		super.release();
		cols = "2";
		space = true;
		styleClass = null;
		height = null;
		id = null;
		width = null;
		align = null;
		borderSpacing = 0;
	}
	
	public Object processStartLayoutEvent(StartLayoutEvent in_event) throws JspException {
		if (currentCols > Integer.parseInt(cols)) {
			TagUtils.write(pageContext, "</tr><tr>");
			currentCols = 1;
		}
		currentCols += currentSpan;
		currentSpan = 1;
		return in_event.consume(pageContext, "");
	}
	public Object processEndLayoutEvent(EndLayoutEvent in_event) throws JspException {
		return in_event.consume(pageContext, space ? "<td>&nbsp;</td>" : "");		
	}
	
	public Integer computeColspan(ComputeLayoutSpanEvent in_event) throws JspException {
		currentSpan = in_event.getColspan();
		if (space) {
			// Get default colspan.
			int lc_value = ((Integer)in_event.consume(this)).intValue();
			
			// Add an extra cell for the space after each component. 
			lc_value += in_event.getColspan();
			
			// But we add the last one ourself.
			lc_value -= 1;
			
			// Return.
			return new Integer(lc_value);
		} else {
			// We don't alter the number of td.
			return (Integer) in_event.consume(this);
		}
		
	}
	
	/**
	 * Sets the cols.
	 * @param cols The cols to set
	 */
	public void setCols(String cols) {
		this.cols = cols;
	}

	/**
	 * Returns the space.
	 * @return boolean
	 */
	public boolean isSpace() {
		return space;
	}

	/**
	 * Sets the space.
	 * @param space The space to set
	 */
	public void setSpace(boolean space) {
		this.space = space;
	}

	/**
	 * Sets the styleClass.
	 * @param styleClass The styleClass to set
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	/**
	 * Sets the height.
	 * @param height The height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * Sets the id.
	 * @param id The id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public void setWidth(String in_string) {
		width = in_string;
	}
	
	public void setBorderSpacing(int in_spacing) {
		borderSpacing = in_spacing;
	}

	public String getAlign() {
		return align;
	}
	
	public void setAlign(String align) {
		this.align = align;
	}
	
	/**
	 * @see fr.improve.struts.taglib.layout.event.StaticCodeIncludeListener#processStaticCodeIncludeEvent(fr.improve.struts.taglib.layout.event.StaticCodeIncludeLayoutEvent)
	 */
	public Object processStaticCodeIncludeEvent(StaticCodeIncludeLayoutEvent in_event) throws JspException {
		String lc_value = (String) in_event.sendToParent(this);
		if (height==null && id==null) {
			return lc_value;
		} else {
			staticCode.append(lc_value);
			return "";
		}
	}
}
