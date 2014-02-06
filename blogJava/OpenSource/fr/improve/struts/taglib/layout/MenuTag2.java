package fr.improve.struts.taglib.layout;

import java.util.Vector;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.menu.MenuComponent;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.MenuInterface;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Display a tree like menu.
 * Compatible with Scott Sayles menu framework.
 * @author: Jean-Noël Ribette
 */
public class MenuTag2 extends PanelTag implements MenuInterface {
	protected Vector menus = new Vector();

	/**
	 * Start the tag, do nothing (menu items are not defined yet).
	 */
	public int doStartLayoutTag() throws JspException {	
		return EVAL_BODY_INCLUDE;
	}
	
	/**
	 * Add a menu item (method specified in MenuInterface).
	 */
	public void addItem(MenuComponent item) {
		menus.add(item);
	}

	/**
	 * End the tag, generate the HTML code for the menu items added.
	 */
	public int doEndLayoutTag() throws JspException {
		StringBuffer buffer = new StringBuffer();
		// width = "90";
		doStartPanel(buffer);
		doBeforeBody(buffer);
	
		buffer.append("<tr><td><table border=0 cellpadding=10><tr><td><table border=0>");
		doPrintMenu(buffer,(MenuComponent[])menus.toArray(new MenuComponent[menus.size()]),1);
		buffer.append("</table></td></tr></table></td></tr>");
		
		doAfterBody(buffer);
		doEndPanel(buffer);
	
		TagUtils.write(pageContext, buffer.toString());
		menus.removeAllElements();
		return EVAL_PAGE;
	}
	
	/**
	 * Iterates over the MenuComponent[].	 
	 */
	protected void doPrintMenu(StringBuffer buffer, MenuComponent[] menus, int level) throws JspException {
		for (int i=0;i<menus.length;i++) {
			// Print the menu item.
			MenuComponent item = (MenuComponent) menus[i];				
			doPrintMenuItem(buffer, level, item);
			
			// Print the sub items.
			MenuComponent[] subMenus = item.getMenuComponents();
			String key = item.getTitle();	
			doPrintSubMenu(buffer, level, key, subMenus);
			
			buffer.append("\n");
		}
	
	}

	/**
	 * Generate the HTML code for one menu item. 
	 */
	protected void doPrintMenuItem(StringBuffer buffer, int level, MenuComponent item) throws JspException {
		String link = item.getLocation();
		String forward = item.getForward();
		String page = item.getPage();
		String action = item.getAction();
		String key = item.getTitle();
		String image = item.getImage();
		String target = item.getTarget();
		String style = item.getStyle();
		
		boolean lc_hasLink = link!=null || forward!=null || page!=null || action!=null;

		buffer.append("<tr valign=\"top\"><td class=\"");
		buffer.append(styleClass);
		if (lc_hasLink) {
			buffer.append("\" align=\"left");
		} else {
			buffer.append("\" onClick=\"changeMenu('");
			buffer.append(key);
			buffer.append("')\" style=\"cursor:hand");
		}
		buffer.append("\">");
		
		doPrintSpaces(buffer, level);
		
		if (lc_hasLink) {
			doStartLink(buffer, link, forward, page, action, target, style);
		}
		
		if (image!=null) {
			buffer.append("<img src=");
			buffer.append(image);
			buffer.append(">&nbsp;");
		}
		
		buffer.append(LayoutUtils.getLabel(pageContext,bundle, key, null, false));
		if (lc_hasLink) {
			doEndLink(buffer);
		}
		buffer.append("</td></tr>");
	}			

	private void doPrintSpaces(StringBuffer buffer, int level) {
		switch (level) {
			case 2: buffer.append("&nbsp;&nbsp;"); break;
			case 3: buffer.append("&nbsp;&nbsp;&nbsp;>&nbsp;"); break;
		}
	}
	
	/**
	 * @deprecated.
	 */
	protected void doStartLink(StringBuffer buffer, String link, String forward, String page, String target, String style) throws JspException {
		doStartLink(buffer, link, forward, page, null, target, style);
	}

	protected void doStartLink(StringBuffer buffer, String link, String forward, String page, String action, String target, String style) throws JspException {
		buffer.append("<a href=\"");
		buffer.append(LayoutUtils.computeURL(pageContext, forward, link, page, action, null,null, null, false, target));
		if (target!=null) {
			buffer.append("\" target=\"");
			buffer.append(target);
		}
		if (style!=null) {
			buffer.append("\" style=\"");
			buffer.append(style);
		}
		buffer.append("\">");
	}
	
	protected void doEndLink(StringBuffer buffer) {
		buffer.append("</a>");
	}

	/**
	 * Print the submenu for a menu item.	
	 */
	protected void doPrintSubMenu(StringBuffer in_buffer, int in_level, String in_key, MenuComponent[] in_subMenus) throws JspException {
		if (in_subMenus!=null && in_subMenus.length!=0) {
			//buffer.append("<tr><td id=");
			//buffer.append(key);
			//buffer.append("b style=\"display:none\"></td><td id=");
			//buffer.append(key);
			//buffer.append("><table border=0 cellspacing=0 cellpadding=0>");
			//buffer.append("<script language=\"JavaScript\">initMenu('" + key  +"');</script>\n");
			//doPrintMenu(buffer, subMenus, level+1);
			//buffer.append("</table></td></tr>");
	
			in_buffer.append("<tr valign=top><td><div id=\"");
			in_buffer.append(in_key);
			in_buffer.append("b\" style=\"display:none\"></div><div id=\"");
			in_buffer.append(in_key);
			in_buffer.append("\"><table border=0 cellspacing=0 cellpadding=0>");
			in_buffer.append("<script language=\"JavaScript\">initMenu('" + in_key  +"');</script>\n");
			doPrintMenu(in_buffer, in_subMenus, in_level+1);
			in_buffer.append("</table></div></td></tr>");
			
		}
	}
}
