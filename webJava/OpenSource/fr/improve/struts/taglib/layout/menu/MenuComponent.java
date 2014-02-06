/*
 * MenuComponent.java
 *
 * Created on January 28, 2001, 8:10 PM
 */

package fr.improve.struts.taglib.layout.menu;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * MenuComponent, base on Struts-Menu.
 * 
 * @author  Scott Sayles
 * @author jnribette
 */
public class MenuComponent extends MenuBase  implements Serializable {

	protected ArrayList menuComponents = new ArrayList();
	protected Map children = new HashMap();
    
	protected static MenuComponent[] _menuComponent = new MenuComponent[0];
	protected static MenuAction[] _menuAction = new MenuAction[0];
    
	protected MenuComponent parentMenu = null;
	
	/**
	 * A menu can be shared in the ServletContext 
	 * so it is not possible to store the value 
	 * in the menu itself.
	 * 
	 *  This class is serializable, 
	 *  so open is transient.
	 */
	private transient ThreadLocal open = new ThreadLocal();
	
	protected ArrayList menuActions = new ArrayList();
    
  
	/** Creates new MenuComponent */
	public MenuComponent() {
		super();
	}
	
	public MenuComponent(String in_id) {
		super(in_id);
		if (in_id==null || in_id.indexOf('*')!=-1 || in_id.indexOf('_')!=-1) {
			throw new IllegalArgumentException("MenuComponent id cannot be null, nor contain '*' or '_'");
		}
	}
    
	/**
	 * Adds a MenuComponent
	 * @param menuComponent
	 */
	public void addMenuComponent(MenuComponent menuComponent) {
		menuComponents.add(menuComponent);
		menuComponent.setParent(this);
		if((menuComponent.getName() == null) || (menuComponent.getName().equals(""))) {
			menuComponent.setName(this.name + menuComponents.size());
		}
		String lc_id = menuComponent.getId();
		if (lc_id!=null) {
			children.put(lc_id, menuComponent);
		}
	}
    
	public MenuComponent[] getMenuComponents() {
		MenuComponent[] menus = (MenuComponent[])menuComponents.toArray(_menuComponent);
		return menus;
	}
	
	/**
	 * Return the child with the specified id if id are being used.
	 * However, return the element at the specified index.
	 * 
	 * @param in_id the item id, or index.
	 * @return
	 */
	public MenuComponent getChild(String in_id) {
		if (children.isEmpty() && hasMenuComponents()) {
			// Pas d'id.
			try {
				int lc_pos = Integer.parseInt(in_id);
				if (lc_pos<menuComponents.size()) {
					return (MenuComponent) menuComponents.get(lc_pos);
				}
				return null;
			} catch (NumberFormatException e) {
				return null;
}
		} else {
			return (MenuComponent) children.get(in_id);
		}
	}

	public void setParent(MenuComponent parentMenu) {
		this.parentMenu = parentMenu;
	}
    
	public MenuComponent getParent() {
		return parentMenu;
	}
	
	/**
	 * Returns an array of the actions.
	 */
	public MenuAction[] getMenuAction() {
		return (MenuAction[]) menuActions.toArray(_menuAction);
	}
	
	/**
	 * Adds an action.
	 */
	public void addMenuAction(MenuAction in_action) {
		menuActions.add(in_action);
	}

	/**
	 * Return true if the menu has submenus.
	 */
	public boolean hasMenuComponents() {		
		return !menuComponents.isEmpty();
	}
	
	/**
	 * Add a child menu at the specfied index.
	 */
	public void addMenuComponent(int in_index, MenuComponent in_menuComponent) {
		menuComponents.add(in_index, in_menuComponent);
		String lc_id = in_menuComponent.getId();
		if (lc_id!=null) {
			children.put(lc_id, in_menuComponent);
		}
	}
	
	public void removeMenuComponent(int in_index) {
		MenuComponent lc_element = (MenuComponent)menuComponents.get(in_index);
		String lc_id = lc_element.getId();
		if (lc_id!=null) {
			children.remove(lc_id);
		}
		menuComponents.remove(in_index);

	}
	
	public void removeMenuComponent(MenuComponent in_menuComponent) {
		String lc_id = in_menuComponent.getId();
		if (lc_id!=null) {
			children.remove(lc_id);
		}
		menuComponents.remove(in_menuComponent);
	}
	
	public void setOpen(boolean in_open) {
		checkOpen();
		open.set(in_open ? Boolean.TRUE : Boolean.FALSE);
	}
	
	/**
	 * Reinitialize the "open" ThreadLocal
	 * if a serialization/deserialization has occur. 
	 *
	 */
	private void checkOpen() {
		if (open==null) {			
			open = new ThreadLocal();
		}
	}
	
	public boolean isOpen() {
		checkOpen();
		Boolean lc_open = (Boolean) open.get();
		if (lc_open==null) {
			return false;
		} else {
			return lc_open.booleanValue();
		}
	}
	
	public void setOpen(boolean in_open, int in_level) {
		setOpen(in_open);
		int lc_level = in_level - 1;
		if (lc_level>0) {
			MenuComponent[] lc_menus = (MenuComponent[]) getMenuComponents();
			for (int i=0; i < lc_menus.length; i++) {
				lc_menus[i].setOpen(in_open, lc_level);
			}
		}
	}
	
	public int indexOf(MenuComponent in_component) {
		return menuComponents.indexOf(in_component);
	}
	
	public void closeAll() {
		if (isOpen() || getParent()==null) {
			MenuComponent[] lc_menus = (MenuComponent[]) getMenuComponents();
			for (int i=0; i < lc_menus.length; i++) {
				lc_menus[i].closeAll();
			}
		}
		setOpen(false);
	}
}