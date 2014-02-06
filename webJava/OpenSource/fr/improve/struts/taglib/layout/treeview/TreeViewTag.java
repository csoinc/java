package fr.improve.struts.taglib.layout.treeview;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.PanelTag;
import fr.improve.struts.taglib.layout.menu.MenuComponent;
import fr.improve.struts.taglib.layout.skin.Skin;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.MenuInterface;
import fr.improve.struts.taglib.layout.util.TagUtils;
import fr.improve.struts.taglib.layout.util.TreeviewInterface;

/**
 * Display a treeview.
 * 
 * If the treeview is defined from <layout:menuItem> tags it is totally loaded and nodes are open and close by javascript.<br>
 * <br>
 * If the treeview is defined from the menu repository, only the root nodes are loaded. 
 * Nested nodes are loaded one time when the user open them.
 * This allows for very big treeview without having to send a lot of data at a time.
 * 
 * Compatible with Scott Sayles menu framework.
 * @author: Jean-Noël Ribette
 */
public class TreeViewTag extends PanelTag implements MenuInterface {

	/**
	 * Key used to store the treeview ids in the session.
	 */
	private static final String TREEVIEW_NUMBER = "fr.improve.struts.taglib.layout.TreeviewTag.TREEVIEW_NUMBER";
	
	/**
	 * Default treeview action url
	 */
	public static final String DEFAULT_TREEVIEW_ACTION = "treeview.do";
	
	/**
	 * Useful structure storing the ids of the known treeview.
	 * 
	 * Ids of named treeview defined in the menu repository are stored in the namedTreeview map.
	 * 
	 * Ids of treeview defined with menuitem tags are stored in the anonymousTreeview map 
	 * and are associated with the request URI.
	 * This makes it impossible to have two anonymous treeview in the same request.
	 */
	public class TreeViewIds implements Serializable {
		private int counter = -1;
		java.util.Hashtable namedTreeview;
		java.util.Hashtable anonymousTreeview;
		TreeViewIds() {
			namedTreeview = new Hashtable();
			anonymousTreeview = new Hashtable();	
		}
		public synchronized int getNewId() {
			counter++;
			return counter;	
		}
	}

	/**
	 * The content of the treeview.
	 */		
	protected Vector menus = new Vector();
	
	/**
	 * True if the styleClass should be subfixed with the node depth.
	 */
	protected boolean autoIncrement = true;
	
	/**
     * Specify the number of levels.
     */
    protected int expandedLevelsAtFirst = -1;
    
    /**
	 * If there are nested &lt;layout:menuItem&gt; tags, those tags will call this methods to add items to the treeview.
	 */	
	public void addItem(MenuComponent item) {
		menus.add(item);
	}
	public void addItems(MenuComponent[] items) {
		menus.addAll(Arrays.asList(items));
	}
	
/**
 * End the tag and display the treeview.
 */
public int doEndLayoutTag() throws JspException {
	// Get the menu from the menu repository if the menuItem tag is not used.
	// menuItem tags CAN be used WITH menu attributs specified (this doesn't allow
	// to use indirection, but allows to use cookie to store treeview state).
	boolean allowIndirection = false;
	if (menus.size()==0 && name!=null) {
		MenuComponent lc_tree = LayoutUtils.getMenu(pageContext, name);
		if (lc_tree==null) {
			throw new JspException("Could not find menu named \"" + name + "\"");
		}
		addItems(lc_tree.getMenuComponents());
		allowIndirection = true;
	}
	
	doStartLayout();
	StringBuffer buffer = new StringBuffer();
	doStartPanel(buffer);
	
	buffer.append("<tr><td>");
	
	// Get the treeview id.
	String lc_i = computeTreeviewId();
	
	buffer.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" id=\"treeView" + lc_i+ "\">\n");
	
	// Compute the styleClass name.
	String lc_styleClass = styleClass;
	if (lc_styleClass==null) {
		lc_styleClass = "";
	}
	if (lc_styleClass.length()!=0 && autoIncrement) {
		lc_styleClass += "1";
	}

	// Generate the treeview content
	doPrintMenu(buffer,(MenuComponent[])menus.toArray(new MenuComponent[menus.size()]), (HttpServletRequest)pageContext.getRequest(), pageContext.getServletContext(), bundle, lc_i, name, lc_styleClass, allowIndirection);
	
	buffer.append("</table>");
	
	// If we are using indirection, generates an hidden frame.	
	if (pageContext.getRequest().getAttribute("fr.improve.struts.taglib.layout.treeview.TreeViewTag.KEY")==null && name!=null && allowIndirection) {
		buffer.append("<iframe id=\"treeFrame\" name=\"treeFrame\" style=\"width:0px; height:0px; border: 0px\" src=\"");
		buffer.append(buildIFrameSource((HttpServletRequest)pageContext.getRequest()));
		buffer.append("\"></iframe>");
		pageContext.getRequest().setAttribute("fr.improve.struts.taglib.layout.treeview.TreeViewTag.KEY", "");
	}
	
