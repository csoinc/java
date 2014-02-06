package fr.improve.struts.taglib.layout;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Vector;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import fr.improve.struts.taglib.layout.menu.MenuComponent;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.MenuInterface;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Menu tag for use with Hiermenus, compatible with Scott Sayles menu framework
 * PENDING: 
 * 1. use the Layout.properties file instaed of a custom file.
 * 2. use a free menu system instead of HierMenus.
 * @author: Jean-Noël Ribette
 */
public class DynMenuTag2 extends PanelTag implements MenuInterface{
	protected Vector menus = new Vector();
	protected String config = "fr.improve.struts.taglib.layout.DefaultDynMenuConfig";

	protected String ID = "fr.improve.struts.taglib.layout.DynMenuTag2.ID";
	// hack to give each menu a different id.
	protected int menuNumber;
	
	protected String top;
	protected String left;
	
	protected boolean includeScript = false;
	protected static final String SCRIPT_KEY = "fr.improve.struts.taglib.layout.DynMenuTag2.SCRIPT_KEY";
	
	public static void includeScriptCode(StringBuffer in_buffer, PageContext in_pg) {
		if (in_pg.getRequest().getAttribute(SCRIPT_KEY)!=null) {
			in_buffer.append("<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"");
			in_buffer.append(LayoutUtils.getSkin(in_pg.getSession()).getConfigDirectory(in_pg.getRequest()));
			in_buffer.append("/HM_Loader.js\" TYPE='text/javascript'></SCRIPT>\n");
			in_pg.getRequest().removeAttribute(SCRIPT_KEY);
		}		
	}

	public void addItem(MenuComponent item) {
		menus.add(item);
	}
	public int doEndLayoutTag() throws JspException {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("<script LANGUAGE='JavaScript1.2' TYPE='text/javascript'>\n");

		doPrintMenu(buffer, (MenuComponent[])menus.toArray(new MenuComponent[menus.size()]), "");
		buffer.append("</script>\n");
		
		pageContext.getRequest().setAttribute(SCRIPT_KEY, "");
		
		if (includeScript) {
			includeScriptCode(buffer, pageContext);
		}
			
		TagUtils.write(pageContext,buffer.toString());

		pageContext.getRequest().setAttribute(ID, new Integer(menuNumber+1));

		// Reset the tag.
		menus.removeAllElements();
		
		return EVAL_PAGE;
	}
	
	public void release() {
		super.release();
		config ="fr.improve.struts.taglib.layout.DefaultDynMenuConfig";		
		left = null;
		top = null;
		includeScript = false;
	}
public void doPrintMenu(StringBuffer buffer, MenuComponent[] menus, String level) throws JspException {
	HashMap subMenusMap = new HashMap();
	
	buffer.append("HM_Array");
	buffer.append(menuNumber);
	buffer.append(level);
	buffer.append(" = [\n");	
	if (level.equals("")) doPrintMenuConfig(buffer);
	else buffer.append("[],\n");
		
	for (int i=0;i<menus.length;i++) {
		MenuComponent item = (MenuComponent) menus[i];
		String link = item.getLocation();
		String forward = item.getForward();
		String page = item.getPage();
		String target = item.getTarget();
		/* String*/ key = item.getTitle();
		MenuComponent[] subMenus = item.getMenuComponents();

		if (i!=0) buffer.append(",");
			
		buffer.append("[\"");
		buffer.append(getLabel());
		buffer.append("\",\"");
		if (link!=null || page!=null || forward!=null) {
			if (target!=null) {
				buffer.append("javascript:window.open('").append(LayoutUtils.computeURL(pageContext, forward, link, page, null, null, null, null, false, null)).append("','").append(target);
				buffer.append("','')");
			} else {
				buffer.append(LayoutUtils.computeURL(pageContext, forward, link, page, null, null, null, null, false, null));
			}
		}
		buffer.append("\",1,0,");
		if (subMenus==null || subMenus.length==0) buffer.append("0]\n"); else {
			buffer.append("1]\n");
			subMenusMap.put(level + "_" + (i+1),subMenus);
		}
				
	}

	buffer.append("]\n\n");
		
	Set set = subMenusMap.entrySet();
	Iterator iterator = set.iterator();
	while (iterator.hasNext()) {
		Entry entry = (Entry) iterator.next();
		String subLevel = (String) entry.getKey();
		MenuComponent[] subMenus = (MenuComponent[]) entry.getValue();
		doPrintMenu(buffer, subMenus, subLevel);
	}
	
}
protected void doPrintMenuConfig(StringBuffer buffer) throws JspException {
	// get the SKIN
	String skin = LayoutUtils.getSkin(pageContext.getSession()).getCssFileName();
	skin = skin.substring(0, skin.length()-4);

	// check the top & left position are numbers	
	try {
		if (left!=null) {
			Integer.parseInt(left);
		} 
		if (top!=null) {		
			Integer.parseInt(top);
		}
	} catch (NumberFormatException e) {
		JspException jspe = new JspException("dynMenu: left or top position is not a valid number");
		TagUtils.saveException(pageContext, jspe);
		throw jspe;
	}
	
	// get the config
	try {
		ResourceBundle bundle = ResourceBundle.getBundle(config, new Locale(skin,""));
		buffer.append("[");
		buffer.append(bundle.getString("width"));
		buffer.append(",");
		buffer.append(left !=null ? left : bundle.getString("left_position"));
		buffer.append(",");
		buffer.append(top !=null ? top : bundle.getString("top_position"));	
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
		JspException jspe = new JspException("dynMenu: missing attribute " + e.getMessage());
		TagUtils.saveException(pageContext, jspe);
		throw jspe;
	}
	
}
	public int doStartLayoutTag() throws JspException {
		Integer lc_int = (Integer) pageContext.getRequest().getAttribute(ID);
		if (lc_int==null) {
			menuNumber = 1;	
		} else {
			menuNumber = lc_int.intValue();	
		}
		return EVAL_BODY_INCLUDE;
	}
	public void setConfig(String config) {
		this.config = config;
	}
	public void setLeft(String left) {
		this.left = left;
	}
	public void setTop(String top) {
		this.top = top;
	}
	public void setIncludeScript(boolean in_b) {
		includeScript = in_b;
	}

}
