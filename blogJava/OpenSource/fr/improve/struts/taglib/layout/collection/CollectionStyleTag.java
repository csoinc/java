package fr.improve.struts.taglib.layout.collection;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.Constants;

import fr.improve.struts.taglib.layout.util.LayoutUtils;
/**
 * Allow to fix the style class of the inner collectionItem tags.
 * @author: JeanNoel Ribette
 */
public class CollectionStyleTag extends javax.servlet.jsp.tagext.TagSupport {
	protected String name = Constants.BEAN_KEY;
	protected String property;
	protected String value;
	protected String valueName;
	protected String valueProperty;
	protected String matchStyleClass;
	protected String nonMatchStyleClass;
	protected String matchStyle;
	protected String nonMatchStyle;

	protected String style;
	protected String styleClass;

	protected BaseCollectionTag collectionTag;
	
	protected boolean notNull = false;
	protected String greaterThan;
	protected String lessThan;

public int doEndTag() {
	if (styleClass!=null) {
		collectionTag.setTempStyleClass(null);
		styleClass = null;
	}
	if (style!=null) {
		collectionTag.removeTempStyle();
		style = null;
	}	
	collectionTag = null;
	return EVAL_PAGE;
}
public int doStartTag() throws JspException {
	try {		
		// get the collection
		collectionTag = (BaseCollectionTag) findAncestorWithClass(this, BaseCollectionTag.class);
		if (collectionTag==null) throw new JspException("Invalid use of collectionStyle tag");
		if (collectionTag.first) return EVAL_BODY_INCLUDE;

		// calculate the styleClass
		if (checkCondition()) {
			styleClass = matchStyleClass; 
			style = matchStyle;
		} else {
			styleClass = nonMatchStyleClass;
			style = nonMatchStyle;
		}

		// set the styleClass for the inner item.
		if (styleClass!=null) {
			collectionTag.setTempStyleClass(styleClass);
		}
		if (style!=null) {
			collectionTag.addTempStyle(style);
		}
		
	} catch (ClassCastException e) {
		throw new JspException("Invalid use of collectionStyle tag");
	}
		
	return EVAL_BODY_INCLUDE;
}

	protected boolean checkCondition() throws JspException {
		Object lc_value = pageContext.findAttribute(name);
		if (lc_value!=null) {
			lc_value = LayoutUtils.getProperty(lc_value, property);
		}		
		
		if (greaterThan!=null) {
			return checkGreaterThan(lc_value);
		}else if (lessThan!=null) {
			return checkLessThan(lc_value);
		} else if (notNull) {
			return lc_value!=null;
		} else {
		
			Object lc_valueToCheck = null;
			if (valueName!=null) {
				lc_valueToCheck = pageContext.findAttribute(valueName);
				if (lc_valueToCheck!=null) lc_valueToCheck = LayoutUtils.getProperty(lc_valueToCheck, valueProperty);
			} else {
				lc_valueToCheck = value;
			}
			
			if (lc_valueToCheck!=null && lc_value!=null && lc_valueToCheck.toString().equals(lc_value.toString())) {
				return true;
			} else {
				return false;
			}
		}
	}

	private boolean checkLessThan(Object in_value) {
		return checkGreaterThan(lessThan, in_value);
	}

	private boolean checkGreaterThan(Object in_value) {		
		return checkGreaterThan(in_value, greaterThan);
	}
	
