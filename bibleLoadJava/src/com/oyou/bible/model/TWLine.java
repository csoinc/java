package com.oyou.bible.model;

import java.io.UnsupportedEncodingException;

import com.oyou.common.reader.Encoder;

/**
 * @author	Owen Ou
 * May 6, 2007
 */
public class TWLine extends Line {
	static final long serialVersionUID = 1; 

	public String getEncodedContent() {
		String b5Content = null;
		try {
			b5Content = new String((super.getContent().getBytes(Encoder.BIG5)));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return b5Content;
	}

}
