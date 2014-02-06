package com.oyou.gwt.banner.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @author	Owen Ou
 * @version $Id: IsEntity.java,v 1.1 2008/06/29 14:24:40 oyou Exp $
 * Oct 17, 2007
 */
public abstract class IsEntity implements IsSerializable {

  private String id;

  public IsEntity() {
  }

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

}
