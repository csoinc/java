package fr.improve.struts.taglib.layout.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import fr.improve.struts.taglib.layout.collection.BaseCollectionTag;

/**
 * Implementation of the CollectionInterface displaying each element onf the collection in a different panels.
 * The selected properties of the bean in the collection are displayed on a different line in each panel.
 * 
 * @author: Jean-Noël Ribette
 */
public class NewsCollection extends BasicPanel implements CollectionInterface {
	private List headers = new ArrayList();
	private PanelInterface innerPanel = new BasicPanel();
	private BaseCollectionTag collectionTag;
	/**
	 * Init the collection.
	 */
	public void init(PageContext pg, String in_styleClass, TagSupport in_panel) throws JspException {
		super.init(pg, in_styleClass, in_panel);
		headers.clear();
		innerPanel.init(pg, in_styleClass, in_panel);
		collectionTag = (BaseCollectionTag) in_panel;
	}
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
				buffer.append("<tr><td colspan=\"");
				buffer.append(colspan);
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
				buffer.append("</td></tr>");
			}
		}
	}	
	/**
	 * Prepare to display the headers.
	 */
	public void doStartHeaders(StringBuffer out_buffer) {
		// do nothing.
	}
	/**
	 * Display a header.
	 */
	public void doPrintHeader(StringBuffer out_buffer, String in_header, String in_width, String in_sortUrl) {
		// Memorize the header.
		headers.add(in_header);
	}
	/**
	 * Finish to render the headers.
	 */	
	public void doEndHeaders(StringBuffer out_buffer) {
		// do nothing.
	}
	/**
	 * Prepare to render a line.
	 */	
	public void doStartItems(StringBuffer out_buffer) {
		innerPanel.doStartPanel(out_buffer, "center", collectionTag.getWidth());
	}
	/**
	 * Render an element in the line.
	 */
	public void doPrintItem(StringBuffer out_buffer, String in_item, String[] in_styleClass, String in_id) {
		if (collectionTag.getColumn()==0) {
			innerPanel.doPrintTitle(out_buffer, in_item);
			out_buffer.append("<tr><td class=\"");
			out_buffer.append(in_styleClass[0]);
			out_buffer.append("\"><table border=0>");							
		} else {
			out_buffer.append("<tr><td align=right class=");
			out_buffer.append(styleClass);
			out_buffer.append(">");
			out_buffer.append(headers.get(collectionTag.getColumn()));
			out_buffer.append(" : </td><td class=");
			out_buffer.append(in_styleClass[0]);
			out_buffer.append(">");
			out_buffer.append(in_item);
			out_buffer.append("</td></tr>");			
		}
	}
	/**
	 * Finish to render a line.
	 */	
	public void doEndItems(StringBuffer out_buffer) {
		out_buffer.append("</table></td></tr>");
		innerPanel.doEndPanel(out_buffer);
		out_buffer.append("<br>\n");
	}		
	public void doStartPanel(StringBuffer buffer, String align, String width) {
		if (isNested) {
			buffer.append("<tr><td colspan=\"2\">\n");	
		}
	}
	public void doEndPanel(StringBuffer buffer) {
		if (isNested) {		
			buffer.append("</td></tr>\n");		
		}
}	
}