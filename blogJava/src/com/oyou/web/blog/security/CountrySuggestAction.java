package com.oyou.web.blog.security;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.improve.struts.taglib.layout.suggest.SuggestAction;

/**
 * @author  Owen Ou
 */
public class CountrySuggestAction extends SuggestAction {
	private static final Log log = LogFactory.getLog(CountrySuggestAction.class);

	String[] suggests = {"CANADA", "USA", "AMERICA", "CHINA"};
	
	public Collection getSuggestionList(HttpServletRequest request, String word) {
		log.debug(">>getSuggestionList");
		ArrayList<String> matchs = new ArrayList<String>();
		if (word != null && word.length() > 0) {
			for (int i=0; i<suggests.length; i++) {
				String suggest  = suggests[i];
				if (suggest.toLowerCase().startsWith(word.toLowerCase().trim())) {
					log.debug("Add " + suggest);
					matchs.add(suggest);
				} else {
					log.debug("Ignore " + suggest + " for word " + word);
				}
			}
		}
		log.debug("<<getSuggestionList " + matchs.size());
		return matchs;
	}


}
