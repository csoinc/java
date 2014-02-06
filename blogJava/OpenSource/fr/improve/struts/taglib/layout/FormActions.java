package fr.improve.struts.taglib.layout;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.LayoutEventListener;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.util.LayoutUtils;

/**
 * Buttons in forms must be added within this tag.
 * <br>
 * This tag is deprecated. <layout:line> should be used instead.
 *
 * @author: Jean-Noel Ribette
 * @deprecated 
 * 
 * Can also auto generate the needed buttons;
 **/
public class FormActions extends LabelledTag implements LayoutEventListener {
	protected String align = "CENTER";

	/**
	 * FormActions constructor comment.
	 */
	public FormActions() {
		super();
	}
	public int doEndLayoutTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			out.println("</td></tr>");
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return EVAL_PAGE;
	}
	public int doStartLayoutTag() throws JspException {

		StringBuffer sb = new StringBuffer("<tr><td align=\"").append(align).append("\" colspan=\"");
		sb.append(LayoutUtils.getSkin(pageContext.getSession()).getFieldInterface().getColumnNumber());
		sb.append("\">");

		if (key != null) {
			String action =
				LayoutUtils.getBeanFromPageContext(pageContext, name, property).toString();
			if (action.equals("Edit") || (action.equals("Create"))) {
				// +save button
				sb.append("<input type=\"submit\" name=\"submit\" value=\"");
				sb.append(LayoutUtils.getLabel(pageContext, key + ".save", null));
				sb.append("\">&nbsp;");
				// + reset button
				sb.append("<input type=\"reset\" name=\"reset\" value=\"");
				sb.append(LayoutUtils.getLabel(pageContext, key + ".reset", null));
				sb.append("\">&nbsp;");
			}

			if (action.equals("Delete")) {
				// + confirm button
				sb.append("<input type=\"submit\" name=\"submit\" value=\"");
				sb.append(LayoutUtils.getLabel(pageContext, key + ".confirm", null));
				sb.append("\">&nbsp;");
			}

			// + cancel button
			sb.append(
				"<input type=\"submit\" name=\"org.apache.struts.taglib.html.CANCEL\" value=\"");
			sb.append(LayoutUtils.getLabel(pageContext, key + ".cancel", null));
			sb.append("\">");
		}

		try {
			JspWriter out = pageContext.getOut();
			out.println(sb.toString());
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return EVAL_BODY_INCLUDE;
	}
	
	public void release() {
		super.release();
		align = "CENTER";
	}
	
	public Object processStartLayoutEvent(StartLayoutEvent in_event) {
		return Boolean.FALSE;
	}
	public Object processEndLayoutEvent(EndLayoutEvent in_event) {
		return Boolean.FALSE;
	}
	/**
	 * Gets the align.
	 * @return Returns a String
	 */
	public String getAlign() {
		return align;
	}

	/**
	 * Sets the align.
	 * @param align The align to set
	 */
	public void setAlign(String align) {
		this.align = align;
	}	
}