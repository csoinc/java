package com.oyou.bible.util;

import java.util.Hashtable;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.bible.model.Book;
import com.oyou.bible.reader.BookReader;
import com.oyou.bible.reader.NIVBooksReader;

public class NIVInputHelper {
	private static final Log log = LogFactory.getLog(NIVInputHelper.class);
	private static Hashtable<String,Book> books = new Hashtable<String,Book>();
	static {
		BookReader bookReader = new NIVBooksReader();
		Book book = null;
		while ((book = bookReader.getNextBook()) != null) {
			books.put(book.getName(), book);
		}
		bookReader.close();
	}
	
	static public Book getBookByName(String name) {
		log.debug(">>getBookByName: " + name);
		name = name.trim();
		Book book = books.get(name);
		return book;
	}

	static public boolean isBook(String name) {
		log.debug(">>getBookByName: " + name);
		name = name.trim();
		if (name.length() > 50) return false;
		Book book = books.get(name);
		if (book == null) return false;
		else return true;
	}
	
	static public String getDigit(String line) {
		StringBuffer sb = new StringBuffer();
		line = line.trim();
		if (line == null || line.length() == 0) return null;
		for (int i=0; i<line.length(); i++) {
			char c = line.charAt(i);
			if (Character.isDigit(c)) {
				sb.append(c);
			} else {
				break;
			}	
		}
		return sb.toString();
	}

	static public boolean isLine(String line) {
		boolean flag = false;
		line = line.trim();
		if (line == null || line.length() == 0) return false;
		char c = line.charAt(0);
		if (!Character.isWhitespace(c)) {
			flag = true;
		} 
		return flag;
	}

	static public boolean isDigitLine(String line) {
		boolean flag = false;
		line = line.trim();		
		if (line == null || line.length() == 0) return false;
		char c = line.charAt(0);
		if (Character.isDigit(c)) {
			flag = true;
		} 
		return flag;
	}
	
	static public boolean isLetterLine(String line) {
		boolean flag = false;
		line = line.trim();
		if (line == null || line.length() == 0) return false;
		char c = line.charAt(0);
		if (Character.isLetter(c) || '"' == c) {
			flag = true;
		} 
		return flag;
	}
	
	static public boolean isChapter(int currChapter, int currSeaction) {
		return false;
	}

    static public int getPsalmChapter(String line) {
		line = line.trim();
    	if (line != null && line.startsWith("PSALM")) {
    		int chapter = -1;
    		try {
        		StringTokenizer st = new StringTokenizer(line, " ");
        		String book = st.nextToken();
        		String chapterStr = st.nextToken();
    			chapter = Integer.parseInt(chapterStr);
    		} catch (Exception e) {
    			log.error("Error on get chapter from " + line + " " + e.getMessage());
    		}
    		return chapter;
    	} else {
    		return -1;
    	}
    }

    static public boolean isPsalmLine(String line) {
		line = line.trim();
    	if (line != null && line.startsWith("PSALM")) return true;
    	else return false;
    }	
    
}
