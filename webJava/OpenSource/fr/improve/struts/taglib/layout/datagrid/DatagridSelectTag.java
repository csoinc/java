/*
 * Created on 13 avr. 2004
 *
 * Copyright Improve SA 2004.
 * All rights reserved.
 */
package fr.improve.struts.taglib.layout.datagrid;

import java.util.Iterator;

import javax.servlet.jsp.JspException;

import org.apache.struts.util.ResponseUtils;

import fr.improve.struts.taglib.layout.collection.SimpleItemContext;
import fr.improve.struts.taglib.layout.field.Choice;
import fr.improve.struts.taglib.layout.field.ChoiceTag;

/**
 * Simplified collectionInput tag for the datagrid : only the property and key attributes are needed.
 * @author jnribette
 */
public class DatagridSelectTag extends AbstractDatagridColumnTag implements ChoiceTag {
	/**
	 * Simple bean to hold a select option.
	 */
	static class DatagridChoice {
		private DatagridChoice(Choice in_choice) {
			label = in_choice.getChoiceLabel();
			value= in_choice.getChoiceValue();
		}
		String label;
		String value;
	}
	
	/**
	 * Return the item context associated with the datagrod column
	 * Here, a DatagridItemContext of type select.
	 */
	protected SimpleItemContext createItemContext() {
		DatagridItemContext lc_context = new DatagridItemContext();
		lc_context.setColumnType(ColumnType.select());
		return lc_context;
	}

	/**
	 * Deprecated method from the ChoiceTag interface.
	 * @deprecated
	 */
	public void addChoice(StringBuffer sb, String value, String label) throws JspException {
		// Deprecated method from the ChoiceTag interface.
		
	}

	/**
	 * Add an option value (method fro mthe ChoiceTag interface.
	 */
	public void addChoice(StringBuffer in_buffer, Choice in_choice) throws JspException {
		((DatagridItemContext)context).getColumnType().getValues().add(new DatagridChoice(in_choice));
	}

	/**
	 * Add the option tags after the select tag.
	 */
	protected String doAfterValue() throws JspException {
		StringBuffer buffer = new StringBuffer();
		Iterator it = ((DatagridItemContext)context).getColumnType().getValues().iterator();
		Object fieldValue = buildInputFieldValue(parentCollectionTag, false);
		while (it.hasNext()) {
			DatagridChoice choice = (DatagridChoice) it.next();			
			buffer.append("<option value=\"");
			buffer.append(choice.value);
			buffer.append("\"");
			if (fieldValue!=null && fieldValue.toString().equals(choice.value)) {
				buffer.append(" selected");
			}
			buffer.append(">");
			buffer.append(ResponseUtils.filter(choice.label));
			buffer.append("</option>\n");
		}
		buffer.append("</select>\n");
		return buffer.toString();
	}

	/**
	 * The tag should start with &lt;select and not &lt;input
	 */
	protected void appendFieldStart(StringBuffer in_buffer) {
		in_buffer.append("<select");
	}

	/**
	 * Do not append the field value in the select tag.
	 */
	protected void appendFieldValue(StringBuffer in_buffer, Object in_value) {
		// Do nothing.
	}

	protected int evaluateFirstBody() {
		return EVAL_BODY_INCLUDE;
	}

	protected int evaluateNextBody() {
		return EVAL_BODY_INCLUDE;
	}

	protected void reset() {
		((DatagridItemContext)context).getColumnType().getValues().clear();
		super.reset();
	}	
}
