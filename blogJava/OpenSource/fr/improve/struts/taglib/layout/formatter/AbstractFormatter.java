package fr.improve.struts.taglib.layout.formatter;

import javax.servlet.jsp.PageContext;
/**
 * Struts-Layout abstract formatter class.<br/>
 * 
 * A formatter is a class that is responsible for formatting data.
 * This class is intended to be subclassed directly.
 * The formatter class must be defined in the skin configuration file.
 * 
 * @author: Jean-Noel Ribette
 * @see fr.improve.struts.taglib.layout.formatter.DispatchFormatter
 */
public abstract class AbstractFormatter {
	/**
	 * Format the specified in_value object with the specified in_format format.
	 * @param in_value	the value to format, which can be null.
	 * @param in_format the format to apply. This is the value specified in the type attribute of the struts-layout tags supporting this attribute.
	 * @patam in_pageContext	the current pageContext 
	 */
	public abstract String format(Object in_value, String in_format, PageContext in_pageContext) throws FormatException;
}
