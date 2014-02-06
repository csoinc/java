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

import org.apache.struts.Globals;

import fr.improve.struts.taglib.layout.menu.MenuAction;
import fr.improve.struts.taglib.layout.menu.MenuComponent;
import fr.improve.struts.taglib.layout.skin.Skin;
import fr.improve.struts.taglib.layout.treeview.TreeItemInfo;
import fr.improve.struts.taglib.layout.treeview.TreeViewTag;

/**
 * TODO Modify the javascript to allow \n.
 * 
 * @author jnribette
 */
public class BasicTreeview implements TreeviewInterface {
	protected static String VERTICAL_TREE = "treeLine.gif";
	protected static String CLOSE_NODE = "treeNodeClose.gif";
	protected static String LAST_CLOSE_NODE = "treeNodeCloseLast.gif";
	protected static String OPEN_NODE = "treeNodeOpen.gif";
	protected static String LAST_OPEN_NODE = "treeNodeOpenLast.gif";
	protected static String LAST_ITEM = "treeItemLast.gif";
	protected static String ITEM = "treeItem.gif";
	
	/**
	 * Starts a link.
	 */	
	public void startLink(StringBuffer buffer, HttpServletRequest in_request, ServletContext in_servletcontext, String in_styleClass, String in_link, String target) throws JspException {
		buffer.append("<a href=\"");
		String lc_link = in_link;
		if (target==null && !LayoutUtils.getSkin(in_request.getSession()).getFollowLinkIfFormChanged()) {			
			// PENDING
			// LayoutUtils.computeURL should be called, but this would required a lot of refactorization.
			StringBuffer lc_newLink = new StringBuffer("javascript:checkFormChange('");
			lc_newLink.append(in_link);
			lc_newLink.append("','");
			lc_newLink.append(LayoutUtils.getLabel(in_request, in_servletcontext,  Globals.MESSAGES_KEY, "layout.dataLost", null, false));
			lc_newLink.append("')");
			lc_newLink.append("\" onMouseOver=\"status='");
			lc_newLink.append(in_link);
			lc_newLink.append("';return true;\" onMouseOut=\"status='';return true;");
			lc_link = lc_newLink.toString();
		}
		buffer.append(lc_link);
		buffer.append("\"");
		if (in_styleClass!=null && in_styleClass.length()!=0) {
			buffer.append(" class=\"");
			buffer.append(in_styleClass);
			buffer.append("\"");	
		}
		if (target!=null && target.length()>0) {
			buffer.append(" target=\"");
			buffer.append(target);
			buffer.append("\"");	
		}	
		buffer.append(">");		
	}

	/**
	 * Ends a link.
	 */
	public void endLink(StringBuffer buffer) {
		buffer.append("</a>");
	}

	/**
	 * Renders a label (easy !)
	 */
	public void renderLabel(StringBuffer buffer, HttpServletRequest in_request, ServletContext in_servletcontext, String in_bundle, String key) throws JspException {
		buffer.append(LayoutUtils.getLabel(in_request, in_servletcontext,in_bundle, key, null, false));
	}
	
	/**
	 * Renders a link with a label.
	 */
	public void renderLabel(StringBuffer buffer, HttpServletRequest in_request, ServletContext in_servletcontext, TreeItemInfo in_info, MenuComponent in_item) throws JspException {
		String lc_link = in_item.getLocation();
		if (lc_link!=null && lc_link.length()!=0) {
			startLink(buffer, in_request, in_servletcontext, in_info.styleClass, lc_link, in_item.getTarget());
		}
		renderLabel(buffer, in_request, in_servletcontext, in_info.bundle, in_item.getTitle());
		if (lc_link!=null && lc_link.length()!=0) {
			endLink(buffer);
		}
	}

	// Render the item image.
	public void renderImage(StringBuffer buffer, HttpServletRequest in_request, TreeItemInfo in_info, MenuComponent in_item) {
		if (in_item.getImage()!=null) {
			if (in_info.hasSubMenus) {
			    // javascript to fully collapse or expand a node 
			    buffer.append("<a href=\"");
			    if (!in_info.useIndirection) {
			        buffer.append("javascript://\"");
			    } else {
			        buffer.append(in_request.getContextPath());
			        buffer.append("/");
			        buffer.append(LayoutUtils.getSkin(in_request.getSession()).getProperty(Skin.TREEVIEW_ACTION, TreeViewTag.DEFAULT_TREEVIEW_ACTION));
			        buffer.append("?open=");
			        buffer.append(in_info.path);
			        buffer.append("&bundle=");
			        buffer.append(in_info.bundle);
			        buffer.append("&name=");
			        buffer.append(in_info.name);
			        buffer.append("&class=");
			        buffer.append(in_info.styleClass);               
			        buffer.append("\" id=\"treeViewNode");
			        buffer.append(in_info.path);
			        buffer.append("\"");
			    }
			    
			    buffer.append(" onclick=\"return changeTreeAndSubtrees('");
			    
			    
	//	                buffer.append("<a href=\"");
	//	                buffer.append("javascript://\" onclick=\"return changeTreeAndSubtrees('");
			    buffer.append(in_info.path);              
			    buffer.append("');");
			    buffer.append("\" target=\"treeFrame\">");
			}           
			
			// Image
			buffer.append("<img src=\"");
			buffer.append(in_info.imageDirectory);      
			buffer.append("/");
			buffer.append(in_item.getImage());
			buffer.append("\" border=\"0\">");
			
			if (in_info.hasSubMenus) {
				// javascript to fully collapse or expand a node
			    buffer.append("</a>&nbsp;");
			}
		}
	}

