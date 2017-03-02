package com.oyou.bible.model;

import java.io.UnsupportedEncodingException;

import com.oyou.common.reader.Encoder;

/**
 * @author	Owen Ou
 * May 6, 2007
 */
public class TWBook extends Book {
	static final long serialVersionUID = 1; 

	public String getEncodedName() {
		String b5Name = null;
		try {
			b5Name = new String((super.getName().getBytes(Encoder.BIG5)));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return b5Name;
	}

}
