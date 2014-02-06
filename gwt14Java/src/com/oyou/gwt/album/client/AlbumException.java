package com.oyou.gwt.album.client;

import com.google.gwt.user.client.rpc.SerializableException;

public class AlbumException extends SerializableException {
	static final long serialVersionUID = 1;

	public AlbumException() {
		super();
	}
	
	public AlbumException(String msg) {
		super(msg);
	}

}
