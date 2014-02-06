/*
 * Created on 22 mars 2004
 *
 * Copyright Improve SA 2004.
 * All rights reserved.
 */
package fr.improve.struts.taglib.layout.util;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.menu.MenuComponent;
import fr.improve.struts.taglib.layout.treeview.TreeItemInfo;

/**
 * This interface defines the API to render a treeview. 
 * @author jnribette
 */
public interface TreeviewInterface {
	
	/**
	 * Starts an item row.	 
	 */
	public abstract void startRow(StringBuffer buffer);
	
	/**
	 * Display the tree images in a TD.	 
	 */
	public abstract void renderTree(StringBuffer buffer, HttpServletRequest in_request, TreeItemInfo in_info);
	
	/**
	 * If the item has subitems, starts a sub menu.
	 */
	public abstract void startSubMenu(StringBuffer buffer);
	
	/**
	 * Render the item icon.
	 */
	public abstract void renderIcon(StringBuffer buffer, TreeItemInfo in_info, MenuComponent in_item);
	
	/**
	 * Starts the TD that contains the item image and title.
	 */
	public abstract void startLabel(StringBuffer buffer, String in_styleClass, String onclick, String style);
	
	/**
	 * Renders the image associated to the item.
	 */
	public abstract void renderImage(StringBuffer buffer, HttpServletRequest in_request, TreeItemInfo in_info, MenuComponent in_item);
	
	/**
	 * Renders in the buffer the title of the item. If a location is associated to the item, the title
	 * is rendered inside a link element. 
	 */
	public abstract void renderLabel(StringBuffer buffer,
			HttpServletRequest in_request, ServletContext in_servletcontext,
			TreeItemInfo in_info, MenuComponent in_item) throws JspException;
	
	/**
	 * Ends the TD that contains the item image and title.
	 */
	public abstract void endLabel(StringBuffer buffer);

	/**
	 * Render the actions of the item.
	 */
	public abstract void renderActions(StringBuffer buffer, HttpServletRequest in_request, ServletContext in_context, TreeItemInfo lc_info, MenuComponent item) throws JspException;
	
	
	public abstract int endSubMenu(StringBuffer buffer,
			HttpServletRequest in_request, ServletContext in_servletcontext,
			int lc_numberOfMenusPrinted, MenuComponent menu, 
			TreeItemInfo in_info) throws JspException;
	
	/**
	 * Ends an item row.
	 */
	public abstract void endRow(StringBuffer buffer);
	
	
	/**
	 * Compute the new styleClass: 
	 * If the styleClass ends with a digit, increment the digit by one.
	 */
	public abstract String computeStyleClass(String in_styleClass);

}