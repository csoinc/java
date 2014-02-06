package com.oyou.domain.blog.orm;

import java.sql.Timestamp;
import java.util.Collection;

import com.oyou.common.Entity;
import com.oyou.common.util.StringHelper;

/**
 * 
 * @author	Owen Ou
 * @since 	Nov 3, 2007
 * @version	$Id: BlogUser.java,v 1.1 2008/06/29 14:36:52 oyou Exp $
 */
public class BlogUser extends Entity {
	static final long serialVersionUID = 1;
	private String loginName;
	private String loginPassword;
	private String email;
	private String firstname;
	private String lastname;
	private String nickname;
	private String phoneHome;
	private String phoneCell;
	private String unit;
	private String street;
	private String city;
	private String province;
	private String country;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Timestamp accessTime;
	private boolean status;
	private BlogUserType blogUserType;
	private Collection<BlogGroup> blogGroups;
	private BlogUserStatistic blogUserStatistic;

	public Timestamp getAccessTime() {
		return accessTime;
	}

	public Collection<BlogGroup> getBlogGroups() {
		return blogGroups;
	}

	public BlogUserType getBlogUserType() {
		return blogUserType;
	}

	public String getCity() {
		return StringHelper.filterXSS(city);
	}

	public String getCountry() {
		return StringHelper.filterXSS(country);
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public String getEmail() {
		return StringHelper.filterXSS(email);
	}

	public String getFirstname() {
		return StringHelper.filterXSS(firstname);
	}

	public String getLastname() {
		return StringHelper.filterXSS(lastname);
	}

	public String getLoginName() {
		return StringHelper.filterXSS(loginName);
	}

	public String getLoginPassword() {
		return StringHelper.filterXSS(loginPassword);
	}

	public String getNickname() {
		return nickname;
	}

	public String getPhoneCell() {
		return StringHelper.filterXSS(phoneCell);
	}

	public String getPhoneHome() {
		return StringHelper.filterXSS(phoneHome);
	}

	public String getProvince() {
		return StringHelper.filterXSS(province);
	}

	public String getStreet() {
		return StringHelper.filterXSS(street);
	}

	public String getUnit() {
		return StringHelper.filterXSS(unit);
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public boolean isStatus() {
		return status;
	}

	public void setAccessTime(Timestamp accessTime) {
		this.accessTime = accessTime;
	}

	public void setBlogGroups(Collection<BlogGroup> blogGroups) {
		this.blogGroups = blogGroups;
	}

	public void setBlogUserType(BlogUserType blogUserType) {
		this.blogUserType = blogUserType;
	}

	public void setCity(String city) {
		this.city = StringHelper.filterXSS(city);
	}

	public void setCountry(String country) {
		this.country = StringHelper.filterXSS(country);
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public void setEmail(String email) {
		this.email = StringHelper.filterXSS(email);
	}

	public void setFirstname(String firstname) {
		this.firstname = StringHelper.filterXSS(firstname);
	}

	public void setLastname(String lastname) {
		this.lastname = StringHelper.filterXSS(lastname);
	}

	public void setLoginName(String loginName) {
		this.loginName = StringHelper.filterXSS(loginName);
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = StringHelper.filterXSS(loginPassword);
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setPhoneCell(String phoneCell) {
		this.phoneCell = StringHelper.filterXSS(phoneCell);
	}

	public void setPhoneHome(String phoneHome) {
		this.phoneHome = StringHelper.filterXSS(phoneHome);
	}

	public void setProvince(String province) {
		this.province = StringHelper.filterXSS(province);
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setStreet(String street) {
		this.street = StringHelper.filterXSS(street);
	}

	public void setUnit(String unit) {
		this.unit = StringHelper.filterXSS(unit);
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public BlogUserStatistic getBlogUserStatistic() {
		return blogUserStatistic;
	}

	public void setBlogUserStatistic(BlogUserStatistic blogUserStatistic) {
		this.blogUserStatistic = blogUserStatistic;
	}
}
