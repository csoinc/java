package fr.improve.struts.taglib.layout.util;

import java.util.List;
import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.field.AbstractLayoutFieldTag;

/**
 * Interface implemented by an object capable of displaying a field.
 * 
 * A field as a label and a value.
 * 
 * @author jer80876
 */
public interface FieldInterface {
	public void doStartField(StringBuffer in_buffer, AbstractLayoutFieldTag in_tag, String in_label, Object in_value) throws JspException;
	public void doEndField(StringBuffer in_buffer, AbstractLayoutFieldTag in_tag, Object in_value) throws JspException;
	public void doStartErrors(StringBuffer in_buffer, AbstractLayoutFieldTag in_tag, List in_errors);
	public void doEndErrors(StringBuffer in_buffer, AbstractLayoutFieldTag in_tag, List in_errors) throws JspException;
	public int getColumnNumber();
}
