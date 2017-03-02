package com.oyou.bible.reader;

import java.util.StringTokenizer;


import com.oyou.bible.model.Book;
import com.oyou.bible.model.TWBook;
import com.oyou.bible.util.BibleConstants;
import com.oyou.common.reader.BIG5Reader;

public class TWBooksReader extends BIG5Reader implements BookReader {

	public TWBooksReader() {
		super();
		this.setFilename(BibleConstants.getInstance().getProperty(BibleConstants.TW_INDEX_FILE));
		this.setBufferedReader();
	}

	public TWBooksReader(String filename) {
		super(filename);
	}
	
	public Book getNextBook() {
		TWBook book = new TWBook();
		String buffer;
		if ((buffer = this.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(buffer, " ");
			String token = null;
//			if (st.hasMoreTokens()) {
//				token = st.nextToken();
//				if (Integer.parseInt(token) == 0) {
//					book.setNewTestament(false);
//				} else {
//					book.setNewTestament(true);
//				}
//			}
			if (st.hasMoreTokens()) {
				token = st.nextToken();
				book.setId(Integer.parseInt(token));
			}
			if (st.hasMoreTokens()) {
				token = st.nextToken();
				book.setIndex(Integer.parseInt(token));
			}
			if (st.hasMoreTokens()) {
				token = st.nextToken();
				book.setCode(token);
			}
			if (st.hasMoreTokens()) {
				token = st.nextToken().trim();
			}	
			while (st.hasMoreTokens()) {
				token += st.nextToken();
			} 
			book.setName(token);
		}
		else {
			book = null;
		}
		return book;
	}

}
