package com.oyou.gwt.console.client;

public final class IsError extends Entity {
	public static final String SUCCESS = "0";
	public static final String ERROR = "-1";

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
