package com.oyou.web.bible;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.oyou.common.exception.BusinessException;
import com.oyou.common.util.StrutsSession;
import com.oyou.domain.bible.SearchPageList;
import com.oyou.domain.bible.orm.BibleBook;
import com.oyou.web.bible.resources.CNWords;
import com.oyou.web.bible.resources.TWWords;
import com.oyou.web.bible.resources.Words;
import com.oyou.web.util.StrutsHelper;
import com.oyou.web.view.Selection;

public class BibleSearchAction extends BibleAction {
	private static final Log log = LogFactory.getLog(BibleSearchAction.class);

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug(">>list()");
		BibleSearchForm searchForm = (BibleSearchForm) form;
		
		String lang = StrutsHelper.getLanguage(request);
		if (searchForm.getBookList() == null || !lang.equals(searchForm.getLanguage())) {
			Selection sel = null;
			List<BibleBook> books = getBibleService().getBibleBooks();
			List<Selection> bkList = new ArrayList<Selection>();
			if (StrutsSession.LANGUAGE_EN.equalsIgnoreCase(lang)) {
				sel = new Selection("", Words.SELECTION + " " + Words.BOOK);
			} else if (StrutsSession.LANGUAGE_CN.equalsIgnoreCase(lang)) {
				sel = new Selection("", CNWords.SELECTION + CNWords.BOOK);
			} else {
				sel = new Selection("", TWWords.SELECTION + TWWords.BOOK);
			}
			bkList.add(sel);
			for (BibleBook book : books) {
				if (StrutsSession.LANGUAGE_EN.equalsIgnoreCase(lang)) {
					sel = new Selection(book.getCode(), book.getName());
				} else if (StrutsSession.LANGUAGE_CN.equalsIgnoreCase(lang)) {
					sel = new Selection(book.getCode(), book.getNameCN());
				} else {
					sel = new Selection(book.getCode(), book.getNameTW());
				}
				bkList.add(sel);
			}
			searchForm.setBookList(bkList);
		}
		if (searchForm.getCatList() == null || !lang.equals(searchForm.getLanguage())) {
			Selection sel = null;
			List<Selection> ctList = new ArrayList<Selection>();
			if (StrutsSession.LANGUAGE_EN.equalsIgnoreCase(lang)) {
				sel = new Selection("", Words.SELECTION + " " + Words.SCOPE);
				ctList.add(sel);
				sel = new Selection("AT", Words.WHOLE_BIBLE);
				ctList.add(sel);
				sel = new Selection("OT", Words.TESTAMENT_OLD);
				ctList.add(sel);
				sel = new Selection("NT", Words.TESTAMENT_NEW);
				ctList.add(sel);
			} else if (StrutsSession.LANGUAGE_CN.equalsIgnoreCase(lang)) {
				sel = new Selection("", CNWords.SELECTION + CNWords.SCOPE);
				ctList.add(sel);
				sel = new Selection("AT", CNWords.WHOLE_BIBLE);
				ctList.add(sel);
				sel = new Selection("OT", CNWords.TESTAMENT_OLD);
				ctList.add(sel);
				sel = new Selection("NT", CNWords.TESTAMENT_NEW);
				ctList.add(sel);
			} else {
				sel = new Selection("", TWWords.SELECTION + TWWords.SCOPE);
				ctList.add(sel);
				sel = new Selection("AT", TWWords.WHOLE_BIBLE);
				ctList.add(sel);
				sel = new Selection("OT", TWWords.TESTAMENT_OLD);
				ctList.add(sel);
				sel = new Selection("NT", TWWords.TESTAMENT_NEW);
				ctList.add(sel);
			}
			searchForm.setCatList(ctList);
		}
		searchForm.setLanguage(lang);
		
		SearchPageList pageList = searchForm.getPageList();
		if (pageList == null)
			pageList = new SearchPageList();
		if (pageList.getWhat() == null)
			pageList.setWhat("");
		if (pageList.getWhere() == null)
			pageList.setWhere("");

		if (searchForm.getBooks() != null && searchForm.getCats() != null) {
			String[] selBooks = searchForm.getBooks();
			String[] selCats = searchForm.getCats();
			List<String> codes = new ArrayList<String>();
			for (int i = 0; i < selCats.length; i++) {
				if (selCats[i] != null && !"".equals(selCats[i])) {
					codes.add(selCats[i]);
				}	
			}
			for (int i = 0; i < selBooks.length; i++) {
				if (selBooks[i] != null && !"".equals(selBooks[i])) {
					codes.add(selBooks[i]);
				}	
			}
			pageList.setCodes(codes);
		} else if (searchForm.getBooks() != null) {
			String[] selBooks = searchForm.getBooks();
			List<String> codes = new ArrayList<String>();
			for (int i = 0; i < selBooks.length; i++) {
				if (selBooks[i] != null && !"".equals(selBooks[i])) {
					codes.add(selBooks[i]);
				}	
			}
			pageList.setCodes(codes);
		} else if (searchForm.getCats() != null) {
			String[] selCats = searchForm.getCats();
			List<String> codes = new ArrayList<String>();
			for (int i = 0; i < selCats.length; i++) {
				if (selCats[i] != null && !"".equals(selCats[i])) {
					codes.add(selCats[i]);
				}	
			}
			pageList.setCodes(codes);
		} else {
			List<String> codes = new ArrayList<String>();
			codes.add("AT");
			pageList.setCodes(codes);
		}
		String pageId = request.getParameter("page");
		if (pageId == null)
			pageList.setNumber(1);
		else
			pageList.setNumber(Integer.parseInt(pageId));
		pageList.setSize(BibleForm.DEFAULT_MAX_LINE);

		pageList = getBibleService().searchBibleLinePageList(pageList);
		searchForm.setPageList(pageList);

		if (StrutsSession.LANGUAGE_EN.equalsIgnoreCase(lang)) {
			searchForm.setLanguage(BibleSearchForm.LANGUAGE_ENGLISH);
		} else if (StrutsSession.LANGUAGE_CN.equalsIgnoreCase(lang)) {
			searchForm.setLanguage(BibleSearchForm.LANGUAGE_CHINESE_CN);
		} else {
			searchForm.setLanguage(BibleSearchForm.LANGUAGE_CHINESE_TW);
		}
		log.debug("<<list()");
		return mapping.findForward(FORWARD_SUCCESS);
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug("==unspecified()");
		BibleSearchForm searchForm = (BibleSearchForm) form;
		SearchPageList pageList = searchForm.getPageList();
		if (pageList == null)
			pageList = new SearchPageList();
		if (pageList.getWhat() == null)
			pageList.setWhat("");
		if (pageList.getWhere() == null)
			pageList.setWhere("");

		String pageId = request.getParameter("page");
		if (pageId == null)
			pageList.setNumber(1);
		else
			pageList.setNumber(Integer.parseInt(pageId));
		pageList.setSize(BibleForm.DEFAULT_MAX_LINE);
		searchForm.setPageList(pageList);

		return list(mapping, searchForm, request, response);
	}

}
