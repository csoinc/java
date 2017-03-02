package com.oyou.bible.reader;

import java.util.StringTokenizer;

import com.oyou.bible.model.Book;
import com.oyou.bible.util.BibleConstants;
import com.oyou.common.reader.ISO88591Reader;

public class NIVBooksReader extends ISO88591Reader implements BookReader {

	public NIVBooksReader() {
		super();
		this.setFilename(BibleConstants.getInstance().getProperty(BibleConstants.NIV_INDEX_FILE));
		this.setBufferedReader();
	}

	public NIVBooksReader(String filename) {
		super(filename);
	}
	
	public Book getNextBook() {
		Book book = new Book();
		String buffer;
		if ((buffer = this.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(buffer, " ");
			String token = null;
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
				token = st.nextToken();
			}
			while (st.hasMoreTokens()) {
				token = token + " " + st.nextToken();
			} 
			book.setName(token);
		}
		else {
			book = null;
		}
		return book;
	}

}
