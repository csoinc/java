package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.menu.MenuComponent;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.Menu;
import fr.improve.struts.taglib.layout.util.MenuItem;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Insert the type's description here.
 * Creation date: (23/03/01 10:09:16)
 * @author: Jean-Noël Ribette
 */
public class MenuTag extends PanelTag {
	protected Menu menu;
public void addItem(MenuItem item) {
		if (menu==null) menu = new Menu();
		menu.addItem(item);
}
public int doEndLayoutTag() throws JspException {
	StringBuffer buffer = new StringBuffer();	
	doStartPanel(buffer);
	doBeforeBody(buffer);

	buffer.append("<tr><td><table border=0 cellpadding=10><tr><td><table border=0>");
	doPrintMenu(buffer,menu,1);
	buffer.append("</table></td></tr></td></tr></table>");
	
	doAfterBody(buffer);
	doEndPanel(buffer);
	
	TagUtils.write(pageContext, buffer.toString());		
	
	return EVAL_PAGE;
}
protected void doPrintMenu(
	StringBuffer buffer,
	MenuComponent menu,
	int level)
	throws JspException {

	String link = menu.getLocation();
	String key = menu.getTitle();

	if (link == null) {
		buffer.append("<tr><td class=");
		buffer.append(styleClass);
		buffer.append(" onClick=\"changeMenu('");
		buffer.append(key);
		buffer.append("')\" style=\"cursor:hand\">");
		if (level == 2)
			buffer.append("&nbsp;&middot;&nbsp;");
		if (level == 3)
			buffer.append("&nbsp;&nbsp;&nbsp;>&nbsp;");
		buffer.append(getLabel());
		buffer.append("</td></tr>");
	} else {
		buffer.append("<tr><td align=left class=");
		buffer.append(styleClass);
		buffer.append(">");
		if (level == 2)
			buffer.append("&nbsp;&middot;&nbsp;");
		if (level == 3)
			buffer.append("&nbsp;&nbsp;&nbsp;>&nbsp;");
		buffer.append("<a href=\"");
		buffer.append(link);
		buffer.append("\">");
		buffer.append(getLabel());
		buffer.append("</a></td></tr>");
	}

	MenuComponent[] subMenus = menu.getMenuComponents();
	for (int i = 0; i < subMenus.length; i++) {
		MenuComponent subMenu = subMenus[i];
		buffer.append("<tr id=");
		buffer.append(key);
		buffer.append("><td><table border=0 cellspacing=0 cellpadding=0>");
		buffer.append(
			"<script language=\"JavaScript\">initMenu('" + key + "');</script>\n");
		doPrintMenu(buffer, subMenu, level + 1);
		buffer.append("</table></td></tr>");
	}
	buffer.append("\n");

}
protected void doPrintMenu(StringBuffer buffer, Menu menu, int level) throws JspException {
	Object[] items = menu.getItems();
	
	for (int i=0;i<items.length;i++) {
		MenuItem item = (MenuItem) items[i];
		String link = item.getLink();
		String key = item.getKey();
		Menu subMenu = item.getSubMenu();
	
		if (link==null) {
			buffer.append("<tr><td class=");
			buffer.append(styleClass);
			buffer.append(" onClick=\"changeMenu('");
			buffer.append(key);
			buffer.append("')\" style=\"cursor:hand\">");
			if (level==2) buffer.append("&nbsp;&middot;&nbsp;");
			if (level==3) buffer.append("&nbsp;&nbsp;&nbsp;>&nbsp;");
			buffer.append(getLabel());
			buffer.append("</td></tr>");
		} else {
			buffer.append("<tr><td align=left class=");
			buffer.append(styleClass);
			buffer.append(">");
			if (level==2) buffer.append("&nbsp;&middot;&nbsp;");
			if (level==3) buffer.append("&nbsp;&nbsp;&nbsp;>&nbsp;");
			buffer.append("<a href=\"");
			buffer.append(link);
			buffer.append("\">");		
			buffer.append(getLabel());
			buffer.append("</a></td></tr>");
		}

		if (subMenu!=null) {
			buffer.append("<tr id=");
			buffer.append(key);
			buffer.append("><td><table border=0 cellspacing=0 cellpadding=0>");
			buffer.append("<script language=\"JavaScript\">initMenu('" + key  +"');</script>\n");
			doPrintMenu(buffer, subMenu, level+1);
			buffer.append("</table></td></tr>");
		}
		
		buffer.append("\n");
	}

}
public int doStartLayoutTag() throws JspException {
	if (name!=null) try {
		menu = (Menu) LayoutUtils.getBeanFromPageContext(pageContext, name, property);
	} catch (ClassCastException e) {
		throw new JspException("MenuTag: :Object under name=" + name + " property=" + property + " is not of type Menu.");			
	}
	
	
	return EVAL_BODY_INCLUDE;
}
	public void release() {
		super.release();
		menu = null;
	}
}
