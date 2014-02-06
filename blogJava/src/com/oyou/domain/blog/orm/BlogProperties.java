package com.oyou.domain.blog.orm;

import com.oyou.common.Entity;

public class BlogProperties extends Entity {
	static final long serialVersionUID = 1;

	private String key;
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
