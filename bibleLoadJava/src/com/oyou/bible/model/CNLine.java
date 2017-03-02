package com.oyou.bible.model;

import java.io.UnsupportedEncodingException;

import com.oyou.common.reader.Encoder;

/**
 * @author	Owen Ou
 * May 6, 2007
 */
public class CNLine extends Line {
	static final long serialVersionUID = 1; 

	public String getEncodedContent() {
		String gbContent = null;
		try {
			gbContent = new String((super.getContent().getBytes(Encoder.GB2312)));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return gbContent;
	}

}