	public void endLabel(StringBuffer buffer) {
		buffer.append("</td>");
	}

	public void startLabel(StringBuffer buffer, String in_styleClass, String onclick, String style) {
		// display the label
		buffer.append("<td");
		if (in_styleClass!=null && in_styleClass.length()!=0) {
			buffer.append(" class=\"");
			buffer.append(in_styleClass);
			buffer.append("\"");	
		}
		if (style!=null) {
			buffer.append(" style=\"");
			buffer.append(style);
			buffer.append("\"");	
		}
		if (onclick!=null) {
			buffer.append(" onclick=\"");
			buffer.append(onclick);
			buffer.append("\"");				
		}
		buffer.append(">");
	}

	public void endRow(StringBuffer buffer) {		
		// end a row. 		
		buffer.append("</tr>");
	}

	public int endSubMenu(StringBuffer buffer, HttpServletRequest in_request, ServletContext in_servletcontext, int lc_numberOfMenusPrinted, MenuComponent currentMenu, TreeItemInfo in_info) throws JspException {
		buffer.append("</tr>");
		buffer.append("<tr><td valign=\"top\" colspan=\"2\" id=\"treeView" + in_info.path + "\"");
		if (in_info.isClosed) {
			buffer.append(" style=\"display:none;\"");
		}
		buffer.append(">");
		if (!in_info.useIndirection) {
			buffer.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">");
			lc_numberOfMenusPrinted += TreeViewTag.doPrintMenu(buffer, currentMenu.getMenuComponents(), in_request, in_servletcontext, in_info.bundle, in_info.path, in_info.name, in_info.styleClass);
			buffer.append("</table>");
		}
		buffer.append("</td></tr>");
		buffer.append("</table></td>");
		return lc_numberOfMenusPrinted;
	}

	public void startSubMenu(StringBuffer buffer) {
		buffer.append("<td valign=\"top\" colspan=\"2\"><table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\"><tr>");
	}

	/**
	 * Render an icon.
	 * Do nothing in this implementation.
	 */
	public void renderIcon(StringBuffer buffer, TreeItemInfo in_info, MenuComponent in_item) {
		buffer.append("<td width=\"1\">");	
		/* zone coup?e et d?plac?e ? c?t? du label/lien, qqs lignes plus loin
			if (image!=null) {
				buffer.append("<img src=\"");
				buffer.append(imageDirectory);		
				buffer.append("/");
				buffer.append(image);
				buffer.append("\" border=\"0\">");
			}
		*/
		buffer.append("</td>");
	}

	/**
	 * Display the tree images in a TD.	 
	 */
	public void renderTree(StringBuffer buffer, HttpServletRequest in_request, TreeItemInfo in_info) {
		
		buffer.append("<td valign=\"top\" height=\"100%\"");

		// if it's not the last item, special code to display a vertical line in background.	
		if (!in_info.isLast) {
			buffer.append(" style=\"background-image: url(");
			buffer.append(in_info.imageDirectory);
			buffer.append("/");
			buffer.append(VERTICAL_TREE);
			buffer.append("); background-repeat: repeat-y; \"");						
		}
		
		buffer.append(" width=\"");
		buffer.append(16);
		buffer.append("\">");
		
		// choose the right image to display.
		if (in_info.hasSubMenus) {
			String lc_closeImage;
			String lc_openImage;
			if (in_info.isLast) {
				lc_closeImage = LAST_CLOSE_NODE;
				lc_openImage = LAST_OPEN_NODE;
			} else {
				lc_closeImage = CLOSE_NODE;
				lc_openImage = OPEN_NODE;
			}						
			startTreeUrl(buffer, in_request, in_info, lc_closeImage, lc_openImage);
			renderTreeImage(buffer, in_info, lc_closeImage, lc_openImage, 16, 22);			
			endTreeUrl(buffer);		
		} else {			
			buffer.append("<img src=\"");			
			buffer.append(in_info.imageDirectory);				
			buffer.append("/");					
			if (in_info.isLast) {
				// last item (not a folder)
				buffer.append(LAST_ITEM);
			} else {
				// not last item (not a folder)
				buffer.append(ITEM);
			}	
			buffer.append("\" border=\"0\" width=\"16\" height=\"22\">");			
		}						
		
		buffer.append("</td>");	
		// ------------------ end display the tree -------------------
	}

	public void renderTreeImage(StringBuffer buffer, TreeItemInfo in_info, String lc_closeImage, String lc_openImage, int in_width, int in_height) {		
		buffer.append("<img src=\"");			
		buffer.append(in_info.imageDirectory);				
		buffer.append("/");		
		buffer.append(in_info.isClosed ? lc_closeImage : lc_openImage);
		buffer.append("\" id=\"treeViewImage");
		buffer.append(in_info.path);
		buffer.append("\" border=\"0\" width=\"");
		buffer.append(in_width);
		buffer.append("\" height=\"");
		buffer.append(in_height);
		buffer.append("\" align=\"middle\">");
	}

	public void endTreeUrl(StringBuffer buffer) {
		buffer.append("</a>");
	}

	public void startTreeUrl(StringBuffer buffer, HttpServletRequest in_request, TreeItemInfo in_info, String lc_closeImage, String lc_openImage) {
		buffer.append("<a href=\"");
		if (!in_info.useIndirection) {
			buffer.append("javascript://\" onclick=\"return changeTree('");
		} else {
			buffer.append(in_request.getContextPath());
			buffer.append("/");
			buffer.append(LayoutUtils.getSkin(in_request.getSession()).getProperty(Skin.TREEVIEW_ACTION, TreeViewTag.DEFAULT_TREEVIEW_ACTION));
			buffer.append("?open=");
			buffer.append(in_info.path);
			buffer.append("&bundle=");
			buffer.append(in_info.bundle);
			buffer.append("&name=");
			buffer.append(in_info.name);
			buffer.append("&class=");
			buffer.append(in_info.styleClass);				
			buffer.append("\" id=\"treeViewNode");
			buffer.append(in_info.path);
			buffer.append("\" onclick=\"return changeTree('");
		}
		buffer.append(in_info.path);
		buffer.append("','");
		buffer.append(in_info.imageDirectory);
		buffer.append("/");
		buffer.append(lc_openImage);
		buffer.append("','");
		buffer.append(in_info.imageDirectory);
		buffer.append("/");
		buffer.append(lc_closeImage);				
		buffer.append("');");
		buffer.append("\" target=\"treeFrame\">");
	}
	

	/**
	 * Starts a row.	 
	 */
	public void startRow(StringBuffer buffer) {				
		buffer.append("<tr>");
	}

	/**
	 * Compute the new styleClass: 
	 * If the styleClass ends with a digit, increment the digit by one.
	 */
	public String computeStyleClass(String in_styleClass) {
		String lc_newStyleClass = in_styleClass;
		if (in_styleClass.length()!=0) {
			char lc_lastChar = in_styleClass.charAt(in_styleClass.length()-1);
			if (Character.isDigit(lc_lastChar)) {
			// Asume there won't be more than 9 defined styleClasses.
			lc_newStyleClass = lc_newStyleClass.substring(0, lc_newStyleClass.length()-1) + (char)(lc_lastChar+1);
			}
		}
		return lc_newStyleClass;
	}
	
	public void renderActions(StringBuffer buffer, HttpServletRequest in_request, ServletContext in_context, TreeItemInfo in_info, MenuComponent in_item) throws JspException {
		MenuAction[] lc_actions = in_item.getMenuAction();
		buffer.append("<td align=\"right\" nowrap>");  
		int i;
		for (i = 0; i < lc_actions.length; i++) {
			MenuAction lc_menuAction = lc_actions[i];
			String lc_href = lc_menuAction.getHref();
			String lc_target = lc_menuAction.getTarget();
			if (lc_href!=null) {
				String lc_computedHref = lc_href;
				if (lc_computedHref.indexOf('?')==-1) {
					lc_computedHref = lc_computedHref + "?path=" + in_info.path;
				}
				startLink(buffer, in_request, in_context, null, lc_computedHref, lc_target);
			}
			buffer.append("<img src=\"");
			buffer.append(in_info.imageDirectory);
			buffer.append("/");
			buffer.append(lc_menuAction.getImgSrc());
			buffer.append("\" align=\"middle\" border=\"0\">");
			if (lc_href!=null) {
				endLink(buffer);
			}
		}
		buffer.append("</td>");
	}

}
