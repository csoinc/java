package fr.improve.struts.taglib.layout.layer;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.LayoutTagSupport;
import fr.improve.struts.taglib.layout.collection.CollectionTag;
import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.ParentFinder;

/**
 * This tag allow you to apply in a layer differents properties from a bean
 * inside ou outside a collection
 * 
 * @author: Damien Viel, Jean-Noel Ribette
 **/
public class LayerItemTag extends LayoutTagSupport  {
	
	private String name;
	private String property;
	private String key;
	private String value;
	private String JSPValue;
	
	/* (non-Javadoc)
	 * @see fr.improve.struts.taglib.layout.LayoutTagSupport#initDynamicValues()
	 */
	protected void initDynamicValues() {
		JSPValue = value;
		value = Expression.evaluate(value,pageContext);
		super.initDynamicValues();
	}
	
	/* (non-Javadoc)
	 * @see fr.improve.struts.taglib.layout.LayoutTagSupport#reset()
	 */
	protected void reset() {
		value = JSPValue;
		JSPValue = null;
	}
	
	/**
	 * @return Returns the key.
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key The key to set.
	 */
	public void setKey(String key) {
		this.key = key;
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
	/**
	 * @return Returns the value.
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value The value to set.
	 */
	public void setValue(String value) {
		this.value = value;
	}
	protected Object buildContent() throws JspException {
		Object lc_cell = null;
		if (value!=null) {
			return value;
		}
		if (name == null) {
			// The item to add is a property of a bean in the current collection.
			CollectionTag collectionTag = (CollectionTag) ParentFinder.findLayoutTag(this, CollectionTag.class);
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
	
	
	/* (non-Javadoc)
	 * @see fr.improve.struts.taglib.layout.LayoutTagSupport#doEndLayoutTag()
	 */
	public int doEndLayoutTag() throws JspException {
		
		return super.doEndLayoutTag();
	}
	/* (non-Javadoc)
	 * @see fr.improve.struts.taglib.layout.LayoutTagSupport#doStartLayoutTag()
	 */
	public int doStartLayoutTag() throws JspException {
		StringBuffer results = new StringBuffer("");
		String lc_key = LayoutUtils.getLabel(pageContext,getKey(),null);
		Object lc_contentObject = buildContent();
		String lc_content = lc_contentObject==null ? null : lc_contentObject.toString();
		
		if (lc_key!=null){
			results.append(lc_key);
		}
		if (lc_content!=null && lc_content.length()>0){
			results.append(lc_content);	
		}
		LayerTag lc_layerTag = (LayerTag) ParentFinder.findLayoutTag(this, LayerTag.class);
		lc_layerTag.addContent(results.toString());		
		return (EVAL_BODY_INCLUDE);
	}
	
}