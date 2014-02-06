package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;
import fr.improve.struts.taglib.layout.util.LayoutUtils;

/**
 * Base class for the tags dealing with form input
 * The tag renders html code looking like: &lt;tr&gt;&lt;th&gt; input field title &lt;/th&gt;&lt;td&gt; input field &lt;/td&gt;&lt;/tr&gt;
 *
 * @author: Jean-Noël Ribette
 * @deprecated
 */
public abstract class BaseFieldTag extends LabelledTag {

/**
 * Append the title of the field to the buffer
 */
protected void beginField(StringBuffer buffer) throws JspException {
	buffer.append("<tr><th class=");
	buffer.append(styleClass);
	buffer.append("><span class=");
	buffer.append(styleClass);
	buffer.append(">");
	if (key!=null) buffer.append(getLabel()); else buffer.append("&nbsp;");
	buffer.append("</span></th><td class=");
	buffer.append(styleClass);
	buffer.append(">");	
}
/**
 * End the field (close the html tags)
 */
protected void endField(StringBuffer buffer) {
	buffer.append("</td></tr>\n");
}

public void setProperty(String property) {
	this.property = property;
	if (LayoutUtils.getNoErrorMode()) 
			this.property = "property";
}
}