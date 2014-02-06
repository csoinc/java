package com.oyou.common.reader;


/**
 * GB reader base class
 * @author	Owen Ou
 *
 */
abstract public class GB2312Reader extends Reader {

	public GB2312Reader() {
		super();
		this.setEncoder(Encoder.GB2312);
	}

	public GB2312Reader(String filename) {
		super(filename, Encoder.GB2312);
	}
	
}
