package com.oyou.bible.pdf;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import com.lowagie.text.Chapter;
import com.lowagie.text.Chunk;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Section;
import com.oyou.bible.model.Book;
import com.oyou.bible.model.CNBook;
import com.oyou.bible.model.CNLine;
import com.oyou.bible.model.Line;
import com.oyou.bible.reader.BBEBooksReader;
import com.oyou.bible.reader.BBELinesReader;
import com.oyou.bible.reader.BookReader;
import com.oyou.bible.reader.KJVLinesReader;
import com.oyou.bible.reader.LineReader;
import com.oyou.bible.util.BibleConstants;
import com.oyou.common.util.DebugHelper;

public class CNBBEPdfWriter extends CNPdfWriter {
	protected Hashtable<String, Book> bbeBooks = new Hashtable<String, Book>();
	protected Hashtable<String, Line> bbeLines = new Hashtable<String, Line>();
	protected Hashtable<String, Line> kjvLines = new Hashtable<String, Line>();
	private static final String BBE = "[bbe]";
	private static final String KJV = "[kjv]";
	private static final String HGB = "[hgb]";
	
	public CNBBEPdfWriter() {
		super();
		this.loadAllData();
	}

	public CNBBEPdfWriter(String cover, String footer) {
		super(cover, footer);
		this.loadAllData();
	}

	public CNBBEPdfWriter(String filename, String author, String cover, String footer) {
		super(filename, author, cover, footer);
		this.loadAllData();
	}

