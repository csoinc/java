package com.oyou.gwt.album.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @author	Owen Ou
 * @version $Id: Entity.java,v 1.1 2008/06/29 14:24:25 oyou Exp $
 * @since Nov 14, 2007
 */
public abstract class Entity implements IsSerializable {

	private String id;

	public Entity() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
