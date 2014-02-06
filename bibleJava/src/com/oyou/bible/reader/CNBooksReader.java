package com.oyou.bible.reader;

import java.util.StringTokenizer;


import com.oyou.bible.model.Book;
import com.oyou.bible.model.CNBook;
import com.oyou.bible.util.BibleConstants;
import com.oyou.common.reader.GB2312Reader;

public class CNBooksReader extends GB2312Reader implements BookReader {

	public CNBooksReader() {
		super();
		this.setFilename(BibleConstants.getInstance().getProperty(BibleConstants.CN_INDEX_FILE));
		this.setBufferedReader();
	}

	public CNBooksReader(String filename) {
		super(filename);
	}
	
	public Book getNextBook() {
		CNBook book = new CNBook();
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
