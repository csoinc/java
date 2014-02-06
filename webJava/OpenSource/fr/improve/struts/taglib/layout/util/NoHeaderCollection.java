package fr.improve.struts.taglib.layout.util;

/**
 * Modification of the BasicCollection implementation of CollectionInterface.
 * This version does not display collection headers.
 * 
 * @author: Jean-Noël Ribette
 */
public class NoHeaderCollection extends BasicCollection {

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
}