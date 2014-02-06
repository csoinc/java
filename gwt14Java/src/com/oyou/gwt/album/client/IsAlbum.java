package com.oyou.gwt.album.client;

/**
 * 
 * @author	Owen Ou
 * @version $Id: IsAlbum.java,v 1.1 2008/06/29 14:24:23 oyou Exp $
 * @since Nov 26, 2007
 */
public final class IsAlbum extends Entity {
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
