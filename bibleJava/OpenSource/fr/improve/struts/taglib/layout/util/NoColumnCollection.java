package fr.improve.struts.taglib.layout.util;


/**
 * Modification of the BasicCollection implementation of CollectionInterface.
 * This version does not display collection headers.
 * 
 * @author: Jean-Noël Ribette
 */
public class NoColumnCollection extends BasicCollection {

	/**
	 * Prepare to display the headers: do nothing.
	 */
	public void doStartHeaders(StringBuffer out_buffer) {
		// Do nothing.
	}
	/**
	 * Display a header : do nothing.
	 */
	public void doPrintHeader(StringBuffer out_buffer, String in_header, String in_width, String in_sortUrl) {
		// Do nothing.
	}
	
	/**
	 * Finish to render the headers : no nothing.
	 */	
	public void doEndHeaders(StringBuffer out_buffer) {
		// Do nothing.
	}
	
	public void doStartItems(StringBuffer out_buffer) {
		// Do nothing.
	}
	
	/**
	 * Render an element in the line.
	 */
	public void doPrintItem(StringBuffer out_buffer, String in_item, String[] in_styleClass, String in_id) {		
		out_buffer.append("<li");
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

		out_buffer.append("</li>");
	}
	/**
	 * Finish to render a line.
	 */	
	public void doEndItems(StringBuffer out_buffer) {
		// Do nothing.
	}
	
	
}