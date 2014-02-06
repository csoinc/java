package com.oyou.domain.blog.orm;


import com.oyou.common.Entity;
import com.oyou.common.util.StringHelper;

public class BlogUserType extends Entity {
	static final long serialVersionUID = 1;
	public static Long ADMIN = Long.valueOf("1");
	public static Long LEADER = Long.valueOf("2");
	public static Long USER = Long.valueOf("3");
	public static Long GUEST = Long.valueOf("4");
	public static String ADMIN_ROLE = "Admin";
	public static String LEADER_ROLE = "Leader";
	public static String USER_ROLE = "User";
	public static String GUEST_ROLE = "Guest";

	private String name;
	private String nameCN;
	private String nameTW;

	private boolean status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public static Long getTypeFromRole(String role) {
		if (StringHelper.isNotEmpty(role)) {
			if (ADMIN_ROLE.equalsIgnoreCase(role)) {
				return ADMIN;
			} else if (LEADER_ROLE.equalsIgnoreCase(role)) {
				return LEADER;
			} else if (USER_ROLE.equalsIgnoreCase(role)) {
				return USER;
			} else if (GUEST_ROLE.equalsIgnoreCase(role)) {
				return GUEST;
			}
		} 
		return null;
	}

	public String getNameCN() {
		return nameCN;
	}

	public void setNameCN(String nameCN) {
		this.nameCN = nameCN;
	}

	public String getNameTW() {
		return nameTW;
	}

	public void setNameTW(String nameTW) {
		this.nameTW = nameTW;
	}


}
