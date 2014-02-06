package fr.improve.struts.taglib.layout.util;

import java.util.MissingResourceException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import fr.improve.struts.taglib.layout.HtmlTag;
import fr.improve.struts.taglib.layout.PanelTag;
import fr.improve.struts.taglib.layout.skin.Skin;
/**
 * Insert the type's description here.
 * Creation date: (06/06/01 13:14:21)
 * @author: Jean-Noël Ribette
 */
public class ImagePanel implements PanelInterface {
	private String styleClass;
	private boolean isNested = false;	
	
	private	String bg_width;
	private String bg_height;
	private String bg_bottom_height;
	private String bg_top_height;
	private String bottom;
	private String bottom_LEFT;
	private String bottom_RIGHT;
	private String left;
	private String right;
	private String top;
	private String top_LEFT;
	private String top_RIGHT;
	
	private String imageDir;
	public void doAfterBody(StringBuffer buffer) {
	buffer.append("</table></td>\n");
}
	public void doBeforeBody(StringBuffer buffer, String align) {
	buffer.append("<td class=");
	buffer.append(styleClass);
	buffer.append("><table width=\"100%\"");
	if (align!=null) {
			buffer.append(" align=\"");
			buffer.append(align);
			buffer.append("\"");
	}
	buffer.append("	border=0>\n");
}
/**
 * doEndPanel method comment.
 */
public void doEndPanel(StringBuffer buffer) {
	// buffer.append("</table></td>\n");
	
	// end of the middle line
	buffer.append("<td width=\"");
	buffer.append(bg_width);
	buffer.append("\" background=\"");
	buffer.append(right);
	buffer.append("\" style=\"background-repeat:repeat-y;\">" + doPrintClearPix() + "</td>");
	buffer.append("</tr>");

	// last line
	buffer.append("<tr>");

	buffer.append("<td width=\"");
	buffer.append(bg_width);
	buffer.append("\" height=\"");
	buffer.append(bg_bottom_height);
	buffer.append("\"><img src=\"");
	buffer.append(bottom_LEFT);
	buffer.append("\"></td>");
	
	buffer.append("<td height=\"");
	buffer.append(bg_bottom_height);
	buffer.append("\" background=\"");
	buffer.append(bottom);
	buffer.append("\" style=\"background-repeat:repeat-x;\">"+ doPrintClearPix() + "</td>");

	buffer.append("<td width=\"");
	buffer.append(bg_width);
	buffer.append("\" height=\"");
	buffer.append(bg_bottom_height);
	buffer.append("\"><img src=\"");
	buffer.append(bottom_RIGHT);
	buffer.append("\" valign=\"top\"></td>");
	
	buffer.append("</tr>");

	//end table
	buffer.append("</table>");
	}

	public String doPrintClearPix(){
		return "<img src=\""+imageDir+ "/clearpix.gif\" height=\"1\" width=\"1\" border=\"0\">";	
	}
	
	public void doPrintBlankLine(StringBuffer buffer, int cols) {
		buffer.append("<tr><td colspan=" + cols + ">" + doPrintClearPix() + "</td></tr>\n");
	}
/**
 * doPrintTitle method comment.
 */
public void doPrintTitle(StringBuffer buffer, String title) {
	buffer.append("<td");

	if (title!=null) {
		buffer.append("><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"");
		buffer.append(styleClass);
		buffer.append("\"><tr><th class=\"");
		buffer.append(styleClass);
		buffer.append("\" height=\"");
		buffer.append(bg_top_height);
		buffer.append("\" background=\"");
		buffer.append(top);
		buffer.append("\" style=\"background-repeat:repeat-x;\"");
		buffer.append("\">");
		buffer.append(title);
		buffer.append("</th></tr></table>");
	} else {
		buffer.append(" height=\"");
		buffer.append(bg_top_height);
		buffer.append("\" background=\"");
		buffer.append(top);
		buffer.append("\" style=\"background-repeat:repeat-x;\">" + doPrintClearPix());
	}
	buffer.append("</td>");

	buffer.append("<td width=\"");
	buffer.append(bg_width);
	buffer.append("\" height=\"");
	buffer.append(bg_top_height);
	buffer.append("\"><img src=\"");
	buffer.append(top_RIGHT);
	buffer.append("\"></td>");
	
	buffer.append("</tr>");
	
	// middle
	buffer.append("<tr>");

	buffer.append("<td width=\"");
	buffer.append(bg_width);
	buffer.append("\" background=\"");
	buffer.append(left);
	buffer.append("\" style=\"background-repeat:repeat-y;\">" + doPrintClearPix() + "</td>");
		
	// buffer.append("<td><table cellspacing=0 cellpadding=0 border=0 width=100%>\n");	
}
/**
 * doStartPanel method comment.
 */
public void doStartPanel(StringBuffer buffer, String align, String width) {
	// table
	buffer.append("<table cellspacing=0 cellpadding=0 border=0");
	if (align!=null) {
		buffer.append(" align=");
		buffer.append(align);
	}
	if (width!=null) { 
		buffer.append(" width=");
		buffer.append(width);
	}
	buffer.append(">");
	
	// top line
	buffer.append("<tr>");

	buffer.append("<td width=\"");
	buffer.append(bg_width);
	buffer.append("\" height=\"");
	buffer.append(bg_top_height);
	buffer.append("\"><img src=\"");
	buffer.append(top_LEFT);
	buffer.append("\"></td>");
}
	public void init(PageContext pageContext, String in_styleClass, TagSupport in_panel) throws JspException {
		this.styleClass = in_styleClass;
		
		Skin l_skin = LayoutUtils.getSkin(pageContext.getSession());
		imageDir = l_skin.getImageDirectory(pageContext.getRequest());
		
		try {
			bg_width = l_skin.getProperty("width");
			bg_height = l_skin.getProperty("height");
			
			top_LEFT = l_skin.getProperty("top_LEFT");
			top = l_skin.getProperty("top");
			top_RIGHT = l_skin.getProperty("top_RIGHT");
			left = l_skin.getProperty("left");
			right = l_skin.getProperty("right");
			bottom_LEFT = l_skin.getProperty("bottom_LEFT");
			bottom = l_skin.getProperty("bottom");
			bottom_RIGHT = l_skin.getProperty("bottom_RIGHT");
		} catch (MissingResourceException e) {
			throw new JspException("Bad imagepanel configuration: can't find key " + e.getKey() + " in file " + e.getClassName());
		}
		
		try {
			bg_top_height = l_skin.getProperty("top_height");
			bg_bottom_height = l_skin.getProperty("bottom_height");
		
		} catch (MissingResourceException e) {
			if (bg_height.length()!=0){
				bg_bottom_height = bg_height;
				bg_top_height = bg_height;
			}
		}
		
		Tag lc_parent = in_panel!=null ? TagSupport.findAncestorWithClass(in_panel, PanelTag.class) : null;
		isNested = !LayoutUtils.isPanelNesting() && lc_parent != null && !(lc_parent instanceof HtmlTag);
	}
}
