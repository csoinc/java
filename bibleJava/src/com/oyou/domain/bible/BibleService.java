package com.oyou.domain.bible;

import java.sql.SQLException;
import java.util.List;


import com.oyou.common.spring.SpringService;
import com.oyou.domain.bible.orm.BibleBook;
import com.oyou.domain.bible.orm.BibleLine;

public interface BibleService extends SpringService {

	public BibleBook getBibleBook(int sequence);

	public List<BibleBook> getBibleBooks();

	public List<BibleBook> getBibleBooks(boolean testament);

	public List<Integer> getBibleChapters(int sequence);

	public BibleDAO getBibleDAO();

	public List<BibleLine> getBibleLines(int sequence);

	public List<BibleLine> getBibleLines(int sequence, int chapter);

	public List<BibleLine> getBibleLines(int sequence, Object[] chapters);

	public List<BibleLine> getBibleLinesAfter(boolean testament, int sequence, int chapter);

	public boolean getBibleTestament(int sequence);

	public void loadBibleBook();

	public void loadBibleBookEN();

	public void loadBibleBookCN() throws SQLException;

	public void loadBibleBookTW() throws SQLException;

	public void loadBibleLine();

	public void loadBibleLineEN();

	public void loadBibleLineCN() throws SQLException;

	public void loadBibleLineTW() throws SQLException;

	public void setBibleDAO(BibleDAO bibleDAO);

	public List<BibleLine> searchBibleLines(String what, String where);

	public List<BibleLine> searchBibleLines(String what, String where, List<String> codes);
	
	public SearchPageList searchBibleLinePageList(SearchPageList pageList);

}
