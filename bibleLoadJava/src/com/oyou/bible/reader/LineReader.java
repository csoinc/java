package com.oyou.bible.reader;

import com.oyou.bible.model.Line;

/**
 * reader interface
 * @author	Owen Ou
 *
 */
public interface LineReader {
	
	public void close();
	public Line getNextLine();
	
}
