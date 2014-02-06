package fr.improve.struts.taglib.layout.field;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.util.LayoutUtils;
/**
 * Base clas for unique value select tag.
 * @author: Jean-Noël Ribette
 */
public abstract class AbstractUniqueSelectTag extends AbstractSelectTag {
	protected String match;
	private String value;
	
/**
 * Insert the method's description here.
 * Creation date: (01/12/2001 22:32:19)
 * @return java.lang.String
 */
public java.lang.String getMatch() {
	return match;
}
/**
 * Return the value to display.
 * Note that this value will be test to check if the field should be displayed or not.
 */
protected Object getFieldValue() throws JspException {
	Object lc_value;
	if (match!=null) {
		lc_value = match;
	} else if (value!=null) {
		lc_value = value;
		match = value;
	} else {	
		lc_value = LayoutUtils.getBeanFromPageContext(pageContext, name, property);
		if (lc_value==null) match = ""; else match = lc_value.toString();
	}
	if (lc_value!=null && lc_value.toString().length()==0) return null;
	return lc_value;
}
	protected void reset() {
		super.reset();
		match = null;
	}
	
	public void release() {
		super.release();
		value = null;	
	}
	
	/**
	 * Insert the method's description here.
	 * Creation date: (01/12/2001 22:32:19)
	 * @param newMatch java.lang.String
	 */
	public void setMatch(java.lang.String newMatch) {
		match = newMatch;
	}
	
	/**
	 * Returns the value.
	 * @return String
	 */
	public final String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 * @param value The value to set
	 */
	public final void setValue(String value) {
		this.value = value;
	}

}
