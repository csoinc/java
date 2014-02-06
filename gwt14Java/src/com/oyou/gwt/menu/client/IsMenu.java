package com.oyou.gwt.menu.client;

/**
 * 
 * @author	Owen Ou
 * @version $Id: IsMenu.java,v 1.1 2008/06/29 14:24:37 oyou Exp $
 * @since Nov 28, 2007
 */
public final class IsMenu extends Entity {
	private String name;

	private String nameCN;

	public String getName() {
		return name;
	}

	public String getNameCN() {
		return nameCN;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameCN(String nameCN) {
		this.nameCN = nameCN;
	}

}
