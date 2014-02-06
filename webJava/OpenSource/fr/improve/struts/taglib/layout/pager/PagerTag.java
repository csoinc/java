package fr.improve.struts.taglib.layout.pager;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import fr.improve.struts.taglib.layout.LayoutTagSupport;
import fr.improve.struts.taglib.layout.collection.BaseCollectionTag;
import fr.improve.struts.taglib.layout.collection.CollectionTag;
import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.LayoutEventListener;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.skin.BadSkinConfigurationException;
import fr.improve.struts.taglib.layout.skin.Skin;
import fr.improve.struts.taglib.layout.sort.SortUtil;
import fr.improve.struts.taglib.layout.util.IPagerRenderer;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Limits the number of items displayed in a collection
 * <br>
 * @see BaseCollectionTag BaseCollectionTag
 *
 * @author: Jean-Noel Ribette
 **/
public class PagerTag
	extends LayoutTagSupport
	implements LayoutEventListener, PagerStatusListener {

	public static final String PAGE_NUMBER_KEY = "pagerPage";
	public static final String PAGER_SESSION_KEY = "pagerSessionId";
	protected static final String PAGER_INDEX = "fr.improve.struts.taglib.layout.pager.PagerTag.PAGER_INDEX";
	protected static final String PAGER_INDEX_CURRENT_PAGE = "fr.improve.struts.taglib.layout.pager.PagerTag.PAGER_INDEX_CURRENT_PAGE";
    
    protected static final String BOTTOM = "bottom";
    protected static final String TOP = "top";
    protected static final String BOTH = "both";
		
	protected String url;
	protected String maxPageItems;
	protected String jspMaxPageItems;
	
	/**
	 * Total number of elements in the list. 
	 */
	protected int size;
	protected int numberOfPage;
    protected String linksLocation = BOTTOM;
    private String pagerId = null;
    protected String sessionPagerId = null;
	
	protected String previousMsgKey = "layout.pager.previous.label";
	protected String nextMsgKey = "layout.pager.next.label";
	
	protected String previousImgKey = "layout.pager.previous.img";
	protected String nextImgKey = "layout.pager.next.img";
	
	protected static final String MAX_PAGE_ITEMS_PROPERTY = "layout.pager.maxPageItems";
	protected static final String MAX_LINKS_PROPERTY = "layout.pager.maxLinks";
	public static final String DISPLAY_DIRECT_LINK = "layout.pager.displayDirect";
	
	protected String gotoProperty = "layout.pager.goto";
	
	protected String width;
	protected String align;
	protected String styleClass;		
	protected String jspStyleClass;
	
	/**
	 * Pager renderer.
	 */
	protected IPagerRenderer renderer;
	
	
	protected void initDynamicValues() {
		jspStyleClass = styleClass;
		if (styleClass==null){
			styleClass = LayoutUtils.getSkin(pageContext.getSession()).getProperty("styleclass.pager",null);
		}
	}
	protected void reset() {
		styleClass = jspStyleClass;
		jspStyleClass = null;
	}
	/**
	 * Process pager status event.
	 */
	public Object processPagerStatusEvent(PagerStatusEvent in_event) throws JspException {			
			Integer[] lc_infos = new Integer[4];
			lc_infos[0] = new Integer(getCurrentPage());
			lc_infos[1] = new Integer(numberOfPage);
			lc_infos[2] = new Integer(size);
			lc_infos[3] = new Integer(maxPageItems);		
			return lc_infos;
	}
			
	/**
	 * Process StartLayout events.
	 */
	public Object processStartLayoutEvent(StartLayoutEvent in_event) throws JspException {
		Tag lc_source = in_event.getSource();		
		if (lc_source instanceof PagerContainer) {
			PagerContainer lc_pagerContainer = (PagerContainer) lc_source;
			lc_pagerContainer.setOffset(getIndexFirstItem());
			lc_pagerContainer.setLength(getIndexLastItem() - getIndexFirstItem());
			
			// ask for sort.			
			((CollectionTag) lc_pagerContainer).setSortType(CollectionTag.SORT_PAGER);
            
            if (TOP.equalsIgnoreCase(getLinksLocation())
                    || BOTH.equalsIgnoreCase(getLinksLocation())) {
                // put an anchor so that the javascript knows where to add the pager
                new StartLayoutEvent(this, null).send();
                
                StringBuffer lc_pagerAnchor = new StringBuffer();
                lc_pagerAnchor.append("<td colspan=\"");
                lc_pagerAnchor.append(String.valueOf(LayoutUtils.getSkin(pageContext.getSession()).getFieldInterface().getColumnNumber()));
                if (styleClass!=null) {
                    lc_pagerAnchor.append("\" class=\"");
                    lc_pagerAnchor.append(styleClass);
                }
                lc_pagerAnchor.append("\"><a id=\"");
                lc_pagerAnchor.append(pagerId + "Anchor");
                lc_pagerAnchor.append("\"/></td>");
                
                TagUtils.write(pageContext, lc_pagerAnchor.toString());
                new EndLayoutEvent(this, null).send();
            }
		}
		// Resent the event.
		return in_event.sendToParent(this);
	}
	
	/**
	 * Process EndLayout events
	 */
	public Object processEndLayoutEvent(EndLayoutEvent in_event) throws JspException {
		Tag lc_source = in_event.getSource();		
		boolean lc_printPager = false;
		if (lc_source instanceof PagerContainer) {
				PagerContainer lc_pagerContainerTag = (PagerContainer) lc_source;
				setSize(lc_pagerContainerTag.getSize());
				
				url = SortUtil.getURLForCollection("", (HttpServletRequest)pageContext.getRequest());				
				lc_printPager = true;							
		}
		// Print the pager
		if (lc_printPager) {
			// Compute the page number.
			computePageNumbers();
			
			StringBuffer sb = new StringBuffer();
			doPrintTag(sb);
			// prints the pager at the bottom
			TagUtils.write(pageContext, sb.toString());
            
			if (size >= Integer.parseInt(maxPageItems)) {
	            if (TOP.equalsIgnoreCase(getLinksLocation())
	                    || BOTH.equalsIgnoreCase(getLinksLocation())) {
	            	// use javascript to copy the pager at the anchor
	                StringBuffer js = new StringBuffer();
	                js.append("<script>");
	                
	                // clone and add the node close to the anchor
	                js.append("document.getElementById('");
	                js.append(pagerId+"Anchor");
	                js.append("').parentNode.insertBefore(document.getElementById('");
	                js.append(pagerId);
	                js.append("').cloneNode(true), document.getElementById('");
	                js.append(pagerId+"Anchor");
	                js.append("'));");
	                
	                // rename the new node
	                js.append("document.getElementById('");
	                js.append(pagerId);
	                js.append("').setAttribute('id','");
	                js.append(pagerId+"Top");
	                js.append("');");
	                
	                if (TOP.equalsIgnoreCase(getLinksLocation())) {
	                    // If on ly TOP, then the pager of the bottom must be removed
	                    js.append("document.getElementById('");
	                    js.append(pagerId);
	                    js.append("').parentNode.removeChild(document.getElementById('");
	                    js.append(pagerId);
	                    js.append("'));");
					}
	                
	                js.append("</script>");
	                
	                TagUtils.write(pageContext, js.toString());
	            }
			}
            
		}
		// Resent the event.
		return in_event.sendToParent(this);
	}

	public int doEndLayoutTag() throws JspException {
		url = null;
		size = 0;
		numberOfPage = 0;		
		maxPageItems = jspMaxPageItems;
		jspMaxPageItems = null;
		return EVAL_PAGE;
	}
    
	public int doStartLayoutTag() throws JspException {
		// compute the default max number of items per page.
		computeDefaultMaxPageItems();
		
        // create a unique identifier for this pager
        pagerId = "pager" + new Date().getTime();
        
        // to be sure that we won't have the same Date if there's another pager in the page
        try {Thread.sleep(1);} catch (InterruptedException e) {}       
		       
		addIndex ();
		return EVAL_BODY_INCLUDE;
	}
	
	/**
	 * Initialize the maxPageItems. 
	 * If a value is specified in the jsp, use it and evaluate it if it is an EL.
	 * If no value is specified, use the default value in the skin configuration file. 
	 */
	protected void computeDefaultMaxPageItems() {
		jspMaxPageItems = maxPageItems;
		maxPageItems = Expression.evaluate(maxPageItems, pageContext);
		if (maxPageItems==null) {			
			maxPageItems = LayoutUtils.getSkin(pageContext.getSession()).getProperty(MAX_PAGE_ITEMS_PROPERTY);
		}
	}
	
	/**
	 * Initialize the number of pages.
	 */
	protected void computePageNumbers() {
		int l_maxPageItems = Integer.parseInt(maxPageItems);
		numberOfPage = (int) Math.ceil((double)getSize() / (double)l_maxPageItems);
	}

	/**
	 * Add current index of this pager to pageContext
	 *
	 */
	protected void addIndex () {
		String l_index = (String) pageContext.getAttribute(PAGER_INDEX);
		if (l_index == null) {
			l_index = "0";
		} else {
			l_index = String.valueOf(Integer.parseInt(l_index) + 1);
		}
		pageContext.setAttribute(PAGER_INDEX, l_index);
	}
	public void release() {
		styleClass = null;
		maxPageItems = null;
        linksLocation = BOTTOM;
        pagerId = null;
        align = null;
        sessionPagerId = null;
		super.release();	
	}
	/**
	 * Creation date: (15/06/01 15:38:19)
	 * @return java.lang.String
	 */
	public java.lang.String getUrl() {
		return url;
	}
	/**
	 * Creation date: (15/06/01 15:38:19)
	 * @param newUrl java.lang.String
	 */
	public void setUrl(java.lang.String newUrl) {
		url = newUrl;
	}

	/**
	 * Gets the maxPageItems
	 * @return Returns a String
	 */
	public String getMaxPageItems() {
		return maxPageItems;
	}
	/**
	 * Sets the maxPageItems
	 * @param maxPageItems The maxPageItems to set
	 */

	public void setMaxPageItems(String in_maxPageItems) throws JspException {
		String l_errorMessageNumber =
			"PagerTag : invalid attribut : maxPageItems should be a number";
		String l_errorMessageNumberBiggerThanOne =
			"PagerTat : invalid attribut : maxPageItems should be a number bigger than 0";

		try {
			int l_maxPageItems = Integer.parseInt(in_maxPageItems);

			if (l_maxPageItems < 1) {
				throw new JspException(l_errorMessageNumberBiggerThanOne);
			}
		} catch (NumberFormatException enfe) {
			throw new JspException(l_errorMessageNumber);
		}

		maxPageItems = in_maxPageItems;
	}
	/**
	 * Return current index of this tag in page
	 * @return java.lang.String
	 */
	protected String getPagerIndex() {
		return (String) pageContext.getAttribute(PAGER_INDEX);
	}
	/**
	 * Return current pager index of this tag for this jsp page
	 * @param in_pagerIndex
	 * @return
	 */ 
	protected Integer getCurrentPagePagerIndex(String in_pagerIndex) {
		return (Integer) pageContext.getSession().getAttribute(((HttpServletRequest)pageContext.getRequest()).getServletPath() + PAGER_INDEX_CURRENT_PAGE + in_pagerIndex);
	} 
	/**
	 * set current pager index of this tag for this jsp page
	 * @param in_pagerIndex
	 * @param in_currentPage
	 */
	protected void setCurrentPagePagerIndex(String in_pagerIndex, int in_currentPage) {
		pageContext.getSession().setAttribute(((HttpServletRequest)pageContext.getRequest()).getServletPath() + PAGER_INDEX_CURRENT_PAGE + in_pagerIndex, new Integer(in_currentPage));
	} 
	protected int getCurrentPage() {
		// Compute currentPage or restore it form pageContext
		String l_pagerIndex = getPagerIndex();
		// Get the index of pager (ie collection) to sort or change page
		String l_collectionIndex = pageContext.getRequest().getParameter(SortUtil.SORTUTIL_COLLECTION);
		
		//If null, where are not comming from a pager  action, and the collection
		//may have changed.
		// TODO is it possible to check if the collection has changed ?
		if (l_collectionIndex==null) {
		
			if (sessionPagerId!=null && pageContext.getSession().getAttribute(sessionPagerId)!=null){
				return new Integer((String)pageContext.getSession().getAttribute(sessionPagerId)).intValue();
			}	
			return 0;
		}
		
		// If not equals, it is NOT to sort or change page		
		if (!l_pagerIndex.equals(l_collectionIndex)) {
			
			if (sessionPagerId!=null && pageContext.getSession().getAttribute(sessionPagerId)!=null){
				return new Integer((String)pageContext.getSession().getAttribute(sessionPagerId)).intValue();
			}			
	
			if (getCurrentPagePagerIndex(l_pagerIndex) !=  null) {
				return getCurrentPagePagerIndex(l_pagerIndex).intValue();
			}						
			// value by default
			return 0;
		}
		
		// It is diffent, so compute again
		int lc_currentPage = -1;
		String lc_possibleValue =
			pageContext.getRequest().getParameter(PAGE_NUMBER_KEY);
		if (lc_possibleValue != null) {
			try {
				lc_currentPage = Integer.parseInt(lc_possibleValue);
			} catch (NumberFormatException nfe) {
				// do nothing
			}
		} else if (lc_currentPage == -1) {
			Integer lc_integerPossibleValue =
				(Integer) pageContext.getRequest().getAttribute(PAGE_NUMBER_KEY);
			if (lc_integerPossibleValue != null) {
				lc_currentPage = lc_integerPossibleValue.intValue();
			}
		}
		if (lc_currentPage == -1) {
			lc_currentPage = 0;
		}
		// save current page
		setCurrentPagePagerIndex(l_pagerIndex, lc_currentPage);
		
		if (sessionPagerId !=null && pageContext.getSession().getAttribute(sessionPagerId)!=null){
			pageContext.getSession().setAttribute(sessionPagerId,String.valueOf(lc_currentPage));
		} 
		return lc_currentPage;
	}
	
	public String getURL(int in_page) {
		String l_url = url;
		if (l_url.indexOf("?")==-1) {
			l_url += "?";
		} else {
			l_url += "&";
		}
		l_url += PAGE_NUMBER_KEY;
		l_url += "=";
		l_url += in_page;
		if (sessionPagerId!=null) {
			l_url += "&";
			l_url += PAGER_SESSION_KEY;
			l_url += "=";
			l_url += sessionPagerId;
		}
		return l_url;		
	}

	public int getIndexFirstItem() {
		int l_currentPage = getCurrentPage();
		int l_maxPageItems = Integer.parseInt(maxPageItems);
		return l_currentPage * l_maxPageItems;

	}
	public int getIndexLastItem() {
		int l_currentPage = getCurrentPage();
		int l_maxPageItems = Integer.parseInt(maxPageItems);
		return (l_currentPage + 1) * l_maxPageItems;
	}

	public void setSize(int in_size) {
		size = in_size;
	}
	public int getSize() {
		return size;
	}
	
	/**
	 * Generate the pager links.
	 **/
	public void doPrintTag(StringBuffer in_buffer) throws JspException {		
		int l_maxPageItems = Integer.parseInt(maxPageItems);
		int l_currentPage = getCurrentPage();
		
		if (size<l_maxPageItems) {
			return;	
		}
		
		Skin skin = LayoutUtils.getSkin(pageContext.getSession());
		renderer = skin.getPagerRenderer();
		
		// Start.
		renderer.doStartPager(this, in_buffer);
		
		// Previous section.
		doPrintPrevious(in_buffer, l_currentPage, skin);
		
		// Main section
		numberOfPage = doPrintMain(in_buffer, l_maxPageItems, l_currentPage);

		// Next section
		doPrintNext(in_buffer, l_maxPageItems, l_currentPage, skin);
		
		// Direct go section
		doPrintDirect(in_buffer, l_maxPageItems, l_currentPage, skin);

		//End.
		renderer.doEndPager(this, in_buffer);	
	}
	
	/**
	 * Print a link to the previous page.
	 * This method used to generate the link itself,
	 * but now delegates the generation to the renderer.
	 */
	protected void doPrintPrevious(StringBuffer in_buffer, int in_currentPage, Skin in_skin) {		
		renderer.doPrintPrevious(this, in_buffer, in_currentPage);
	}
	
	/**
	 * Print links to pages.
	 * This method used to generate the link itself,
	 * but now delegates the generation to the renderer.
	 */
	protected int doPrintMain(StringBuffer in_buffer, int in_maxPageItems, int in_currentPage) {
		renderer.doPrintMain(this, in_buffer, in_maxPageItems, in_currentPage);
		return numberOfPage;
	}
	
	/**
	 * Print a link to the next page.
	 * This method used to generate the link itself,
	 * but now delegates the generation to the renderer.
	 */
	protected void doPrintNext(StringBuffer in_buffer, int in_maxPageItems, int in_currentPage, Skin in_skin) {
		renderer.doPrintNext(this, in_buffer, in_maxPageItems, in_currentPage);
	}
	
	/**
	 * Print an input box to type in the page number.
	 * This method used to generate the HTML itself,
	 * but now delegates the generaton to the renderer.
	 */
	protected void doPrintDirect(StringBuffer in_buffer, int in_maxPageItems, int in_currentPage, Skin in_skin) throws JspException {
		if (Boolean.valueOf(LayoutUtils.getSkin(pageContext.getSession()).getProperty(PagerTag.DISPLAY_DIRECT_LINK)).booleanValue()) {
			renderer.doPrintDirect(this, in_buffer, in_maxPageItems, in_currentPage);
		}
	}
	
	/**
	 * Compute the maximum number of links to display (configured in the skin).
	 */
	public int computeMaxLinks() {
		String s = LayoutUtils.getSkin(pageContext.getSession()).getProperty(MAX_LINKS_PROPERTY);
		int i;
		try {
			i = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			throw new BadSkinConfigurationException("The layout.pager.maxLinksProperty is not a number: " + s);
		}
		return i;
	}
	
	/**
	 * While generating the links to the different pages, 
	 * return true if a link to a specific pages must be displayed or not. 
	 * This code is used to not display to many links.
	 */
	public boolean shouldDisplay(int in_index, int in_displayed, int in_size, int in_maxSize) {
		if (in_maxSize>in_size) {
			// No size control, limit has not been reached.
			return true;
		}
		if (in_index==0) {
			// Always display the first link.
			return true;
		}
		if (in_index==in_size-1) {
			// Always display the last link.
			return true;
		}
		if (in_displayed==in_index) {
			// Always display the current page.
			return true;
		}
		if (in_displayed==in_index-1) {
			// Always display the previous page.
			return true;
		}
		if (in_displayed==in_index+1) {
			// Always display the next page.
			return true;
		}
		
		return false;
	}

	/**
	 * Sets the styleClass.
	 * @param styleClass The styleClass to set
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	public String getStyleClass() {
		return styleClass;
	}

	/**
	 * Sets the width.
	 * @param width The width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}
	
	/**
	 * @return Returns the linksLocation.
	 */
	public final String getLinksLocation()
	{
		return linksLocation;
	}	
	/**
	 * @param linksLocation The linksLocation to set.
	 */
	public final void setLinksLocation(String linksLocation)
	{
		this.linksLocation = linksLocation;
	}
	/**
     * Retourne l'identifiant unique g?n?r? lors du doStartTag
     * et qui permet d'identifier le tag "table" qui englobe
     * tout le rendu du PagerTag.
     * (de sorte ? pouvoir par la suite le d?placer avec du Javascript)
     * 
	 * @return Returns the pagerId.
	 */
	public final String getPagerId()
	{
		return pagerId;
	}
	
	public void setAlign(String in_align) {
		align = in_align;
	}
	public String getAlign() {
		return align;
	}

	public String getSessionPagerId() {
		return sessionPagerId;
	}

	public void setSessionPagerId(String sessionPagerId) {
		this.sessionPagerId = sessionPagerId;
	}
	public String getNextImgKey() {
		return nextImgKey;
	}
	public void setNextImgKey(String nextImgKey) {
		this.nextImgKey = nextImgKey;
	}
	public String getNextMsgKey() {
		return nextMsgKey;
	}
	public void setNextMsgKey(String nextMsgKey) {
		this.nextMsgKey = nextMsgKey;
	}
	public String getPreviousImgKey() {
		return previousImgKey;
	}
	public void setPreviousImgKey(String previousImgKey) {
		this.previousImgKey = previousImgKey;
	}
	public String getPreviousMsgKey() {
		return previousMsgKey;
	}
	public void setPreviousMsgKey(String previousMsgKey) {
		this.previousMsgKey = previousMsgKey;
	}
	public String getGotoProperty() {
		return gotoProperty;
	}
	public void setGotoProperty(String gotoProperty) {
		this.gotoProperty = gotoProperty;
	}
	public String getWidth() {
		return width;
	}
	public int getNumberOfPage() {
		return numberOfPage;
	}
}