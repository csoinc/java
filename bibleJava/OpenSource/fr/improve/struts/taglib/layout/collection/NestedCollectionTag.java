package fr.improve.struts.taglib.layout.collection;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Tag permettant de faire des itérations imbriquées.
 * @author jer80876
 */
public class NestedCollectionTag extends TagSupport {
	protected String name;
	protected String property;
	protected String id;
	protected String indexId;

	protected CollectionTag collectionTag;
	
	public int doStartTag() throws JspException {
		CollectionTag lc_collectionTag = (CollectionTag) findAncestorWithClass(this, CollectionTag.class);
		
		if (lc_collectionTag.isFirst()) try {
			CollectionsIterator lc_iterator = new CollectionsIterator(null, property, id, indexId);
			lc_collectionTag.addIterator(lc_iterator);
		} catch (Exception e) {
			TagUtils.saveException(pageContext, e);
			throw new JspException(e.getMessage());
		}
		
		return EVAL_BODY_INCLUDE;
	}
	
	public int doEndTag() {
		return EVAL_PAGE;
	}
	
	public void release() {
		super.release();
		name = null;
		property = null;
		id = null;	
		indexId = null;
	}
	
	
	/**
	 * Returns the id.
	 * @return String
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the property.
	 * @return String
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * Sets the id.
	 * @param id The id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Sets the property.
	 * @param property The property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * Returns the indexId.
	 * @return String
	 */
	public String getIndexId() {
		return indexId;
	}

	/**
	 * Sets the indexId.
	 * @param indexId The indexId to set
	 */
	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

}
