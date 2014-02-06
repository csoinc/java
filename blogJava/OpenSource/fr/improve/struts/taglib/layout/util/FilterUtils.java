package fr.improve.struts.taglib.layout.util;

/**
 * Filter characters.
 */
public class FilterUtils {
	/**
	 * Filter simple and double quotes.
	 * Suppose the Javascript string is delimited by simple quotes.
	 */
	public static String filterQuotes(String in_string) {
		StringBuffer result = new StringBuffer(in_string);
		for (int i=0; i < result.length(); i++) {
			switch (result.charAt(i)) {
				case '\'':
					// just add an antislash to escpae the quote.
					result.insert(i, '\\');
					i++;
					break;
				case '\"':
					// need to convert the double quote to ascii.
					result.replace(i, i+1, "' + String.fromCharCode(34) + '");					
					i+=30;
					break;
			}			
		}
		return result.toString();
	}
}
