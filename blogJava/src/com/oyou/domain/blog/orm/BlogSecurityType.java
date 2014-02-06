package com.oyou.domain.blog.orm;

import com.oyou.common.Entity;

public class BlogSecurityType extends Entity {
	static final long serialVersionUID = 1;
	public static Long ADMIN = Long.valueOf("1");
	public static Long LEADER = Long.valueOf("2");
	public static Long USER = Long.valueOf("3");
	public static Long GUEST = Long.valueOf("4");
	
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
