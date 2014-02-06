package com.oyou.domain.bible;

import java.util.List;


import com.oyou.common.hibernate.dao.CommonDAO;
import com.oyou.domain.bible.orm.BibleBook;
import com.oyou.domain.bible.orm.BibleLine;

/**
 * 
 * @author Owen Ou May 10, 2007
 */
public interface BibleDAO extends CommonDAO {

	public BibleBook getBibleBook(int sequence);

	public BibleBook getBibleBook(String code);

	public BibleLine getBibleLine(int sequence, int chapter, int section);

	public List<BibleBook> getBibleBooks();

	public List<BibleBook> getBibleBooks(boolean testament);

	public List<BibleLine> getBibleLines(int sequence);

	public List<Integer> getBibleChapters(int sequence);

	public List<BibleLine> getBibleLines(int sequence, int chapter);

	public List<BibleLine> getBibleLines(int sequence, Object[] chapters);

	public List<BibleLine> getBibleLinesAfter(boolean testament, int sequence, int chapter);

	public List<BibleLine> searchBibleLines(String what, String where);

	public List<BibleLine> searchBibleLines(String word, String where, List<String> codes);

	public SearchPageList searchBibleLinePageList(SearchPageList pageList);
	
}
