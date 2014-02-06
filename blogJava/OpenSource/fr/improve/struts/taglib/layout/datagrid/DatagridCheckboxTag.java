/*
 * Created on 13 avr. 2004
 *
 * Copyright Improve SA 2004.
 * All rights reserved.
 */
package fr.improve.struts.taglib.layout.datagrid;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.collection.CollectionTag;
import fr.improve.struts.taglib.layout.collection.SimpleItemContext;
import fr.improve.struts.taglib.layout.util.LayoutUtils;

/**
 * Simplified collectionInput tag for the datagrid : only the property and key attributes are needed.
 * @author jnribette
 */
public class DatagridCheckboxTag extends AbstractDatagridColumnTag {
	
	protected SimpleItemContext createItemContext() {
		DatagridItemContext context = new DatagridItemContext();
		context.setColumnType(ColumnType.CHECKBOX);
		return context;
	}

	protected String getType() {
		return "checkbox";
	}
	
	/**
	 * Build the input field value : use the Datagrid object.
	 */
	protected Object buildInputFieldValue(CollectionTag in_parent, boolean in_anyError) throws JspException {
		return "true";
	}
	
	protected String buildAdditionalAttributes(CollectionTag in_parent) throws JspException{
		// get the value from the current bean in the collection
		Object value = LayoutUtils.getProperty(in_parent.getBean(), property);
		if (value!=null && "true".equals(value.toString())) {
			return "checked";
		} else {
			return "";
		}
	}	
}
