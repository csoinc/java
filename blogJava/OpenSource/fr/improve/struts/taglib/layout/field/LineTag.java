package fr.improve.struts.taglib.layout.field;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.LayoutTagSupport;
import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.LayoutEventListener;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.util.TagUtils;
/**
 * Allow to align more than one field on a line. Fields in different line tags are aligned.
 * @author: JeanNoël Ribette
 */
public class LineTag extends LayoutTagSupport implements LayoutEventListener {
	protected boolean space = true;
/**
 * End a line of field.
 */
public int doEndLayoutTag() throws JspException {
	if (Boolean.FALSE.equals(new EndLayoutEvent(this, null).send())) {
		TagUtils.write(pageContext, "</tr>");
	}	
	return EVAL_PAGE;
}
public void doPrintSeparator(StringBuffer buffer) {
	buffer.append("<td>&nbsp;&nbsp;</td>\n");
}
/**
 * Start a line of field.
 */
public int doStartLayoutTag() throws JspException {
	if (Boolean.FALSE.equals(new StartLayoutEvent(this, null).send())) {
		TagUtils.write(pageContext, "<tr>");
	}
	return EVAL_BODY_INCLUDE;
}
public Object processStartLayoutEvent(StartLayoutEvent in_event) throws JspException {
	return in_event.consume(pageContext, "");
}
public Object processEndLayoutEvent(EndLayoutEvent in_event) throws JspException {
	StringBuffer lc_buffer = new StringBuffer();
	if (space) {
		doPrintSeparator(lc_buffer);
	}
	return in_event.consume(pageContext, lc_buffer.toString());
}
	public void release() {
		super.release();
		space = true;
	}
	public void setSpace(boolean in_boolean) {
		space = in_boolean;
	}
}
