package fr.improve.struts.taglib.layout.util;

import fr.improve.struts.taglib.layout.menu.MenuComponent;

/**
 * interface that should implement a menu or menuItem tag so that menuItems cab e added
 * @author: JeanNoël Ribette
 */
public interface MenuInterface {
	public void addItem(MenuComponent item);
}
