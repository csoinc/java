package com.oyou.domain.blog.orm;

import com.oyou.common.Entity;

public class BlogLanguageType extends Entity {
	static final long serialVersionUID = 1;
	public static final Long CANTONESE = Long.valueOf("1");
	public static final Long ENGLISH = Long.valueOf("2");
	public static final Long MANDARIN = Long.valueOf("3");
	
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
