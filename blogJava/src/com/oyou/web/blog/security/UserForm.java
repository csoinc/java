package com.oyou.web.blog.security;

import org.apache.struts.validator.ValidatorForm;

public class UserForm extends ValidatorForm {
	static final long serialVersionUID = 1;
	private String loginName = "loginName";
	private String loginPassword = "loginPassword";

	public String getLoginName() {
		return loginName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void reset() {
		setLoginName(null);
		setLoginPassword(null);
	}	

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
}
