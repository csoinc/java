/*
 * Created on 2 avr. 2004
 *
 * Copyright Improve SA 2004.
 * All rights reserved.
 */
package fr.improve.struts.taglib.layout.field;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * The optionsDependent tag allows to make interdependent combo box.
 * 
 * @author jnribette
 */
public class OptionsDependentTag extends TagSupport {
	/**
	 * Helper data object holding all the required information to make 2 combos interdependent. 
	 */
	static class DependentInfo {
		/**
		 * Name of the bean holding the main collection in the request.
		 */
		String mainCollectionName;
		/**
		 * Property of the bean holding the main collection in the request.
		 */
		String mainCollectionProperty;
		
		/**
		 * Property of the bean in the main collection holding the value.		 
		 */
		String mainCollectionBeanProperty;
				
		/**
		 * Property of the form bean holding the main property.
		 */
		String mainProperty;
		
		/**
		 * Property of the form bean holding the dependent property.
		 */
		String dependentProperty;
		
		/**
		 * Property of the main select elements holding the nested property.
		 */
		String dependentCollectionProperty;
		
		String dependentCollectionLabel;
		String dependentCollectionValue;
	}
	
	/**
	 * The property of the beans of the main combo that holds the nested collection. 
	 */
	protected String collection;
	
	/**
	 * The property this combo depends from.
	 */
	protected String dependsFrom;
	
	/**
	 * The label property.
	 */
	protected String labelProperty;
	
	/**
	 * The value property.
	 */
	protected String property;
	
	/**
	 * Key of the map holding the DependentInfo objects in the pageContext. 
	 */
	protected static final String DEPENDENT_INFO_KEY = "fr.improve.struts.taglib.layout.field.OptionsDependentTag.DEPENDENT_INFO_KEY"; 
	
	/**
	 * Do many things
	 */
	public int doStartTag() throws JspException {
		// Find DependentInfo based on the dependsForm attribute.
		DependentInfo lc_info = getDependentInfo(dependsFrom, pageContext);
		
		// Initialize the DependentInfo.
		initializeChildDependentInfo(lc_info);
		
		// Make the combo interdependent.
		makeDependentCombo(lc_info);			
		
		return SKIP_BODY;
	}	
		
	protected void initializeChildDependentInfo(DependentInfo lc_info) {
		SelectTag lc_selectTag = (SelectTag) findAncestorWithClass(this, SelectTag.class);
		lc_info.dependentProperty = lc_selectTag.getProperty();
		lc_info.dependentCollectionProperty = collection;	
		lc_info.dependentCollectionLabel = labelProperty;
		lc_info.dependentCollectionValue = property;
	}

	protected static DependentInfo getDependentInfo(String in_dependsFrom, PageContext in_pg) {
		// Get the DependentInfo map.
		Map lc_map = (Map) in_pg.findAttribute(DEPENDENT_INFO_KEY);
		if (lc_map==null) {
			lc_map = new HashMap();
			in_pg.setAttribute(DEPENDENT_INFO_KEY, lc_map);
		}
		
		// Get the specified DependentInfo.
		DependentInfo lc_info = (DependentInfo) lc_map.get(in_dependsFrom);
		if (lc_info==null) {
			lc_info = new DependentInfo();
			lc_info.mainProperty = in_dependsFrom;
			lc_map.put(in_dependsFrom, lc_info);
		}
		
		// Return the DependentInfo.
		return lc_info;
	}

	/**
	 * Init the dependent combo this tag is a datasource from. 
	 */
	protected void makeDependentCombo(DependentInfo in_dependentInfo) throws JspException {
		// 1 : generate js data.
		generateDependentComboData(in_dependentInfo);
		
		// 2 : register a Javascript handler for the parent select tag.
		registerDependentComboHandler(in_dependentInfo);			
	}
	
	/**
	 * Build JS array name.
	 */
	protected String buildCollectionName(DependentInfo in_info) {
		StringBuffer lc_collection = new StringBuffer();
		lc_collection.append(in_info.mainCollectionProperty==null ? in_info.mainCollectionName : in_info.mainCollectionProperty);
		lc_collection.append("_");
		lc_collection.append(in_info.dependentCollectionProperty);
		return lc_collection.toString();		
	}
	
	/**
	 * Generate js data for dependent combos.
	 */
	protected void generateDependentComboData(DependentInfo in_info) throws JspException {
		String lc_collection = buildCollectionName(in_info);
		TagUtils.write(pageContext, "<script>var ");
		TagUtils.write(pageContext, lc_collection);
		TagUtils.write(pageContext, " = new Array();\n");
		
		Iterator lc_it = LayoutUtils.getIterator(pageContext, in_info.mainCollectionName, in_info.mainCollectionProperty);
		generateDependentComboData(in_info, lc_it, lc_collection);
		TagUtils.write(pageContext, "</script>\n");		
	}
	
