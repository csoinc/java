package fr.improve.struts.taglib.layout.util;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.pager.PagerTag;

/**
 * Interface for renderer that generates a pager HTML code.
 * Unlike other renderers, pager renderer are singleton objects.
 * 
 * @author JN RIBETTE
 */
public interface IPagerRenderer {
	
	/**
	 * Start displaying the pager.
	 */
	public void doStartPager(PagerTag in_tag, StringBuffer in_buffer);

	/**
	 * Display the link the previous page
	 */
	public void doPrintPrevious(PagerTag tag, StringBuffer in_buffer, int currentPage);

	/**
	 * Display links to numbered pages.
	 */
	public void doPrintMain(PagerTag tag, StringBuffer in_buffer, int maxPageItems, int currentPage);
	
	/**
	 * Display the link to the next page
	 */
	public void doPrintNext(PagerTag tag, StringBuffer in_buffer, int maxPageItems, int currentPage);

	/**
	 * Display an input box to type in the page number
	 */
	public void doPrintDirect(PagerTag tag, StringBuffer in_buffer, int maxPageItems, int currentPage) throws JspException;
	
	/**
	 * End displaying the pager.
	 */
	public void doEndPager(PagerTag in_tag, StringBuffer in_buffer);
	
}
