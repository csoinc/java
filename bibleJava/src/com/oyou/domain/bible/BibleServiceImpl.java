package com.oyou.domain.bible;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.bible.model.Book;
import com.oyou.bible.model.CNBook;
import com.oyou.bible.model.CNLine;
import com.oyou.bible.model.Line;
import com.oyou.bible.model.TWBook;
import com.oyou.bible.model.TWLine;
import com.oyou.bible.reader.BBEBooksReader;
import com.oyou.bible.reader.BBELinesReader;
import com.oyou.bible.reader.BookReader;
import com.oyou.bible.reader.CNBooksReader;
import com.oyou.bible.reader.CNLinesReader;
import com.oyou.bible.reader.LineReader;
import com.oyou.bible.reader.NIVBooksReader;
import com.oyou.bible.reader.NIVLinesReader;
import com.oyou.bible.reader.TWBooksReader;
import com.oyou.bible.reader.TWLinesReader;
import com.oyou.common.spring.SpringServiceImpl;
import com.oyou.domain.bible.orm.BibleBook;
import com.oyou.domain.bible.orm.BibleLine;

public class BibleServiceImpl extends SpringServiceImpl implements BibleService {
	private static final Log log = LogFactory.getLog(BibleService.class);
	private static int batch = 30;
	// private static int batch = 1;
	private BibleDAO bibleDAO;

	public BibleBook getBibleBook(int sequence) {
		return bibleDAO.getBibleBook(sequence);
	}

	public List<BibleBook> getBibleBooks() {
		return bibleDAO.getBibleBooks();
	}

	public List<BibleBook> getBibleBooks(boolean testament) {
		return bibleDAO.getBibleBooks(testament);
	}

	public List<Integer> getBibleChapters(int sequence) {
		return bibleDAO.getBibleChapters(sequence);
	}

	public BibleDAO getBibleDAO() {
		return bibleDAO;
	}

	public List<BibleLine> getBibleLines(int sequence) {
		return bibleDAO.getBibleLines(sequence);
	}

	public List<BibleLine> getBibleLines(int sequence, int chapter) {
		return bibleDAO.getBibleLines(sequence, chapter);
	}

	public List<BibleLine> getBibleLines(int sequence, Object[] chapters) {
		return bibleDAO.getBibleLines(sequence, chapters);
	}

	public List<BibleLine> getBibleLinesAfter(boolean testament, int sequence, int chapter) {
		return bibleDAO.getBibleLinesAfter(testament, sequence, chapter);
	}

	public boolean getBibleTestament(int sequence) {
		BibleBook book = this.getBibleBook(sequence);
		if (book != null)
			return book.getTestament().booleanValue();
		return false;
	}

	public void loadBibleBook() {
		BookReader reader = new BBEBooksReader();
		Book book = null;
		BibleBook bBook = null;
		int i = 1;
		boolean testament = false;
		while ((book = reader.getNextBook()) != null) {
			bBook = bibleDAO.getBibleBook(book.getId());
			if (bBook == null) {
				bBook = new BibleBook();
			}
			bBook.setCode(book.getCode());
			bBook.setName(book.getName());
			bBook.setNumber(book.getIndex());
			bBook.setSequence(book.getId());
			if (book.getCode().equals("Mat"))
				testament = true;
			bBook.setTestament(testament);
			bibleDAO.saveOrUpdate(bBook);
			if (i++ % batch == 0) {
				// 20, same as the JDBC batch size
				// flush a batch of inserts and release memory:
				bibleDAO.flushSession();
			}
		}
		reader.close();
	}

	public void loadBibleBookEN() {
		BookReader reader = new NIVBooksReader();
		Book book = null;
		BibleBook bBook = null;
		int i = 1;
		boolean testament = false;
		while ((book = reader.getNextBook()) != null) {
			bBook = bibleDAO.getBibleBook(book.getId());
			if (bBook == null) {
				bBook = new BibleBook();
			}
			bBook.setCode(book.getCode());
			bBook.setName(book.getName());
			bBook.setNumber(book.getIndex());
			bBook.setSequence(book.getId());
			if (book.getCode().equals("Mat"))
				testament = true;
			bBook.setTestament(testament);
			bibleDAO.saveOrUpdate(bBook);
			if (i++ % batch == 0) {
				// 20, same as the JDBC batch size
				// flush a batch of inserts and release memory:
				bibleDAO.flushSession();
			}
		}
		reader.close();
	}
	
	public void loadBibleBookCN() throws SQLException {
		log.debug("==loadBibleBookCN()");
		BookReader reader = new CNBooksReader();
		CNBook book = null;
		BibleBook bBook = null;
		int i = 1;
		boolean testament = false;
		while ((book = (CNBook) reader.getNextBook()) != null) {
			bBook = bibleDAO.getBibleBook(book.getId());
			if (bBook == null) {
				bBook = new BibleBook();
			}
			bBook.setCode(book.getCode());
			bBook.setNameCN(book.getName());
			bBook.setNumber(book.getIndex());
			bBook.setSequence(book.getId());
			if (book.getCode().equals("Mat"))
				testament = true;
			bBook.setTestament(testament);
			bibleDAO.saveOrUpdate(bBook);
			if (i++ % batch == 0) {
				// 20, same as the JDBC batch size
				// flush a batch of inserts and release memory:
				bibleDAO.flushSession();
			}
		}
		reader.close();
	}

