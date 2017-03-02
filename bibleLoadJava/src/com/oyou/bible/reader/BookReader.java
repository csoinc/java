package com.oyou.bible.reader;

import com.oyou.bible.model.Book;

/**
 * book reader interface
 * @author	Owen Ou
 *
 */
public interface BookReader {

	public void close();
	
	public Book getNextBook();
	
}
