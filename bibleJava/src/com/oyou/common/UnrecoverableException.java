package com.oyou.common;

import java.util.Calendar;

public class UnrecoverableException extends RuntimeException {
	static final long serialVersionUID = Calendar.getInstance().getTimeInMillis(); 
	
	public UnrecoverableException() {
		super();
	}

	public UnrecoverableException(String message) {
		super(message);
	}

	public UnrecoverableException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnrecoverableException(Throwable cause) {
		super(cause);
	}
	
}
