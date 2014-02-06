package com.oyou.common.domain;

import java.io.Serializable;

public class Pagination implements Serializable {
	private int number; // current page number
	private int size; // page size, rows/lines
	private int total; // total pages

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
