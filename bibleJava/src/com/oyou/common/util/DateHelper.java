package com.oyou.common.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Helper class for date type of SQL or Util
 * @author  Owen Ou
 */
public class DateHelper {
	public static final String DDMMMYYYY = "ddMMMyyyy";
	public static final String DD_MM_YY = "dd/MM/yy";
	public static final String DD_MM_YYYY = "dd/MM/yyyy";
	private static String DEFAULT_PATTERN = DDMMMYYYY;
	public static final String HHMMSS = " hh:mm:ss";
	public static final String HHMMSSA = " hh:mm:ss a";
	public static Locale locale = Locale.CANADA;
	public static final String MM_DD_YY = "MM/dd/yy";
	public static final String MM_DD_YYYY = "MM/dd/yyyy";
	public static final String MMMMDDYYYY = "MMMM dd, yyyy";
	public static final String YY_MM_DD = "yy/MM/dd";
	public static final String YYYY_MM_DD = "yyyy/MM/dd";
	public static final String YYYY = "yyyy";

	/**
	 * Add m months in date
	 * @param date
	 * @param m
	 * @return
	 */
	public final static Date addMonths(Date date, int m) {
		if (m == 0)
			return date;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, m);
		return new java.sql.Date(calendar.getTime().getTime());
	}

	/**
	 * from the calendar get sql date type
	 * @param calendar
	 * @return
	 */
	public final static Date createSQLDate(Calendar calendar) {
		return (new Date(calendar.getTime().getTime()));
	}

	/**
	 * create sql date type base on year, month and day
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public final static Date createSQLDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		java.util.Date date = calendar.getTime();
		Date sqlDate = new Date(date.getTime());
		return sqlDate;
	}

	/**
	 * get date string of the sql date based on the default date format MM/dd/yyyy 
	 * @param date
	 * @return
	 */
	public final static String formatSQLDate(java.sql.Date date) {
		return formatSQLDate(date, DEFAULT_PATTERN);
	}

	/**
	 * get date string of sql date based on the format pattern, for example  MMM dd yyyy
	 * @param date
	 * @param pattern
	 * @return
	 */
	public final static String formatSQLDate(java.sql.Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
		return sdf.format(date);
	}
    
	/**
	 * get current system sql type date
	 * @return
	 */
	public final static Date getCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * get current system sql type timestamp
	 * @return
	 */
	public final static Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * check sql type date is null or not
	 * @param date
	 * @return
	 */
	public final static boolean isNull(java.sql.Date date) {
		return (date == null);
	}

	/**
	 * check util type date is null or not
	 * @param date
	 * @return
	 */
	public final static boolean isNull(java.util.Date date) {
		return (date == null);
	}

	/**
	 * check sql type timestamp is null or not
	 * @param date
	 * @return
	 */
	public final static boolean isNull(Timestamp date) {
		return (date == null);
	}

	/**
	 * check the date string is valid date or not
	 * @param date
	 * @param pattern
	 * @return
	 */
	public final static boolean isValidDate(String date, String pattern) {
		Date sDate = null;
		try {
			sDate = new java.sql.Date((parseSQLDate(date, pattern)).getTime());
			if (sDate == null)
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * get the util type date based on the default format pattern MM/dd/yyyy
	 * @param date
	 * @return
	 * @throws java.text.ParseException
	 */
	public final static java.util.Date parseDate(String date) throws java.text.ParseException {
		return parseDate(date, DEFAULT_PATTERN);
	}

	/**
	 * get the util type date based on the format pattern, for example MMM dd yyyy
	 * @param date
	 * @param pattern
	 * @return
	 * @throws java.text.ParseException
	 */
	public final static java.util.Date parseDate(String date, String pattern) throws java.text.ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
		ParsePosition parsePos = new ParsePosition(0);
		sdf.setLenient(false);
		return sdf.parse(date, parsePos);
	}

	/**
	 * get the sql type date based on the default parse format pattern MM/dd/yyyy 
	 * @param date
	 * @return
	 * @throws IllegalArgumentException
	 */
	public final static java.sql.Date parseSQLDate(String date) throws IllegalArgumentException {
		return parseSQLDate(date, DEFAULT_PATTERN);
	}

	/**
	 * get the sql type date based on the parse format pattern, for example MMM dd yyyy
	 * @param date
	 * @param pattern
	 * @return
	 * @throws IllegalArgumentException
	 */
	public final static java.sql.Date parseSQLDate(String date, String pattern) throws IllegalArgumentException {
		java.util.Date uDate = null;
		java.sql.Date sDate = null;
		try {
			uDate = DateHelper.parseDate(date, pattern);
			sDate = new java.sql.Date(uDate.getTime());
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
		return sDate;
	}

	/**
	 * get sql type timestamp from the date sting
	 * @param date
	 * @return
	 */
	public final static java.sql.Timestamp parseSQLTimestamp(String date) {
		return java.sql.Timestamp.valueOf(date);
	}

}
