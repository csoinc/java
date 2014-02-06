package com.oyou.bible.writer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.bible.model.CNLine;
import com.oyou.bible.reader.CNLinesReader;
import com.oyou.bible.reader.LineReader;

public class IndexFile {
	private static final Log log = LogFactory.getLog(IndexFile.class);

	public void createIndexFile() {
		log.info(">>createIndexFile()");
		LineReader reader = new CNLinesReader();
		IndexFileWriter writer = new IndexFileWriter();
		CNLine line = null;
		String savedBook = "";
		String book = "";
		int index = 1;
		int id = 1;
		while ((line = (CNLine)reader.getNextLine()) != null) {
			book = line.getBook();
			if (!book.equals(savedBook)) {
				if (book.equals("Mat")) index = 1;
				writer.writeLine(id+" "+index+" "+book);
				savedBook = book;
				index++;
				id++;
			} 
		}
		reader.close();
		writer.close();
		log.info("<<createIndexFile()");
	}
	
}
