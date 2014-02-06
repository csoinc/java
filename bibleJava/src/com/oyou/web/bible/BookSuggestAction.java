package com.oyou.web.bible;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.domain.bible.orm.BibleBook;

/**
 * @author  Owen Ou
 */
public class BookSuggestAction extends BibleSuggestAction {
	private static final Log log = LogFactory.getLog(BookSuggestAction.class);

	public static List<String> suggests = new ArrayList<String>();
	
	@SuppressWarnings("rawtypes")
	public Collection getSuggestionList(HttpServletRequest request, String text) {
		log.debug(">>getSuggestionList");
		ArrayList<String> matchs = new ArrayList<String>();
		if (suggests.size() == 0) this.loadBooks();
		if (text != null && text.length() >= 2) {
			for (String suggest : suggests) {
				if (suggest.toLowerCase().startsWith(text.toLowerCase().trim())) {
					log.debug("Add " + suggest);
					matchs.add(suggest);
				} 
			}
		}
		log.debug("<<getSuggestionList " + matchs.size());
		return matchs;
	}

	@SuppressWarnings("rawtypes")
	protected Map getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.create", "create");
		map.put("button.update", "update");
		map.put("button.delete", "delete");
		map.put("button.search", "list");
		return map;
	}

	private void loadBooks() {
		List<BibleBook> books = (List<BibleBook>)this.getBibleService().getBibleBooks();
		for (BibleBook book : books) {
			suggests.add(book.getName());
			suggests.add(book.getNameCN());
			suggests.add(book.getNameTW());
		}
	}
	
}
