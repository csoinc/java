package fr.improve.struts.taglib.layout.util;

import javax.servlet.jsp.JspException;

/**
 * @author Damien Viel
 */
public interface IMultiLevelHeaderRenderer {
	public void startMultiLevelHeaderRow(StringBuffer in_buffer);
	public void renderMultiLevelHeader(StringBuffer in_buffer, String in_title, String in_sortUrl, String in_styleClass, int in_colspan, int in_rowspan, String in_width) throws JspException;
	public void endMultiLevelHeaderRow(StringBuffer in_buffer);
}
