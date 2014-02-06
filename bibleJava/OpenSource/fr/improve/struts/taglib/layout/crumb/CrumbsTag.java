package fr.improve.struts.taglib.layout.crumb;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.PanelTag;
import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;
/**
 * Displays bread crumbs.
 * @author: Jean-Noël Ribette
 */
public class CrumbsTag extends PanelTag {
	/**
	 * does the inner crumb tag need to display a crumb separator before displaying the crumb<br>
	 * false for the first crumb<br>
	 * true for the followers
	 */
	protected boolean needSeparator = false;

	/**
	 * the current crumb level calculated by counting the number of time needSeparator() is count.
 	 * use to generate the css class to use for the crumbs
	 */
	protected int level = 0;

	/**
	 * the separator character<br>
	 * default: |
	 */
	protected String separator = "|";

	/**
	 * the css style clas to use if the sepatator is an image (in this case, the separator is defined as a background-image in the css file)
	 */
	 protected String separatorClass;
	 protected String separatorWidth;
	 protected String separatorHeight;
	 protected String separatorDivTag;
	 
	 /**
	  * If the crumbs should be taken in the context, name of the bean containing them.
	  */
	 protected String crumbsName;
	 protected String crumbsProperty;
	 
	public int doEndLayoutTag() throws JspException {
		StringBuffer buffer = new StringBuffer();
		
		if (crumbsName!=null && crumbsName.length()!=0) {
			Iterator lc_iterator = LayoutUtils.getIterator(pageContext, crumbsName, crumbsProperty);
			while (lc_iterator.hasNext()) {
				Crumb lc_crumb = (Crumb) lc_iterator.next();
				printCrumb(buffer, lc_crumb);
			}
		}
						
		buffer.append("</td></tr>");
		doAfterBody(buffer);
		doEndPanel(buffer);
		TagUtils.write(pageContext, buffer.toString());
		new EndLayoutEvent(this, null).send();
		
		// Reset the tag.
		needSeparator = false;
		level = 0;
		
		return EVAL_PAGE;
	}
	public int doStartLayoutTag() throws JspException {
		new StartLayoutEvent(this, null).send();
		StringBuffer buffer = new StringBuffer();
		doStartPanel(buffer);
		doPrintTitle(buffer);
		doBeforeBody(buffer);
		buffer.append("<tr><td>");
		TagUtils.write(pageContext, buffer.toString());
		return EVAL_BODY_INCLUDE;
	}
	public int getLevel() {
		return level;
	}
/**
 * Insert the method's description here.
 * @return java.lang.String
 */
public java.lang.String getSeparator() {
	if (separatorClass==null) return separator;
	if (separatorDivTag==null) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<div class=\"");
		buffer.append(separatorClass);
		buffer.append("\" width=\"");
		buffer.append(separatorWidth);
		buffer.append("\" height=\"");
		buffer.append(separatorHeight);
		buffer.append("\">&nbsp</div>");
		separatorDivTag = buffer.toString();
	}
	return separatorDivTag;
}

	/**
	 * Display a crumb.
	 */
	public void printCrumb(StringBuffer in_buffer, Crumb in_crumb) throws JspException {			
		if (needSeparator()) {
			in_buffer.append("&nbsp;");
			in_buffer.append(getSeparator());
			in_buffer.append("&nbsp;");
		}
		
		String lc_styleClass = getStyleClass();
		if (lc_styleClass!=null) lc_styleClass += getLevel();

		in_buffer.append("<a");
		String lc_link = in_crumb.getLink();
		if (lc_link!=null) {
			in_buffer.append(" href=\"");
			if (!lc_link.startsWith("javascript:")) {
				in_buffer.append(((HttpServletRequest)pageContext.getRequest()).getContextPath());
			}
			in_buffer.append(lc_link);
			in_buffer.append("\"");
			if (in_crumb.getTarget()!=null) {
				in_buffer.append(" target=\"");
				in_buffer.append(in_crumb.getTarget());
				in_buffer.append("\"");
			}
		}
		if (lc_styleClass!=null) {
			in_buffer.append(" class=\"");
			in_buffer.append(lc_styleClass);
			in_buffer.append("\"");
		}
		in_buffer.append(">");
		
		String lc_bundle = in_crumb.getBundle();
		if (lc_bundle==null) {
			lc_bundle = bundle;
		}
		in_buffer.append(LayoutUtils.getLabel(pageContext, lc_bundle, in_crumb.getKey(), null, false));
		in_buffer.append("</a>");		
	}

/**
 * Insert the method's description here.
 * Creation date: (18/07/2001 14:38:59)
 * @return java.lang.String
 */
public java.lang.String getSeparatorClass() {
	return separatorClass;
}
/**
 * Insert the method's description here.
 * Creation date: (18/07/2001 14:38:59)
 * @return java.lang.String
 */
public java.lang.String getSeparatorHeight() {
	return separatorHeight;
}
/**
 * Insert the method's description here.
 * Creation date: (18/07/2001 14:38:59)
 * @return java.lang.String
 */
public java.lang.String getSeparatorWidth() {
	return separatorWidth;
}
	public boolean needSeparator() {
		boolean value = needSeparator;
		needSeparator = true;
		level++;
		return value;
	}
	public void release() {
		super.release();
		
		needSeparator = false;
		level = 0;
		separatorClass = null;
		separatorHeight = null;
		separatorWidth = null;
		separatorDivTag = null;
		
		crumbsName = null;
		crumbsProperty = null;
	}
/**
 * Insert the method's description here.
 * Creation date: (17/07/2001 17:01:10)
 * @param newSeparator java.lang.String
 */
public void setSeparator(java.lang.String newSeparator) {
	separator = newSeparator;
}
/**
 * Insert the method's description here.
 * Creation date: (18/07/2001 14:38:59)
 * @param newSeparatorClass java.lang.String
 */
public void setSeparatorClass(java.lang.String newSeparatorClass) {
	separatorClass = newSeparatorClass;
}
/**
 * Insert the method's description here.
 * Creation date: (18/07/2001 14:38:59)
 * @param newSeparatorHeight java.lang.String
 */
public void setSeparatorHeight(java.lang.String newSeparatorHeight) {
	separatorHeight = newSeparatorHeight;
}
/**
 * Insert the method's description here.
 * Creation date: (18/07/2001 14:38:59)
 * @param newSeparatorWidth java.lang.String
 */
public void setSeparatorWidth(java.lang.String newSeparatorWidth) {
	separatorWidth = newSeparatorWidth;
}
	/**
	 * Gets the crumbsName.
	 * @return Returns a String
	 */
	public String getCrumbsName() {
		return crumbsName;
	}

	/**
	 * Sets the crumbsName.
	 * @param crumbsName The crumbsName to set
	 */
	public void setCrumbsName(String crumbsName) {
		this.crumbsName = crumbsName;
	}

	/**
	 * Gets the crumbsProperty.
	 * @return Returns a String
	 */
	public String getCrumbsProperty() {
		return crumbsProperty;
	}

	/**
	 * Sets the crumbsProperty.
	 * @param crumbsProperty The crumbsProperty to set
	 */
	public void setCrumbsProperty(String crumbsProperty) {
		this.crumbsProperty = crumbsProperty;
	}

}
