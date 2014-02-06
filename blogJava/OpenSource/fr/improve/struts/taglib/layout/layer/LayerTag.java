package fr.improve.struts.taglib.layout.layer;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.MessageResources;

import fr.improve.struts.taglib.layout.LayoutTag;
import fr.improve.struts.taglib.layout.LayoutTagSupport;
import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.event.StaticCodeIncludeLayoutEvent;
import fr.improve.struts.taglib.layout.formatter.FormatException;
import fr.improve.struts.taglib.layout.util.FormUtilsInterface;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

public class LayerTag extends LayoutTagSupport implements LayoutTag {

	/**
	 * The message resources for this package.
	 */
	protected static MessageResources messages = MessageResources.getMessageResources(Constants.Package + ".LocalStrings");

	/**
	 * The JSP bean name for query parameters.
	 */
	protected String name = null;
	protected String jspName = null;
	protected String styleClass = null;
	protected String jspStyleclass = null;
	protected String type;
	
	protected StringBuffer results;
	
	
	/**
	 * @return Returns the styleClass.
	 */
	public String getStyleClass() {
		return styleClass;
	}
	/**
	 * @param styleClass The styleClass to set.
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	
	
	public String getName() {
		return (this.name);
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * The JSP bean property name for query parameters.
	 */
	protected String property = null;

	public String getProperty() {
		return (this.property);
	}

	public void setProperty(String property) {
		this.property = property;
	}

	
	/* (non-Javadoc)
	 * @see fr.improve.struts.taglib.layout.LayoutTagSupport#initDynamicValues()
	 */
	protected void initDynamicValues() {
		jspStyleclass = styleClass;
		if (styleClass==null){
			styleClass = LayoutUtils.getSkin(pageContext.getSession()).getProperty("styleclass.layer",null);
		}
		jspName = name;
		name = Expression.evaluate(name,pageContext);
		super.initDynamicValues();
	}
	
	/**
	 * Render the beginning of the hyperlink. Indexed property since 1.1
	 * 
	 * @exception JspException
	 *                if a JSP exception has occurred
	 */
	public int doStartLayoutTag() throws JspException {
		Integer i = (Integer)pageContext.getRequest().getAttribute("layerId");
		if (i==null){
			i = new Integer(100);
		} 
		i = new Integer(i.intValue()+1);
		pageContext.getRequest().setAttribute("layerId",i);
		
		results = new StringBuffer("<DIV id='layer" + String.valueOf(i)+ "' style='position:absolute;top:0;left:0;visibility:hidden'>");
		results.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ");
		if (getStyleClass()!=null && getStyleClass().length()>0){
			results.append(" class=\""+ getStyleClass() +"\"");
		}
		results.append(">"); 
		results.append("<tr>"); 
		results.append("<td valign=top");
		if (getStyleClass()!=null && getStyleClass().length()>0){
			results.append(" class=\""+ getStyleClass() +"\"");
		}
		results.append(">");
		//ResponseUtils.write(pageContext, results.toString());
		return (EVAL_BODY_INCLUDE);
	}

	/**
	 * Render the end of the hyperlink.
	 * 
	 * @exception JspException
	 *                if a JSP exception has occurred
	 */
	public int doEndLayoutTag() throws JspException {
		Integer i = (Integer)pageContext.getRequest().getAttribute("layerId");
		 
		results.append("</td>");
		results.append("</tr>"); 
		results.append("</table></DIV>");
		TagUtils.write(pageContext, new StaticCodeIncludeLayoutEvent(this, results.toString()).send().toString());
		
		TagUtils.write(pageContext, "<a href=\"#\"");
		TagUtils.write(pageContext, " onmouseover=\"showLayoutLayer('layer" + String.valueOf(i)+ "',event);\"");
		TagUtils.write(pageContext, " onmouseout=\"hideLayoutLayer('layer" + String.valueOf(i)+ "');\">");
		
		Object lc_value = LayoutUtils.getBeanFromPageContext(pageContext, name,property);
		if (type!=null) try {
			lc_value = LayoutUtils.getSkin(pageContext.getSession()).getFormatter().format(lc_value, type, pageContext);
		} catch (FormatException e) {
			throw new JspException("Format " + type + " failed: " + e.getMessage());
		}
		TagUtils.write(pageContext, lc_value==null ? "" : lc_value.toString());
		TagUtils.write(pageContext, "</a>");
		return (EVAL_PAGE);

	}
	
	public void addContent(String in_content) {
		results.append(in_content);
	}

	protected void reset() {
		showLink = true;
		name = jspName;
		jspName = null;
		styleClass = jspStyleclass;
		jspStyleclass = null;
	}

	/**
	 * Prepare to display the link.
	 */
	protected void beginLinkLayout(StringBuffer in_buffer) throws JspException {
		in_buffer.append("<td>&nbsp;</td><td");
		//			if (valign!=null) {
		//				in_buffer.append(" valign=\"");
		//				in_buffer.append(valign);
		//				in_buffer.append("\"");
		//			}
		in_buffer.append(">");
	}

	/**
	 * End the display of the action.
	 */
	protected void endLinkLayout(StringBuffer in_buffer) {
		in_buffer.append("</td>");
	}

	/**
	 * Release any acquired resources.
	 */
	public void release() {

		super.release();
		name = null;
		property = null;
		policy = null;
		display = true;
		type = null;
	}

	protected boolean display = true;

	protected boolean showLink = true;

	protected String policy = null;

	/**
	 * Set in wich form modes the action should be displayed or not. The format
	 * form in_mode is X,Y,Z where allowed values are D (Displayed) and N (not
	 * displayed) in the same order as the input field tags.
	 */
	public void setMode(String in_mode) {
		if (in_mode == null || in_mode.length() != 5) {
			throw new IllegalArgumentException("The specified mode" + in_mode
					+ " is invalid");
		}
		int lc_formMode = LayoutUtils.getSkin(pageContext.getSession())
				.getFormUtils().getFormDisplayMode(pageContext);
		char lc_displayMode;
		switch (lc_formMode) {
		case FormUtilsInterface.CREATE_MODE:
			lc_displayMode = in_mode.charAt(0);
			break;
		case FormUtilsInterface.EDIT_MODE:
			lc_displayMode = in_mode.charAt(2);
			break;
		case FormUtilsInterface.INSPECT_MODE:
			lc_displayMode = in_mode.charAt(4);
			break;
		default:
			lc_displayMode = 'D';
		}
		display = lc_displayMode == 'D' || lc_displayMode == 'd';
	}

	/**
	 * @return Returns the policy.
	 */
	public String getPolicy() {
		return policy;
	}

	/**
	 * @param policy
	 *            The policy to set.
	 */
	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public Object processEndLayoutEvent(EndLayoutEvent in_event)
			throws JspException {
		return Boolean.FALSE;
	}

	public Object processStartLayoutEvent(StartLayoutEvent in_event)
			throws JspException {
		return Boolean.FALSE;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}