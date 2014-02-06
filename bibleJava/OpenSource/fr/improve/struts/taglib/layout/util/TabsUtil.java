/*
 * Created on 5 oct. 2004
 *
 * Copyright Improve SA
 * All right reserved
 */
package fr.improve.struts.taglib.layout.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.improve.struts.taglib.layout.tab.TabTag;

/**
 * Simple utilities for the tabs
 * 
 * @author JN Ribette
 */
public class TabsUtil {
	
	/**
	 * Set the current tab in a tag group.
	 * @param in_tabsGroup			the value specified in the jsp under the selectedTabKeyName attribute of the tabs tag
	 * @param in_selectedTabKey		the selected tab key
	 * @param in_request			the http request.
	 */
	public static void setCurrentTab(String in_tabsGroup, String in_selectedTabKey, HttpServletRequest in_request, HttpServletResponse in_response) {
		// Check parameters.
		if (in_tabsGroup==null || in_tabsGroup.length()==0) {
			throw new IllegalArgumentException("in_tabsGroups cannot be null or empty");
		}
		if (in_selectedTabKey==null || in_selectedTabKey.length()==0) {
			throw new IllegalArgumentException("in_selectedtabKey cannot be null or empty");
		}
		if (in_request==null) {
			throw new IllegalArgumentException("in_request cannot be null");
		}
		
		// Set request attribute.
		in_request.setAttribute(in_tabsGroup, in_selectedTabKey);
		
		// Set cookie value.
		TabTag.setSelectedTabNameFromCookie(in_request, in_response, in_tabsGroup, in_selectedTabKey);
	}
}
