package com.oyou.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * HTML view helper
 * 
 * @author Owen Ou
 */
public class HTMLHelper extends DebugHelper {
	private static final Log log = LogFactory.getLog(HTMLHelper.class);

	public static String ENTER = "\r";

	public static String HTML_ENTER = "<br/>";

	public static String PARAGRAPH_BEGIN = "<p>";
	public static String PARAGRAPH_END = "</p>";

	public static final String HTML_PATTERN = "[\\S\\s]*<[\\S\\s]*>[\\S\\s]*";
	
	public static final String CSS_SQL = "sql";
	
	public static String formatToHTML(String message) {
		Pattern p = Pattern.compile(HTML_PATTERN);
		Matcher m = p.matcher(message);
		if (StringHelper.isNotEmpty(message) && !m.matches()) {
			Pattern p1 = Pattern.compile(ENTER);
			Matcher m1 = p1.matcher(message);
			return PARAGRAPH_BEGIN + m1.replaceAll(PARAGRAPH_END + ENTER + PARAGRAPH_BEGIN) + PARAGRAPH_END;
		}
		return  message;
	}

	public static String formatToText(String message) {
		if (StringHelper.isNotEmpty(message) && message.indexOf(ENTER) == -1) {
			Pattern p = Pattern.compile(HTML_ENTER);
			Matcher m = p.matcher(message);
			return m.replaceAll(HTML_ENTER + ENTER);
		}
		return message;
	}

	public static String getHTMLTable(Object obj) {
		if (obj == null)
			return null;
		StringBuffer sb = new StringBuffer();
		sb.append("<table border=1 class="+CSS_SQL+">\n");
		sb.append(getHTMLTitle(new StringBuffer(), obj, obj.getClass()) + "\n");
		sb.append(getHTMLRow(new StringBuffer(), obj, obj.getClass()) + "\n");
		sb.append("</table>\n");
		return sb.toString();
	}

	public static String getHTMLTable(List objs) {
		StringBuffer sb = new StringBuffer();
		sb.append("<table border=1 class="+CSS_SQL+">\n");
		if (objs != null && objs.size() > 0) {
			Object obj = objs.get(0);
			sb.append(getHTMLTitle(new StringBuffer(), obj, obj.getClass()) + "\n");
		}	
		for (Iterator iter = objs.iterator(); iter.hasNext();) {
			Object obj = iter.next();
			log.debug("==Row type " + obj.getClass().getName());
			sb.append(getHTMLRow(new StringBuffer(), obj, obj.getClass()) + "\n");
		}
		sb.append("</table>\n");
		return sb.toString();
	}

	public static String getHTMLTableFromMap(List objs) {
		StringBuffer sb = new StringBuffer();
		sb.append("<table border=1 class="+CSS_SQL+">\n");
		if (objs != null && objs.size() > 0) {
			Map obj = (Map)objs.get(0);
			sb.append(getHTMLTitle(new StringBuffer(), obj, obj.getClass()) + "\n");
		}	
		for (Iterator iter = objs.iterator(); iter.hasNext();) {
			Map obj = (Map)iter.next();
			log.debug("==Row type " + obj.getClass().getName());
			sb.append(getHTMLRow(new StringBuffer(), obj, obj.getClass()) + "\n");
		}
		sb.append("</table>\n");
		return sb.toString();
	}
	
	private static String getHTMLTitle(StringBuffer sb, Object obj, Class clz) {
		sb.append("<thead class="+CSS_SQL+"><tr>");
		sb.append(getHTMLTitleColumns(new StringBuffer(), obj, clz));
		sb.append("</tr></thead>\n");
		return sb.toString();
	}

	private static String getHTMLTitleColumns(StringBuffer sb, Object obj, Class clz) {
		log.debug("==process class " + obj.getClass().getName());
		if (obj instanceof Map || obj instanceof HashMap) {
			Map map = (Map) obj;
			Set keys = map.keySet();
			if (!keys.isEmpty()) {
				Iterator it = keys.iterator();
				while (it.hasNext()) {
					sb.append("<th class="+CSS_SQL+">" + it.next() + "</th>");
				}
			}
		} else {
			Method[] methods = clz.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				String name = method.getName();
				if (!method.isSynthetic() && !method.isBridge() && (method.getModifiers() == Modifier.PUBLIC) && name.startsWith("get")) {
					sb.append("<th class="+CSS_SQL+">" + name.substring("get".length()) + "</th>");
				}
			}
			if (clz.getSuperclass() != null && (clz.getSuperclass().getName().indexOf("Entity") == -1)) {
				getHTMLTitleColumns(sb, obj, clz.getSuperclass());
			}	
		}
		return sb.toString();
	}

	private static String getHTMLRow(StringBuffer sb, Object obj, Class clz) {
		sb.append("<tbody class="+CSS_SQL+"><tr>");
		sb.append(getHTMLRowColumns(new StringBuffer(), obj, clz));
		sb.append("</tr></tbody>\n");
		return sb.toString();
	}

	private static String getHTMLRowColumns(StringBuffer sb, Object obj, Class clz) {
		log.debug("==process class " + obj.getClass().getName());
		if (obj instanceof Map || obj instanceof HashMap) {
			Map map = (Map) obj;
			Set keys = map.keySet();
			if (!keys.isEmpty()) {
				Iterator it = keys.iterator();
				while (it.hasNext()) {
					String key = (String)it.next();
					String value = map.get(key) == null? "" : map.get(key).toString();
					sb.append("<td class="+CSS_SQL+">" + value + "</td>");
				}
			}
		} else {
			Method[] methods = clz.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				String name = method.getName();
				try {
					// if (method.getModifiers() == Modifier.PUBLIC && name.startsWith("get")) {
					if (!method.isSynthetic() && !method.isBridge() && (method.getModifiers() == Modifier.PUBLIC) && name.startsWith("get") && methodFilter(name)) {
						sb.append("<td class="+CSS_SQL+">");
						Object value = method.invoke(obj, new Object[] {});
						sb.append(getObjectValueByType(value));
						sb.append("</td>");
					}
				} catch (IllegalAccessException iae) {
					iae.printStackTrace();
					log.equals(iae.getMessage());
				} catch (InvocationTargetException ite) {
					ite.printStackTrace();
				}
			}
			if (clz.getSuperclass() != null && (clz.getSuperclass().getName().indexOf("Entity") == -1)) {
				getHTMLRowColumns(sb, obj, clz.getSuperclass());
			}	
		}
		return sb.toString();
	}

}
