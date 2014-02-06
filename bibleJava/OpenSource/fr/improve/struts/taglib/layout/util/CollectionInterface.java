package fr.improve.struts.taglib.layout.util;

import javax.servlet.jsp.JspException;

/**
 * Definition of the collection interface
 * @author: Jean-Noël Ribette
 */
public interface CollectionInterface  extends PanelInterface {
	/**
	 * Prepare to render the headers.
	 */
	public void doStartHeaders(StringBuffer out_buffer);
	/**
	 * Render a header.
	 */	
	public void doPrintHeader(StringBuffer out_buffer, String in_header, String in_width, String in_sortUrl);
	/**
	 * Finish to render the headers.
	 */	
	public void doEndHeaders(StringBuffer out_buffer);	
	/**
	 * Prepare to render a line.
	 */	
	public void doStartItems(StringBuffer out_buffer) throws JspException;	
	/**
	 * Render an element in the line.
	 */	
	public void doPrintItem(StringBuffer out_buffer, String in_item, String[] in_styleClass, String in_id);	
	/**
	 * Finish to render a line.
	 */	
	public void doEndItems(StringBuffer out_buffer);
	/**
	 * Render an empty collection
	 */
	public void doPrintEmptyCollection(StringBuffer out_buffer, String in_message);
}