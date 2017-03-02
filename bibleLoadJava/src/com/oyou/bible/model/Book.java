package com.oyou.bible.model;

import java.io.Serializable;

public class Book implements Serializable {
	static final long serialVersionUID = 1; 
	private boolean newTestament;
	private int id; //id per book
	private int index; //index per book
	private String code;
	private String name;

	public String getCode() {
		return code;
	}

	public int getId() {
		return id;
	}

	public int getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	public boolean isNewTestament() {
		return newTestament;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNewTestament(boolean newTestament) {
		this.newTestament = newTestament;
	}
}
