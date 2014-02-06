package com.oyou.gwt.blog.client;

/**
 * 
 * @author	Owen Ou
 * @version $Id: IsGroup.java,v 1.1 2008/06/29 14:23:46 oyou Exp $
 * @since Dec 5, 2007
 */
public final class IsGroup extends Entity {
	private String name;

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