	protected void generateDependentComboData(DependentInfo in_info, Iterator in_it, String in_collection) throws JspException {
		String collection = in_collection;
		String nestedCollection = in_info.dependentCollectionProperty;
		
		int i = 0;
		while (in_it.hasNext()) {
			Object lc_bean = in_it.next();
			// Define the js bean.
			TagUtils.write(pageContext, collection);
			TagUtils.write(pageContext, "[");
			TagUtils.write(pageContext, String.valueOf(i));
			TagUtils.write(pageContext, "] = new Object();\n");
			
			// Define the js bean value.
			TagUtils.write(pageContext, collection);
			TagUtils.write(pageContext, "[");
			TagUtils.write(pageContext, String.valueOf(i));
			TagUtils.write(pageContext, "].value = \"");
			TagUtils.write(pageContext, filter(LayoutUtils.getProperty(lc_bean, in_info.mainCollectionBeanProperty)));
			TagUtils.write(pageContext, "\";\n");
			
			// Define the js bean nested collection.
			TagUtils.write(pageContext, collection);
			TagUtils.write(pageContext, "[");
			TagUtils.write(pageContext, String.valueOf(i));
			TagUtils.write(pageContext, "].");
			TagUtils.write(pageContext, nestedCollection);
			TagUtils.write(pageContext, " = new Array();\n");
			
			// Initalize the nested collection
			Object lc_nestedCollection = LayoutUtils.getProperty(lc_bean, nestedCollection);
			Iterator lc_nestedIterator = LayoutUtils.getIterator(lc_nestedCollection);
			int j = 0;
			while (lc_nestedIterator.hasNext()) {
				Object lc_nestedBean = lc_nestedIterator.next();
				TagUtils.write(pageContext, collection);
				TagUtils.write(pageContext, "[");
				TagUtils.write(pageContext, String.valueOf(i));
				TagUtils.write(pageContext, "].");
				TagUtils.write(pageContext, nestedCollection);
				TagUtils.write(pageContext, "[");
				TagUtils.write(pageContext, String.valueOf(j));
				TagUtils.write(pageContext, "] = new Object();\n");
				
				TagUtils.write(pageContext, collection);
				TagUtils.write(pageContext, "[");
				TagUtils.write(pageContext, String.valueOf(i));
				TagUtils.write(pageContext, "].");
				TagUtils.write(pageContext, nestedCollection);
				TagUtils.write(pageContext, "[");
				TagUtils.write(pageContext, String.valueOf(j));
				TagUtils.write(pageContext, "].value = \"");
				TagUtils.write(pageContext, filter(LayoutUtils.getProperty(lc_nestedBean, in_info.dependentCollectionValue)));
				TagUtils.write(pageContext, "\";\n");
				
				TagUtils.write(pageContext, collection);
				TagUtils.write(pageContext, "[");
				TagUtils.write(pageContext, String.valueOf(i));
				TagUtils.write(pageContext, "].");
				TagUtils.write(pageContext, nestedCollection);
				TagUtils.write(pageContext, "[");
				TagUtils.write(pageContext, String.valueOf(j));
				TagUtils.write(pageContext, "].label = \"");
				TagUtils.write(pageContext, filter(LayoutUtils.getProperty(lc_nestedBean, in_info.dependentCollectionLabel)));
				TagUtils.write(pageContext, "\";\n");
				
				j++;				
			}
			
			i++;
		}
		
	}
	
	/**
	 * Filter sensitive js and html character
	 */
	protected String filter(Object in_string) {
		if (in_string==null) {
			return "";
		}
		String lc_string = in_string.toString();
		if (lc_string.length()==0) {		
			return "";
		}
		StringBuffer lc_buffer = new StringBuffer(lc_string.length());
		int i;
		for (i = 0; i < lc_string.length(); i++) {
			char c = lc_string.charAt(i);
			switch (c) {
				case '<':
					lc_buffer.append("&lt;");
					break;
				case '>':
					lc_buffer.append("&gt;");
				case '"':
					lc_buffer.append("\"");
				default: 
					lc_buffer.append(c);
			}
		}
		return lc_buffer.toString();
	}

	/**
	 * Register a Javascript handler to initialize the dependent combos when a value is selected.
	 */
	protected void registerDependentComboHandler(DependentInfo in_info) throws JspException {
		// Find the selected value.
		SelectTag lc_selectTag = (SelectTag) findAncestorWithClass(this, SelectTag.class);
		Object lc_selectedValue = lc_selectTag.getFieldValue();
		if (lc_selectedValue!=null && lc_selectedValue.getClass().isArray()) {
			Object[] lc_temp = (Object[]) lc_selectedValue;
			if (lc_temp.length>0) {
				lc_selectedValue = lc_temp[0];
			}
		}
		if (lc_selectedValue!=null) {
			lc_selectedValue = lc_selectedValue.toString();
		}
		
		// Easy, do it with Javascript.
		String lc_collection = buildCollectionName(in_info);
		TagUtils.write(pageContext, "<script>initDependentComboHandler(\"");		
		TagUtils.write(pageContext, in_info.mainProperty);
		TagUtils.write(pageContext, "\",\"");
		TagUtils.write(pageContext, in_info.dependentProperty);
		TagUtils.write(pageContext, "\",\"");
		TagUtils.write(pageContext, lc_collection);
		TagUtils.write(pageContext, "\",\"");
		TagUtils.write(pageContext, in_info.dependentCollectionProperty);
		TagUtils.write(pageContext, "\",\"");
		TagUtils.write(pageContext, (String)lc_selectedValue);
		TagUtils.write(pageContext, "\");</script>\n");
	}

	
	public void release() {
		collection = null;
		dependsFrom = null;
		labelProperty = null;
		property = null;
	}
	
	public void setCollection(String in_collection) {
		collection = in_collection;
	}
	
	public void setDependsFrom(String in_dependsFrom) {
		dependsFrom = in_dependsFrom;
	}
	
	public void setLabelProperty(String in_labelProperty) {
		labelProperty = in_labelProperty;
	}
	
	public void setProperty(String in_property) {
		property = in_property;
	}
}
