package com.oyou.domain.blog.orm;

import com.oyou.common.Entity;

public class BlogGroupType extends Entity {
	static final long serialVersionUID = 1;
	public static final Long GROUP_HOT = Long.valueOf("0");
	public static final Long GROUP_ALBUM = Long.valueOf("1");
	public static final Long GROUP_CHURCH = Long.valueOf("3");
	public static final Long GROUP_BUSINESS = Long.valueOf("3");
	
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
