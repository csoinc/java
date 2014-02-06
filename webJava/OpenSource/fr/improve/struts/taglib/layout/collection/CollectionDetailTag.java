/*
 * Created on 26 mars 2004
 *
 * Copyright Improve SA 2004.
 * All rights reserved.
 */
package fr.improve.struts.taglib.layout.collection;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * This tag allow to specify a property to display 
 * in a detail field when a line of a list is selected
 * 
 * @author jnribette
 */
public class CollectionDetailTag extends TagSupport {
	protected String name;
	protected String property;
	protected boolean skip = false;
	protected BaseCollectionTag collectionTag;
	
	protected static final String DETAIL_KEY = "fr.improve.struts.taglib.layout.collection.CollectionDetailTag.DETAIL_KEY";
	
	public int doStartTag() throws JspException {
		try {
			collectionTag = 
				(BaseCollectionTag) findAncestorWithClass(this, BaseCollectionTag.class);
			if (collectionTag.getId()==null) {
				throw new JspException("Invalid use of collectionDetail tag: parent collection tag attribute 'id' should be set");
			}
			if (collectionTag.getIndexId()==null) {
				throw new JspException("Invalid use of collectionDetail tag: parent collection tag attribute 'indexId' should be set");
			}
			if (collectionTag.isFirst()) {
				return SKIP_BODY;
			}
			if (name!=null) {
				if (!collectionTag.getSpans().containsKey(name)) {
					skip = true;
					return SKIP_BODY;	
				} else {
					pageContext.setAttribute(CollectionItemTag.SPAN_KEY, collectionTag.getSpans().get(name));
				}
			}
			
		} catch (ClassCastException e) {
			throw new JspException("Invalid use of collectionDetail tag");
		} catch (NullPointerException e) {
			throw new JspException("Invalid use of collectionDetail tag");
		}
		return SKIP_BODY;
	}
	
	public int doEndTag() throws JspException {
		if (collectionTag.isFirst()) {
			Map lc_map = getDetailInfo();
			if (!lc_map.containsKey(collectionTag.getId())) {
				// Put -1 in the map for this if.
				lc_map.put(collectionTag.getId(), new Integer(-1));
				
				// Declare a javascript variable to hold the details info.
				renderInit(collectionTag.getId());
				
				// Ask the collection tag to generate javascript to update the details when a line is selected.				
				String onover = collectionTag.getOnRowMouseOver();
				String onout = collectionTag.getOnRowMouseOut();
				collectionTag.setOnRowMouseOver("showDetail(" + collectionTag.getId() + ",${" + collectionTag.getIndexId() + "});" + onover);
				collectionTag.setOnRowMouseOut("clearDetail(" + collectionTag.getId() + ");" + onout);
			}
			return EVAL_PAGE;
		}
		if (skip) {
			skip = false;
			collectionTag.incrementColumn();
			return EVAL_PAGE;	
		}	
		
		Map lc_map = getDetailInfo();
		Integer lc_int = (Integer) lc_map.get(collectionTag.getId());
		if (lc_int.intValue()< collectionTag.getIndex()) {
			lc_map.put(collectionTag.getId(), new Integer(collectionTag.getIndex()));
			renderNext(collectionTag.getId(), collectionTag.getIndex());
		}
		
		Object lc_value = buildContent();
		renderDetail(collectionTag.getId(), property, collectionTag.getIndex(), lc_value==null ? "" : lc_value.toString());
		
		reset();		
		return EVAL_PAGE;
	}
	
	private Map getDetailInfo() {
		Map lc_map = (Map) pageContext.getAttribute(DETAIL_KEY);
		if (lc_map==null) {
			lc_map = new HashMap();
			pageContext.setAttribute(DETAIL_KEY, lc_map);
		}
		return lc_map;
	}

	/**
	 * This method get the value to display from the collection.	
	 */
	protected Object buildContent() throws JspException {
		Object lc_cell = null;
		if (name == null) {
			// The item to add is a property of a bean in the current collection.
			lc_cell = collectionTag.getBean();
		} else {
			// The item to add is a property of a bean in the context.
			lc_cell = pageContext.findAttribute(name);
		}
		if (lc_cell != null && property != null) {
			// Get the property of the bean.
			lc_cell = getPropertyValue(lc_cell, property);
		}	
		return lc_cell;
	}
	
	/**
	 * This method simply uses BeanUtils (indirectly) to return the value of the property 'in_property' 
	 * of in_bean. This method is intended to be overrided if needed.  
	 */
	protected Object getPropertyValue(Object in_bean, String in_property) throws JspException {
		return LayoutUtils.getProperty(in_bean, in_property);		
	}
	
	/**
	 * This method writes the required javascript code to the response.
	 * @param in_name the name of the object
	 * @param in_property the detail property
	 * @param in_value the value to render 	
	 */
	protected void renderDetail(String in_name, String in_property, int in_index, String in_value) throws JspException {
		TagUtils.write(pageContext, "<script>");
		TagUtils.write(pageContext, in_name);		
		TagUtils.write(pageContext, "[");
		TagUtils.write(pageContext, String.valueOf(in_index));
		TagUtils.write(pageContext, "]['");
		TagUtils.write(pageContext, in_property);
		TagUtils.write(pageContext, "'] = \"");
		TagUtils.write(pageContext, filterDoubleQuotes(in_value));
		TagUtils.write(pageContext, "\"; ");
		TagUtils.write(pageContext, "</script>\n");
	}
	
	/**
	 * Filter double quotes.	 
	 */
	protected String filterDoubleQuotes(String in_value) {
		StringBuffer sb = new StringBuffer(in_value.length());
		int i =0;
		char c;
		for (i = 0; i < in_value.length(); i++) {
			c = in_value.charAt(i);
			if (c=='"') {
				sb.append('\"');
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
		
	
	/**
	 * This method writes the required Javascript code to declare the variable to the response.
	 */
	public void renderInit(String in_name) throws JspException {		
		TagUtils.write(pageContext, "<script>var ");
		TagUtils.write(pageContext, in_name);
		TagUtils.write(pageContext, " = new Array();");
		TagUtils.write(pageContext, "</script>\n");
	}
	
	public void renderNext(String in_name, int in_index) throws JspException {
		TagUtils.write(pageContext, "<script>");
		TagUtils.write(pageContext, in_name);
		TagUtils.write(pageContext, "[");
		TagUtils.write(pageContext, String.valueOf(in_index));
		TagUtils.write(pageContext, "] = new Object();");		
		TagUtils.write(pageContext, "</script>\n");
	}	
	
	protected void reset() {
		collectionTag = null;
		skip = false;
	}
	
	public void release() {
		property = null;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the property.
	 */
	public String getProperty() {
		return property;
	}
	/**
	 * @param property The property to set.
	 */
	public void setProperty(String property) {
		this.property = property;
	}
}
