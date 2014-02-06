package com.oyou.domain.bible.orm;

import com.oyou.common.Entity;

public class BibleBook extends Entity {
	static final long serialVersionUID = 1;
	private Integer sequence;

	private Integer number;

	private Boolean testament;

	private String code;

	private String name;

	private String nameCN;

	private String nameTW;

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getNameCN() {
		return nameCN;
	}

	public String getNameTW() {
		return nameTW;
	}

	public Integer getNumber() {
		return number;
	}

	public Integer getSequence() {
		return sequence;
	}

	public Boolean getTestament() {
		return testament;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameCN(String nameCN) {
		this.nameCN = nameCN;
	}

	public void setNameTW(String nameTW) {
		this.nameTW = nameTW;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public void setTestament(Boolean testament) {
		this.testament = testament;
	}

}
