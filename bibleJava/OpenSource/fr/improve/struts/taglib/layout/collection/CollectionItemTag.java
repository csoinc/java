package fr.improve.struts.taglib.layout.collection;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * This tag is used to add a property to display to a tag that can display collections
 * (the parent tag must be an instanceof BaseCollectionTag)
 * <br><br>
 * header - The key of the title to display for this property<br>
 * property - The property to display<br>
 * <br>
 * @see BaseCollectionTag BaseCollectionTag
 *
 * @author: Jean-Noel Ribette
 **/
public class CollectionItemTag extends FastCollectionItemTag implements BodyTag {
	protected BodyContent bodyContent;
	protected boolean useBody = true; 
	
	public void doInitBody() throws JspException {
		// do nothing.
	}
	public int doAfterBody() throws JspException {
		// do nothing.
		return SKIP_BODY;
	}	
	public void setBodyContent(BodyContent in_content) {
		bodyContent = in_content;
	}
	
	public void release() {
		super.release();
		bodyContent = null;	
	}
	
	public int doStartLayoutTag() throws JspException {
		int lc_result = super.doStartLayoutTag();
		if (lc_result==EVAL_BODY_INCLUDE) {
			return EVAL_BODY_TAG;
		} else {
			return lc_result;
		}
	}
	
	protected Object buildContent() throws JspException {
		if (bodyContent != null && bodyContent.getString().length() > 0) {
			// The item to add is the body content of the tag.
			Object lc_cell = bodyContent.getString();
			bodyContent.clearBody();
			useBody = true;			
			return lc_cell;
		} else {
			useBody = false;
			return super.buildContent();
		}
	}
	
	protected boolean buildFilter() {		
		if (useBody) {
			return false;
		} else {
			return super.buildFilter();
		}
	}		

	}
