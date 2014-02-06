package com.oyou.bible.pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;
import com.oyou.bible.model.Book;
import com.oyou.bible.model.Line;
import com.oyou.bible.reader.BookReader;
import com.oyou.bible.reader.LineReader;
import com.oyou.bible.util.BibleConstants;

public class PdfGenericWriter {
	protected static final Log log = LogFactory.getLog(PdfGenericWriter.class);
	protected Document document;
	protected Font font12;
	protected Font font10;
	protected Font font8; 
	protected String[] sectionBook = {"Psm"};
	protected boolean isSection = BibleConstants.getInstance().SECTION_MODE;
	protected String pdfFilename;
	protected String pdfAuthor;
	protected String cover;
	protected String footer;
	
	public PdfGenericWriter() {
		this.setPdfFilename(BibleConstants.getInstance().getProperty(BibleConstants.BBE_PDF_FILE));
		this.setPdfAuthor(BibleConstants.getInstance().PDF_AUTHOR);
		try {
			document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(this.pdfFilename));
			document.addAuthor(this.pdfAuthor);
			document.addSubject("Holy Bible");
			document.addCreator("JAVA 1.5");
			document.addCreationDate();
			document.open();
		} catch (DocumentException de) {
			log.error(de.getMessage());
		} catch (IOException ioe) {
			log.error(ioe.getMessage());
		}
	}

	public PdfGenericWriter(String filename, String author) {
		this.setPdfFilename(filename);
		this.setPdfAuthor(author);
		try {
			document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(this.pdfFilename));
			document.addAuthor(this.pdfAuthor);
			document.addSubject("Holy Bible");
			document.addCreator("JAVA 1.5");
			document.addCreationDate();
			document.open();
		} catch (DocumentException de) {
			log.error(de.getMessage());
		} catch (IOException ioe) {
			log.error(ioe.getMessage());
		}
	}

	public void close() {
		document.close();
	}

	public String getCover() {
		return cover;
	}

	protected Image getCoverImage(String path) {
		Image img = null;
		try {
			img = Image.getInstance(path);
			img.scaleToFit(500, 850);
			img.setAlignment(Image.ALIGN_CENTER);
		} catch (DocumentException de) {
			log.error(de.getMessage());
		} catch (IOException ioe) {
			log.error(ioe.getMessage());
		}
		return img;
	}

	public Document getDocument() {
		return document;
	}

	public Font getFont10() {
		return font10;
	}

	public Font getFont12() {
		return font12;
	}

	public Font getFont8() {
		return font8;
	}

	public String getFooter() {
		return footer;
	}

	protected HeaderFooter getFooter(String text) {
		HeaderFooter footer = new HeaderFooter(new Phrase(text, font10), false);
		footer.setAlignment(HeaderFooter.ALIGN_CENTER);
		footer.setBorder(0);
		return footer;
	}

	protected HeaderFooter getHeader(String text) {
		HeaderFooter header = new HeaderFooter(new Phrase(""), new Phrase(this.getHeaderSpace(95) + text, font10));
		header.setBorder(0);
		return header;
	}

	protected String getHeaderSpace(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(" ");
		}
		return sb.toString();
	}

	public String getPdfAuthor() {
		return pdfAuthor;
	}

	public String getPdfFilename() {
		return pdfFilename;
	}

	public boolean isSectionBook(String book) {
		boolean yesOrNot = false;
		if (this.isSection) return true;
		for (int i=0; i<sectionBook.length; i++) {
			if (sectionBook[i].equals(book)) {
				yesOrNot = true;
				break;
			}
		}
		return yesOrNot;
	}

	public Hashtable<String, Book> loadBooks(BookReader reader) {
		Hashtable<String, Book> tmpBooks = new Hashtable<String, Book>();
		Book book = null;
		while ((book = reader.getNextBook()) != null) {
			tmpBooks.put(Integer.toString(book.getId()), book);
		}
		reader.close();
		return tmpBooks;
	}

	public Vector<Line> loadLines(LineReader reader) {
		Vector<Line> tmpLines = new Vector<Line>();
		Line line = reader.getNextLine();
		String savedBook = line.getBook();
		String book;
		int id = 1;
		while (line != null) {
			book = line.getBook();
			if (!book.equals(savedBook)) {
				id++;
				savedBook = book;
			} 
			line.setId(id);
			String key = line.getId()+"."+line.getChapter()+"."+line.getSection();
			line.setKey(key);
			tmpLines.add(line);
			line = reader.getNextLine();
		}
		return tmpLines;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public void setFont10(Font font10) {
		this.font10 = font10;
	}

	public void setFont12(Font font12) {
		this.font12 = font12;
	}

	public void setFont8(Font font8) {
		this.font8 = font8;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public void setPdfAuthor(String pdfAuthor) {
		this.pdfAuthor = pdfAuthor;
	}

	public void setPdfFilename(String pdfFilename) {
		this.pdfFilename = pdfFilename;
	}

}
