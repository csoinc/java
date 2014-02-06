/*
 * Created on 17 mars 2005
 */
package fr.improve.struts.taglib.layout.treeview;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.improve.struts.taglib.layout.menu.MenuComponent;


/**
 * Reconceile treeview menus.
 * 
 * @author JN RIBETTE
 */
public class TreeViewReconciler {
	private static final String RECONCEILE_KEY = "fr.improve.struts.taglib.layout.treeview.TreeViewReconciler";
	private static final Log Log = LogFactory.getLog(TreeViewReconciler.class);
	
	public static void reconceileFromCookie(MenuComponent in_menu, HttpServletRequest in_request) {
		if (!isReconcile(in_menu, in_request)) {
			String lc_cookie = getTreeviewCookie(in_request);
			Log.debug("Reconceiling menu " + in_menu.getName() + " from cookie value " + lc_cookie);
			if (lc_cookie!=null) {
				in_menu.closeAll();
				StringTokenizer lc_tokenizer = new StringTokenizer(lc_cookie, "_");
				while (lc_tokenizer.hasMoreTokens()) {
					String lc_token = lc_tokenizer.nextToken();
					if (lc_token.startsWith("treeView")) {
						lc_token = lc_token.substring(8);
					}
					if (lc_token.startsWith(in_menu.getName() + "*")) {
						MenuComponent lc_component = getComponentWithPath(in_menu, lc_token.substring(in_menu.getName().length()+1));
						if (lc_component!=null) {
							lc_component.setOpen(true);
						}
					}
				}
				setReconceile(in_menu, in_request);
			}
		}
	}
	
	private static void setReconceile(MenuComponent in_menu, HttpServletRequest in_request) {
		Set lc_map = (Set) in_request.getAttribute(RECONCEILE_KEY);
		if (lc_map==null) {
			lc_map = new HashSet();
			in_request.setAttribute(RECONCEILE_KEY, lc_map);
		}
		lc_map.add(in_menu);
	}
	
	private static boolean isReconcile(MenuComponent in_menu, HttpServletRequest in_request) {
		Set lc_map = (Set) in_request.getAttribute(RECONCEILE_KEY);
		if (lc_map==null) {
			return false;
		}
		return lc_map.contains(in_menu);
	}
	
	private static Set getReconciledMenu(HttpServletRequest in_request) {
		Set lc_map = (Set) in_request.getAttribute(RECONCEILE_KEY);
		if (lc_map==null) {
			return Collections.EMPTY_SET;
		} else {
			return lc_map;
		}
	}
	
	
	
	private static String getTreeviewCookie(HttpServletRequest in_request) {
		Cookie[] lc_cookies = in_request.getCookies();
		
		if (lc_cookies==null) return null;
		for (int i=0;i<lc_cookies.length;i++) {
			Cookie lc_cookie = lc_cookies[i];
			if (lc_cookie.getName().equals("treeView")) {
				return lc_cookie.getValue();
			}
		}
		return null;
	}
	
	static MenuComponent getComponentWithPath(MenuComponent in_menu, String in_path) {
		int lc_starPos = in_path.indexOf('*');
		if(lc_starPos!=-1) {
			String lc_id = in_path.substring(0, lc_starPos);
			String lc_subPath = in_path.substring(lc_starPos+1);
			MenuComponent lc_menu = in_menu.getChild(lc_id);
			if (lc_menu!=null) {
				return getComponentWithPath(lc_menu, lc_subPath);
			} else {
				return null;
			}
		} else {
				return in_menu.getChild(in_path);
		}
	}
	
	public static void reconceileFromMenu(HttpServletRequest in_request, HttpServletResponse in_response) {
		Set lc_menus = getReconciledMenu(in_request);
		Iterator lc_it = lc_menus.iterator();
		while (lc_it.hasNext()) {
			MenuComponent lc_menu = (MenuComponent) lc_it.next();
			reconceileFromMenu(lc_menu, in_request, in_response);
			lc_menu.closeAll();
		}
		in_request.removeAttribute(RECONCEILE_KEY);
	}
	
	private static void reconceileFromMenu(MenuComponent in_menu, HttpServletRequest in_request, HttpServletResponse in_response) {
		StringBuffer lc_cookievalue = new StringBuffer();
		String lc_path = in_menu.getName();
		reconceileFromMenu(in_menu, lc_cookievalue, lc_path);
		if (lc_cookievalue.length()>0) {
			lc_cookievalue.append("_");
		}
		String lc_newCookieValue = lc_cookievalue.toString();
		Log.debug("Reconceiling cookie with menu " + in_menu.getName() + ". New cookie value is : " + lc_newCookieValue);
		Cookie lc_cookie = new Cookie("treeView", lc_newCookieValue);
		lc_cookie.setPath(in_request.getContextPath());
		in_response.addCookie(lc_cookie);
		
		Cookie[] lc_cookies = in_request.getCookies();		
		if (lc_cookies!=null) {
			for (int i=0;i<lc_cookies.length;i++) {
				Cookie lc_oldCookie = lc_cookies[i];
				if (lc_oldCookie.getName().equals("treeView")) {
					lc_oldCookie.setValue(lc_newCookieValue);
				}
			}
		}
		in_menu.closeAll();
	}
	
	private static void reconceileFromMenu(MenuComponent in_menu, StringBuffer lc_cookievalue, String lc_path) {
		if (in_menu.isOpen() || in_menu.getParent()==null) {
			MenuComponent[] lc_children = (MenuComponent[]) in_menu.getMenuComponents();
			if (in_menu.getParent()!=null) {
				lc_cookievalue.append("_treeView");
				lc_cookievalue.append(lc_path);
			}
			
			for (int i=0; i < lc_children.length; i++) {
				MenuComponent lc_child = lc_children[i];
				String lc_id = lc_child.getId();
				if (lc_id==null) {
					lc_id = String.valueOf(i);
				}
				reconceileFromMenu(lc_child, lc_cookievalue, lc_path + "*" + lc_id);
			}
		}
	}

}
