package fr.improve.struts.taglib.layout;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.Menu;
import fr.improve.struts.taglib.layout.util.MenuItem;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Menu tag for use with Hiermenus
 * @author: Jean-Noël Ribette
 */
public class DynMenuTag extends PanelTag {
	protected Menu menu;
	protected String config = "fr.improve.struts.taglib.layout.DefaultDynMenuConfig";
	public void addItem(MenuItem item) {
		if (menu==null) menu = new Menu();
		menu.addItem(item);
	}
	public int doEndLayoutTag() throws JspException {
		StringBuffer buffer = new StringBuffer();

		buffer.append("<script>\n");
		doPrintMenu(buffer, menu, "");
		buffer.append("</script>\n");
		buffer.append("<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"config/HM_Loader.js\" TYPE='text/javascript'></SCRIPT>");
		
		TagUtils.write(pageContext, buffer.toString());		
		return EVAL_PAGE;
	}
public void doPrintMenu(StringBuffer buffer, Menu menu, String level) throws JspException {
	Object[] items = menu.getItems();

	HashMap subMenus = new HashMap();
	
	buffer.append("HM_Array1");
	buffer.append(level);
	buffer.append(" = [\n");	
	if (level.equals("")) doPrintMenuConfig(buffer);
	else buffer.append("[],\n");
		
	for (int i=0;i<items.length;i++) {
		MenuItem item = (MenuItem) items[i];
		String link = item.getLink();
		String key = item.getKey();
		Menu subMenu = item.getSubMenu();

		if (i!=0) buffer.append(",");
			
		buffer.append("[\"");
		buffer.append(getLabel());
		buffer.append("\",\"");
		if (link!=null) buffer.append(link);
		buffer.append("\",1,0,");
		if (subMenu==null) buffer.append("0]\n"); else {
			buffer.append("1]\n");
			subMenus.put(level + "_" + (i+1),subMenu);
		}
				
	}

	buffer.append("]\n\n");
		
	Set set = subMenus.entrySet();
	Iterator iterator = set.iterator();
	while (iterator.hasNext()) {
		Entry entry = (Entry) iterator.next();
		String subLevel = (String) entry.getKey();
		Menu subMenu = (Menu) entry.getValue();
		doPrintMenu(buffer, subMenu, subLevel);
	}
	
}
// TODO
// cache the bundle
// get the bundle in function of the skin
protected void doPrintMenuConfig(StringBuffer buffer) throws JspException {
	// get the SKIN
	String skin = LayoutUtils.getSkin(pageContext.getSession()).getName();
	
	// get the config
	try {
		ResourceBundle bundle = ResourceBundle.getBundle(config, new Locale(skin,""));
		buffer.append("[");
		buffer.append(bundle.getString("width"));
		buffer.append(",");
		buffer.append(bundle.getString("left_position"));
		buffer.append(",");
		buffer.append(bundle.getString("top_position"));
		buffer.append(",");
		buffer.append(bundle.getString("font_color"));
		buffer.append(",");	
		buffer.append(bundle.getString("mouseover_font_color"));
		buffer.append(",");
		buffer.append(bundle.getString("background_color"));
		buffer.append(",");
		buffer.append(bundle.getString("mouseover_background_color"));
		buffer.append(",");
		buffer.append(bundle.getString("border_color"));
		buffer.append(",");
		buffer.append(bundle.getString("separator_color"));
		buffer.append(",");
		buffer.append(bundle.getString("top_is_permanent"));
		buffer.append(",");
		buffer.append(bundle.getString("top_is_horizontal"));
		buffer.append(",");
		buffer.append(bundle.getString("tree_is_horizontal"));
		buffer.append(",");
		buffer.append(bundle.getString("position_under"));
		buffer.append(",");
		buffer.append(bundle.getString("top_more_images_visible"));
		buffer.append(",");
		buffer.append(bundle.getString("tree_more_images_visible"));
		buffer.append(",");
		buffer.append(bundle.getString("evaluate_upon_tree_show"));
		buffer.append(",");
		buffer.append(bundle.getString("evaluate_upon_tree_hide"));
		buffer.append("],\n");
	} catch (MissingResourceException e) {
		throw new JspException(e.getMessage());
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
		config ="fr.improve.struts.taglib.layout.DefaultDynMenuConfig";
	}
public void setConfig(String config) {
	this.config = config;
}
}
