package fr.improve.struts.taglib.layout.sort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.util.RequestUtils;

import fr.improve.struts.taglib.layout.datagrid.DatagridTag;
import fr.improve.struts.taglib.layout.field.AbstractLayoutFieldTag;
import fr.improve.struts.taglib.layout.field.DateFieldTag;
import fr.improve.struts.taglib.layout.pager.PagerTag;
import fr.improve.struts.taglib.layout.skin.Skin;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.ParentFinder;
/**
 * Insert the type's description here.
 * Creation date: (27/10/2001 13:05:22)
 * 
 * PENDING: does not work with action chaining.
 * 
 * @author: Jean-Noel Ribette
 */
public class SortUtil implements Serializable {
	/**
	 * Log
	 */
	private static final Log LOG = LogFactory.getLog(SortUtil.class);

	/**
	 * Implementation class for SortUtil
	 */
	public static final String SORT_UTIL_CLASS = "sortutil.class";
	
	/**
     * Name of this object in the session.
     */
    public static final String SORTUTIL_KEY =
        "fr.improve.struts.taglib.layout.sort.SortUtil";

    /**
     * Name of the parameter containing the number of the collection to sort.
     */
    public static final String SORTUTIL_COLLECTION = "layoutCollection";

    /**
     * Name of the parameter containing the property to sort the collection by.
     */
    public static final String SORTUTIL_PROPERTY = "layoutCollectionProperty";
    
    /**
     * Name of the sort action property.
     */
    public static final String SORT_UTIL_ACTION = "sortutil.action";

    /**
     * Vector of the collections
     */
    protected Vector collections = new Vector();
    
    /**
     * Sorted state for a collection.
     */
    protected Map sortState = new HashMap();

    /**
     * Old request attributes.
     */
    protected Map attributes = new HashMap();

    /**
     * Old request parameters.
     */
    protected Map parameters = new HashMap();

    /**
     * Original url.
     */
    protected String url;

    /**
     * index when displaying the sorted collection.
     */
    protected int index = 0;
    
    /**
     * Sort rules.
     */
    protected SortRules sortRules;

    protected int hashCode;
    
