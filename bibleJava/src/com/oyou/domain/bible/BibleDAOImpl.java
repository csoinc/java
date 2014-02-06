package com.oyou.domain.bible;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

import com.oyou.common.hibernate.dao.CommonDAOImpl;
import com.oyou.common.hibernate.util.SessionConstants;
import com.oyou.common.util.StringHelper;
import com.oyou.domain.bible.orm.BibleBook;
import com.oyou.domain.bible.orm.BibleLine;

/**
 * 
 * @author Owen Ou May 10, 2007
 */
public class BibleDAOImpl extends CommonDAOImpl implements BibleDAO {
	protected static Log logger = LogFactory.getLog(BibleDAOImpl.class);

	private static final int AT = 0;
	private static final int OT = 1;
	private static final int NT = 2;

	public BibleBook getBibleBook(int sequence) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bb from BibleBook as bb");
		sb.append(" where bb.sequence = :sequence");
		Query query = getSession().createQuery(sb.toString());
		query.setInteger("sequence", sequence);
		return (BibleBook) executeHQObject(query);
	}

	public BibleBook getBibleBook(String code) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bb from BibleBook as bb");
		sb.append(" where bb.code = :code");
		Query query = getSession().createQuery(sb.toString());
		query.setString("code", code);
		return (BibleBook) executeHQObject(query);
	}

	public BibleLine getBibleLine(int sequence, int chapter, int section) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bl from BibleLine as bl");
		sb.append(" where bl.sequence = :sequence");
		sb.append(" and bl.chapter = :chapter");
		sb.append(" and bl.section = :section");

		Query query = getSession().createQuery(sb.toString());
		query.setInteger("sequence", sequence);
		query.setInteger("chapter", chapter);
		query.setInteger("section", section);
		return (BibleLine) executeHQObject(query);
	}

	@SuppressWarnings("unchecked")
	public List<BibleBook> getBibleBooks() {
		StringBuffer sb = new StringBuffer();
		sb.append("select bb from BibleBook as bb");
		sb.append(" order by bb.id");
		Query query = getSession().createQuery(sb.toString());
		return (List<BibleBook>) query.list();
	}

	@SuppressWarnings("unchecked")
	public List<BibleBook> getBibleBooks(boolean testament) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bb from BibleBook as bb");
		sb.append(" where bb.testament = :testament");
		sb.append(" order by bb.id");
		Query query = getSession().createQuery(sb.toString());
		query.setBoolean("testament", testament);
		return (List<BibleBook>) query.list();
	}

	@SuppressWarnings("unchecked")
	public List<BibleLine> getBibleLines(int sequence) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bl from BibleLine as bl");
		sb.append(" where bl.sequence = :sequence");
		Query query = getSession().createQuery(sb.toString()).setCacheable(true);
		query.setInteger("sequence", sequence);
		return (List<BibleLine>) query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getBibleChapters(int sequence) {
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct bl.chapter from BibleLine as bl");
		sb.append(" where bl.sequence = :sequence");
		Query query = getSession().createQuery(sb.toString()).setCacheable(true);
		query.setInteger("sequence", sequence);
		return (List<Integer>) query.list();
	}

	@SuppressWarnings("unchecked")
	public List<BibleLine> getBibleLines(int sequence, int chapter) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bl from BibleLine as bl");
		sb.append(" where bl.sequence = :sequence");
		sb.append(" and bl.chapter = :chapter");
		Query query = getSession().createQuery(sb.toString()).setCacheable(true);
		query.setInteger("sequence", sequence);
		query.setInteger("chapter", chapter);
		return (List<BibleLine>) query.list();
	}

	@SuppressWarnings("unchecked")
	public List<BibleLine> getBibleLinesAfter(boolean testament, int sequence, int chapter) {
		List<BibleLine> list = getBibleLines(sequence, chapter);
		BibleLine line = (BibleLine) list.get(0);
		if (line != null) {
			StringBuffer sb = new StringBuffer();
			sb.append("select bl from BibleLine as bl inner join bl.bibleBook");
			sb.append(" where bl.bibleBook.testament = :testament and bl.id >= :id");
			Query query = getSession().createQuery(sb.toString());
			query.setBoolean("testament", testament);
			query.setLong("id", line.getId());
			list = (List<BibleLine>) query.list();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<BibleLine> getBibleLines(int sequence, Object[] chapters) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bl from BibleLine as bl");
		sb.append(" where bl.sequence = :sequence");
		sb.append(" and bl.chapter in (:chapters)");
		Query query = getSession().createQuery(sb.toString()).setCacheable(false);
		query.setInteger("sequence", sequence);
		query.setParameterList("chapters", chapters);
		return (List<BibleLine>) query.list();
	}

	@SuppressWarnings("unchecked")
	public List<BibleLine> searchBibleLines(String what, String where) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bl from BibleLine as bl inner join bl.bibleBook");
		sb.append(" where (bl.content like :what");
		sb.append(" or bl.contentCN like :what");
		sb.append(" or bl.contentTW like :what)");
		sb.append(" and (bl.bibleBook.name like :where");
		sb.append(" or bl.bibleBook.nameCN like :where");
		sb.append(" or bl.bibleBook.nameTW like :where)");

		Query query = getSession().createQuery(sb.toString()).setMaxResults(SessionConstants.HIBERNATE_QUERY_MAX_ROWS)
				.setTimeout(SessionConstants.HIBERNATE_QUERY_TIMEOUT);
		what = StringHelper.patternValue(what);
		where = StringHelper.patternValue(where);
		logger.debug("==search: " + what + " in " + where);
		query.setString("what", what);
		query.setString("where", where);
		return (List<BibleLine>) query.list();
	}

	@SuppressWarnings("unchecked")
	public List<BibleLine> searchBibleLines(String what, String where, List<String> codes) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bl from BibleLine as bl inner join bl.bibleBook");
		sb.append(" where (bl.content like :what");
		sb.append(" or bl.contentCN like :what");
		sb.append(" or bl.contentTW like :what)");
		sb.append(" and (bl.bibleBook.name like :where");
		sb.append(" or bl.bibleBook.nameCN like :where");
		sb.append(" or bl.bibleBook.nameTW like :where)");
		int st = this.checkCodes(codes);
		if (st == AT) {
			sb.append(" and bl.bibleBook.testament in (0, 1)");
		} else if (st == OT) {
			sb.append(" and bl.bibleBook.testament in (0)");
		} else if (st == NT) {
			sb.append(" and bl.bibleBook.testament in (1)");
		} else {
			if (codes.size() > 0) {
				sb.append(" and bl.bibleBook.code in (");
				boolean isCode = false;
				for (String code : codes) {
					if (code != null && !"".equals(code)) {
						if (isCode)
							sb.append(",");
						sb.append("'" + code + "'");
						isCode = true;
					}
				}
				sb.append(")");
			}
		}
		Query query = getSession().createQuery(sb.toString()).setMaxResults(SessionConstants.HIBERNATE_QUERY_MAX_ROWS)
				.setTimeout(SessionConstants.HIBERNATE_QUERY_TIMEOUT);
		what = StringHelper.patternValue(what);
		where = StringHelper.patternValue(where);

		logger.debug("==search: " + what + " in " + where);
		query.setString("what", what);
		query.setString("where", where);
		return (List<BibleLine>) query.list();
	}

	private int checkCodes(List<String> codes) {
		int rtnInt = -1;
		for (String code : codes) {
			if ("AT".equalsIgnoreCase(code)) {
				rtnInt = AT;
				break;
			} else if ("OT".equalsIgnoreCase(code)) {
				rtnInt = OT;
				break;
			} else if ("NT".equalsIgnoreCase(code)) {
				rtnInt = NT;
				break;
			}
		}
		return rtnInt;
	}

	@SuppressWarnings("unchecked")
	public SearchPageList searchBibleLinePageList(SearchPageList pageList) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bl from BibleLine as bl inner join bl.bibleBook");
		sb.append(" where (bl.content like :what");
		sb.append(" or bl.contentCN like :what");
		sb.append(" or bl.contentTW like :what)");
		sb.append(" and (bl.bibleBook.name like :book");
		sb.append(" or bl.bibleBook.nameCN like :book");
		sb.append(" or bl.bibleBook.nameTW like :book)");
		List<String> codes = pageList.getCodes();
		int st = this.checkCodes(codes);
		if (st == AT) {
			sb.append(" and bl.bibleBook.testament in (0, 1)");
		} else if (st == OT) {
			sb.append(" and bl.bibleBook.testament in (0)");
		} else if (st == NT) {
			sb.append(" and bl.bibleBook.testament in (1)");
		} else {
			if (codes.size() > 0) {
				sb.append(" and bl.bibleBook.code in (");
				boolean isCode = false;
				for (String code : codes) {
					if (code != null && !"".equals(code)) {
						if (isCode)
							sb.append(",");
						sb.append("'" + code + "'");
						isCode = true;
					}
				}
				sb.append(")");
			}
		}
		logger.info("==search query: " + sb.toString());
		Query query = getSession().createQuery(sb.toString()).setMaxResults(SessionConstants.HIBERNATE_QUERY_MAX_ROWS)
				.setTimeout(SessionConstants.HIBERNATE_QUERY_TIMEOUT);
		String what = StringHelper.phraseValue(pageList.getWhat());
		String where = StringHelper.phraseValue(pageList.getWhere());

		logger.debug("==search: " + what + " in " + where);
		query.setString("what", what);
		query.setString("book", where);

		List<BibleLine> list = (List<BibleLine>) query.list();
		String pattern = StringHelper.patternValue(pageList.getWhat());
		if (pattern != null & !pattern.equals(what)) {
			logger.debug("==search: " + pattern + " in " + where);
			query.setString("what", pattern);
			query.setString("book", where);

			List<BibleLine> pList = (List<BibleLine>) query.list();
			list.addAll(pList);
		}
		List<BibleLine> subList = this.getSearchPageList(list, pageList);
		pageList.setResultSet(subList);
		return pageList;
	}

	private List<BibleLine> getSearchPageList(List<BibleLine> list, SearchPageList pageList) {
		int listSize = list.size();
		pageList.setTotal((int) Math.ceil(listSize / pageList.getSize()) + 1);
		List<BibleLine> subList = new ArrayList<BibleLine>();
		int max = (listSize > pageList.getSize() * pageList.getNumber()) ? pageList.getSize() * pageList.getNumber() : listSize;
		int min = pageList.getSize() * (pageList.getNumber() - 1);
		for (int i = min; i < max; i++) {
			subList.add((BibleLine) list.get(i));
		}
		return subList;
	}

}
