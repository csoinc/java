package fr.improve.struts.taglib.layout.field;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.util.ResponseUtils;

import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;
/**
 * Render a "select" tag with its nested "option" tags
 * Works with the html and layout options & option tags
 * @author: Jean-Noel Ribette
 **/
public class SelectTag extends AbstractFieldTag implements ChoiceTag {
	protected org.apache.struts.taglib.html.SelectTag selectTag =
		new org.apache.struts.taglib.html.SelectTag();

	protected String multiple;
	protected String[] match;
	protected boolean isMatched = false;

	protected boolean isClosed = false;

	private List errors = new ArrayList();

	/**
	 * special default values for open selection<br>
	 * if one choice value equals to this value, a text input field is added after the select list.
	 */
	public static final String OTHER_KEY = "struts.layout.other";
	protected String otherKey = "struts.layout.other";
	protected boolean need_other = false;
	protected String otherProperty;
	protected String otherValue;
	
	public boolean isMatched(String value) {
		if ((match == null) || (value == null))
			return (false);
		for (int i = 0; i < match.length; i++) {
			if (value.equals(match[i]))
				return (true);
		}
		return (false);
	}
	
	public void addChoice(StringBuffer out_sb, Choice in_choice) throws JspException {
		addChoice(out_sb, in_choice.getChoiceValue(), in_choice.getChoiceLabel());
	}
			
	/**
	 * Generate the HTML code to add a choice.
	 *
	 * @param sb      Buffer to print the HTML code to.
	 * @param value   The value to send when this choice is selected
	 * @param label   The label to display for this choice.
	 */
	public void addChoice(StringBuffer sb, String value, String label)
		throws JspException {
			
		if (isClosed) {
			throw new IllegalStateException("Cannot add another option after nested text evaluation");	
		}
			
		switch (getFieldDisplayMode()) {
			case MODE_EDIT :
				sb.append("<option value=\"");
				sb.append(value);
				sb.append("\"");
				if (isMatched(value))
					sb.append(" selected");
				sb.append(">");
				if (filter) {
					sb.append(ResponseUtils.filter(label));
				} else {
					sb.append(label);
				}
				sb.append("</option>\r\n");
				break;
			case MODE_INSPECT :
			case MODE_INSPECT_ONLY :
			case MODE_INSPECT_PRESENT :
				if (isMatched(value)) {
					if (OTHER_KEY.equals(value)) {
						//                    sb.append(label);
						//                else {
						// print the other value
						sb.append("<span class=\"");
						sb.append(styleClass);
						sb.append("\">");
						if (otherValue != null) {						
							sb.append(otherValue);
						} else {
							sb.append(LayoutUtils.getBeanFromPageContext(pageContext, name, otherProperty));
						}
						sb.append("</span>");
					} else {
						sb.append("<span class=\"");
						sb.append(styleClass);
						sb.append("\">");
						if (filter) {
							sb.append(ResponseUtils.filter(label));
						} else {
							sb.append(label);
						}
						sb.append("</span>");
					}
					isMatched = true;
				}

		}

	}

	protected void doAfterValue() throws JspException {
		if (!isClosed) {
			isClosed = true;
			
			// Add a "other" option if needed
			if (need_other) {
				StringBuffer buffer = new StringBuffer();
				addChoice(
					buffer,
					OTHER_KEY,
					LayoutUtils.getLabel(pageContext, OTHER_KEY, null));
				TagUtils.write(pageContext, buffer.toString());
			}
			
			if (getFieldDisplayMode() == MODE_EDIT)
				selectTag.doEndTag();
	
			// eventually print a text input field if selection is open
			if (need_other && getFieldDisplayMode() == MODE_EDIT) {
				StringBuffer buffer = new StringBuffer();
				buffer.append("&nbsp;<span id=\"");
				buffer.append(otherProperty);
				buffer.append("span\"");
				if (!isMatched(OTHER_KEY))
					buffer.append(" style=\"display:none\";");
				buffer.append("><span class=\"");
				buffer.append(styleClass);
				buffer.append("\">");
				buffer.append(LayoutUtils.getLabel(pageContext, otherKey, null));
				buffer.append(" :&nbsp;</span><input type=\"text\" name=\"");
				buffer.append(otherProperty);
				buffer.append("\" value=\"");
				if (otherValue != null)
					buffer.append(otherValue);
				else
					buffer.append(
						LayoutUtils.getBeanFromPageContext(pageContext, name, otherProperty));
				buffer.append("\" class=\"");
				buffer.append(styleClass);
				buffer.append("\"></span>");
			}
	
		}
	}
	/**
	* This method is called before displaying the value.
	* This is the place to write something before the value, and set the fieldTag value.
	* @return true - process the tag<br>
	*		   false - skip the tag
	*/
	protected boolean doBeforeValue() throws javax.servlet.jsp.JspException {
		fieldTag = selectTag;
		return true;
	}
	
	/**
	 * Override default error handling.
	 */
	protected int doStartEditMode() throws JspException {
		errors = computeErrors();
		if (isLayout()) {
		    beginFieldLayout();
		    beginFieldError(errors);
		}
	    int lc_result = doStartEditField();    
	    return lc_result;
	}	
	
