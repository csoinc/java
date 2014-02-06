package com.oyou.common;

import java.util.Calendar;

public class RecoverableException extends Exception {
	static final long serialVersionUID = Calendar.getInstance().getTimeInMillis(); 

	public RecoverableException(){
		super();
	}

	public RecoverableException(String errMessage) {
		super(errMessage);
	}

}
