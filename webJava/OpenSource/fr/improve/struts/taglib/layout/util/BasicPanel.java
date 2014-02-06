package fr.improve.struts.taglib.layout.util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
/**
 * Insert the type's description here.
 * Creation date: (06/06/01 11:40:59)
 * @author: Jean-Noël Ribette
 */
public class BasicPanel implements PanelInterface {
	protected String styleClass;
	protected int colspan;
	protected boolean isNested = false;
	public void doAfterBody(StringBuffer buffer) {
	buffer.append("</table></td></tr>\n");
}
	public void doBeforeBody(StringBuffer buffer, String align) {
	buffer.append("<tr><td");
	if (styleClass!=null) {
		buffer.append(" class=\"").append(styleClass).append("\"");
	}
	buffer.append("><table width=\"100%\"");
	if (align!=null) {
			buffer.append(" align=\"");
			buffer.append(align);
			buffer.append("\"");
	}
	buffer.append("	border=\"0\">\n");
	}
/**
 * doEndPanel method comment.
 */
public void doEndPanel(StringBuffer buffer) {
	buffer.append("</table></td></tr></table>\n");
}
/**
 * Insert a blank line in the body of the panel
 */
public void doPrintBlankLine(StringBuffer buffer, int cols) {
	buffer.append("<tr><td colspan=\"" + cols + "\">&nbsp;</td></tr>\n");
}
/**
 * doPrintTitle method comment.
 */
public void doPrintTitle(StringBuffer buffer, String title) {
	if (title!=null) {
		buffer.append("<tr><th align=\"center\"");
		if (styleClass != null) {
			buffer.append(" class=\"").append(styleClass).append("\"");
		}
		buffer.append(">");
		buffer.append(title);
		buffer.append("</th></tr>\n");
	}
	//buffer.append("<tr>");
}
/**
 * doStartPanel method comment.
 */
public void doStartPanel(StringBuffer buffer, String align, String width) {
	buffer.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"");
	if (align!=null) {
		buffer.append(" align=\"");
		buffer.append(align);
		buffer.append("\"");
	}
	if (width!=null) { 
		buffer.append(" width=\"");
		buffer.append(width);
		buffer.append("\"");
	}
	if (styleClass!=null) {
		buffer.append(" class=\"");
		buffer.append(styleClass);
		buffer.append("\"");
	}
	buffer.append("><tr><td valign=\"top\">");
	buffer.append("<table cellspacing=\"1\" cellpadding=\"1\" border=\"0\" width=\"100%\">\n");	
	}
	public void init(PageContext pg, String in_styleClass, TagSupport in_panel) throws JspException {
		this.styleClass = in_styleClass;
		colspan = LayoutUtils.getSkin(pg.getSession()).getFieldInterface().getColumnNumber();
	}
}
