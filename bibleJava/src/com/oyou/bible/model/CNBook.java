package com.oyou.bible.model;

import java.io.UnsupportedEncodingException;

import com.oyou.common.reader.Encoder;

/**
 * @author	Owen Ou
 * May 6, 2007
 */
public class CNBook extends Book {
	static final long serialVersionUID = 1; 

	public String getEncodedName() {
		String gbName = null;
		try {
			gbName = new String((super.getName().getBytes(Encoder.GB2312)));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return gbName;
	}

}
