package com.oyou.common.util;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {

	public static final String XSS_PATTERN = "<[sS][cC][rR][iI][pP][tT]\\s[\\S\\s]*</[sS][cC][rR][iI][pP][tT]>";
	public static final String XSS_BEGIN_PATTERN = "<[sS][cC][rR][iI][pP][tT]\\s[\\s\\S&&[^>]]*>";
    public static final String XSS_END_PATTERN = "</[sS][cC][rR][iI][pP][tT]>";

	public static String binaryValue(boolean b) {
		if (b)
			return "1";
		else
			return "0";
	}

	public static String binaryValue(String s) {
		if (s == null)
			return "";
		if (equals(s, "on") || equals(s, "yes"))
			s = "true";
		if (equals(s, "off") || equals(s, "no"))
			s = "false";
		boolean b = new Boolean(s).booleanValue();
		return binaryValue(b);
	}

	public static boolean booleanValue(String s) {
		if (s == null)
			return false;
		if (equals(s, "on") || equals(s, "yes") || equals(s, "1") || equals(s, "true"))
			return true;
		if (equals(s, "off") || equals(s, "no") || equals(s, "0") || equals(s, "false"))
			return false;
		return false;
	}

	public static double doubleValueOf(String s) {
		return isEmpty(s) ? 0 : Double.parseDouble(s.trim());
	}

	public static double doubleValueOfFormatString(String s) {
		DecimalFormat fmt = new DecimalFormat("#,###.##");
		Number n = fmt.parse(s, new ParsePosition(0));
		return n.doubleValue();
	}

	public static boolean equals(String s1, String s2) {
		if (s1 == null && s2 == null)
			return true;
		if (s1 == null || s2 == null)
			return false;
		return s1.toLowerCase().equalsIgnoreCase(s2.toLowerCase());
	}

	public static String formatNumber(double d, String pattern) {
		DecimalFormat f = new DecimalFormat();
		f.applyPattern(pattern);
		return f.format(d);
	}

	public static boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}

	public static boolean isLetter(String s, int index) {
		if (s == null || s.trim().length() <= index)
			return false;
		return Character.isLetter(s.charAt(index));
	}

	public static boolean isNotEmpty(String s) {
		return s != null && s.trim().length() > 0;
	}

	public static boolean isNumber(String n) {
		try {
			Integer.parseInt(n);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static final int length(String str) {
		return (null == str) ? 0 : str.length();
	}

	public static long longValueOf(String s) {
		return isEmpty(s) ? 0 : Long.parseLong(s.trim());
	}

	public static String replace(String src, String oldStr, String newStr) {
		if (src == null)
			return null;
		StringBuffer buffer = new StringBuffer(src);
		replace(buffer, oldStr, newStr);
		return buffer.toString();
	}

	public static void replace(StringBuffer buffer, String oldStr, String newStr) {
		if (buffer == null || buffer.toString().trim().length() == 0)
			return;
		if (oldStr == null || newStr == null)
			return;
		int index = buffer.toString().indexOf(oldStr);
		while (index >= 0) {
			buffer.delete(index, index + oldStr.length());
			buffer.insert(index, newStr);
			index = buffer.toString().indexOf(oldStr, index + newStr.length());
		}
	}

	public static String toString(Object o) {
		if (o == null)
			return new String();
		else
			return o.toString();
	}

	public static int valueOf(String s) {
		return isEmpty(s) ? 0 : Integer.parseInt(s.trim());
	}
	
	public static String patternValue(String word) {
		StringBuffer sb = new StringBuffer();
		sb.append("%");
		StringTokenizer st = new StringTokenizer(word, " ,;");
		while (st.hasMoreTokens()) {
			sb.append(st.nextToken());
			sb.append("%");
		}
		return sb.toString();
	}

	public static String phraseValue(String word) {
		StringBuffer sb = new StringBuffer();
		if (word != null) word = word.trim();
		sb.append("%");
		sb.append(word);
		sb.append("%");
		return sb.toString();
	}
	
	/**
	 * @deprecated
	 * @param s
	 * @return
	 */
	public static String disableXSS(String s) {
		if (isEmpty(s)) return s;
		s = s.replaceAll("<", "&lt;");
		s = s.replaceAll(">", "&gt;"); 
		s = s.replaceAll("&lt;br/&gt;", "<br/>"); 
		s = s.replaceAll("&lt;br&gt;", "<br>"); 
		//s = s.replaceAll("(", "&#40;");
		//s = s.replaceAll(")", "&#41;"); 
		// "#" = "&#35";
		// "&" = "&#38" 
		return s;
	}

	public static String filterXSS(String s) {
		return XSSFilter(s);
	}
	
	public static String XSSFilter(String message) {
		if (StringHelper.isNotEmpty(message)) {
			Pattern p = Pattern.compile(XSS_PATTERN);
			Matcher m = p.matcher(message);
			return m.replaceAll("");
		}
		return null;
	}
	
	
}