	/**
	 * Override default to not display the value, but the label.
	 */
	public int doStartInspectField() throws JspException {	
		// Maybe do something before dispaying the value.
		if (!doBeforeValue()) return SKIP_BODY;		
	
	return EVAL_BODY_INCLUDE;	
	}
	public int doEndInspectField() throws JspException {
		doAfterValue();
		
        if (!isMatched) {
            Object lc_value = getFieldValue();
            if (lc_value!=null) {
                StringBuffer buffer = new StringBuffer();
                buffer.append("<span class=\"");
                buffer.append(styleClass);
                buffer.append("\">");
                if (lc_value.getClass().isArray()) {
                	int n = Array.getLength(lc_value);
                	for (int i = 0; i < n; i++) {
                		Object lc_item = Array.get(lc_value, i);
                		if (lc_item!=null) {
                			buffer.append(lc_item.toString());
                		}
                	}
                } else {                	
	                buffer.append(lc_value.toString());
                }
                buffer.append("</span>");
                TagUtils.write(pageContext, buffer.toString());
            }
        }
        return EVAL_PAGE;
    }
	
	/**
	 * Override default error handling.
	 */	
	protected int doEndEditMode() throws JspException {
		int lc_result = doEndEditField();	    
	    if (isLayout()) {
	    	endFieldError(errors);
		    endFieldLayout();
	    }
	    return lc_result;
	}
	
	public int doEndEditField() throws JspException {
		doAfterValue();

		return EVAL_PAGE;
	}
	public int doStartEditField() throws JspException {
		// Maybe do something before dispaying the value
		if (!doBeforeValue())
			return SKIP_BODY;

		// Initialize the tag
		if (fieldTag == null)
			throw new JspException(
				getClass().getName()
					+ " should really set the fieldTag value in doBeforeValue() !");

		// Initialize the field value.
		//Object lc_value = getFieldValue();
		//if (lc_value != null)
		//	value = lc_value.toString();
		//else
		//	value = "";

		// PENDING nice, but not that fast.
		copyProperties(fieldTag);		

		//set the name of the javascript to use
		boolean check = isRequired();
		if (check) {
			fieldTag.setOnchange(
				"checkValue(this, '" + property + "','TEXT'," + check + ");" +
				(onchange!=null ? onchange : "")
				);
		}

		fieldTag.doStartTag();

		return EVAL_BODY_INCLUDE;
	}
	/**
	* Return the value(s) that will be displayed.
	*/
	protected java.lang.Object getFieldValue() throws JspException {
		if (match != null)
			return match;

		Object lc_value = super.getFieldValue();
		if (lc_value != null) try {
			if (value!=null) {
				match = new String[1];
				match[0] = value; 				
			} else {
				Object lc_bean = pageContext.findAttribute(name);
				match = LayoutUtils.getArrayProperty(lc_bean, property);
			}
		} catch (Exception e) {
			TagUtils.saveException(pageContext, e);
			throw new JspException(e.getMessage());
		}	
		return lc_value;
	}
	public String getMultiple() {
		return multiple;
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
	public String getSize() {
		return size;
	}
	protected void reset() {
		super.reset();
		match = null;
		isMatched = false;					
		isClosed = false;
		need_other = false;		
		errors.clear();		
	}
	public void release() {
		super.release();
		
		multiple = null;
		onchange = null;		

		otherKey = "struts.layout.other";
		otherValue = null;
		otherProperty = null;		
	}
	public void setMultiple(String multiple) {
		this.multiple = multiple;

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
	public void setSize(String size) {
		this.size = size;
	}
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * If multiple is set to false, render a SIMPLE property.
	 */
	protected void printIndexedHiddenValue(Collection lc_collection) throws JspException {
		if ("true".equalsIgnoreCase(multiple)) {
			super.printIndexedHiddenValue(lc_collection);
		} else {
			if (!lc_collection.isEmpty()) {					
				Object lc_value = lc_collection.iterator().next();
				printSimpleHiddenValue(lc_value);
			}
		}
	}
	
	public String getValue() {
		return "";	
	}
	
	/**
	 * @see fr.improve.struts.taglib.layout.event.LayoutEventListener#processStartLayoutEvent(StartLayoutEvent)
	 */
	public Object processStartLayoutEvent(StartLayoutEvent in_event) throws JspException {
		if (!isClosed) {
			doAfterValue();	
		}
		return super.processStartLayoutEvent(in_event);
	}

	protected void copyProperties(BaseHandlerTag in_dest) throws JspException {
		super.copyProperties(in_dest);		
		selectTag.setProperty(getProperty());
		selectTag.setValue(getValue());
		selectTag.setName(getName());
		selectTag.setMultiple(getMultiple());
		selectTag.setSize(getSize());
	}

	/**
	 * Return false if the value is unset. Special case for arrays.
	 */
	public boolean isFill(Object in_value) throws JspException {
		// Null.
		if (in_value==null) {
			return false;
		}
		
		// Not an array : value should not be an empty String.
		if (!in_value.getClass().isArray()) {
			return in_value.toString().length()!=0;
		}
		
		// Array
		Object[] lc_values = (Object[]) in_value;
		
		// Empty array is unset.
		if (lc_values.length==0) {
			return false;
		}
		
		// Array with one empty element is unset.
		if (lc_values.length==1) {
			Object lc_firstValue = lc_values[0];
			return lc_firstValue!=null && lc_firstValue.toString().length()!=0;
		}
		
		// Array with many elements, assume true.
		return true;
	}
}