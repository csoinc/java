package com.oyou.domain.blog.orm;

import com.oyou.common.Entity;

public class BlogMessageType extends Entity {
	static final long serialVersionUID = 1;
	public static Long IMAGE = Long.valueOf("1");
	public static Long MUSIC = Long.valueOf("2");
	public static Long LINK = Long.valueOf("3");
	public static Long TEXT = Long.valueOf("4");
	public static Long WORD = Long.valueOf("5");
	public static Long HTML = Long.valueOf("6");
	public static Long FILE = Long.valueOf("7");
	public static Long PLAIN = Long.valueOf("8");
	
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
