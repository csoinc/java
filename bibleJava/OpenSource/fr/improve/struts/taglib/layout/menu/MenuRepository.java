/*
 * MenuRepository.java
 *
 * Created on January 29, 2001, 9:51 AM
 */

package fr.improve.struts.taglib.layout.menu;

import java.io.Serializable;
import java.util.Set;

import javax.servlet.http.HttpServlet;

import org.apache.commons.collections.FastHashMap;

/**
 * Menu Repository, from Struts-Menu
 *
 * @author  ssayles
 * @version 
 */
public class MenuRepository implements Serializable {

	public static final String MENU_REPOSITORY_KEY = "fr.improve.struts.taglib.layout.menu.MENU_REPOSITORY";
    
	protected String config = null;
    
	protected String name = null;
    
	protected HttpServlet servlet = null;
    
    
	protected FastHashMap menus = new FastHashMap();
    
	protected FastHashMap displayers = new FastHashMap();

    
	public Set getMenuNames() {
		return menus.keySet();
	}
    
	public MenuComponent getMenu(String menuName) {
		return (MenuComponent)menus.get(menuName);
	}
    
	public void addMenu(MenuComponent menu) {
		menus.put(menu.getName(), menu);
	}
        
	public void setLoadParam(String loadParam) {
		config = loadParam;
	}
	public String getLoadParam() {
		return config;
	}
    
	public void setName(String name) {
		this.name = name;    
	}
    
	public String getName() {
		return name;
	}

	public HttpServlet getServlet() {
		return servlet;
	}

	public void setServlet(HttpServlet servlet) {
		this.servlet = servlet;
	}
}