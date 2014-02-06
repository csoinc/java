package fr.improve.struts.taglib.layout.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import fr.improve.struts.taglib.layout.collection.CollectionItemTag;
import fr.improve.struts.taglib.layout.collection.CollectionTag;
import fr.improve.struts.taglib.layout.datagrid.Datagrid;
import fr.improve.struts.taglib.layout.el.Expression;

/**
 * Simple implementation of the IDatagird renderer..
 * 
 * @author: Jean-Noël Ribette
 */
public class BasicDatagrid implements IDatagridRenderer {
	protected Map styles = new HashMap();		
	protected CollectionTag collectionTag;
	protected PageContext pageContext;
	protected String styleClass;
	
	public void init(PageContext pg, String in_styleClass, TagSupport in_tag) throws JspException {		
		collectionTag = (CollectionTag) in_tag;
		pageContext = pg;
		styleClass = collectionTag.getStyleClass();
		styles.clear();
		styles.put(Datagrid.SELECTED, styleClass + "_SEL");
		styles.put(Datagrid.REMOVED, styleClass + "_DEL");		
	}
	/**
	 * Display a special message if the collection is empty.
	 */
	public void doPrintEmptyCollection(StringBuffer out_buffer, String in_message) {
	    out_buffer.append("<tr><td colspan=\"");
	    out_buffer.append(collectionTag.getHeaders().size());
	    out_buffer.append("\" class=\"");
	    out_buffer.append(styleClass);
	    out_buffer.append("\">&nbsp;");
	    out_buffer.append(in_message);
	    out_buffer.append("</td></tr>");		
	}	
	/**
	 * Overrides the display ot the title.
	 * PENDING: use a nice layout.
	 */
	public void doPrintTitle(StringBuffer buffer, String title) {
		if (title!=null) {
			if (styleClass!=null) {
			    buffer.append("<p class=\"");
			    buffer.append(styleClass);
			    buffer.append("\">");
			}
			buffer.append(title);
			if (styleClass!=null) {
				buffer.append("</p>");								
			}
			//buffer.append("</td></tr><tr align=\"center\"><td colspan=\"2\">");
		}
	}	
	/**
	 * Override doStartPanel
	 */
	public void doStartPanel(StringBuffer buffer, String align, String width) {
		if (collectionTag.getWidth()!=null || collectionTag.getHeight()!=null) {
			buffer.append("<div style=\"");
			if (collectionTag.getWidth()!=null) {
				buffer.append("width=");
				buffer.append(collectionTag.getWidth());
				buffer.append(";");	
				buffer.append("overflow-x:auto;");				
			}	
			if (collectionTag.getHeight()!=null) {
				buffer.append("height=");
				buffer.append(collectionTag.getHeight());
				buffer.append(";");	
				buffer.append("overflow-y:auto;");
			}			
			buffer.append("\">");
		}
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
		buffer.append("<table cellspacing=\"1\" cellpadding=\"1\" border=\"0\" width=\"100%\"");
		if (collectionTag.getStyleId()!=null) {
			buffer.append(" id=\"");
			buffer.append(collectionTag.getStyleId());
			buffer.append("\"");	
		}
		buffer.append(">\n");
	}	
	/**
	 * Prepare to display the headers.
	 */
	public void doStartHeaders(StringBuffer out_buffer) {
		out_buffer.append("<tr>");
	}
	/**
	 * Display a header.
	 */
	public void doPrintHeader(StringBuffer out_buffer, String in_header, String in_width, String in_sortUrl) {
		out_buffer.append("<th");
		if (styleClass!=null) {
			out_buffer.append(" class=\"");
		    out_buffer.append(styleClass);
		    out_buffer.append("\"");
		}
		if (in_width!=null) {
			out_buffer.append(" width=\"");
			out_buffer.append(in_width);	
			out_buffer.append("\"");
		}
		out_buffer.append(">");
					
		out_buffer.append(doPrintSortUrl(in_header, in_sortUrl));
			
		
		out_buffer.append("</th>");
	}
	
