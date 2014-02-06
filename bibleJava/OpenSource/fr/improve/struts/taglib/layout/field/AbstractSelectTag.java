package fr.improve.struts.taglib.layout.field;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.Constants;

import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Superclass of the radios and checkboxes tag.
 * @author: JeanNoel Ribette
 */
public abstract class AbstractSelectTag extends AbstractLayoutFieldTag implements ChoiceTag {
	protected String accesskey;
	protected String maxlength;
	protected String onblur;
	protected String onclick;
	protected String ondblclick;
	protected String onfocus;
	protected String onkeydown;
	protected String onkeyup;
	protected String onkeypress;
	protected String onmouseup;
	protected String onmouseout;
	protected String onmousemove;
	protected String onmouseover;
	protected String onmousedown;
	protected String onchange;	
	
	/**
	 * Numbers of cols in the table
	 */
	protected int cols = 0;
	protected int index = 0;

	/**
	 * special default values for open selection<br>
	 * if one choice value equals to this value, a text input field is added after the select list.
	 */
	public static final String OTHER_KEY = "struts.layout.other";
 	protected String otherKey = "struts.layout.other";
	protected boolean need_other = false;
	protected String otherProperty;
	protected String otherValue;
	public AbstractSelectTag() {
		name = Constants.BEAN_KEY;
	}
	/**
	 * Implemanted by subclasses (CheckboxesTag & RadiosTag).<br>
	 * This method should print into the buffer the html code responsible for adding the value.
	 * @return true if the value is pre-selected, false otherwise.
	 */
	public abstract boolean add1Choice(StringBuffer sb, String value) throws JspException ;
	
	/**
	 * This method will be call by the &lt;layout:option&gt; and &lt;layout:options&gt; tag to add a choice.
	 */
	public final void addChoice(StringBuffer out_buffer, Choice in_choice) throws JspException {
		addChoice(out_buffer, in_choice.getChoiceValue(), in_choice.getChoiceLabel(), in_choice.getChoiceTooltip());
	}
	
