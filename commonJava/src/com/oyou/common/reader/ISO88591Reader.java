package com.oyou.common.reader;

/**
 * EN reader base class
 * @author	Owen Ou
 *
 */
abstract public class ISO88591Reader extends Reader {

	public ISO88591Reader() {
		super();
		this.setEncoder(Encoder.ISO88591);
	}

	public ISO88591Reader(String filename) {
		super(filename, Encoder.ISO88591);
	}
	
}
