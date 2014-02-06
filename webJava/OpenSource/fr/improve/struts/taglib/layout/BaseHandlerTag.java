package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import fr.improve.struts.taglib.layout.util.NestedHelper;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Modified from org.apache.struts.taglib.html.BaseHandlerTag
 * so that the action which the form is posted can be choosed in function of the button which is clicked.
 * <br>
 * The actions must inherit from DispatchAction and the parameter name must be frImproveStrutsTaglibLayoutReqCode
 * Javascript must be enabled on the client's browser. 
 *
 * @author: Jean-Noël Ribette
 */
public abstract class BaseHandlerTag extends org.apache.struts.taglib.html.BaseHandlerTag implements LayoutTag {
	protected String reqCode;
	protected String value;
	protected org.apache.struts.taglib.html.BaseHandlerTag tag;

	protected String selectCode;
	protected String selectName = org.apache.struts.taglib.html.Constants.BEAN_KEY;
	protected String selectProperty;
	
	public int doAfterBody() throws JspException {
		tag.setBodyContent(bodyContent);
		return tag.doAfterBody();
	}
	public PageContext getPageContext() {
		return pageContext;
	}
	public JspWriter getPreviousOut() {
		if (bodyContent!=null) return super.getPreviousOut();
		return null;
	}
	public String getReqCode() {
		return reqCode;
	}
protected String getRequestCode() throws JspException {
	// specify the default action parameter if Action class is subclass of DispatchAction
	try {
	    String parameter = FormTag.computeActionParameter(pageContext);
		StringBuffer buffer = new StringBuffer();
		if (parameter != null && reqCode != null) {
			buffer.append("this.form.elements['");
			buffer.append(NestedHelper.getAdjustedProperty(parameter, pageContext));
			buffer.append("'].value='");
			buffer.append(reqCode);
			buffer.append("'");			
		}
		if (selectCode!=null) {
			buffer.append(";this.form.");
			buffer.append(selectCode);
			buffer.append(".value='");
			buffer.append(fr.improve.struts.taglib.layout.util.LayoutUtils.getBeanFromPageContext(pageContext, selectName, selectProperty));
			buffer.append("'");
		}
		return buffer.toString();
	} catch (Exception e) {
		if (reqCode!=null) {
			JspException jspe = new JspException("BaseHandlerTag - cannot acces the DispatchAction parameter: " + e.getMessage() + "(" + e.getClass().getName() + ")");
			TagUtils.saveException(pageContext, jspe);
			throw jspe;
		}
	}
	return "";
}
/**
 * Insert the method's description here.
 * Creation date: (18/09/2001 18:58:57)
 * @return java.lang.String
 */
public java.lang.String getSelectCode() {
	return selectCode;
}
/**
 * Insert the method's description here.
 * Creation date: (18/09/2001 18:58:57)
 * @return java.lang.String
 */
public java.lang.String getSelectName() {
	return selectName;
}
/**
 * Insert the method's description here.
 * Creation date: (18/09/2001 18:58:57)
 * @return java.lang.String
 */
public java.lang.String getSelectProperty() {
	return selectProperty;
}
	public String getValue() {
		return value;
	}
	public void release() {
		super.release();
		reqCode = null;
		value = null;

		selectCode = null;
		selectName = org.apache.struts.taglib.html.Constants.BEAN_KEY;
		selectProperty = null;
	}
	/**
	 * Set the dispatch action to call when this button clicked.
	 */
	public void setReqCode(String reqCode) {
		this.reqCode = reqCode;
	}
/**
 * Insert the method's description here.
 * Creation date: (18/09/2001 18:58:57)
 * @param newSelectCode java.lang.String
 */
public void setSelectCode(java.lang.String newSelectCode) {
	selectCode = newSelectCode;
}
/**
 * Insert the method's description here.
 * Creation date: (18/09/2001 18:58:57)
 * @param newSelectName java.lang.String
 */
public void setSelectName(java.lang.String newSelectName) {
	selectName = newSelectName;
}
/**
 * Insert the method's description here.
 * Creation date: (18/09/2001 18:58:57)
 * @param newSelectProperty java.lang.String
 */
public void setSelectProperty(java.lang.String newSelectProperty) {
	selectProperty = newSelectProperty;
}
	public void setValue(String value) {
		this.value = value;
	}
}
