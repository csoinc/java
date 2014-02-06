/*
 * Created on 23 nov. 2005
 */
package fr.improve.struts.taglib.layout.suggest;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author rmaton
 */
public abstract class MultipleSuggestAction extends SuggestAction {
	
	public final Collection getSuggestionList(HttpServletRequest in_request, String in_word) {
		Enumeration parameterNames = in_request.getParameterNames();
		Map in_parameters = new HashMap();
		while(parameterNames.hasMoreElements()) {
			String name = (String) parameterNames.nextElement();
			String[] value = in_request.getParameterValues(name);
			if(value.length==1)
				in_parameters.put((String) name, value[0]);
			else
				in_parameters.put((String) name, value);
		}
		return getMultipleSuggestionList(in_request, in_parameters);
	}
	
	public abstract Collection getMultipleSuggestionList(HttpServletRequest in_request, Map in_map);

}