	public final void addChoice(StringBuffer buffer, String value, String label) throws JspException {
		addChoice(buffer, value, label, null);
	}
	
protected final void addChoice(StringBuffer buffer, String value, String label, String tooltip) throws JspException {
	StringBuffer valueBuffer = new StringBuffer();
    boolean selected = add1Choice(valueBuffer, value);
    short displayMode = getFieldDisplayMode();
    if (!selected && ((displayMode == MODE_INSPECT) || (displayMode == MODE_INSPECT_ONLY) || (displayMode == MODE_INSPECT_PRESENT))) return; 
   	
    if (cols != 0) {
        if (index % cols == 0 && index != 0)
            buffer.append("</tr>\n<tr>");
        buffer.append("<td>");
    }   
    
    index++;
 	buffer.append(valueBuffer.toString());
    if (selected || getFieldDisplayMode() == MODE_EDIT) {
        if (styleClass != null) {
            buffer.append("<span class=\"");
            buffer.append(styleClass);
            buffer.append("\"");
            if (tooltip!=null) {
            	buffer.append(" title=\"");
            	buffer.append(tooltip);
            	buffer.append("\"");
            }
            buffer.append(">");
        }
        buffer.append(label);
        if (styleClass != null)
            buffer.append("</span>");
    }
    if (cols != 0) {
        buffer.append("</td>");
    } else {
        buffer.append("<br>");
    }

}
	/**
	 * Render the name of the field
	 * The field value can be append in the stringbuffer just after calling this method
	 */
	public void beginField(StringBuffer buffer) throws JspException {
	//	PENDING
		buffer.append("<tr><th colspan=\"").append(getSkin().getFieldInterface(getModel()).getColumnNumber()).append("\" class=\"");
		buffer.append(styleClass);
		buffer.append("\"><span class=\"");
		buffer.append(styleClass);
		buffer.append("\">");
		if (key!=null) buffer.append(getLabel()); else buffer.append("&nbsp;");
		buffer.append("</span></th></tr><tr><td colspan=\"").append(getSkin().getFieldInterface(getModel()).getColumnNumber()).append("\" class=\"");
		buffer.append(styleClass);
		buffer.append("\">");	
	}
public int doEndEditField() throws JspException {
    // Close the table.
    if (cols != 0) {
        TagUtils.write(pageContext, "</tr></table>");
    }

    // Valeur autre.
    if (need_other) {
        StringBuffer buffer = new StringBuffer();

        StringBuffer lc_otherField = new StringBuffer();
        cols = 0;

        lc_otherField.append("&nbsp;: <input type=\"text\" name=\"");
        lc_otherField.append(otherProperty);
        lc_otherField.append("\" value=\"");
        if (otherValue != null)
            lc_otherField.append(otherValue);
        else
            lc_otherField.append(
                LayoutUtils.getBeanFromPageContext(pageContext, name, otherProperty));
        lc_otherField.append("\" class=\"");
        lc_otherField.append(styleClass);
        lc_otherField.append("\">");

        addChoice(
            buffer,
            OTHER_KEY,
            LayoutUtils.getLabel(pageContext, OTHER_KEY, null) + lc_otherField.toString());
        TagUtils.write(pageContext, buffer.toString());
    }

    // Continue processing this page
    return EVAL_PAGE;
}
public int doEndInspectField() throws JspException {
    if (cols != 0) {
        TagUtils.write(pageContext, "</tr></table>");
    }

    // Valeur autre.
    if (need_other) {
        StringBuffer buffer = new StringBuffer();

        StringBuffer lc_otherField = new StringBuffer();
        cols = 0;

        lc_otherField.append("&nbsp;: ");
        if (otherValue != null)
            lc_otherField.append(otherValue);
        else
            lc_otherField.append(
                LayoutUtils.getBeanFromPageContext(pageContext, name, otherProperty));

        addChoice(
            buffer,
            OTHER_KEY,
            LayoutUtils.getLabel(pageContext, OTHER_KEY, null) + lc_otherField.toString());
        TagUtils.write(pageContext, buffer.toString());
    }

    // Continue processing this page
    return EVAL_PAGE;
}
public int doStartEditField() throws JspException {
	if (cols != 0) {
		TagUtils.write(pageContext, "<table border=\"0\"><tr>");
	}

	// Continue processing this page
	return EVAL_BODY_INCLUDE;
}
public int doStartInspectField() throws JspException {
	if (cols != 0) {
		TagUtils.write(pageContext, "<table border=\"0\"><tr>");
	}

	// Continue processing this page
	return EVAL_BODY_INCLUDE;
}
/**
 * Insert the method's description here.
 * Creation date: (07/09/2001 10:27:22)
 * @return java.lang.String
 */
public java.lang.String getOtherKey() {
	return otherKey;
}
/**
 * Insert the method's description here.
 * Creation date: (07/09/2001 10:27:22)
 * @return java.lang.String
 */
public java.lang.String getOtherProperty() {
	return otherProperty;
}
/**
 * Insert the method's description here.
 * Creation date: (07/09/2001 10:27:22)
 * @return java.lang.String
 */
public java.lang.String getOtherValue() {
	return otherValue;
}
protected void reset() {
	super.reset();
	index = 0;
	need_other = false;	
}
public void release() {
	super.release();
	cols = 0;

	name = Constants.BEAN_KEY;

	otherKey = "struts.layout.other";
	otherValue = null;
	otherProperty = null;
}
	public void setCols(String cols) throws JspException {
		try {
			this.cols = Integer.parseInt(cols);
			if (this.cols<0) throw new NumberFormatException();
		} catch(NumberFormatException e) {
			throw new JspException("cols must be a positive integer. Actual value is: " + cols);
		}
	}
/**
 * Insert the method's description here.
 * Creation date: (07/09/2001 10:27:22)
 * @param newOtherKey java.lang.String
 */
public void setOtherKey(java.lang.String newOtherKey) {
	otherKey = newOtherKey;
	need_other = true;
}
/**
 * Insert the method's description here.
 * Creation date: (07/09/2001 10:27:22)
 * @param newOtherProperty java.lang.String
 */
public void setOtherProperty(java.lang.String newOtherProperty) {
	otherProperty = newOtherProperty;
	need_other = true;
}
/**
 * Insert the method's description here.
 * Creation date: (07/09/2001 10:27:22)
 * @param newOtherValue java.lang.String
 */
public void setOtherValue(java.lang.String newOtherValue) {
	otherValue = newOtherValue;
	need_other = true;
}
	/**
	 * Gets the accessKey.
	 * @return Returns a String
	 */
	public String getAccesskey() {
		return accesskey;
	}

