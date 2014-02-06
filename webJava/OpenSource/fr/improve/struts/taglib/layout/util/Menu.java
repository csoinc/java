package fr.improve.struts.taglib.layout.util;

import java.util.Vector;

/**
 * @author: Jean-Noël Ribette
 */
public class Menu {
	protected Vector items = new Vector();
public void addItem(MenuItem item) {
	items.add(item);
}
public void addItem(String key, String link, Menu subMenu) {
	items.add(new MenuItem(key, link, subMenu));		
}
public Object[] getItems() {
	return items.toArray();
}
public void insertItem(Menu item, int index) {
	items.insertElementAt(item,index);
}
public void removeItem(String key) {
	for (int i=0;i<items.size();i++) {
		if (((MenuItem)items.get(i)).getKey().equals(key)) items.remove(i);
	}
}
}
