package com.oyou.gwt.console.client;

import com.google.gwt.user.client.rpc.SerializableException;

public class ConsoleException extends SerializableException {
	static final long serialVersionUID = 1;

	public ConsoleException() {
		super();
	}
	
	public ConsoleException(String msg) {
		super(msg);
	}

}
