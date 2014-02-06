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
import com.oyou.common.util.StringHelper;
import com.oyou.common.util.StrutsSession;
import com.oyou.domain.bible.orm.BibleBook;
import com.oyou.domain.bible.orm.BibleLine;
import com.oyou.web.bible.resources.CNWords;
import com.oyou.web.bible.resources.TWWords;
import com.oyou.web.bible.resources.Words;
import com.oyou.web.util.StrutsHelper;
import com.oyou.web.view.Selection;

public class SelectionAction extends BibleAction {
	protected static final Log log = LogFactory.getLog(SelectionAction.class);
	protected static String KEY_TESTAMENT = "key.testament";
	protected static String KEY_BOOK = "key.book";
	protected static String KEY_CHAPTER = "key.chapter";

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug(">>list()");
		String selT = request.getParameter("t");
		String selB = request.getParameter("b");
		String selC = request.getParameter("c");

		SelectionForm selectionForm = (SelectionForm) form;
		String[] languages = selectionForm.getLanguages();
		if (languages != null) {
			selectionForm.setEnglish("");
			selectionForm.setChineseCN("");
			selectionForm.setChineseTW("");
			for (int i = 0; i < languages.length; i++) {
				log.debug("==select " + languages[i]);
				if (StrutsSession.LANGUAGE_EN.equals(languages[i])) {
					selectionForm.setEnglish(SelectionForm.LANGUAGE_ENGLISH);
				} else if (StrutsSession.LANGUAGE_CN.equals(languages[i])) {
					selectionForm.setChineseCN(SelectionForm.LANGUAGE_CHINESE_CN);
				} else if (StrutsSession.LANGUAGE_TW.equals(languages[i])) {
					selectionForm.setChineseTW(SelectionForm.LANGUAGE_CHINESE_TW);
				}
			}
		}
		String sTestament = null;
		String sBook = null;
		Object[] sChapter = null;
		sTestament = (String) request.getSession().getAttribute(KEY_TESTAMENT);
		sBook = (String) request.getSession().getAttribute(KEY_BOOK);
		sChapter = (Object[]) request.getSession().getAttribute(KEY_CHAPTER);

		if (selT != null && !"".equals(selT)) {
			if ("true".equals(selT))
				selT = "1";
			else
				selT = "0";
			selectionForm.setTestament(selT);
		}
		if (selB != null && !"".equals(selB)) {
			selectionForm.setBook(selB);
		} 
		if (selC != null && !"".equals(selC)) {
			String[] cs = new String[1];
			cs[0] = selC;
			selectionForm.setChapters(cs);
		} 
		
		log.debug("==testament " + selectionForm.getTestament());

		if (!StringHelper.isEmpty(selectionForm.getTestament()) && !selectionForm.getTestament().equals(sTestament)) {
			List<BibleBook> books = null;
			if (selectionForm.getTestament().equals("1")) {
				books = getBibleService().getBibleBooks(true);
			} else {
				books = getBibleService().getBibleBooks(false);
			}
			List<Selection> list = new ArrayList<Selection>();
			Selection sel = null;
			String lang = StrutsHelper.getLanguage(request);
			if (StrutsSession.LANGUAGE_EN.equalsIgnoreCase(lang)) {
				sel = new Selection("", Words.SELECTION + " " + Words.BOOK);
			} else if (StrutsSession.LANGUAGE_CN.equalsIgnoreCase(lang)) {
				sel = new Selection("", CNWords.SELECTION + CNWords.BOOK);
			} else {
				sel = new Selection("", TWWords.SELECTION + TWWords.BOOK);
			}
			request.getSession().setAttribute(KEY_TESTAMENT, selectionForm.getTestament());
			list.add(sel);
			for (BibleBook book : books) {
				if (StrutsSession.LANGUAGE_EN.equalsIgnoreCase(lang)) {
					sel = new Selection(book.getSequence().toString(), book.getName());
				} else if (StrutsSession.LANGUAGE_CN.equalsIgnoreCase(lang)) {
					sel = new Selection(book.getSequence().toString(), book.getNameCN());
				} else {
					sel = new Selection(book.getSequence().toString(), book.getNameTW());
				}
				list.add(sel);
			}
			selectionForm.setBooks(list);
			if (selB == null || "".equals(selB)) {
				selectionForm.setBook("");
				log.debug("<<list()");
				return mapping.findForward(FORWARD_SUCCESS);
			}
		} else if (StringHelper.isEmpty(selectionForm.getTestament())) {
			log.debug("<<list()");
			return mapping.findForward(FORWARD_SUCCESS);
		}
		
