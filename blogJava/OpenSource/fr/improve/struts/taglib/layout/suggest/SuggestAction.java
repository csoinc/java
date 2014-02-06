package fr.improve.struts.taglib.layout.suggest;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * The base suggest tag action
 * @author: Francois Goldgewicht
 */
public abstract class SuggestAction extends org.apache.struts.action.Action {
	
	public ActionForward execute(ActionMapping in_mapping, ActionForm in_form, HttpServletRequest in_request, HttpServletResponse in_response) throws Exception {
	
		in_response.addHeader("Content-Type", "text/xml");
    	
    	String word = in_request.getParameter("word");
    	String encoding = in_request.getParameter("enc");
    	
    	String suggestions = getXMLSuggestionList(in_request, word, encoding);
    	
    	in_response.getOutputStream().print(suggestions);
    	
    	return null;		

	}
	
	protected String getXMLSuggestionList(HttpServletRequest in_request, String in_word, String in_encoding) {
		
		Collection suggestions = getSuggestionList(in_request, in_word);
		
		StringBuffer res = new StringBuffer("<?xml version=\"1.0\" encoding=\"").append(in_encoding).append("\" ?>\n");
		
		res.append("<results>");
		if (suggestions!=null) {
			Iterator iter = suggestions.iterator();	
			while(iter.hasNext()) {
				String currentWord = (String) iter.next();
				res.append("<result value=\"").append(currentWord).append("\" />");
			}
		}
		
		res.append("</results>");
		
		return res.toString();
		
	}

	
	public abstract Collection getSuggestionList(HttpServletRequest in_request, String in_word);
	
}