    /**
     * Return the sort state of the current collection for the specified property.
     * 0 : no sort for this property
     * 1 : sort ascending for this property
     * -1 : sort descending for this property
     */
    public static short getSortState(HttpServletRequest in_request, String in_property) {
    	SortUtil lc_util = (SortUtil) in_request.getAttribute(SORTUTIL_KEY);
    	if (lc_util==null || lc_util.sortState.isEmpty()) {
    		return 0;
    	} else {
    		Object[] lc_sort = (Object[]) lc_util.sortState.values().iterator().next();
    		if (in_property.equals(lc_sort[0])) {
    			return ((Integer)lc_sort[1]).shortValue();
    		} else {
    			return 0;
    		}
    	}
    }

public void initSortUtil(HttpServletRequest in_request, String in_url) {
	
	// Copy the parameters.
	// (only if it is not already in the url)
	Enumeration lc_enumeration = in_request.getParameterNames();
	boolean hasUrlParameter = in_url.indexOf('?')!=-1;
	parameters.clear();
	while (lc_enumeration.hasMoreElements()) {
		String lc_key = (String) lc_enumeration.nextElement();
		if (!hasUrlParameter || in_url.indexOf(lc_key +  "=") ==-1) {			
			parameters.put(lc_key, in_request.getParameter(lc_key));
		}
	}
	
	// Copy the attributes.
	lc_enumeration = in_request.getAttributeNames();
	attributes.clear();
	while (lc_enumeration.hasMoreElements()) {
		Object lc_key = lc_enumeration.nextElement();
		Object lc_value = in_request.getAttribute(lc_key.toString());
		if (lc_value instanceof Tag) {
			// Tags are not useful here, and are not serializable 
			LOG.debug("Skipping JSP tag " + lc_key + " : " + lc_value);
			continue;
		}
		if (ParentFinder.TAGS_KEY.equals(lc_key)) {
			// The tag stack is not useful here.
			LOG.debug("Skipping JSP tags stack");
			continue;
		}
		if (DateFieldTag.CALENDAR_KEY.equals(lc_key)) {
			// The javascript calendar code must be added each time.
			LOG.debug("Skipping calendar code");
			continue;
		}
		if (DatagridTag.LOADED.equals(lc_key)) {
			// The javascript datagrid code must be added each time.
			LOG.debug("Skipping datagrid code");
			continue;
		}
		
		if (AbstractLayoutFieldTag.FIRST_ERROR_FIELD_FOCUS.equals(lc_key)) {
			LOG.debug("Skipping first field in error focus");
			continue;
		}
		
		if (((String)lc_key).startsWith("javax.servlet")) {
			// Skip servlet information.
			LOG.debug("Skipping servlet information " + lc_key);
			continue;
		}
		
		if (((String)lc_key).startsWith("atg.servlet")) {
			// Skip Dynamo information.
			LOG.debug("Skipping dynamo information " + lc_key);
			continue;
		}
		
		if (((String)lc_key).startsWith("_")) {
			if (lc_value!=null && lc_value.getClass().getName().indexOf("atg.")!=-1) {
				// Skip Dynamo information.
				LOG.debug("Skipping unsafe atg data " + lc_key);
				continue;
			}
		}
		
		if (((String)lc_key).equals(Globals.ERROR_KEY) && !LayoutUtils.getSkin(in_request.getSession()).getSortKeepErrorMessage()) {
			// Skip error message, for example if they are only displayed once in a popup.
			LOG.debug("Skipping Struts error messages " + lc_key);
			continue;
		}
		
		LOG.debug("Adding " + lc_key + " : " + lc_value);
		attributes.put(lc_key, lc_value);		
	}

	// Save the url.
	url = in_url;
}

public static void addCollection(
    Object in_list,
    HttpServletRequest in_request) {

    // Get a sort util.
    SortUtil lc_sortUtil =
        (SortUtil) in_request.getSession().getAttribute(SORTUTIL_KEY);
    if (lc_sortUtil==null || in_request.getAttribute(SORTUTIL_KEY + "aaa")==null) {	    
	    lc_sortUtil = createSortUtil(in_request);
		in_request.getSession().setAttribute(SORTUTIL_KEY, lc_sortUtil);
		in_request.setAttribute(SORTUTIL_KEY + "aaa", "aaa");
    }
	lc_sortUtil.collections.add(in_list);
}
/**
 * Put all the saved attributes back in the request.
 */
protected void buildRequest(HttpServletRequest in_request) {
	Iterator lc_iterator = attributes.keySet().iterator();
	while (lc_iterator.hasNext()) {
		Object lc_key = lc_iterator.next();
		in_request.setAttribute(lc_key.toString(), attributes.get(lc_key));
	}
}

/**
 * Fix the current page for the pager.
 */
protected void setCurrentPage(HttpServletRequest in_request) {
	String lc_name = in_request.getParameter(PagerTag.PAGER_SESSION_KEY);
	String lc_value = in_request.getParameter(PagerTag.PAGE_NUMBER_KEY);
	if (lc_name!=null && lc_value!=null && in_request.getSession().getAttribute(lc_name)!=null) {
		in_request.getSession().setAttribute(lc_name, lc_value);
	}
}


/**
 * Put all the saved parameters back in the request.
 */
protected String buildURL(HttpServletRequest in_request) {
	Iterator lc_iterator = parameters.keySet().iterator();
	StringBuffer lc_url = new StringBuffer(url);
	boolean hasParameter = url.indexOf('?')!=-1;
	while (lc_iterator.hasNext()) {
		Object lc_key = lc_iterator.next();
		if (!PagerTag.PAGE_NUMBER_KEY.equals(lc_key)) {
		if (hasParameter) {
			lc_url.append("&"); 
		} else {
			lc_url.append("?");
			hasParameter = true;
		}
		lc_url.append(LayoutUtils.encodeURL(lc_key.toString()));
		lc_url.append("=");
	
		Object lc_param = parameters.get(lc_key);
		if (lc_param!=null) {
			lc_url.append(LayoutUtils.encodeURL(lc_param.toString()));
		}		
		}
	}
	return lc_url.toString();
}
protected List getCollectionToSort(HttpServletRequest in_request) throws SortException {
	String lc_indexAsString = in_request.getParameter(SORTUTIL_COLLECTION);
	int lc_index;
	try {
		lc_index = Integer.parseInt(lc_indexAsString);
	} catch (NumberFormatException e) {
		throw new SortException("Impossible to find the collection to sort");
	}
    List lc_list = null;
    if (lc_index>=0) {
	    try {
		    lc_list =  (List) collections.get(lc_index);
	    } catch (ClassCastException e) {
			throw new SortException("Impossible to sort a bean of type " + collections.get(lc_index).getClass());
	    }
    }
    return lc_list;
}
protected String getPropertyToSort(HttpServletRequest in_request) throws SortException {
	return in_request.getParameter(SORTUTIL_PROPERTY);
}
	public List getSortedCollection() {
		List lc_list = (List) collections.get(index);
		index++;
		return lc_list;
	}
/**
 * Method called by the collection tag.
 */
public static String getURLForCollection(
    String in_property,
    HttpServletRequest in_request) {

	// Get a sort util.
    SortUtil lc_sortUtil = (SortUtil) in_request.getSession().getAttribute(SORTUTIL_KEY);
    if (lc_sortUtil==null || in_request.getAttribute(SORTUTIL_KEY + "aaa")==null) {	    
	    lc_sortUtil = createSortUtil(in_request);
		in_request.getSession().setAttribute(SORTUTIL_KEY, lc_sortUtil);
		in_request.setAttribute(SORTUTIL_KEY + "aaa", "aaa");
    }

    // Get the sort action.
    String lc_action = null;
    try {
    	lc_action = LayoutUtils.getSkin(in_request.getSession()).getProperty(SORT_UTIL_ACTION);    
    } catch (MissingResourceException e) {
		lc_action = "sort.do";
	}
    
    // Returns the url.
    StringBuffer lc_buffer = new StringBuffer(in_request.getContextPath());    
    lc_buffer.append("/");
    lc_buffer.append(lc_action);
    lc_buffer.append("?");
    lc_buffer.append(SORTUTIL_COLLECTION);
    lc_buffer.append('=');
    lc_buffer.append(lc_sortUtil.collections.size()-1);
    lc_buffer.append("&amp;");
    lc_buffer.append(SORTUTIL_PROPERTY);
    lc_buffer.append('=');
    lc_buffer.append(in_property);
    lc_buffer.append("&amp;");
    lc_buffer.append("layoutCollectionState");
    lc_buffer.append('=');
    lc_buffer.append(getRequestState(in_request));
    return lc_buffer.toString();
}
public String sort(HttpServletRequest in_request) throws SortException {
	// Check if we are in a legal state.
	validateRequest(in_request);
	
	// Get the collection to sort.
	List lc_collection = getCollectionToSort(in_request);

	// Get the property to sort the collection by.
	String lc_property = getPropertyToSort(in_request);

	// Sort the collection.
	sortCollection(lc_collection, lc_property);
	
	// Build the request.
	buildRequest(in_request);
	
	// Deal with pagination issue.
	setCurrentPage(in_request);

	// Remove the sort util from the session, and put it in the request.
	in_request.getSession().removeAttribute(SORTUTIL_KEY);
	in_request.setAttribute(SORTUTIL_KEY, this);
	
	// Update the sort collection state.

	return buildURL(in_request);
}
protected void sortCollection(List in_collection, String in_property) {
	// If property is valid, sort according to this property.
	if (in_property!=null && in_property.length()!=0) {
		
		// Create a copy of the collection. This allows to determine the sort order.
		List copy = new ArrayList(in_collection);
		
		// Sort the collection.
		Collections.sort(in_collection, new BeanComparator(in_property, sortRules));
		int order = 1; // default order is 1, ascending.
		
		
		if (copy.equals(in_collection)) {
			// The list was already sorted in this order, reverse the order.
			Collections.reverse(in_collection);
			order = -1;
		}
		
		// Update the sort order state.
		int index = collections.indexOf(in_collection);
		sortState.put(new Integer(index), new Object[]{in_property, new Integer(order)});
	}
}


/**
 * Get the session state to generate a valid sort request.
 */
private static final String getRequestState(HttpServletRequest in_request) {
	Integer lc_value = (Integer) in_request.getSession().getAttribute(SORTUTIL_KEY + "bbb");
	if (lc_value==null) {
		lc_value = new Integer(0);
		in_request.getSession().setAttribute(SORTUTIL_KEY + "bbb", lc_value);
	}
	return lc_value.toString();
}

/**
 * Throws an exception if it is illegal to sort a collection.
 * This is the case if the back button is used. 
 */
protected final void validateRequest(HttpServletRequest in_request) throws SortException {
	// Get the session value.
	Integer lc_integer = (Integer) in_request.getSession().getAttribute(SORTUTIL_KEY + "bbb");
	if (lc_integer == null) {
		throw new SortException("session state unknown");
	}
	
	// Get the request value.
	String lc_value = in_request.getParameter("layoutCollectionState");
	if (lc_value == null) {
		throw new SortException("bad request");
	}
	
	// If the session value is greater than the request value, throw an exception.
	// Unless the skin is configured to not check the state.
	Skin lc_skin = LayoutUtils.getSkin(in_request.getSession());
	Integer lc_integerValue = new Integer(lc_value);
	
	if (lc_skin.isSortTokenRequired() && lc_integer.compareTo(lc_integerValue)>0) {
		throw new SortException("bad session state");
	}
	
	// Ok, update the session state.
	in_request.getSession().setAttribute(SORTUTIL_KEY + "bbb", new Integer(lc_integer.intValue()+1));
}

/**
 * Build a new sort util.
 * PENDING: problems with array of parameters and include that will give bad url.
 */
public static SortUtil createSortUtil(HttpServletRequest in_request) {
	
	SortUtil lc_sortUtil;
	try {
		// Get SortUtil class from Skin configuration.
		String lc_sortUtilClass = LayoutUtils.getSkin(in_request.getSession()).getProperty(SORT_UTIL_CLASS);
		try {
			// Instanciate SortUtil.
			lc_sortUtil = (SortUtil) RequestUtils.applicationInstance(lc_sortUtilClass);
		} catch (Exception e) {
			e.printStackTrace();
			lc_sortUtil = new SortUtil();
		}
	} catch (MissingResourceException mre) {
		lc_sortUtil = new SortUtil();
	}
	
	// Set SortUtil String comparator.
	lc_sortUtil.sortRules = getRules(in_request);
	
	// Initialize SortUtil
	lc_sortUtil.initSortUtil(in_request, in_request.getServletPath());
	return lc_sortUtil;
}

	/**
	 * Get String sorting rules.
	 */
	private static SortRules getRules(HttpServletRequest in_request) {
		Locale locale = LayoutUtils.getLocale(in_request);
		return LayoutUtils.getSkin(in_request.getSession()).getSortRules(locale);
	}

}
