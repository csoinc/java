package fr.improve.struts.taglib.layout.util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
/**
 * Definition of the panel interface
 * @author: Jean-Noël Ribette
 */
public interface PanelInterface {
	public void doAfterBody(StringBuffer buffer) throws JspException;
	public void doBeforeBody(StringBuffer buffer, String align) throws JspException ;
	public void doEndPanel(StringBuffer buffer);
	public void doPrintBlankLine(StringBuffer buffer, int cols) throws JspException;
	/**
	 * This method prints the title of the panel.
	 * Be careful that this method can be called with title = null.
	 **/
	public void doPrintTitle(StringBuffer buffer, String title);
	public void doStartPanel(StringBuffer buffer, String align, String width);
/**
 * This method is called once before each use of the panel
 * Give the panel in argument to be able to get the styleClass, the key etc. if needed
 */
public void init(PageContext pageContext, String in_styleClass, TagSupport in_tag) throws JspException;
}
