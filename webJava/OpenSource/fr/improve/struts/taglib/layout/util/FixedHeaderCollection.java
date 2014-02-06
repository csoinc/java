package fr.improve.struts.taglib.layout.util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import fr.improve.struts.taglib.layout.collection.CollectionItemTag;
import fr.improve.struts.taglib.layout.collection.CollectionTag;
import fr.improve.struts.taglib.layout.el.Expression;

/**
 * New implementation of the CollectionInterface, with fixed header.
 * 
 * @author: Jean-Noël Ribette, F. BELLINGARD, Damien Viel
 */
public class FixedHeaderCollection extends BasicPanel implements CollectionInterface, IMathCollectionRenderer, IFooterRenderer, IMultiLevelHeaderRenderer {
	protected CollectionTag collectionTag;
	protected PageContext pageContext;
	
	public void init(PageContext pg, String in_styleClass, TagSupport in_tag) throws JspException {
		super.init(pg, in_styleClass, in_tag);
		collectionTag = (CollectionTag) in_tag;
		pageContext = pg;
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
			if (collectionTag.getWidth()!=null && collectionTag.getHeight()!=null) {
				buffer.append("width:");
				buffer.append(collectionTag.getWidth());
				buffer.append("px;");	
				buffer.append("height:");
				buffer.append(collectionTag.getHeight());
				buffer.append("px;");	
				buffer.append("overflow:auto;");
			} else {
				if (collectionTag.getWidth()!=null) {
					buffer.append("width:");
					buffer.append(collectionTag.getWidth());
					buffer.append("px;");	
					buffer.append("overflow-x:auto;");				
				}	
				if (collectionTag.getHeight()!=null) {
					buffer.append("height:");
					buffer.append(collectionTag.getHeight());
					buffer.append("px;");	
					buffer.append("overflow-y:auto;overflow:-moz-scrollbars-vertical;");
				}
			}
			buffer.append("\">");
		}
		buffer.append("<table");
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
		out_buffer.append("<tr id=\"head\">");
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
		out_buffer.append(">");
	}
	/**
	 * Render an element in the line.
	 */
	public void doPrintItem(StringBuffer out_buffer, String in_item, String[] in_styleClass, String in_id) {		
		out_buffer.append("<td");
		if (in_styleClass[0] != null) {
			out_buffer.append(" class=\"");
			out_buffer.append(in_styleClass[0]);
			out_buffer.append("\"");
		}
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
		buffer.append("</table>\n");
		if (collectionTag.getWidth()!=null || collectionTag.getHeight()!=null) {
			buffer.append("</div>");
			
			// Generate the div that will hold the collection headers in front of the collection.
			String lc_id = WidgetUtils.generateId(pageContext.getRequest(), "collectionLayerId");
			buffer.append("<div style=\"position:absolute;top:0;left:0\" id=\"colLayer");
			buffer.append(lc_id);
			buffer.append("\"></div>");
			
			// Load collection scrollbar javascript.
			if (pageContext.getRequest().getAttribute("zzz")==null) {
				buffer.append("<script src=\"");
				buffer.append(LayoutUtils.getSkin(pageContext.getSession()).getConfigDirectory(pageContext.getRequest()));
				buffer.append("/scrollbar.js\"></script>");
				pageContext.getRequest().setAttribute("zzz", "zzz");
			}
			
			// Initialize the div content.
			buffer.append("<script>addLoadEvent(function() {initScrollbar(\"");
			buffer.append(collectionTag.getStyleId());
			buffer.append("\",\"colLayer");
			buffer.append(lc_id);
			buffer.append("\")});</script>");
		}		
	}
	public void endFooter(StringBuffer in_buffer) {
		in_buffer.append("</tr>\n");

	}
	public void printFooterElement(StringBuffer in_buffer, String in_element, int in_span) {
		in_buffer.append("<td ");
		if (in_span>1) {
			in_buffer.append(" colspan=\"");
			in_buffer.append(in_span);
			in_buffer.append("\"");
		}
		in_buffer.append(">");
		in_buffer.append(in_element);
		in_buffer.append("</td>");

	}
	public void startFooter(StringBuffer in_buffer) {
		in_buffer.append("<tr>");
	}
	
	// ------------------- Math data renderer -----------------------
		
	/**
	 * Print end of math data
	 */
	public void endMathData(StringBuffer in_buffer) {
		in_buffer.append("</tr>\n");
	}
	
	public void renderMathData(StringBuffer in_buffer, String in_element, int in_span, String in_resultId, String in_styleClass) {
		in_buffer.append("<td");
		if (in_styleClass!=null){
			in_buffer.append(" class=\"");
			in_buffer.append(in_styleClass);
			in_buffer.append("\"");
		}
		if (in_span>1){
			in_buffer.append(" colspan=\"");
			in_buffer.append(in_span);
			in_buffer.append("\"");
		}
		if (in_resultId!=null && in_resultId.length()!=0){
			in_buffer.append(" id=\"" + in_resultId + "\"");
		}
		in_buffer.append(">");
		in_buffer.append(in_element);
		in_buffer.append("</td>\n");
	}
	
	public void startMathData(StringBuffer in_buffer) {
		in_buffer.append("<tr>\n");
	}
	
	// ---------------------- Multi level header renderer ---------------------
	
	public void renderMultiLevelHeader(StringBuffer in_buffer, String in_title, String in_sortUrl, String in_styleClass, int in_colspan, int in_rowspan, String in_width) {
		in_buffer.append("<th");
		if (in_colspan!=1) {
			in_buffer.append(" colspan=\"");
			in_buffer.append(in_colspan);
			in_buffer.append("\"");
		}
		if (in_styleClass!=null) {
			in_buffer.append(" class=\"");
			in_buffer.append(in_styleClass);
			in_buffer.append("\"");
		}
		if (in_rowspan!=1) {
			in_buffer.append(" rowspan=\"");
			in_buffer.append(in_rowspan);
			in_buffer.append("\"");
		}
		if (in_width!=null) {
			in_buffer.append(" width=\"");
			in_buffer.append(in_width);
			in_buffer.append("\"");
		}
		in_buffer.append(">");
		in_buffer.append(doPrintSortUrl(in_title, in_sortUrl));
		in_buffer.append("</th>");
	}

	public void endMultiLevelHeaderRow(StringBuffer in_buffer) {
		in_buffer.append("</tr>\n");
	}
	public void startMultiLevelHeaderRow(StringBuffer in_buffer) {
		in_buffer.append("<tr valign=\"top\" id=\"header\">");
	}
}