	/**
	 * Paragraph per chapter or section control by isSection, <br>
	 * the books inside the sectionBook need Paragraph per section.<br> 
	 */
	public void createContent() {
		Iterator it = lines.iterator();
		CNLine line = null;
		Line bbeLine = null;
		Line kjvLine = null;
		CNBook book = null;
		Book bbeBook = null;
		String savedBook = "";
		int savedChapter = 0;
		String currentBook = "";
		int currentChapter = 0;
		Chapter pdfBook = null;
		Section pdfChapter = null;
		boolean bookMarkOpen = false;
		boolean savedMode = this.isSection;
		boolean currentMode = this.isSection;
		StringBuffer sb = new StringBuffer();

		while (it.hasNext()) {
			line = (CNLine) it.next();
			bbeLine = (Line) bbeLines.get(line.getKey());
			if (bbeLine == null) {
				log.error("bbeLine is null " + DebugHelper.getJSONString(line));
			}
			kjvLine = (Line) kjvLines.get(line.getKey());
			if (kjvLine == null) {
				log.error("kjvLine is null " + DebugHelper.getJSONString(line));
			}
			currentBook = line.getBook();
			currentChapter = line.getChapter();
			currentMode = this.isSectionBook(currentBook);
			if (!currentBook.equals(savedBook)) {
				if (pdfBook != null) {
					try {
						if (!savedMode) {
							if (sb.length() > 0) {
								Paragraph pdfContent = new Paragraph(sb.toString(), font12);
								pdfChapter.add(pdfContent);
							}
							sb = new StringBuffer();
						}
						document.add(Chunk.NEXTPAGE);
						document.add(pdfBook);
						pdfBook = null;
						pdfChapter = null;
					} catch (DocumentException de) {
						log.error(de.getMessage());
					}
				}
				book = (CNBook)books.get(Integer.toString(line.getId()));
				bbeBook = (Book)bbeBooks.get(Integer.toString(line.getId()));
				if (book == null) {
					log.error("Book is null " + line.getId());
				}
				if (bbeBook == null) {
					log.error("bbeBook is null " + line.getId());
				}
				document.resetHeader();
				document.setHeader(this.getHeader(book.getName()+" "+bbeBook.getName()));
				Paragraph bookTitle = new Paragraph(book.getName()+" "+bbeBook.getName(), font8);
				pdfBook = new Chapter(bookTitle, book.getIndex());
				if (bookMarkOpen) {
					pdfBook.setBookmarkOpen(true);
					bookMarkOpen = false;
				} else {
					pdfBook.setBookmarkOpen(false);
				}
				Paragraph chapterTitle = new Paragraph(Integer.toString(line.getChapter())+" "+CNWords.CHAPTER, font8);
				pdfChapter = pdfBook.addSection(chapterTitle, 0);
				if (!savedMode) {
					sb.append(line.getChapter() + BibleConstants.getInstance().CHAPTER_SECTION_SEPARATOR + line.getSection() + " " + line.getContent() + " ");
				} else {
					Paragraph pdfContent;
					if (bbeLine != null) {
						pdfContent = new Paragraph(bbeLine.getChapter() + BibleConstants.getInstance().CHAPTER_SECTION_SEPARATOR + bbeLine.getSection() + BBE + " " + bbeLine.getContent(), font12);
						pdfChapter.add(pdfContent);
					}
					if (kjvLine != null) {
						pdfContent = new Paragraph(kjvLine.getChapter() + BibleConstants.getInstance().CHAPTER_SECTION_SEPARATOR + kjvLine.getSection() + KJV + " " + kjvLine.getContent(), font12);
						pdfChapter.add(pdfContent);
					}
					pdfContent = new Paragraph(line.getChapter() + BibleConstants.getInstance().CHAPTER_SECTION_SEPARATOR + line.getSection() + HGB + " " + line.getContent(), font12);
					pdfChapter.add(pdfContent);
				}
				savedMode = currentMode;	
				savedBook = currentBook;
				savedChapter = currentChapter;
			} else {
				if (currentChapter != savedChapter) {
					if (!savedMode) {
						if (sb.length() > 0) {
							Paragraph pdfContent = new Paragraph(sb.toString(), font12);
							pdfChapter.add(pdfContent);
						} 
						sb = new StringBuffer();
					} 
					Paragraph chapterTitle = new Paragraph(Integer.toString(line.getChapter())+" "+CNWords.CHAPTER, font8);
					pdfChapter = pdfBook.addSection(chapterTitle, 0);
					savedChapter = currentChapter;
				}
				if (!savedMode) {
					sb.append(line.getChapter() + BibleConstants.getInstance().CHAPTER_SECTION_SEPARATOR + line.getSection() + " " + line.getContent() + " ");
				} else {
					Paragraph pdfContent;
					if (bbeLine != null) {
						pdfContent = new Paragraph(bbeLine.getChapter() + BibleConstants.getInstance().CHAPTER_SECTION_SEPARATOR + bbeLine.getSection() + BBE + " " + bbeLine.getContent(), font12);
						pdfChapter.add(pdfContent);
					}
					if (kjvLine != null) {
						pdfContent = new Paragraph(kjvLine.getChapter() + BibleConstants.getInstance().CHAPTER_SECTION_SEPARATOR + kjvLine.getSection() + KJV + " " + kjvLine.getContent(), font12);
						pdfChapter.add(pdfContent);
					}
					pdfContent = new Paragraph(line.getChapter() + BibleConstants.getInstance().CHAPTER_SECTION_SEPARATOR + line.getSection() + HGB + " " + line.getContent(), font12);
					pdfChapter.add(pdfContent);
				}
			}
		}
		if (pdfBook != null) {
			try {
				if (!savedMode) {
					if (sb.length() > 0) {
						Paragraph pdfContent = new Paragraph(sb.toString(), font12);
						pdfChapter.add(pdfContent);
					}
					sb = new StringBuffer();
				}
				document.add(Chunk.NEXTPAGE);
				document.add(pdfBook);
			} catch (DocumentException de) {
				log.error(de.getMessage());
			}
		}
		close();
	}
	
	public void loadAllData() {
		Vector<Line> tmpLines = new Vector<Line>();
		isSection = true; 
		//1. load bbe
		LineReader lineReader = new BBELinesReader();
		BookReader bookReader = new BBEBooksReader();
		tmpLines.addAll(this.loadLines(lineReader));
		bbeBooks.putAll(this.loadBooks(bookReader));
		lineReader.close();
		bookReader.close();
		Iterator it = tmpLines.iterator();
		while (it.hasNext()) {
			Line line = (Line)it.next();
			bbeLines.put(line.getKey(), line);
		}
		//2. load kjv
		lineReader = new KJVLinesReader();
		tmpLines.clear();
		tmpLines.addAll(this.loadLines(lineReader));
		lineReader.close();
		it = tmpLines.iterator();
		while (it.hasNext()) {
			Line line = (Line)it.next();
			kjvLines.put(line.getKey(), line);
		}
	}

}
