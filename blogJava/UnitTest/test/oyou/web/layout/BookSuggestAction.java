package test.oyou.web.layout;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.improve.struts.taglib.layout.suggest.SuggestAction;

/**
 * @author  Owen Ou
 */
public class BookSuggestAction extends SuggestAction {
	private static final Log log = LogFactory.getLog(BookSuggestAction.class);

	public Collection getSuggestionList(HttpServletRequest request, String word) {
		log.debug(">>getSuggestionList");

		ArrayList<String> suggestions = new ArrayList<String>();
		log.debug("<<getSuggestionList " + suggestions.size());
		return suggestions;
	}

}
