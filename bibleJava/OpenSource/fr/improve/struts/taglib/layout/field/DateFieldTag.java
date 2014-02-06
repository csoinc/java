package fr.improve.struts.taglib.layout.field;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.apache.struts.Globals;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.html.FormTag;

import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.event.StaticCodeIncludeLayoutEvent;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;
/**
 * TextFieldTag which allows to type a date, set the formatter to "date" and display a calendar to help typing the date.
 *
 * @author: Jean-No?l Ribette
 */
public class DateFieldTag extends TextFieldTag {
	public static final String CALENDAR_KEY = "fr.improve.struts.taglib.layout.field.DateFieldTag.CLANDER_KEY";
	private static final Map dateSymbols = new HashMap();

	/**
	 * Special date format pattern overriding the default pattern for the current struts locale.
	 */
	protected String patternKey;
	protected String pattern;
	protected String startYear;
	protected String endYear; 
	protected String calendarTooltip;
	
	public DateFieldTag() {
		// format = "date";
	}
protected void doAfterValue() throws JspException {
    if (getFieldDisplayMode() == MODE_EDIT) {
        Locale lc_locale = findLocale();
        StringBuffer buffer = new StringBuffer();
        buffer.append("<a href=\"javascript://\" onclick=\"showCalendar(");
		
		Object lc_value = getFieldValue();		
		Calendar lc_date = parseDate(lc_value==null ? "" : lc_value.toString(), lc_locale);
		String lc_year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		
        buffer.append(lc_date.get(Calendar.YEAR));
        buffer.append(",");
        buffer.append(lc_date.get(Calendar.MONTH) + 1);
        buffer.append(",");
        buffer.append(lc_date.get(Calendar.DAY_OF_MONTH));
        buffer.append(",");
        if (pattern==null) {
        	buffer.append("null");
        } else {
        	buffer.append("'");
        	buffer.append(pattern);
        	buffer.append("'");
        }	        
        buffer.append(",'");
        FormTag lc_formTag = (FormTag) pageContext.findAttribute(Constants.FORM_KEY);
        if (lc_formTag==null) {
        	throw new JspException("<layout:date> tag is not nested into a <html:form> or <layout:form> tag");
        }
        String lc_formName = lc_formTag.getBeanName(); // For Struts 1.0, the property to use is name.         
        buffer.append(lc_formName);
        buffer.append("','");
        buffer.append(property);
        buffer.append("',event,");
        buffer.append(startYear==null ? String.valueOf(lc_date.get(Calendar.YEAR)) : Expression.evaluate(startYear,pageContext));
        buffer.append(",");
        buffer.append(endYear==null  ? (startYear==null ? String.valueOf(lc_date.get(Calendar.YEAR)+2) : lc_year) : Expression.evaluate(endYear, pageContext));
        buffer.append(");");

        buffer.append("\"><img alt=\"");
        buildCalendarTooltip(buffer);
        buffer.append("\" border=\"0\" src=\"");

        buffer.append(getSkin().getImageDirectory(pageContext.getRequest()));
        buffer.append("/");
        buffer.append(getSkin().getProperty("layout.calendar"));
        buffer.append("\"></a>");

        if (pageContext.getRequest().getAttribute(CALENDAR_KEY)==null) {
        	// include thre required HTML / javascript code.
        	StringBuffer lc_buffer2 = new StringBuffer();
        	includeClientCode(lc_buffer2, lc_date, lc_locale);
        	buffer.append(new StaticCodeIncludeLayoutEvent(this, lc_buffer2.toString()).send());
        	pageContext.getRequest().setAttribute(CALENDAR_KEY, "");
        }
        TagUtils.write(pageContext, buffer.toString());
    }
}

	/**
	 * Find the Locale to use. 
	 * @return the Struts Locale if set, the browser Locale otherwise. 	 
	 */
	protected Locale findLocale() {
		Locale lc_locale = (Locale) pageContext.findAttribute(Globals.LOCALE_KEY);
		if (lc_locale==null) {
			lc_locale = pageContext.getRequest().getLocale();	
		}
	return lc_locale;
	}
	
	protected void buildCalendarTooltip(StringBuffer buffer) throws JspException {
		if (calendarTooltip==null) {
			buffer.append("pick up a date");
		} else {
			buffer.append(LayoutUtils.getLabel(pageContext, bundle, calendarTooltip, null, false));
		}
	}
	
