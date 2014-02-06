package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.LayoutEventListener;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * LI tag for Struts-Layout.
 * This tag is both a message tag and a column tag.
 * 
 * @author JN Ribette
 */
public class ListItemTag extends LabelledTag implements LayoutEventListener {
	protected boolean hasContent;
	
	/**
	 * Start of custom tag. Display the label if there is one.
	 */
	public int doStartLayoutTag() throws JspException {
		new StartLayoutEvent(this, "<td>").send();
		
		if (key!=null) {
			TagUtils.write(pageContext, "<p");
			if (styleClass!=null) {
				TagUtils.write(pageContext, " class=\"");
				TagUtils.write(pageContext, styleClass);
				TagUtils.write(pageContext, "\">");
			}
			TagUtils.write(pageContext, getLabel());
			TagUtils.write(pageContext, "</p>");
		}
		
		hasContent = false;
		
		return EVAL_BODY_INCLUDE;	
	}
	
	public Object processEndLayoutEvent(EndLayoutEvent in_event)
			throws JspException {
		return in_event.consume(pageContext, "</tr>");
	}

	public Object processStartLayoutEvent(StartLayoutEvent in_event)
			throws JspException {
		if (!hasContent) {
			hasContent = true;
			startContent(in_event.getSource().getPageContext());
		}
		return in_event.consume(pageContext, "<tr>");
	}
	
	protected void startContent(PageContext pageContext) throws JspException {
		TagUtils.write(pageContext, "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
	}
	
	protected void endContent(PageContext pageContext) throws JspException {
		TagUtils.write(pageContext, "</table>");
	}
	
	/**
	 * End of custom tag. Close all opened HTML tags.
	 */
	public int doEndLayoutTag() throws JspException {
		if (hasContent) {
			endContent(pageContext);
		}
		
		new EndLayoutEvent(this, "</td>").send();
		return EVAL_PAGE;
	}	
}