		log.debug("==book " + selectionForm.getBook());
		if (!StringHelper.isEmpty(selectionForm.getBook()) && !selectionForm.getBook().equals(sBook)) {
			List<Integer> chapters = getBibleService().getBibleChapters(Integer.parseInt(selectionForm.getBook()));
			List<Selection> list = new ArrayList<Selection>();
			Selection sel = null;
			String lang = StrutsHelper.getLanguage(request);
			if (StrutsSession.LANGUAGE_EN.equalsIgnoreCase(lang)) {
				sel = new Selection("", Words.SELECTION + " " + Words.CHAPTER);
			} else if (StrutsSession.LANGUAGE_CN.equalsIgnoreCase(lang)) {
				sel = new Selection("", CNWords.SELECTION + CNWords.CHAPTER);
			} else {
				sel = new Selection("", TWWords.SELECTION + TWWords.CHAPTER);
			}
			request.getSession().setAttribute(KEY_BOOK, selectionForm.getBook());
			list.add(sel);
			for (Integer chapter : chapters) {
				if (StrutsSession.LANGUAGE_EN.equalsIgnoreCase(lang)) {
					sel = new Selection(chapter.toString(), Words.CHAPTER + " " + chapter.toString());
				} else if (StrutsSession.LANGUAGE_CN.equalsIgnoreCase(lang)) {
					sel = new Selection(chapter.toString(), chapter.toString() + CNWords.CHAPTER);
				} else {
					sel = new Selection(chapter.toString(), chapter.toString() + TWWords.CHAPTER);
				}
				list.add(sel);
			}
			selectionForm.setChapterList(list);
			if (selC == null && "".equals(selC)) {
				selectionForm.setChapters(null);
				log.debug("<<list()");
				return mapping.findForward(FORWARD_SUCCESS);
			}
		} else if (StringHelper.isEmpty(selectionForm.getBook())) {
			log.debug("<<list()");
			return mapping.findForward(FORWARD_SUCCESS);
		}
		log.debug("==chapter " + selectionForm.getChapters());
		if (selectionForm.getChapters() != null && !selectionForm.getChapters().equals(sChapter)) {
			String[] chapters = selectionForm.getChapters();
			Object[] iChapters = new Object[chapters.length];
			for (int i = 0; i < chapters.length; i++) {
				if (StringHelper.isNotEmpty(chapters[i]))
					iChapters[i] = Integer.parseInt(chapters[i]);
				else
					iChapters[i] = -1;
			}
			List<BibleLine> lines = getBibleService().getBibleLines(Integer.parseInt(selectionForm.getBook()), iChapters);
			selectionForm.setLines(lines);
			request.getSession().setAttribute(KEY_CHAPTER, selectionForm.getChapters());
			log.debug("<<list()");
			return mapping.findForward(FORWARD_SUCCESS);
		} else {
			log.debug("<<list()");
			return mapping.findForward(FORWARD_SUCCESS);
		}
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug(">>unspecified()");
		SelectionForm selectionForm = (SelectionForm) form;
		if (selectionForm.getLanguageList() == null) {
			log.debug("==Language is null");
			List<Selection> list = new ArrayList<Selection>();
			String lang = StrutsHelper.getLanguage(request);
			Selection sel;
			if (StrutsSession.LANGUAGE_EN.equalsIgnoreCase(lang)) {
				sel = new Selection("", Words.SELECTION + " " + Words.LANGUAGE);
			} else if (StrutsSession.LANGUAGE_CN.equalsIgnoreCase(lang)) {
				sel = new Selection("", CNWords.SELECTION + CNWords.LANGUAGE);
			} else {
				sel = new Selection("", TWWords.SELECTION + TWWords.LANGUAGE);
			}
			list.add(sel);
			if (StrutsSession.LANGUAGE_EN.equalsIgnoreCase(lang)) {
				sel = new Selection(StrutsSession.LANGUAGE_EN, Words.ENGLISH);
			} else if (StrutsSession.LANGUAGE_CN.equalsIgnoreCase(lang)) {
				sel = new Selection(StrutsSession.LANGUAGE_EN, CNWords.ENGLISH);
			} else {
				sel = new Selection(StrutsSession.LANGUAGE_EN, TWWords.ENGLISH);
			}
			list.add(sel);
			if (StrutsSession.LANGUAGE_EN.equalsIgnoreCase(lang)) {
				sel = new Selection(StrutsSession.LANGUAGE_CN, Words.CHINESE_CN);
			} else if (StrutsSession.LANGUAGE_CN.equalsIgnoreCase(lang)) {
				sel = new Selection(StrutsSession.LANGUAGE_CN, CNWords.CHINESE_CN);
			} else {
				sel = new Selection(StrutsSession.LANGUAGE_CN, TWWords.CHINESE_CN);
			}
			list.add(sel);
			if (StrutsSession.LANGUAGE_EN.equalsIgnoreCase(lang)) {
				sel = new Selection(StrutsSession.LANGUAGE_TW, Words.CHINESE_TW);
			} else if (StrutsSession.LANGUAGE_CN.equalsIgnoreCase(lang)) {
				sel = new Selection(StrutsSession.LANGUAGE_TW, CNWords.CHINESE_TW);
			} else {
				sel = new Selection(StrutsSession.LANGUAGE_TW, TWWords.CHINESE_TW);
			}
			list.add(sel);
			if (StrutsSession.LANGUAGE_EN.equalsIgnoreCase(lang)) {
				selectionForm.setEnglish(SelectionForm.LANGUAGE_ENGLISH);
			} else if (StrutsSession.LANGUAGE_CN.equalsIgnoreCase(lang)) {
				selectionForm.setChineseCN(SelectionForm.LANGUAGE_CHINESE_CN);
			} else {
				selectionForm.setChineseTW(SelectionForm.LANGUAGE_CHINESE_TW);
			}
			selectionForm.setLanguageList(list);
		}
		if (selectionForm.getTestaments() == null) {
			log.debug("==Testaments is null");
			List<Selection> list = new ArrayList<Selection>();
			String lang = StrutsHelper.getLanguage(request);
			Selection sel;
			if (StrutsSession.LANGUAGE_EN.equalsIgnoreCase(lang)) {
				sel = new Selection("", Words.SELECTION + " " + Words.TESTAMENT);
			} else if (StrutsSession.LANGUAGE_CN.equalsIgnoreCase(lang)) {
				sel = new Selection("", CNWords.SELECTION + CNWords.TESTAMENT);
			} else {
				sel = new Selection("", TWWords.SELECTION + TWWords.TESTAMENT);
			}
			list.add(sel);
			if (StrutsSession.LANGUAGE_EN.equalsIgnoreCase(lang)) {
				sel = new Selection("1", Words.TESTAMENT_NEW);
			} else if (StrutsSession.LANGUAGE_CN.equalsIgnoreCase(lang)) {
				sel = new Selection("1", CNWords.TESTAMENT_NEW);
			} else {
				sel = new Selection("1", TWWords.TESTAMENT_NEW);
			}
			list.add(sel);
			if (StrutsSession.LANGUAGE_EN.equalsIgnoreCase(lang)) {
				sel = new Selection("0", Words.TESTAMENT_OLD);
			} else if (StrutsSession.LANGUAGE_CN.equalsIgnoreCase(lang)) {
				sel = new Selection("0", CNWords.TESTAMENT_OLD);
			} else {
				sel = new Selection("0", TWWords.TESTAMENT_OLD);
			}
			list.add(sel);
			selectionForm.setTestament("");
			request.getSession().setAttribute(KEY_TESTAMENT, null);
			request.getSession().setAttribute(KEY_BOOK, null);
			request.getSession().setAttribute(KEY_CHAPTER, null);
			selectionForm.setTestaments(list);
		}

		log.debug("<<unspecified()");
		return list(mapping, selectionForm, request, response);
	}

}
