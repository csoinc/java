package com.oyou.web.blog.security;

public class PasswordForm extends UserForm {
	static final long serialVersionUID = 1;
	private String confirmPassword = "confirmPassword";

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void reset() {
		super.reset();
		setConfirmPassword(null);
	}	
	
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	

}
