package com.oyou.web.blog.security;

public class ForgotPasswordForm extends UserForm {
	static final long serialVersionUID = 1;
	private String phoneHome;
	private String email = "your@email.com";

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneHome() {
		return phoneHome;
	}

	public void reset() {
		setPhoneHome(null);
		setEmail(null);
	}	
	
	public void setPhoneHome(String phoneHome) {
		this.phoneHome = phoneHome;
	}
}
