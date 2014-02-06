package fr.improve.struts.taglib.layout.util;

/**
 * @author: Jean-Noël Ribette
 */
public class MenuItem {
	protected String key;
	protected String link;
	protected String image;
	protected Menu subMenu;

public MenuItem(String key, String link, Menu subMenu) {
	this.key = key;
	this.link = link;
	this.subMenu = subMenu;
}
public MenuItem(String key, String link, String image, Menu subMenu) {
	this.key = key;
	this.link = link;
	this.subMenu = subMenu;
	this.image = image;
}
	public String getImage() {
		return image;
	}
public String getKey() {
	return key;
}
public String getLink() {
	return link;
}
public Menu getSubMenu() {
	return subMenu;
}
}
