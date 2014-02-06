package fr.improve.struts.taglib.layout;

/*
 * $Header: /cvs/OYO/COMMON/webJava/OpenSource/fr/improve/struts/taglib/layout/HtmlTag.java,v 1.1 2008/06/29 15:56:22 oyou Exp $
 * $Revision: 1.1 $
 * $Date: 2008/06/29 15:56:22 $
 *
 * ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Tomcat", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Renders an HTML &lt;html&gt; element with appropriate language attributes if
 * there is a current Locale available in the user's session.
 * Also render an &lt;head&gt; element with a link to the stylesheet to use, 
 * a link to the struts-layout javascript and a &lt;base&gt; element
 *
 * NOTE: the property locale needs to have a getter called isLocale for SUN 1.3.1 VM and getLocale for IBM 1.3.0
 * 
 * PENDING add a layout attribute (boolean) to choose if the table and br should be generated or not.
 *
 * @author Jean-Noel Ribette
 */

public class HtmlTag extends PanelTag {

	/**
	 * Should we set the current Locale for this user if needed?
	 */
	protected boolean locale = false;

	/**
	 * Are we rendering an xhtml page?
	 */
	protected boolean xhtml = false;
	
	/**
	 * onload event handler.
	 */
	protected String onload;
	
	/**
	 * content align.
	 */
	protected String contentAlign = "center";
	
	/**
	 * layout ?
	 */
	protected boolean layout = true;

	protected org.apache.struts.taglib.html.BaseTag baseTag =
		new org.apache.struts.taglib.html.BaseTag();
	protected org.apache.struts.taglib.html.HtmlTag htmlTag =
		new org.apache.struts.taglib.html.HtmlTag();
		
	public HtmlTag() {
		super();
		width = "80%";	
		align ="center";		
		contentAlign = "center";
		layout = true;
	}	
			
	/**
	 * Process a StartLayoutEvent.
	 * @return type of Layout tag
	 */
	public Object processStartLayoutEvent(StartLayoutEvent in_event) throws JspException {	
		return Boolean.FALSE;
	}
	public Object processEndLayoutEvent(EndLayoutEvent in_event) throws JspException {		
		return Boolean.FALSE;	
	}

public void doBeforeBody(StringBuffer sb) {
	sb.append("<br><table cellspacing=\"0\" cellpadding=\"10\" width=\"100%\" align=\"");
	sb.append(contentAlign);
	sb.append("\" border=\"0\">");
}
protected void doEndHtml(StringBuffer sb) {	
	DynMenuTag2.includeScriptCode(sb, pageContext);
	sb.append("</body>");
}
/**
 * Process the end of this tag.
 *
 * @exception JspException if a JSP exception has occurred
 */
public int doEndLayoutTag() throws JspException {

	StringBuffer sb = new StringBuffer();
	if (layout) {
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</table>");
	}
	doEndHtml(sb);
	TagUtils.write(pageContext, sb.toString());
	htmlTag.doEndTag();

	return (EVAL_PAGE);

}
protected void doPrintHead(StringBuffer sb) throws JspException {
    if (key != null) {
        sb.append("\t<title>");
        sb.append(getLabel());
        sb.append("</title>\n");
    }
    sb.append("\t<link rel=\"stylesheet\" href='");
    sb.append(getSkin().getCssDirectory(pageContext.getRequest()));
    sb.append("/");
    sb.append(LayoutUtils.getSkin(pageContext.getSession()).getCssFileName());
    sb.append("' type=\"text/css\">\n");
    sb.append("\t<script language=\"Javascript\" src=\"");
    sb.append(getSkin().getConfigDirectory(pageContext.getRequest()));
    sb.append("/javascript.js\"></script>");
    sb.append("<script>var imgsrc=\"");
    sb.append(getSkin().getImageDirectory(pageContext.getRequest()));
    if (!getSkin().getImageDirectory(pageContext.getRequest()).endsWith("/"))
        sb.append("/");
	sb.append("\"; var scriptsrc=\"");
	sb.append(getSkin().getConfigDirectory(pageContext.getRequest()));
	if (!getSkin().getConfigDirectory(pageContext.getRequest()).endsWith("/")) sb.append("/");		        
    sb.append("\"; var langue=\"");
    sb.append(LayoutUtils.getLocale(pageContext).getLanguage());
    sb.append("\"; var contextPath=\"");
    sb.append(((HttpServletRequest)pageContext.getRequest()).getContextPath());
    sb.append("\";</script>");
}
/**
 * Process the start of this tag.
 *
 * @exception JspException if a JSP exception has occurred
 */
public int doStartLayoutTag() throws JspException {

	// append the html tag
	LayoutUtils.copyProperties(htmlTag,this);
	htmlTag.doStartTag();
	
	// append the head tag	
	TagUtils.write(pageContext, "\n<head>\n\t");
	
	LayoutUtils.copyProperties(baseTag,this);
	baseTag.doStartTag();
	baseTag.doEndTag();
	TagUtils.write(pageContext, "\n");
	
	StringBuffer sb = new StringBuffer();
	doPrintHead(sb);
	TagUtils.write(pageContext, sb.toString());
		

	sb.setLength(0);
	sb.append("\n</head>\n");
	
	sb.append("<body");
	if (onload!=null) {
		sb.append(" onload=\"");
		sb.append(onload);
		sb.append("\"");
	}
	sb.append(">\n");

	// append the title table
	if (styleClass!=null) {
		doStartPanel(sb);
		if (key!=null) doPrintTitle(sb);
		doEndPanel(sb);
	}
	
	// prepare the body layout
	if (layout) {
		doBeforeBody(sb);
		sb.append("<tr>");
		sb.append("<td align=\"center\">");
	}

	TagUtils.write(pageContext, sb.toString());
	return (EVAL_BODY_INCLUDE);

}
public boolean getLocale() {
		return locale;
	}
	public boolean getXhtml() {
		return xhtml;
	}
	public boolean isLocale() {
		return locale;
	}
/**
  * Release any acquired resources.
  */
public void release() {
	super.release();

	htmlTag.release();
	baseTag.release();
	
	locale = false;
	xhtml = false;
	
	width = "80%";
	align = "center";
	
	onload = null;
	layout = true;
	contentAlign = "center";
}
/**
 * Creation date: (30/05/01 16:07:40)
 * @param newLocale boolean
 */
public void setLocale(boolean newLocale) {
	locale = newLocale;
}
	public void setXhtml(boolean xhtml) {
		this.xhtml = xhtml;
	}
	public String getOnload() {
		return onload;
	}

	public void setOnload(String in_onload) {
		onload = in_onload;
	}
	
	public void setAlign(String in_align) {
		contentAlign = in_align;
	}
	
	public void setLayout(boolean in_layout) {
		layout = in_layout;
	}

}
