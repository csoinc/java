package fr.improve.struts.taglib.layout.field;

import java.util.Collection;
import java.util.ArrayList;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.util.LayoutUtils;
/**
 * Base class for multiple select tag.
 * @author: Jean-Noël Ribette
 */
public abstract class AbstractMultipleSelectTag extends AbstractSelectTag {
	protected Collection matches;
	private static final Collection EMPTY_COLLECTION = new ArrayList();
/**
 * Return the value to display.
 * Note that this value will be test to check if the field should be displayed or not.
 */
protected Object getFieldValue() throws JspException {
	Collection lc_value;
	if (matches==null) {
		lc_value = LayoutUtils.getCollection(LayoutUtils.getBeanFromPageContext(pageContext, name, property));
		if (lc_value==null) matches = EMPTY_COLLECTION; else matches = lc_value;
	} else {
		lc_value = matches;
	}
	if (lc_value==null || lc_value.isEmpty()) return null;
	return lc_value;
}
protected void reset() {
	super.reset();	
   matches = null;	
}
public void setMatches(Collection in_matches) {
	matches = in_matches;
}
}
