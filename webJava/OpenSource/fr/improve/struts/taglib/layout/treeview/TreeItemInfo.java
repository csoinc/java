/*
 * Created on 22 mars 2004
 *
 * Copyright Improve SA 2004.
 * All rights reserved.
 */
package fr.improve.struts.taglib.layout.treeview;

/**
 * Data object helping to render an item.
 * 
 * @author jnribette
 */
public class TreeItemInfo {
	public String bundle; 
	public String name;
	public String styleClass;
	public String imageDirectory;
	public boolean hasSubMenus;
	public boolean isLast;
	public String path;
	public boolean isClosed;
	public boolean useIndirection;
}
