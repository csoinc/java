package fr.improve.struts.taglib.layout.datagrid;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.collection.CollectionInputTag;
import fr.improve.struts.taglib.layout.collection.CollectionTag;
import fr.improve.struts.taglib.layout.el.EvaluationException;
import fr.improve.struts.taglib.layout.util.LayoutUtils;

public class AbstractDatagridColumnTag extends CollectionInputTag {
	/**
	 * Build the input field name.
	 * The pattern is : "property attribute of the datagrid tag" . "property of the column tag" [ index ]
	 */
	protected String buildInputFieldName(CollectionTag in_parent) throws JspException, EvaluationException {				
		StringBuffer lc_fieldNameBuffer = new StringBuffer();
		lc_fieldNameBuffer.append(in_parent.getProperty());
		lc_fieldNameBuffer.append(".");
		lc_fieldNameBuffer.append(property);		
		lc_fieldNameBuffer.append("[");	
		lc_fieldNameBuffer.append(in_parent.getIndex());		
		lc_fieldNameBuffer.append("]");
		return lc_fieldNameBuffer.toString();		
	}
	
	/**
	 * Build the input field value : use the Datagrid object.
	 */
	protected Object buildInputFieldValue(CollectionTag in_parent, boolean in_anyError) throws JspException {
		// get the value from the current bean in the collection
		Object value = LayoutUtils.getProperty(in_parent.getBean(), property);		
		return value;
	}
}
