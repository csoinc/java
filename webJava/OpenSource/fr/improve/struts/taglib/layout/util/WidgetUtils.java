/*
 * Created on 22 avr. 2004
 */
package fr.improve.struts.taglib.layout.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

/**
 * Define usefull method to work with widgets.
 * 
 * @author jnribette
 */
public class WidgetUtils {
	private static final String WIDGET_ID = "fr.improve.struts.taglib.layout.util.WidgetUtils.WIDGET_ID"; 
	
	/**
	 * Generate a unique page id for a specified widget. 
	 */
	public static String generateId(ServletRequest in_request, String in_widgetType) {
		// Get the Map holding the last attributed ids.
		Map lc_map = (Map) in_request.getAttribute(WIDGET_ID);
		if (lc_map==null) {
			lc_map = new HashMap();
			in_request.setAttribute(WIDGET_ID, lc_map);
		}
		
		// Get the last attribute id.
		Integer lc_id = (Integer) lc_map.get(in_widgetType);
		if (lc_id==null) {
			lc_id = new Integer(-1);
		}
		
		// Increment the id
		lc_id = new Integer(lc_id.intValue()+1);
		
		// Store the new id.
		lc_map.put(in_widgetType, lc_id);
		
		// Return the id.
		return lc_id.toString();
	}
}
