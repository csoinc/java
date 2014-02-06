package fr.improve.struts.taglib.layout.sort;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * This class contains useful methods to deal with javascript collection sorting on the client.
 * 
 * @author JN Ribette
 *
 */
public class JavascriptSortUtil {
	private static final String ID_KEY = "fr.improve.struts.taglib.layout.sort.JavascriptSortUtil.ID_KEY";
	/**
	 * Make it possible to sort numbers and date by prefixing those objects by an hidden string 
	 * which will be sortable by the browser.
	 */
	public static final String makeSortable(Object in_object) {
		if (in_object==null) {
			return null;
		} else if (in_object instanceof Number) {			
			double lc_double = ((Number) in_object).doubleValue();
			StringBuffer lc_buffer = new StringBuffer(20);
			lc_buffer.append("<!-- ");
			lc_buffer.append(Double.doubleToLongBits(lc_double));
			lc_buffer.append(" -->");			
			return lc_buffer.toString();
		} else if (in_object instanceof Date) {
			long lc_long = ((Date)in_object).getTime();
			StringBuffer lc_buffer = new StringBuffer(20);
			lc_buffer.append("<!-- ");
			lc_buffer.append(Long.toHexString(lc_long));
			lc_buffer.append(" -->");
			return lc_buffer.toString();
		} else {
			return null;
		}
	}
	/**
	 * Returns a unique id for the collection. This allows to different several collections in a jsp.
	 */
	public static String getId(HttpServletRequest in_request) {
		Integer lc_oldId = (Integer) in_request.getSession().getAttribute(ID_KEY);
		if (lc_oldId==null) {
			lc_oldId = new Integer(0);
		}
		int lc_int = lc_oldId.intValue();
		if (lc_int == 100) {
			lc_int = 0;	
		}
		Integer lc_newId = new Integer(lc_int + 1);
		in_request.getSession().setAttribute(ID_KEY, lc_newId);
		return lc_newId.toString();
	}
	
	/**
	 * Returns the value that will be returned by the next call to getId().
	 */
	public static String predictNextId(HttpServletRequest in_request) {
		Integer lc_oldId = (Integer) in_request.getSession().getAttribute(ID_KEY);
		if (lc_oldId==null) {
			lc_oldId = new Integer(0);
		}
		int lc_int = lc_oldId.intValue();
		if (lc_int == 100) {
			lc_int = 0;	
		}
		Integer lc_newId = new Integer(lc_int + 1);	
		return lc_newId.toString();
	}
}