	public void loadBibleBookTW() throws SQLException {
		BookReader reader = new TWBooksReader();
		TWBook book = null;
		BibleBook bBook = null;
		int i = 1;
		boolean testament = false;
		while ((book = (TWBook) reader.getNextBook()) != null) {
			bBook = bibleDAO.getBibleBook(book.getId());
			if (bBook == null) {
				bBook = new BibleBook();
			}
			bBook.setCode(book.getCode());
			bBook.setNameTW(book.getName());
			bBook.setNumber(book.getIndex());
			bBook.setSequence(book.getId());
			if (book.getCode().equals("Mat"))
				testament = true;
			bBook.setTestament(testament);
			bibleDAO.saveOrUpdate(bBook);
			if (i++ % batch == 0) {
				// 20, same as the JDBC batch size
				// flush a batch of inserts and release memory:
				bibleDAO.flushSession();
			}
		}
		reader.close();
	}

	public void loadBibleLine() {
		LineReader reader = new BBELinesReader();
		Line line = null;
		BibleLine bLine = null;
		int i = 1;
		while ((line = reader.getNextLine()) != null) {
			int sequence = bibleDAO.getBibleBook(line.getBook()).getSequence();
			bLine = bibleDAO.getBibleLine(sequence, line.getChapter(), line.getSection());
			if (bLine == null) {
				bLine = new BibleLine();
			}
			bLine.setSequence(sequence);
			bLine.setCode(line.getBook());
			bLine.setChapter(line.getChapter());
			bLine.setSection(line.getSection());
			bLine.setContent(line.getContent());
			bibleDAO.saveOrUpdate(bLine);
			if (i++ % batch == 0) {
				// 20, same as the JDBC batch size
				// flush a batch of inserts and release memory:
				bibleDAO.flushSession();
			}
		}
		bibleDAO.flushSession();
		reader.close();
	}

	public void loadBibleLineEN() {
		LineReader reader = new NIVLinesReader();
		Line line = null;
		BibleLine bLine = null;
		int i = 1;
		while ((line = reader.getNextLine()) != null) {
			int sequence = bibleDAO.getBibleBook(line.getBook()).getSequence();
			bLine = bibleDAO.getBibleLine(sequence, line.getChapter(), line.getSection());
			if (bLine == null) {
				bLine = new BibleLine();
			}
			bLine.setSequence(sequence);
			bLine.setCode(line.getBook());
			bLine.setChapter(line.getChapter());
			bLine.setSection(line.getSection());
			bLine.setContent(line.getContent());
			bibleDAO.saveOrUpdate(bLine);
			if (i++ % batch == 0) {
				// 20, same as the JDBC batch size
				// flush a batch of inserts and release memory:
				bibleDAO.flushSession();
			}
		}
		bibleDAO.flushSession();
		reader.close();
	}
	
	public void loadBibleLineCN() throws SQLException {
		log.debug("==loadBibleLineCN()");
		LineReader reader = new CNLinesReader();
		CNLine line = null;
		BibleLine bLine = null;
		int i = 1;
		while ((line = (CNLine) reader.getNextLine()) != null) {
			int sequence = bibleDAO.getBibleBook(line.getBook()).getSequence();
			bLine = bibleDAO.getBibleLine(sequence, line.getChapter(), line.getSection());
			if (bLine == null) {
				bLine = new BibleLine();
			}
			bLine.setSequence(sequence);
			bLine.setCode(line.getBook());
			bLine.setChapter(line.getChapter());
			bLine.setSection(line.getSection());
			bLine.setContentCN(line.getContent());
			bibleDAO.saveOrUpdate(bLine);
			if (i++ % batch == 0) {
				// 20, same as the JDBC batch size
				// flush a batch of inserts and release memory:
				bibleDAO.flushSession();
			}
		}
		bibleDAO.flushSession();
		reader.close();
	}

	public void loadBibleLineTW() throws SQLException {
		LineReader reader = new TWLinesReader();
		TWLine line = null;
		BibleLine bLine = null;
		int i = 1;
		while ((line = (TWLine) reader.getNextLine()) != null) {
			int sequence = bibleDAO.getBibleBook(line.getBook()).getSequence();
			bLine = bibleDAO.getBibleLine(sequence, line.getChapter(), line.getSection());
			if (bLine == null) {
				bLine = new BibleLine();
			}
			bLine.setSequence(sequence);
			bLine.setCode(line.getBook());
			bLine.setChapter(line.getChapter());
			bLine.setSection(line.getSection());
			bLine.setContentTW(line.getContent());
			bibleDAO.saveOrUpdate(bLine);
			if (i++ % batch == 0) {
				// 20, same as the JDBC batch size
				// flush a batch of inserts and release memory:
				bibleDAO.flushSession();
			}
		}
		bibleDAO.flushSession();
		reader.close();
	}

	public void setBibleDAO(BibleDAO bibleDAO) {
		this.bibleDAO = bibleDAO;
	}
	
	public List<BibleLine> searchBibleLines(String what, String where) {
		return this.bibleDAO.searchBibleLines(what, where);
	}

	public List<BibleLine> searchBibleLines(String what, String where, List<String> codes) {
		return this.bibleDAO.searchBibleLines(what, where, codes);
	}
	
	public SearchPageList searchBibleLinePageList(SearchPageList pageList) {
		return this.bibleDAO.searchBibleLinePageList(pageList);
	}
	
}