	/**
	 * Sets the accessKey.
	 * @param accessKey The accessKey to set
	 */
	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}

	/**
	 * Gets the maxlength.
	 * @return Returns a String
	 */
	public String getMaxlength() {
		return maxlength;
	}

	/**
	 * Sets the maxlength.
	 * @param maxlength The maxlength to set
	 */
	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}

	/**
	 * Gets the onblur.
	 * @return Returns a String
	 */
	public String getOnblur() {
		return onblur;
	}

	/**
	 * Sets the onblur.
	 * @param onblur The onblur to set
	 */
	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}

	/**
	 * Gets the onclick.
	 * @return Returns a String
	 */
	public String getOnclick() {
		return onclick;
	}

	/**
	 * Sets the onclick.
	 * @param onclick The onclick to set
	 */
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	/**
	 * Gets the ondblclick.
	 * @return Returns a String
	 */
	public String getOndblclick() {
		return ondblclick;
	}

	/**
	 * Sets the ondblclick.
	 * @param ondblclick The ondblclick to set
	 */
	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}

	/**
	 * Gets the onfocus.
	 * @return Returns a String
	 */
	public String getOnfocus() {
		return onfocus;
	}

	/**
	 * Sets the onfocus.
	 * @param onfocus The onfocus to set
	 */
	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}

	/**
	 * Gets the onkeydown.
	 * @return Returns a String
	 */
	public String getOnkeydown() {
		return onkeydown;
	}

	/**
	 * Sets the onkeydown.
	 * @param onkeydown The onkeydown to set
	 */
	public void setOnkeydown(String onkeydown) {
		this.onkeydown = onkeydown;
	}

	/**
	 * Gets the onkeyup.
	 * @return Returns a String
	 */
	public String getOnkeyup() {
		return onkeyup;
	}

	/**
	 * Sets the onkeyup.
	 * @param onkeyup The onkeyup to set
	 */
	public void setOnkeyup(String onkeyup) {
		this.onkeyup = onkeyup;
	}

	/**
	 * Gets the onkeypress.
	 * @return Returns a String
	 */
	public String getOnkeypress() {
		return onkeypress;
	}

	/**
	 * Sets the onkeypress.
	 * @param onkeypress The onkeypress to set
	 */
	public void setOnkeypress(String onkeypress) {
		this.onkeypress = onkeypress;
	}

	/**
	 * Gets the onmouseup.
	 * @return Returns a String
	 */
	public String getOnmouseup() {
		return onmouseup;
	}

	/**
	 * Sets the onmouseup.
	 * @param onmouseup The onmouseup to set
	 */
	public void setOnmouseup(String onmouseup) {
		this.onmouseup = onmouseup;
	}

	/**
	 * Gets the onmouseout.
	 * @return Returns a String
	 */
	public String getOnmouseout() {
		return onmouseout;
	}

	/**
	 * Sets the onmouseout.
	 * @param onmouseout The onmouseout to set
	 */
	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

	/**
	 * Gets the onmousemove.
	 * @return Returns a String
	 */
	public String getOnmousemove() {
		return onmousemove;
	}

	/**
	 * Sets the onmousemove.
	 * @param onmousemove The onmousemove to set
	 */
	public void setOnmousemove(String onmousemove) {
		this.onmousemove = onmousemove;
	}

	/**
	 * Gets the onmouseover.
	 * @return Returns a String
	 */
	public String getOnmouseover() {
		return onmouseover;
	}

	/**
	 * Sets the onmouseover.
	 * @param onmouseover The onmouseover to set
	 */
	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}

	/**
	 * Gets the onmousedown.
	 * @return Returns a String
	 */
	public String getOnmousedown() {
		return onmousedown;
	}

	/**
	 * Sets the onmousedown.
	 * @param onmousedown The onmousedown to set
	 */
	public void setOnmousedown(String onmousedown) {
		this.onmousedown = onmousedown;
	}

	/**
	 * Gets the onchange.
	 * @return Returns a String
	 */
	public String getOnchange() {
		return onchange;
	}

	/**
	 * Sets the onchange.
	 * @param onchange The onchange to set
	 */
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}
}
