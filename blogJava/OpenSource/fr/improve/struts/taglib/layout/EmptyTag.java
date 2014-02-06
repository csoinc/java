package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Generate an empty cell.
 * 
 * @author jnribette
 */
public class EmptyTag extends LayoutTagSupport {
	protected String styleClass;
	
	public int doStartLayoutTag() throws JspException {
		if (Boolean.TRUE.equals(new StartLayoutEvent(this, null).send())){
			TagUtils.write(pageContext, "<td colspan=\"");
			TagUtils.write(pageContext, String.valueOf(LayoutUtils.getSkin(pageContext.getSession()).getFieldInterface().getColumnNumber()));
			if (styleClass!=null) {
				TagUtils.write(pageContext, "\" class=\"");
				TagUtils.write(pageContext, styleClass);
			}
			TagUtils.write(pageContext, "\">&nbsp;</td>");
		} else {
			TagUtils.write(pageContext, "<BR/>");
		}
		new EndLayoutEvent(this, null).send();
		return SKIP_BODY;
	}
	
	public int doEndLayoutTag() throws JspException {		
		return EVAL_PAGE;
	}
	
	public void release() {
		styleClass = null;	
	}
	
	public void setStyleClass(String in_styleClass) {
		styleClass = in_styleClass;	
	}
}