	buffer.append("</td></tr>");
	doEndPanel(buffer);
    
    if (expandedLevelsAtFirst > 0) {
        // the user wants to have the tree expanded the first time it is generated
        Cookie[] lc_cookies = ((HttpServletRequest) pageContext.getRequest()).getCookies();
        boolean lc_firstDisplay = true;
        if (lc_cookies != null) {
            for (int i = 0; i < lc_cookies.length; i++)
        	{
        		if (lc_cookies[i].getName().equals("treeView")) {
                    // a cookie is stored: that means this is not the first rendering
                    lc_firstDisplay = false;
        			break;
                }
        	}
        }
        if (lc_firstDisplay) {
            buffer.append("<script>");
            buffer.append("expandFirstLevels(\"");
            buffer.append(lc_i);
            buffer.append("\", ");
            buffer.append(expandedLevelsAtFirst);
            buffer.append(");");
            buffer.append("</script>");
        }
    }
    
	TagUtils.write(pageContext, buffer.toString());
	doEndLayout();

	menus.removeAllElements();
	name = null;
	return EVAL_PAGE;
}

	/**
	 * Compute the id of the treeview.
	 * Treeview ids are integers starting from 0.
	 */
	private String computeTreeviewId() throws JspException {
		if (name!=null) {
			return name;
		}
		TreeViewIds treeviewIds = (TreeViewIds) pageContext.getSession().getAttribute(TREEVIEW_NUMBER);
		Integer lc_id;
		if (treeviewIds == null) {
			treeviewIds = new TreeViewIds();
			pageContext.getSession().setAttribute(TREEVIEW_NUMBER, treeviewIds);
		}
		if (name!=null) {
			lc_id = (Integer) treeviewIds.namedTreeview.get(name);
			if (lc_id==null) {
				lc_id = new Integer(treeviewIds.getNewId());
				treeviewIds.namedTreeview.put(name, lc_id);
			}
			return lc_id.toString();
		} else {
			if (pageContext.getRequest().getAttribute(TREEVIEW_NUMBER)!=null) {
				throw new JspException("There can be only one anonymous treeview per request");
			} else {
				pageContext.getRequest().setAttribute(TREEVIEW_NUMBER, "");
				lc_id = (Integer) treeviewIds.anonymousTreeview.get(((HttpServletRequest)pageContext.getRequest()).getRequestURI());
				if (lc_id==null) {
					lc_id = new Integer(treeviewIds.getNewId());
					treeviewIds.anonymousTreeview.put(((HttpServletRequest)pageContext.getRequest()).getRequestURI(), lc_id);
				}
				return lc_id.toString();
			}
		}
	}
	
	public static int doPrintMenu(StringBuffer buffer, MenuComponent[] menus, HttpServletRequest in_request, ServletContext in_servletcontext, String in_bundle, String in_path, String in_name, String in_styleClass) throws JspException {
		return doPrintMenu(buffer, menus, in_request, in_servletcontext, in_bundle, in_path, in_name, in_styleClass, true);
	}

	/**
	 * Print the given menus.
	 * 
	 * @param buffer				StringBuffer in which the HTML code is generated
	 * @param menus				The menus do display
	 * @param in_request			The current request
	 * @param in_servletContext	The context of the servlet (in which is the application message resources).
	 * @param in_bundle			Name of the message resources to use.
	 * @param in_path				The nested menus path
	 * @param in_name				Name of the menu if taken from the menu repository
	 * 
	 * @return 					The number of items generated.
	 */
	public static int doPrintMenu(StringBuffer buffer, MenuComponent[] menus, HttpServletRequest in_request, ServletContext in_servletcontext, String in_bundle, String in_path, String in_name, String in_styleClass, boolean in_allowIndirection) throws JspException {
		// Get a treeview renderer. 
		TreeviewInterface lc_treeviewRenderer = LayoutUtils.getSkin(in_request.getSession()).getTreeviewInterface();
		
		// init the info.
		TreeItemInfo lc_info = new TreeItemInfo();
		
		int lc_numberOfMenusPrinted = menus.length;

		// Get the images directory.
		lc_info.imageDirectory = LayoutUtils.getSkin(in_request.getSession()).getImageDirectory(in_request);

		// Compute the styleClass.
		lc_info.styleClass = lc_treeviewRenderer.computeStyleClass(in_styleClass);
		
		lc_info.bundle = in_bundle;
		lc_info.name = in_name;
		
		// Render each item.
		for (int i=0;i<menus.length;i++) {
			
			// ------------------- initialize the iteration -------------- //
			
			
			// Get the current item.
			MenuComponent item = (MenuComponent) menus[i];
			String link = item.getLocation();
			String key = item.getTitle();
			String image = item.getImage();
			String target = item.getTarget();
			String onclick = item.getOnClick();
			String style = item.getStyle();
			
			// Are there submenus ?	
			lc_info.hasSubMenus = item.hasMenuComponents();
			
			// Is this the last iteration ?
			lc_info.isLast = i+1 == menus.length;
			
			// Compute the path of the current item.
			// If the item has an id, use it, else use the item position.
			String lc_id = item.getId();
			if (lc_id==null) {
				lc_id = String.valueOf(i);
			}
			lc_info.path = in_path.length()==0 ? lc_id : in_path + "*" + lc_id;
			
			// Is the current item open or closed ?
			lc_info.isClosed = TreeViewTag.isNodeClosed(in_request, lc_info.path);
			
			// What is the number of items to render at once in the HTML code ?
	        int lc_numberOfMenusToLoad = LayoutUtils.getSkin(in_request.getSession()).getNumberOfMenusLoaded();
	        
	        // Should all items be rendered at once in the HTML code ?
			lc_info.useIndirection = in_allowIndirection && lc_numberOfMenusPrinted >= lc_numberOfMenusToLoad && in_name!=null && lc_info.isClosed;					
			
					
			
			// ------------------- do some real stuff now ----------------- //
			
			// Start a row.
			lc_treeviewRenderer.startRow(buffer);
			
			// Render a first cell containing the tree image.
			lc_treeviewRenderer.renderTree(buffer, in_request, lc_info);

			// Start a submenu if there is one.
			if (lc_info.hasSubMenus) {
				lc_treeviewRenderer.startSubMenu(buffer);	
			}

			// Render an icon.
			lc_treeviewRenderer.renderIcon(buffer, lc_info, item);
			
			// Start the label TD.
			lc_treeviewRenderer.startLabel(buffer, in_styleClass, onclick, style);
	        
			// Render the image.
	        lc_treeviewRenderer.renderImage(buffer, in_request, lc_info, item);	        
	        
	        // Render the label.		
			lc_treeviewRenderer.renderLabel(buffer, in_request, in_servletcontext, lc_info, item);			
			
			// End the label TD.
			lc_treeviewRenderer.endLabel(buffer);
			
			lc_treeviewRenderer.renderActions(buffer, in_request, in_servletcontext, lc_info, item);
			
			if (lc_info.hasSubMenus) {
				lc_numberOfMenusPrinted = lc_treeviewRenderer.endSubMenu(buffer, in_request, in_servletcontext, lc_numberOfMenusPrinted, item, lc_info);
			}						

			lc_treeviewRenderer.endRow(buffer);
		}
		return lc_numberOfMenusPrinted;
	}
	
	/**
	 * Returns true if the node is close.
	 * A node is close by default. It is open if the page has already been displayed and the user has opened it.
	 * This would have lead javascript code to modify a cookie.
	 * So all we have to do is to read the cookie.
	 * <br/><br/>
	 * The cookie holds the list of the open node and uses the following format: _<node1 id>_<node2 id>_<node3 id>_
	 */
	public static boolean isNodeClosed(HttpServletRequest in_request, String in_path) {
		Cookie[] lc_cookies = in_request.getCookies();
		
		if (lc_cookies==null) return true;
		for (int i=0;i<lc_cookies.length;i++) {
			Cookie lc_cookie = lc_cookies[i];
			if (lc_cookie.getName().equals("treeView")) {
				if (lc_cookie.getValue().indexOf("_treeView" + in_path + "_")==-1) {
					return true;	
				} else {
					return false;	
				}
			} 
		}		
		return true;	
	}
	

public int doStartLayoutTag() throws JspException {	
	return EVAL_BODY_INCLUDE;
}
public void setAutoIncrement(String in_value) {
	if ("true".equalsIgnoreCase(in_value)) {
		autoIncrement = true;	
	} else {
		autoIncrement = false;	
	}
}
public void release() {
	super.release();
	autoIncrement = true;
	expandedLevelsAtFirst = -1;
}

	/**
	 * @param expandedLevelsAtFirst The expandedLevelsAtFirst to set.
	 */
	public final void setExpandedLevelsAtFirst(int expandedLevelsAtFirst)
	{
		this.expandedLevelsAtFirst = expandedLevelsAtFirst;
	}
	
	/**
	 * Build an URL for the iframe.
	 * The url must be relative to the current URL, otherwise 
	 * some version of IE will complain in SSL mode.
	 */
	private String buildIFrameSource(HttpServletRequest in_request) {
		String action = LayoutUtils.getSkin(in_request.getSession()).getProperty(Skin.TREEVIEW_ACTION, DEFAULT_TREEVIEW_ACTION);
		return in_request.getContextPath() + "/" + action + "?iframe=iframe";
	}
}