	/**
	 * Include the html and javascript required code for the calendar.
	 */
	protected void includeClientCode(StringBuffer out_buffer, Calendar in_calendar, Locale in_locale) {
	        	out_buffer.append("<div id=\"slcalcod\" style=\"position:absolute; left:100px; top:100px; z-index:10; visibility:hidden;\"><script>printCalendar(");
	
				DateFormatSymbols lc_symbols = (DateFormatSymbols) dateSymbols.get(in_locale);
				if (lc_symbols==null) {
					lc_symbols = new DateFormatSymbols(in_locale);
					dateSymbols.put(in_locale, lc_symbols);
				}
				String[] lc_daySymbols = lc_symbols.getShortWeekdays();
				String[] lc_monthSymbols = lc_symbols.getShortMonths();								
				
				// printing the content of the arrays from 0 to array.length does'nt work (the arrays contains null elements ?)
				out_buffer.append("\"").append(lc_daySymbols[Calendar.SUNDAY]).append("\",");
				out_buffer.append("\"").append(lc_daySymbols[Calendar.MONDAY]).append("\",");
				out_buffer.append("\"").append(lc_daySymbols[Calendar.TUESDAY]).append("\",");
				out_buffer.append("\"").append(lc_daySymbols[Calendar.WEDNESDAY]).append("\",");
				out_buffer.append("\"").append(lc_daySymbols[Calendar.THURSDAY]).append("\",");
				out_buffer.append("\"").append(lc_daySymbols[Calendar.FRIDAY]).append("\",");
				out_buffer.append("\"").append(lc_daySymbols[Calendar.SATURDAY]).append("\",");
				out_buffer.append(in_calendar.getFirstDayOfWeek()).append(",");				
				
				out_buffer.append("\"").append(lc_monthSymbols[Calendar.JANUARY]).append("\",");
				out_buffer.append("\"").append(lc_monthSymbols[Calendar.FEBRUARY]).append("\",");
				out_buffer.append("\"").append(lc_monthSymbols[Calendar.MARCH]).append("\",");
				out_buffer.append("\"").append(lc_monthSymbols[Calendar.APRIL]).append("\",");
				out_buffer.append("\"").append(lc_monthSymbols[Calendar.MAY]).append("\",");
				out_buffer.append("\"").append(lc_monthSymbols[Calendar.JUNE]).append("\",");
				out_buffer.append("\"").append(lc_monthSymbols[Calendar.JULY]).append("\",");
				out_buffer.append("\"").append(lc_monthSymbols[Calendar.AUGUST]).append("\",");
				out_buffer.append("\"").append(lc_monthSymbols[Calendar.SEPTEMBER]).append("\",");
				out_buffer.append("\"").append(lc_monthSymbols[Calendar.OCTOBER]).append("\",");
				out_buffer.append("\"").append(lc_monthSymbols[Calendar.NOVEMBER]).append("\",");
				out_buffer.append("\"").append(lc_monthSymbols[Calendar.DECEMBER]).append("\",");
				out_buffer.append(in_calendar.get(Calendar.DAY_OF_MONTH)).append(",");
				out_buffer.append(in_calendar.get(Calendar.MONTH)+1).append(",");
				out_buffer.append(in_calendar.get(Calendar.YEAR));
	        	out_buffer.append(");</script></div>");	
	}
	
	
	public void release() {
		super.release();
		//format = "date";
		patternKey = null;
		pattern = null;
		startYear = null;
		endYear = null;
		calendarTooltip = null;
	}
	
	/**
	 * Try to parse the date...
	 */
	protected Calendar parseDate(Object in_date, Locale in_locale) throws JspException {
		Calendar lc_calendar = Calendar.getInstance(in_locale);		
		SimpleDateFormat lc_format = null;
		if (patternKey==null) {
			// Default format for the given locale.
			lc_format = (SimpleDateFormat) SimpleDateFormat.getDateInstance(DateFormat.SHORT, in_locale);				
			pattern = lc_format.toPattern();
		} else {
			// Custom format, first look in the specified bundle 
			pattern = LayoutUtils.getLabel(pageContext, bundle, patternKey, null, true);
			if (pattern==null) {
				// If not found, look in the default bundle.
				pattern = LayoutUtils.getLabel(pageContext, patternKey, null);
			}
			lc_format = new SimpleDateFormat(pattern);
		}
		if (in_date!=null && in_date.toString().length()>0) {
			ParsePosition lc_position = new ParsePosition(0);
			Date lc_date = lc_format.parse(in_date.toString(), lc_position);
			if (lc_position.getIndex()==in_date.toString().length() && lc_date!=null) {
				lc_calendar.setTime(lc_date);
			}
		}
		return lc_calendar;
	}
	/**
	 * Sets the patternKey.
	 * @param patternKey The patternKey to set
	 */
	public void setPatternKey(String patternKey) {
		this.patternKey = patternKey;
	}

	public void setEndYear(String in_string) {
		endYear = in_string;
	}

	public void setStartYear(String in_string) {
		startYear = in_string;
	}
	
	public void setCalendarTooltip(String in_string) {
		calendarTooltip = in_string;
	}

}
