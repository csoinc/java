package fr.improve.struts.taglib.layout;

import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.taglib.html.Constants;

import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.LayoutEventListener;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Class for the usual input fields (text, textarea, password and checkbox). <br>
 * This tag automatically display the error associated to the input field if there is one.
 * Javascript for simple type checking is also generated.<br>
 * <br>
 * Type for text can be: text, number, date or email.<br>
 * <br>
 * Values can be read-write, read-only, or read & resend.
 *
 * The content of the tag is displayed after the input field 
 * so that buttons or other elements can be added
 *
 * @author: Jean-Noel Ribette
 * @deprecated
 **/
public class FieldTag extends BaseFieldTag implements LayoutEventListener {

	protected String accept;
	protected String access = READWRITE;
	public static final String CHECKBOX = "CHECKBOX";
	// the taglib.html field properties
	protected String cols = null;
	public static final String DATE = "DATE";
	public static final String EMAIL ="EMAIL";
	// the error associated with this input field
	protected String error = null;
	protected org.apache.struts.taglib.html.BaseHandlerTag fieldTag = null;
	protected boolean isRequired = false;
	protected String maxlength = null;
	public static final String NUMBER = "NUMBER";
	public static final String PASSWORD = "PASSWORD";
	public static final String READONLY = "READONLY";
	public static final String READSEND ="READSEND";
	// the read/write possibilities
	// see setAccess() for more details
	public static final String READWRITE = "READWRITE";
	protected boolean redisplay = true;
	protected String rows = null;
	// the allowed types for this field
	// TODO: use the work in progress on validation.
	// see setType for more details
	public static final String TEXT = "TEXT";
	public static final String TEXTAREA = "TEXTAREA";
	protected String type = TEXT;
	protected String value = null;

public FieldTag() {
	name = Constants.BEAN_KEY;
}
public int doEndLayoutTag() throws JspException {     

	StringBuffer buffer = new StringBuffer();	
		
	endField(buffer);
	
	TagUtils.write(pageContext, buffer.toString());
	
	return EVAL_PAGE;
}
public int doStartLayoutTag() throws JspException {
	
	StringBuffer buffer = new StringBuffer();
	
	// display the label
	beginField(buffer);

	// get the error if any and print the html tags
	error = retrieveError();
	if (error!=null && access.equals(READWRITE)) 
		buffer.append("<table><tr><td class=ERROR>");


	TagUtils.write(pageContext,buffer.toString());
					
	// create the corresponding tag
	// maybe having a pool of tags would be better
	if (access.equals(READSEND)) type = "hidden";
	if (type.equals(CHECKBOX)) fieldTag = new org.apache.struts.taglib.html.CheckboxTag();
	if (type.equals(PASSWORD)) fieldTag = new org.apache.struts.taglib.html.PasswordTag();
	if (type.equals(TEXT)) fieldTag = new org.apache.struts.taglib.html.TextTag();
	if (type.equals(TEXTAREA)) fieldTag = new org.apache.struts.taglib.html.TextareaTag();
	if (type.equals(DATE)) fieldTag = new org.apache.struts.taglib.html.TextTag();
	if (type.equals(NUMBER)) fieldTag = new org.apache.struts.taglib.html.TextTag();
	if (type.equals(EMAIL)) fieldTag = new org.apache.struts.taglib.html.TextTag();
	if (type.equals("hidden")) fieldTag = new org.apache.struts.taglib.html.HiddenTag();
	// initialize the tag
	LayoutUtils.copyProperties(fieldTag,this);

	//set the name of the javascript to use
	boolean check = isRequired;		
	fieldTag.setOnchange("checkValue(this, '"+ property + "','"+ type +"'," + check +");");
			
	// start the tag
	if (!access.equals(READONLY)) {
		fieldTag.doStartTag();
		fieldTag.doEndTag();
	}

	buffer.setLength(0);
	Object objvalue = LayoutUtils.getBeanFromPageContext(pageContext, name, property);

	// the value is read only so display it as text.
	if (!access.equals(READWRITE) && objvalue!=null) {
		buffer.append("<span class=\"");
		buffer.append(styleClass);
		buffer.append("\">");
		buffer.append(objvalue.toString());
		buffer.append("</span>");
	}

	
	// display the error if any
	if (error!=null && access.equals(READWRITE)) {
		buffer.append(error);
		buffer.append("</td></tr></table>");
	}

		
	// display an image if a value is needed
	if (objvalue==null) objvalue="";
	if (isRequired && objvalue.equals("")) {
		buffer.append("<img name=\"" + property + "required\" src=\"");
		buffer.append(getSkin().getImageDirectory(pageContext.getRequest()));
		buffer.append("/ast.gif\">");
	} else {
		buffer.append("<img name=\"" + property + "required\" src=\"");
		buffer.append(getSkin().getImageDirectory(pageContext.getRequest()));
		buffer.append("/clearpixel.gif\">");
	}

	TagUtils.write(pageContext, buffer.toString());	
		
	return EVAL_BODY_INCLUDE;	
}
public String getAccept() {
	return accept;
}
public String getCols() {
	return cols;
}
public String getMaxlength() {
	return maxlength;
}
public boolean getRedisplay() {
	return redisplay;
}
public String getRows() {
	return rows;
}
public String getSize() {
	return cols;
}
public String getType() {
	return type;
}
public String getValue() {
	return value;
}
public void release() {
	super.release();
	access = READWRITE;
	cols = null;
	isRequired = false;
	rows = null;
	type = TEXT;
	name = Constants.BEAN_KEY;

	if (fieldTag!=null) {
		fieldTag.release();
		fieldTag = null;
	}

	error = null;
}
/**
 * Retrieve the first error associated with the current property if there is one
 */
protected String retrieveError() throws JspException {
	ActionErrors errors = (ActionErrors) pageContext.getAttribute(Globals.ERROR_KEY, PageContext.REQUEST_SCOPE);
	String error = null;
	if (errors!=null && !errors.isEmpty()) {
		Iterator it = errors.get(property);
		if (it.hasNext()) {
			ActionMessage report = (ActionMessage) it.next();
			error = LayoutUtils.getLabel(pageContext, report.getKey(), report.getValues());
		}
	}	
	return error;
	
}
public void setAccept(String accept) {
	this.accept = accept;
}
	/**
 * Default Value: READWRITE
 * Allowed values: READWRITE, READONLY, READSEND
 *
 * Setting access to READONLY will render the property value as text instead of a input field
 * Setting access to READSEND will render the property value as text and as an hidden input field so that the server can get the value back
	 */
public void setAccess(String newAccess) {
	access = READWRITE;
	if (newAccess.equalsIgnoreCase(READONLY)) access = READONLY;
	if (newAccess.equalsIgnoreCase(READSEND)) access = READSEND;
	}

/**
 * Insert the method's description here.
 * Creation date: (19/02/01 11:29:27)
 * @param newCols int
 */
public void setCols(String newCols) {
	cols = newCols;
}
/**
 * If set to "TRUE" a red star is added after the field when the value is null
 * A piece of Javascript adds or removes the start when the value changes
 */
public void setIsRequired(String newIsRequired) {
	isRequired = newIsRequired.equalsIgnoreCase("true");
}
public void setMaxlength(String maxlength) {
	this.maxlength = maxlength;
}
public void setRedisplay(boolean redisplay) {
	this.redisplay = redisplay;
}
/**
 * Insert the method's description here.
 * Creation date: (19/02/01 11:29:27)
 * @param newRows int
 */
public void setRows(String newRows) {
	rows = newRows;
}
/**
 * Insert the method's description here.
 * Creation date: (19/02/01 11:29:27)
 * @param newSize int
 */
public void setSize(String newSize) {
	cols = newSize;
}
/**
 * Type of the input field <br>
 *
 * Default value: TEXT		   <br>
 * Allowed values: PASSWORD, CHECKBOX, NUMBER, DATE, EMAIL <br>
 *
 * For NUMBER, DATE and EMAIL some Javascipt test is done to check
 * if the value type in is correct or not.
 * If not a red start is displayed after the field.
 */
public void setType(String newType) {
	if (newType.equalsIgnoreCase(TEXT)) type = TEXT;
	if (newType.equalsIgnoreCase(TEXTAREA)) type = TEXTAREA;
	if (newType.equalsIgnoreCase(PASSWORD)) type = PASSWORD;
	if (newType.equalsIgnoreCase(CHECKBOX)) type = CHECKBOX;
	if (newType.equalsIgnoreCase(NUMBER)) type = NUMBER;
	if (newType.equalsIgnoreCase(DATE)) type = DATE;
	if (newType.equalsIgnoreCase(EMAIL)) type = EMAIL;
	
}
public void setValue(String value) {
	this.value = value;
}
	/**
	 * Gets the access.
	 * @return Returns a String
	 */
	public String getAccess() {
		return access;
	}

	/**
	 * Gets the isRequired.
	 * @return Returns a boolean
	 */
	public String getIsRequired() {
		return isRequired?"true":"false";
	}	
	public Object processStartLayoutEvent(StartLayoutEvent in_event) {
		return Boolean.FALSE;
	}
	public Object processEndLayoutEvent(EndLayoutEvent in_event) {
		return Boolean.FALSE;
	}

}
