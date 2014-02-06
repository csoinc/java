package com.oyou.bible.writer;

import com.oyou.bible.util.BibleConstants;
/**
 * Create books.txt that include all the books code, then manual input 
 * the book name based on the code
 * 
 * @author	Owen Ou
 * 
 */
public class IndexFileWriter extends Writer {

	public IndexFileWriter() {
		super(BibleConstants.getInstance().getProperty(BibleConstants.INDEX_FILE));
	}

}
