/*
 * Copyrights Improve SA 2005
 * All rights reserved
 */
package fr.improve.struts.taglib.layout.collection.header;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.LayoutTagSupport;
import fr.improve.struts.taglib.layout.collection.BaseCollectionTag;

/**
 * Tag allowing to specify multiple level of collection headers.
 * @author JN Ribette
 */
public class CollectionTitleTag extends LayoutTagSupport implements MultiLevelTitleHandler {
	protected String title;
	protected String tooltip;
	protected String arg0;
	protected String arg1;
	protected String styleClass;
	
	protected MultiLevelHeader header;
	protected BaseCollectionTag collectionTag;
	
	public int doStartLayoutTag() {
		collectionTag = 
			(BaseCollectionTag) findAncestorWithClass(this, BaseCollectionTag.class);
		if (collectionTag.isFirst()) {
			header = new MultiLevelHeader(title, arg0, arg1, styleClass, false);
			header.setTooltip(tooltip);
		}
		return EVAL_BODY_INCLUDE;
	}
	
	public int doEndLayoutTag() throws JspException {
		if (collectionTag.isFirst()) {
			new CollectionItemEvent(this, header).send();
		}
		return EVAL_PAGE;
	}
	
	public Object addCollectionTitle(CollectionItemEvent in_event) {
		header.addHeader((MultiLevelHeader) in_event.getValue());
		return null;
	}

	
	public void release() {
		title= null;
		tooltip = null;
		arg0 = null;
		arg1 = null;
		styleClass = null;
	}
	
	public void setArg0(String in_arg0) {
		this.arg0 = in_arg0;
	}
	public void setArg1(String in_arg1) {
		this.arg1 = in_arg1;
	}
	public void setTitle(String in_title) {
		this.title = in_title;
	}
	public void setStyleClass(String in_styleClass) {
		this.styleClass = in_styleClass;
	}	
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
}