	/**
	 * Display an hyperlink to sort the column. 
	 */
	protected String doPrintSortUrl(String in_header, String in_sortUrl) {
		if (in_sortUrl == null) {
			return in_header;
		}
        else if (collectionTag.getSortPictogram().equalsIgnoreCase("none")) {
            // pas d'image pour faire le tri, mais un lien sur le titre 
			StringBuffer lc_tempBuffer = new StringBuffer("<table border=\"0\" width=\"100%\"><tr><td>");
            lc_tempBuffer.append("&nbsp;");
            lc_tempBuffer.append("</td><td>");
			lc_tempBuffer.append("<a href=\"");
			lc_tempBuffer.append(in_sortUrl);
            lc_tempBuffer.append("\">");
            lc_tempBuffer.append(in_header);
            lc_tempBuffer.append("</a>");
            lc_tempBuffer.append("</td></tr></table>");        
            return lc_tempBuffer.toString();
        }                
        else {
            // utilisation d'une image pour faire le tri
			StringBuffer lc_tempBuffer = new StringBuffer("<table border=\"0\" width=\"100%\"><tr><td>");
			lc_tempBuffer.append("<a href=\"");
			lc_tempBuffer.append(in_sortUrl);
			lc_tempBuffer.append("\"><img src=\"");
			lc_tempBuffer.append(LayoutUtils.getSkin(pageContext.getSession()).getImageDirectory(pageContext.getRequest()));
			lc_tempBuffer.append("/");
			lc_tempBuffer.append(LayoutUtils.getSkin(pageContext.getSession()).getProperty(collectionTag.getSortPictogram()));
			lc_tempBuffer.append("\" border=\"0\" alt=\"");
			lc_tempBuffer.append(LayoutUtils.getSkin(pageContext.getSession()).getProperty(collectionTag.getSortLabel()));
			lc_tempBuffer.append("\"></a>");		
	
			lc_tempBuffer.append("</td><td>");
			lc_tempBuffer.append(in_header);	
			lc_tempBuffer.append("</td></tr></table>");
		
			return lc_tempBuffer.toString();
		}
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
		out_buffer.append("<tr");
		boolean lc_onclick = false;		
		if (collectionTag.getOnRowClick()!=null) {
			out_buffer.append(" onclick=\"");
			out_buffer.append(Expression.evaluate(collectionTag.getOnRowClick(), pageContext));
			out_buffer.append("\"");
			lc_onclick = true;
		}
		if (collectionTag.getOnRowDblClick()!=null) {
			out_buffer.append(" ondblclick=\"");
			out_buffer.append(Expression.evaluate(collectionTag.getOnRowDblClick(), pageContext));
			out_buffer.append("\"");
			lc_onclick = true;
		}
		if (collectionTag.getOnRowMouseOver()!=null) {
			out_buffer.append(" onmouseover=\"");
			out_buffer.append(Expression.evaluate(collectionTag.getOnRowMouseOver(), pageContext));
			out_buffer.append("\"");
			lc_onclick = true;
		}
		if (collectionTag.getOnRowMouseOut()!=null) {
			out_buffer.append(" onmouseout=\"");
			out_buffer.append(Expression.evaluate(collectionTag.getOnRowMouseOut(), pageContext));
			out_buffer.append("\"");
		}		
		if (lc_onclick) {
			out_buffer.append(" style=\"cursor:pointer;cursor:hand;\"");			
		}
			
		out_buffer.append(" class=\"");	
		if (collectionTag.getIndex() % 2 ==0) {
			out_buffer.append(getRowStyleClass());
		} else {
			 out_buffer.append(getRowStyleClass2());
		}		
		out_buffer.append("\">");					
	}
	/**
	 * Render an element in the line.
	 */
	public void doPrintItem(StringBuffer out_buffer, String in_item, String[] in_styleClass, String in_id) {		
		out_buffer.append("<td");
		/*
		if (in_styleClass[0] != null) {
			out_buffer.append(" class=\"");
			out_buffer.append(in_styleClass[0]);
			out_buffer.append("\"");
		}
		*/
		if (in_styleClass.length>1) {
			out_buffer.append(" style=\"");
			for (int i = 0; i < in_styleClass.length-1; i++) {
				out_buffer.append(in_styleClass[i+1]);
			}
			out_buffer.append("\"");
		}
		
		Integer lc_int = (Integer) pageContext.getAttribute(CollectionItemTag.SPAN_KEY);
		if (lc_int!=null && lc_int.intValue()!=1) {
			out_buffer.append(" rowspan=\"");
			out_buffer.append(lc_int.intValue());
			out_buffer.append("\"");
		} 
		
		out_buffer.append(">");
		
		if (in_id!=null) {
			out_buffer.append("<div id=\"");
			out_buffer.append(in_id);
			out_buffer.append("\">");
		}		
		
		out_buffer.append(in_item);
		
		if (in_id!=null) {
			out_buffer.append("</div>");
		}

		out_buffer.append("</td>");
	}
	/**
	 * Finish to render a line.
	 */	
	public void doEndItems(StringBuffer out_buffer) {
		out_buffer.append("</tr>");
	}
	public void doEndPanel(StringBuffer buffer) {
		buffer.append("</table></td></tr></table>\n");
		if (collectionTag.getWidth()!=null || collectionTag.getHeight()!=null) {
			buffer.append("</div>");	
		}		
	}
	
	// ------------------------- specific datagrid methods ----------------- //
		
	/**
	 * Return the style of the specified column.
	 */
	public String getColumnStyleClass(int in_columnNumber) {
		// No specified style
		return "";
	}

	/**
	 * Return the usual row style class.
	 */
	public String getRowStyleClass() {		
		return styleClass;
	}

	/**
	 * Return the alternate style class.
	 */
	public String getRowStyleClass2() {
		return styleClass + "2";
	}

	/**
	 * Return the styles for specific states.
	 */
	public Map getRowStyleClassMap() {
		return styles;
	}
	
	// ---------------- unused inherited methods --------------- //
	
	public void doAfterBody(StringBuffer buffer) throws JspException {
		// Do nothing
	}

	public void doBeforeBody(StringBuffer buffer, String align) throws JspException {
		// Do nothing
	}

	public void doPrintBlankLine(StringBuffer buffer, int cols) throws JspException {
		// Do nothing
	}

}