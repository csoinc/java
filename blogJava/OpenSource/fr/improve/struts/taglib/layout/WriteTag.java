package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.ResponseUtils;

import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.formatter.FormatException;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.NestedHelper;
import fr.improve.struts.taglib.layout.util.ParentFinder;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Insert the type's description here.
 * Creation date: (12/09/2001 11:58:27)
 * @author: JeanNoël Ribette
 */
public class WriteTag extends org.apache.struts.taglib.bean.WriteTag implements LayoutTag {
	protected String type;
	protected String styleClass;
	protected String style;
	protected boolean layout = true;
	
	protected String jspProperty;
	protected String jspName;
	
	protected static Class[] parameters = { PageContext.class, Object.class };
	
	public WriteTag() {
		super();
		name = Constants.BEAN_KEY;
	}
  
	public void setLayout(boolean in_b) {
		layout = in_b;
	}

	public boolean isLayout() {
		return layout;
	}
	public String getStyle() {
		return style;
	}

	public void setStyle(String in_style) {
		this.style = in_style;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String in_styleClass) {
		this.styleClass = in_styleClass;
	}
  
  public final int doStartTag() throws JspException {
  	// Evaluate property.
  	jspProperty = property;
  	property = Expression.evaluate(property, pageContext); // EL support
  	property = NestedHelper.getAdjustedProperty(property, pageContext); // Nested support
  	
  	// Evaluate name.
  	jspName = name;
  	name = Expression.evaluate(name, pageContext);
  	
  	// Register the tag. 
    ParentFinder.registerTag(pageContext, this);
    
    // Start the tag.
    return doStartLayoutTag();
  }   
  
  public final int doEndTag() throws JspException {
    try {
    	// End the tag. 
    	return doEndLayoutTag();
    } finally {      
    	// Deregister the tag.
    	ParentFinder.deregisterTag(pageContext);
    	
    	// Restore the attributes.
    	property = jspProperty;
    	jspProperty = null;
    	name = jspName;
    	jspName = null;
    }
  }
  
  public int doEndLayoutTag() throws JspException {
    return super.doEndTag();
  }  

	/**
	 * Process the start tag.
	 *
	 * @exception JspException if a JSP exception has occurred
	 */
	public int doStartLayoutTag() throws JspException {		
		doStartLayout();

		String lc_value = write(pageContext, name, property, type, scope);
		if (filter) {
			TagUtils.write(pageContext, ResponseUtils.filter(lc_value));
		} else {
			TagUtils.write(pageContext, lc_value);
		}

		doEndLayout();	
		// Continue processing this page
		return (SKIP_BODY);
	}
	/**
	 * Print the required HTML code so that the tag can be nested in panel and line tags.<br>
	 * This won't do anything if styleClass is null.
	 */
	private void doStartLayout() throws JspException {
		if (layout) {			
			StringBuffer lc_buffer = new StringBuffer("<th");
			if (styleClass!=null) {
				lc_buffer.append(" class=\"");
				lc_buffer.append(styleClass);
				lc_buffer.append("\"");
			}
			if (style!=null) {
				lc_buffer.append(" style=\"");
				lc_buffer.append(style);
				lc_buffer.append("\"");
			}
			
			lc_buffer.append(" colspan=\"");
			lc_buffer.append(LayoutUtils.getSkin(pageContext.getSession()).getFieldInterface().getColumnNumber());
			lc_buffer.append("\">");
			new StartLayoutEvent(this, lc_buffer.toString()).send();
		}
	}
	
	/**
	 * Print the required HTML code so that the tag can be nested in panel and line tags.<br>
	 * This won't do anything if styleClass is null.
	 */	
	private void doEndLayout() throws JspException {
		if (layout) {				
			new EndLayoutEvent(this,"</th>").send();
		}	
	}
	public void release() {
		super.release();
		this.type = null;
		this.style = null;
		this.styleClass = null;
		this.name = Constants.BEAN_KEY;
		layout = true;
	}
	public void setType(String in_type) {
		this.type = in_type;
	}
public static String write(PageContext pageContext, Object value, String type) throws JspException {
	if (value == null) {
		return ""; // Nothing to output
	}
	if (type==null || type.length()==0) return value.toString();
	
	// Format the value
	String lc_formattedValue = null;
	try {
		lc_formattedValue = LayoutUtils.getSkin(pageContext.getSession()).getFormatter().format(value, type, pageContext);
	} catch (FormatException fe) {
		throw new JspException("Format " + type + " failed (" + fe.getMessage() + ")");
	}
	return (lc_formattedValue == null ? "" : lc_formattedValue);
}
public static String write(PageContext pageContext, Object bean, String property, String type) throws JspException {
	// Look up the requested property value
	Object value = null;
	try {
		value = fr.improve.struts.taglib.layout.util.LayoutUtils.getProperty(bean, property);
	} catch (JspException e) {
		return ""; // Nothing to output
	}
	return write(pageContext, value, type);
}
public static String write(PageContext pageContext, String name, String property, String type, String scope) throws JspException {
	Object bean = pageContext.findAttribute(name);
	return write(pageContext, bean, property, type);
	}

  public PageContext getPageContext() {
    return pageContext;
  }

}
