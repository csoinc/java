package com.oyou.common.reader;


/**
 * GB reader base class
 * @author	Owen Ou
 *
 */
abstract public class BIG5Reader extends Reader {

	public BIG5Reader() {
		super();
		this.setEncoder(Encoder.BIG5);
	}

	public BIG5Reader(String filename) {
		super(filename, Encoder.BIG5);
	}
	
}
