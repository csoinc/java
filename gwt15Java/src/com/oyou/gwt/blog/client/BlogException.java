package com.oyou.gwt.blog.client;

import com.google.gwt.user.client.rpc.SerializableException;

/**
 * 
 * @author	Owen Ou
 * @version $Id: BlogException.java,v 1.1 2008/06/29 14:23:46 oyou Exp $
 * @since Dec 5, 2007
 */
public class BlogException extends SerializableException {
	static final long serialVersionUID = 1;

	public BlogException() {
		super();
	}
	
	public BlogException(String msg) {
		super(msg);
	}

}