	private boolean checkGreaterThan(Object in_value, Object greaterThan) {
		if (in_value==null || greaterThan==null) {
			return false;
		}
		
		Number n1;
		Number n2;
		if (in_value instanceof Number) {
			n1 = (Number) in_value;
		} else {
			n1 = new Float(in_value.toString());
		}
		if (greaterThan instanceof Number) {
			n2 = (Number) greaterThan;
		} else {
			n2 = new Float(greaterThan.toString());
		}
		return n1.doubleValue() >= n2.doubleValue();
	}
/**
 * Insert the method's description here.
 * Creation date: (09/10/2001 16:24:12)
 * @return java.lang.String
 */
public java.lang.String getMatchStyleClass() {
	return matchStyleClass;
}
/**
 * Insert the method's description here.
 * Creation date: (09/10/2001 16:14:52)
 * @return java.lang.String
 */
public java.lang.String getName() {
	return name;
}
/**
 * Insert the method's description here.
 * Creation date: (09/10/2001 16:24:12)
 * @return java.lang.String
 */
public java.lang.String getNonMatchStyleClass() {
	return nonMatchStyleClass;
}
/**
 * Insert the method's description here.
 * Creation date: (09/10/2001 16:14:52)
 * @return java.lang.String
 */
public java.lang.String getProperty() {
	return property;
}
/**
 * Insert the method's description here.
 * Creation date: (09/10/2001 16:14:52)
 * @return java.lang.String
 */
public java.lang.String getValue() {
	return value;
}
/**
 * Insert the method's description here.
 * Creation date: (16/10/2001 12:58:47)
 * @return java.lang.String
 */
public java.lang.String getValueName() {
	return valueName;
}
/**
 * Insert the method's description here.
 * Creation date: (16/10/2001 12:58:48)
 * @return java.lang.String
 */
public java.lang.String getValueProperty() {
	return valueProperty;
}
	public void release() {
		super.release();
		name = Constants.BEAN_KEY;
		property = null;
		value = null;
		valueName = null;
		valueProperty = null;
		matchStyleClass = null;
		nonMatchStyleClass = null;
		matchStyle = null;
		nonMatchStyle = null;
		collectionTag = null;
		
		greaterThan = null;
		lessThan = null;
	}
/**
 * Insert the method's description here.
 * Creation date: (09/10/2001 16:24:12)
 * @param newMatchStyleClass java.lang.String
 */
public void setMatchStyleClass(java.lang.String newMatchStyleClass) {
	matchStyleClass = newMatchStyleClass;
}
/**
 * Insert the method's description here.
 * Creation date: (09/10/2001 16:14:52)
 * @param newName java.lang.String
 */
public void setName(java.lang.String newName) {
	name = newName;
}
/**
 * Insert the method's description here.
 * Creation date: (09/10/2001 16:24:12)
 * @param newNonMatchStyleClass java.lang.String
 */
public void setNonMatchStyleClass(java.lang.String newNonMatchStyleClass) {
	nonMatchStyleClass = newNonMatchStyleClass;
}
/**
 * Insert the method's description here.
 * Creation date: (09/10/2001 16:14:52)
 * @param newProperty java.lang.String
 */
public void setProperty(java.lang.String newProperty) {
	property = newProperty;
}
/**
 * Insert the method's description here.
 * Creation date: (09/10/2001 16:14:52)
 * @param newValue java.lang.String
 */
public void setValue(java.lang.String newValue) {
	value = newValue;
}
/**
 * Insert the method's description here.
 * Creation date: (16/10/2001 12:58:48)
 * @param newValueName java.lang.String
 */
public void setValueName(java.lang.String newValueName) {
	valueName = newValueName;
}
/**
 * Insert the method's description here.
 * Creation date: (16/10/2001 12:58:48)
 * @param newValueProperty java.lang.String
 */
public void setValueProperty(java.lang.String newValueProperty) {
	valueProperty = newValueProperty;
}
	/**
	 * Sets the matchStyle.
	 * @param matchStyle The matchStyle to set
	 */
	public void setMatchStyle(String matchStyle) {
		this.matchStyle = matchStyle;
	}

	/**
	 * Sets the nonMatchStyle.
	 * @param nonMatchStyle The nonMatchStyle to set
	 */
	public void setNonMatchStyle(String nonMatchStyle) {
		this.nonMatchStyle = nonMatchStyle;
	}
	
	public void setPresent(boolean in_bool) {
		notNull = in_bool;
	}
	
	public void setGreaterThan(String in_greater) {
		greaterThan = in_greater;
	}
	
	public void setLessThan(String in_less){
		lessThan = in_less;
	}

}
