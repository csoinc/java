package com.oyou.web.bible;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.oyou.bible.pdf.CNWords;
import com.oyou.bible.pdf.TWWords;
import com.oyou.bible.pdf.Words;
import com.oyou.common.exception.BusinessException;
import com.oyou.common.util.StringHelper;
import com.oyou.common.util.StrutsSession;
import com.oyou.domain.bible.orm.BibleBook;
import com.oyou.domain.bible.orm.BibleLine;
import com.oyou.web.util.StrutsHelper;

import fr.improve.struts.taglib.layout.menu.MenuComponent;
import fr.improve.struts.taglib.layout.util.LayoutUtils;

public class ListingAction extends BibleAction {
	protected static final Log log = LogFactory.getLog(ListingAction.class);

	protected MenuComponent createChildMenu(MenuComponent parent, String title, String id) {
		MenuComponent child = new MenuComponent(id);
		parent.addMenuComponent(child);
		child.setTitle(title);
		return child;
	}

	protected  MenuComponent createMenuTreeForBooks(List<BibleBook> books, MenuComponent menu, String reqCode, String menuType) {
		for (BibleBook book : books) {
			MenuComponent mBook = null;
			if (ListingForm.MENU_BIBLE_CHINESE_TW.equals(menuType)) {
				mBook = createChildMenu(menu, book.getNameTW(), String.valueOf(book.getSequence()));
			} else if (ListingForm.MENU_BIBLE_CHINESE_CN.equals(menuType)) {
				mBook = createChildMenu(menu, book.getNameCN(), String.valueOf(book.getSequence()));
			} else {
				mBook = createChildMenu(menu, book.getName(), String.valueOf(book.getSequence()));
			}
			mBook.setLocation("bibleTree.do?reqCode=" + reqCode + "&sid=" + book.getSequence().toString());
			List<Integer> chapters = getBibleService().getBibleChapters(book.getSequence());
			for (Integer chapter : chapters) {
				MenuComponent mChapter = null;
				if (ListingForm.MENU_BIBLE_CHINESE_TW.equals(menuType)) {
					mChapter = createChildMenu(mBook, chapter.toString() + TWWords.CHAPTER, book.getSequence().toString() + ":" +chapter);
				} else if (ListingForm.MENU_BIBLE_CHINESE_CN.equals(menuType)) {
					mChapter = createChildMenu(mBook, chapter.toString() + CNWords.CHAPTER, book.getSequence().toString() + ":" +chapter);
				} else {
					mChapter = createChildMenu(mBook, Words.CHAPTER + " " + chapter.toString(), book.getSequence().toString() + ":" +chapter);
				}
				mChapter.setLocation("bibleTree.do?reqCode=" + reqCode + "&sid=" + book.getSequence().toString() + "&cid=" + chapter);
			}
		}
		return menu;
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		ListingForm listingForm = (ListingForm)form;
		if (ListingForm.LANGUAGE_CHINESE_TW.equals(listingForm.getChineseTW())) {
			listingForm.setMenu(ListingForm.MENU_BIBLE_CHINESE_TW);
		} else if (ListingForm.LANGUAGE_CHINESE_CN.equals(listingForm.getChineseCN())) {
			listingForm.setMenu(ListingForm.MENU_BIBLE_CHINESE_CN);
		} else {
			listingForm.setMenu(ListingForm.MENU_BIBLE_ENGLISH);
		}
		return this.listTestaments(mapping, listingForm, request, response);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		ListingForm listingForm = (ListingForm)form;
		String lang = (String) request.getParameter("english");
		listingForm.setEnglish(lang);
		lang = (String) request.getParameter("chineseCN");
		listingForm.setChineseCN(lang);
		lang = (String) request.getParameter("chineseTW");
		listingForm.setChineseTW(lang);
		return this.list(mapping, form, request, response);
	}
	
	/**
	 * Listing menu top level is the books
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws BusinessException
	 */
	protected ActionForward listBooks(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug(">>listBooks()");
		ListingForm listingForm = (ListingForm)form;
		MenuComponent menu = new MenuComponent();
		menu.setName(listingForm.getMenu());
		List<BibleBook> books = getBibleService().getBibleBooks();
		menu = this.createMenuTreeForBooks(books, menu, "listBooks", listingForm.getMenu());
		LayoutUtils.addMenuIntoSession(request, menu);
		log.debug("<<listBooks()");
		return this.updateList(mapping, form, request, response);
	}

	/**
	 * Listing menu top level is the books of new testament
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws BusinessException
	 */
	protected ActionForward listNewTestament(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug(">>listNewTestament()");
		ListingForm listingForm = (ListingForm)form;
		MenuComponent menu = new MenuComponent();
		menu.setName(listingForm.getMenu());
		List<BibleBook> books = getBibleService().getBibleBooks(true);
		menu = this.createMenuTreeForBooks(books, menu, "listNewTestament", listingForm.getMenu());
		LayoutUtils.addMenuIntoSession(request, menu);
		log.debug("<<listNewTestament()");
		return this.updateList(mapping, form, request, response);
	}
	
