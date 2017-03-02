package com.oyou.bible.reader;

import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import com.oyou.bible.model.Book;
import com.oyou.bible.util.BibleConstants;
import com.oyou.common.reader.ISO88591Reader;

public class BBEBooksReader extends ISO88591Reader implements BookReader {

	public BBEBooksReader() {
		super();
		this.setFilename(BibleConstants.getInstance().getProperty(BibleConstants.BBE_INDEX_FILE));
		this.setBufferedReader();
	}

	public BBEBooksReader(String filename) {
		super(filename);
	}
	
	public Book getNextBook() {
		Book book = new Book();
		String buffer = this.readLine();

		StringTokenizer st;
		if (StringUtils.isNotEmpty(buffer)) {
			st = new StringTokenizer(buffer, " ");
			if (st.countTokens() < 4) {
				buffer = this.readLine();
				st = new StringTokenizer(buffer, " ");
			}
			String token = null;
			if (st.hasMoreTokens()) {
				token = st.nextToken();
				if (Integer.parseInt(token) == 0) {
					book.setNewTestament(false);
				} else {
					book.setNewTestament(true);
				}
			}
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
