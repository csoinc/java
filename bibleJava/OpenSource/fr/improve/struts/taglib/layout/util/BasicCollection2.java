package fr.improve.struts.taglib.layout.util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import fr.improve.struts.taglib.layout.collection.CollectionTag;
;

/**
 * Simple implementation of the CollectionInterface.
 * 
 * @author: Jean-Noël Ribette
 */
public class BasicCollection2 extends BasicPanel implements CollectionInterface {
	CollectionTag collectionTag;
	PageContext pageContext;
	/**
	 * Display a special message if the collection is empty.
	 */
	public void doPrintEmptyCollection(StringBuffer out_buffer, String in_message) {
	    out_buffer.append("<tr><td><span class=\"");
	    out_buffer.append(styleClass);
	    out_buffer.append("\">&nbsp;");
	    out_buffer.append(in_message);
	    out_buffer.append("</span></td></tr>");		
	}	
	/**
	 * Overrides the display ot the title.
	 */
	public void doPrintTitle(StringBuffer buffer, String title) {
		if (title!=null) {
			if (isNested) {
				buffer.append("<td colspan=\"");
				buffer.append(LayoutUtils.getSkin(pageContext.getSession()).getFieldInterface().getColumnNumber());
				buffer.append("\">");
			}
			if (styleClass!=null) {
			    buffer.append("<p class=\"");
			    buffer.append(styleClass);
			    buffer.append("\">");
			}
			buffer.append(title);
			if (styleClass!=null) {
				buffer.append("</p>");								
			}
			if (isNested) {
				buffer.append("</td></tr><tr align=\"center\">");
			}
		}
	}	
	/**
	 * 
	 */
	private void doPrintSeparator(StringBuffer out_buffer) {
		out_buffer.append("<tr>");
		out_buffer.append("<td colspan=\"");
		out_buffer.append(2 * collectionTag.getNbOfColumns() - 1);
		out_buffer.append("\" class=\"tableborder\"><img src=\"");
		out_buffer.append(LayoutUtils.getSkin(pageContext.getSession()).getImageDirectory(pageContext.getRequest()));
		out_buffer.append("shim.gif\" width=\"1\" height=\"1\"></td>");
		out_buffer.append("</tr>\n");		
	}
	/**
	 * Start display.
	 */
	public void doStartPanel(StringBuffer out_buffer, String in_align, String in_width) {
		out_buffer.append("<td colspan=\"");
		out_buffer.append(LayoutUtils.getSkin(pageContext.getSession()).getFieldInterface().getColumnNumber());
		out_buffer.append("\">\n");	
		out_buffer.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"");
		if (in_align!=null) {
			out_buffer.append(" align=\"");
			out_buffer.append(in_align);
			out_buffer.append("\"");
		}
		if (in_width!=null) { 
			out_buffer.append(" width=\"");
			out_buffer.append(in_width);
			out_buffer.append("\"");
		}
		out_buffer.append(">");
	}
	/**
	 * End display
	 */
	public void doEndPanel(StringBuffer out_buffer) {
		doPrintSeparator(out_buffer);
		out_buffer.append("</table>");
		out_buffer.append("</td>\n");		
	}
	/**
	 * Prepare to display the headers.
	 */
	public void doStartHeaders(StringBuffer out_buffer) {
		doPrintSeparator(out_buffer);
		out_buffer.append("<tr>");
	}
	/**
	 * Display a header.
	 */
	public void doPrintHeader(StringBuffer out_buffer, String in_header, String in_width, String in_sortUrl) {
		if (collectionTag.getColumn()!=0) {
			out_buffer.append("<td class=\"tableborder\" width=\"1\"><div class=\"SHIMARRAY\" width=\"1\" height=\"23\"></div></td>");
		}
		out_buffer.append("<th class=\"tableentete\">");
		out_buffer.append(in_header);
		out_buffer.append("</th>");
	}
	/**
	 * Finish to render the headers.
	 */	
	public void doEndHeaders(StringBuffer out_buffer) {
		out_buffer.append("</tr>");
	}
	/**
	 * Prepare to render a line.
	 */	
	public void doStartItems(StringBuffer out_buffer) {
		doPrintSeparator(out_buffer);
		out_buffer.append("<tr>");
	}
	/**
	 * Render an element in the line.
	 */
	public void doPrintItem(StringBuffer out_buffer, String in_item, String[] in_styleClass, String in_id) {
		if (collectionTag.getColumn()!=0) {
			out_buffer.append("<td class=\"tableborder\" width=\"1\"><div class=\"SHIMARRAY\" width=\"1\" height=\"23\"></div></td>");
		}
		out_buffer.append("<td");
//		if (in_styleClass[0] != null) {
			out_buffer.append(" class=\"");
//			out_buffer.append(in_styleClass[0]);
			out_buffer.append("tabletexte");
			out_buffer.append("\"");
//}		
		if (in_id!=null) {
			out_buffer.append(" id=\"");
			out_buffer.append(in_id);
			out_buffer.append("\"");
		}		
		out_buffer.append(">");
		
		out_buffer.append(in_item);

		out_buffer.append("</td>");
	}
	/**
	 * Finish to render a line.
	 */	
	public void doEndItems(StringBuffer out_buffer) {
		out_buffer.append("</tr>");
	}
	/**
	 * Init the collection.
	 */
	public void init(PageContext pg, String in_styleClass, TagSupport in_panel) throws JspException {
		super.init(pg, in_styleClass, in_panel);
		collectionTag = (CollectionTag) in_panel;
		pageContext = pg;
	}	
}