	/**
	 * Listing menu top level is the books of old testament
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws BusinessException
	 */
	protected ActionForward listOldTestament(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug(">>listOldTestament()");
		ListingForm listingForm = (ListingForm)form;
		MenuComponent menu = new MenuComponent();
		menu.setName(listingForm.getMenu());
		List<BibleBook> books = getBibleService().getBibleBooks(false);
		menu = this.createMenuTreeForBooks(books, menu, "listOldTestament", listingForm.getMenu());
		LayoutUtils.addMenuIntoSession(request, menu);
		log.debug("<<listOldTestament()");
		return this.updateList(mapping, form, request, response);
	}
	
	/**
	 * Default listing
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws BusinessException
	 */
	public ActionForward listTestaments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug(">>listTestaments()");
		ListingForm listingForm = (ListingForm)form;
		MenuComponent menu = new MenuComponent();
		menu.setName(listingForm.getMenu());
		MenuComponent mOld;
		String lang = StrutsHelper.getLanguage(request);
		if (StrutsSession.LANGUAGE_EN.equalsIgnoreCase(lang)) {
			mOld = createChildMenu(menu, Words.TESTAMENT_OLD, "0");
		} else if (StrutsSession.LANGUAGE_CN.equalsIgnoreCase(lang)) {
			mOld = createChildMenu(menu, CNWords.TESTAMENT_OLD, "0");
		} else {
			mOld = createChildMenu(menu, TWWords.TESTAMENT_OLD, "0");
		}
		List<BibleBook> books = getBibleService().getBibleBooks(false);
		mOld = this.createMenuTreeForBooks(books, mOld, "listTestaments", listingForm.getMenu());
		MenuComponent mNew;
		if (StrutsSession.LANGUAGE_EN.equalsIgnoreCase(lang)) {
			mNew = createChildMenu(menu, Words.TESTAMENT_NEW, "1");
		} else if (StrutsSession.LANGUAGE_CN.equalsIgnoreCase(lang)) {
			mNew = createChildMenu(menu, CNWords.TESTAMENT_NEW, "1");			
		} else {
			mNew = createChildMenu(menu, TWWords.TESTAMENT_NEW, "1");			
		}

		books = getBibleService().getBibleBooks(true);
		mNew = this.createMenuTreeForBooks(books, mNew, "listTestaments", listingForm.getMenu());
		LayoutUtils.addMenuIntoSession(request, menu);
		log.debug("<<listTestaments()");
		return this.updateList(mapping, form, request, response);
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		ListingForm listingForm = (ListingForm)form;
		listingForm.setEnglish("");
		listingForm.setChineseTW("");
		listingForm.setChineseCN("");
		String lang = StrutsHelper.getLanguage(request);
		if (StrutsSession.LANGUAGE_EN.equalsIgnoreCase(lang)) {
			listingForm.setEnglish(ListingForm.LANGUAGE_ENGLISH);
			listingForm.setMenu(ListingForm.MENU_BIBLE_ENGLISH);
		} else if (StrutsSession.LANGUAGE_CN.equalsIgnoreCase(lang)) {
			listingForm.setChineseCN(ListingForm.LANGUAGE_CHINESE_CN);
			listingForm.setMenu(ListingForm.MENU_BIBLE_CHINESE_CN);
		} else {
			listingForm.setChineseTW(ListingForm.LANGUAGE_CHINESE_TW);
			listingForm.setMenu(ListingForm.MENU_BIBLE_CHINESE_TW);
		}
		return list(mapping, form, request, response);
	}

	private ActionForward updateList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug(">>updateList()");
		ListingForm listingForm = (ListingForm)form;
		ActionMessages messages = new ActionMessages();
		BibleBook book;
		List<BibleLine> lines;
		String sid = request.getParameter("sid");
		String cid = request.getParameter("cid");
		if (StringHelper.isEmpty(sid)) {
			sid = "1";
			cid = "1";
			lines = getBibleService().getBibleLines(Integer.parseInt(sid), Integer.parseInt(cid));
		} else {
			if (StringHelper.isEmpty(cid)) {
				lines = getBibleService().getBibleLines(Integer.parseInt(sid));
				cid = "1";
			} else {
				lines = getBibleService().getBibleLines(Integer.parseInt(sid), Integer.parseInt(cid));
			}	
		}	
		book = getBibleService().getBibleBook(Integer.parseInt(sid));
		listingForm.setLines(lines);
		listingForm.setBook(book);
		String lang = StrutsHelper.getLanguage(request);
		if (StrutsSession.LANGUAGE_EN.equalsIgnoreCase(lang)) {
			listingForm.setChapter(book.getName() + " " + Words.CHAPTER + " " + cid);
		} else if (StrutsSession.LANGUAGE_CN.equalsIgnoreCase(lang)) {
			listingForm.setChapter(book.getNameCN() + " " + cid + CNWords.CHAPTER);
		} else {
			listingForm.setChapter(book.getNameTW() + " " + cid + TWWords.CHAPTER);
		}	
		ActionMessage message = new ActionMessage("message.bible.confirmed", book.getNameCN(), cid);
		messages.add(ActionMessages.GLOBAL_MESSAGE, message);
		request.setAttribute(Globals.MESSAGE_KEY, messages);
		log.debug("<<updateList()");
		return mapping.findForward(FORWARD_SUCCESS);
	}
	
}
