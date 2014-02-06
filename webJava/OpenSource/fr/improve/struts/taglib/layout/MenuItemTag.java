package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;
import fr.improve.struts.taglib.layout.util.MenuItem;
import fr.improve.struts.taglib.layout.util.Menu;

/**
 * Menu tag used to specify the menu to display.
 * The menu can be defined in the jsp page by nesting menuItem tag
 * or taken from the repository by giving the menu name.
 * @author: Jean-Noël Ribette
 * @deprecated
 */
public class MenuItemTag extends javax.servlet.jsp.tagext.TagSupport {
	protected String key = null;
	protected String link = null;
	protected Menu menu = null;
public void addItem(MenuItem item) {
	if (menu==null) menu = new Menu();
	menu.addItem(item);
}
// TODO rewrite this
public int doEndTag() throws JspException {
	MenuItem item = new MenuItem(key, link, menu);
	try {
		MenuTag menu = (MenuTag) getParent();
		menu.addItem(item);	
	} catch (ClassCastException e) {
		try {
			MenuItemTag menu = (MenuItemTag) getParent();
			menu.addItem(item);
		} catch (ClassCastException f) {
			try {
				DynMenuTag menu = (DynMenuTag) getParent();
				menu.addItem(item);
			} catch (ClassCastException g) {			
				throw new JspException("menuItem tag not in a menu or a menuItem tag");
			}
		}
	}
	return EVAL_PAGE;
}
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}
	public void release() {
		key = null;
		link = null;
		menu = null;
	}
public void setKey(String key) {
	this.key = key;
}
public void setLink(String link) {
	this.link = link;
}
}
