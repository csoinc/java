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
import com.oyou.bible.model.Line;
import com.oyou.bible.reader.BBEBooksReader;
import com.oyou.bible.reader.BBELinesReader;
import com.oyou.bible.reader.BookReader;
import com.oyou.bible.reader.LineReader;
import com.oyou.bible.util.BibleConstants;

public class BBEPdfWriter extends PdfGenericWriter {
	protected Vector<Line> lines = new Vector<Line>();
	protected Hashtable<String, Book> books = new Hashtable<String, Book>();

	public BBEPdfWriter() {
		super();
		setFonts();
		this.loadData();
	}

	public BBEPdfWriter(String cover, String footer) {
		super();
		setFonts();
		this.setCover(cover);
		this.setFooter(footer);
		this.loadData();
	}

	public BBEPdfWriter(String filename, String author, String cover, String footer) {
		super(filename, author);
		setFonts();
		this.setCover(cover);
		this.setFooter(footer);
		this.loadData();
	}

	/**
	 * Paragraph per chapter or section control by isSection, <br>
	 * the books inside the sectionBook need Paragraph per section.<br> 
	 */
	public void createContent() {
		Iterator it = lines.iterator();
		Line line = null;
		Book book = null;
		String savedBook = "";
		int savedChapter = 0;
		String currentBook = "";
		int currentChapter = 0;
		Chapter pdfBook = null;
		Section pdfChapter = null;
		boolean bookMarkOpen = false;
		StringBuffer sb = new StringBuffer();
		boolean currentMode = this.isSection;
		boolean savedMode = this.isSection;
		while (it.hasNext()) {
			line = (Line) it.next();
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
				book = (Book) books.get(Integer.toString(line.getId()));
				if (book == null)
					log.error("Book is null " + line.getId());
				document.resetHeader();
				document.setHeader(this.getHeader(book.getName()));
				Paragraph bookTitle = new Paragraph(book.getName(), font8);
				pdfBook = new Chapter(bookTitle, book.getIndex());
				if (bookMarkOpen) {
					pdfBook.setBookmarkOpen(true);
					bookMarkOpen = false;
				} else {
					pdfBook.setBookmarkOpen(false);
				}
				Paragraph chapterTitle = new Paragraph(Integer.toString(line.getChapter()) + " " + Words.CHAPTER, font8);
				pdfChapter = pdfBook.addSection(chapterTitle, 0);
				if (!savedMode) {
					sb.append(line.getChapter() + BibleConstants.getInstance().CHAPTER_SECTION_SEPARATOR + line.getSection() + " " + line.getContent() + " ");
				} else {
					Paragraph pdfContent = new Paragraph(line.getChapter() + BibleConstants.getInstance().CHAPTER_SECTION_SEPARATOR + line.getSection() + " "
							+ line.getContent(), font12);
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
					Paragraph chapterTitle = new Paragraph(Integer.toString(line.getChapter()) + " " + Words.CHAPTER, font8);
					pdfChapter = pdfBook.addSection(chapterTitle, 0);
					savedChapter = currentChapter;
				}
				if (!savedMode) {
					sb.append(line.getChapter() + BibleConstants.getInstance().CHAPTER_SECTION_SEPARATOR + line.getSection() + " " + line.getContent() + " ");
				} else {
					Paragraph pdfContent = new Paragraph(line.getChapter() + BibleConstants.getInstance().CHAPTER_SECTION_SEPARATOR + line.getSection() + " "
							+ line.getContent(), font12);
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

	public void createContentSlideShow() {
		this.font12 = FontFactory.getInstance().getTimeRomanSlideShow();
		this.createContent();
	}
	
	public void createCover() {
		try {
			document.add(this.getCoverImage(this.cover));
			document.resetPageCount();
			document.setHeader(this.getHeader(""));
			document.setFooter(this.getFooter(this.footer));
		} catch (DocumentException de) {
			log.error(de.getMessage());
		}
	}

	public void loadData() {
		LineReader lineReader = new BBELinesReader();
		BookReader bookReader = new BBEBooksReader();
		lines.addAll(this.loadLines(lineReader));
		books.putAll(this.loadBooks(bookReader));
		lineReader.close();
		bookReader.close();
	}

	public void setFonts() {
		font12 = FontFactory.getInstance().getTimeRoman12();
		font10 = FontFactory.getInstance().getTimeRoman10();
		font8 = FontFactory.getInstance().getTimeRoman8();
	}
}
