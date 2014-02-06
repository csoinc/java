package com.oyou.bible.pdf;

import com.oyou.bible.reader.BBEBooksReader;
import com.oyou.bible.reader.BookReader;
import com.oyou.bible.reader.KJVLinesReader;
import com.oyou.bible.reader.LineReader;

public class KJVPdfWriter extends BBEPdfWriter {
	
	public KJVPdfWriter() {
		super();
	}

	public KJVPdfWriter(String cover, String footer) {
		super(cover, footer);
	}

	public KJVPdfWriter(String filename, String author, String cover, String footer) {
		super(filename, author, cover, footer);
	}

	public void loadData() {
		LineReader lineReader = new KJVLinesReader();
		BookReader bookReader = new BBEBooksReader();
		lines.addAll(this.loadLines(lineReader));
		books.putAll(this.loadBooks(bookReader));
		lineReader.close();
		bookReader.close();
	}
	
}
