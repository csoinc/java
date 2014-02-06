package com.oyou.web.blog.security;


public class RegisterForm extends UserForm {
	static final long serialVersionUID = 1;
	private String email = "your@email.com";
	private String firstname = "firstname";
	private String lastname = "lastname";
	private String nickname = "nickname";
	private String phoneHome = "416-1234567";

	private String phoneCell;
	private String unit;
	private String street;
	private String city;
	private String province;
	private String country;

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getNickname() {
		return nickname;
	}

	public String getPhoneCell() {
		return phoneCell;
	}

	public String getPhoneHome() {
		return phoneHome;
	}

	public String getProvince() {
		return province;
	}

	public String getStreet() {
		return street;
	}

	public String getUnit() {
		return unit;
	}

	public void reset() {
		super.reset();
		setEmail(null);
		setPhoneHome(null);
		setFirstname(null);
		setLastname(null);
		setNickname(null);
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setPhoneCell(String phoneCell) {
		this.phoneCell = phoneCell;
	}

	public void setPhoneHome(String phoneHome) {
		this.phoneHome = phoneHome;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
