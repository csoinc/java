package com.oyou.common.reader;


/**
 * UTF8 reader base class
 * @author	Owen Ou
 *
 */
abstract public class UTF8Reader extends Reader {

	public UTF8Reader() {
		super();
		this.setEncoder(Encoder.UTF8);
	}

	public UTF8Reader(String filename) {
		super(filename, Encoder.UTF8);
	}
	
}
