package fr.improve.struts.taglib.layout.crumb;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Render one crumb in a bread crumb panel.
 * Creation date: (17/07/2001 13:05:46)
 * @author: Jean-Noël Ribette
 */
public class CrumbTag extends TagSupport {
	/**
	 * the link
	 */
	protected String link;

	/**
	 * the key of the message to display
	 */
	protected String key;

	/**
	 * the target
	 */
	protected String target;
	
	/**
	 * the bundle 
	 */
	protected String bundle;
	
	public int doStartTag() throws JspException {
		CrumbImpl lc_crumb = new CrumbImpl();
		lc_crumb.setTarget(target);
		lc_crumb.setLink(link);
		lc_crumb.setKey(key);
		lc_crumb.setBundle(bundle);
		
		StringBuffer buffer = new StringBuffer();
		CrumbsTag lc_tag = (CrumbsTag) findAncestorWithClass(this, CrumbsTag.class);
		if (lc_tag==null) {
			throw new JspException("Invalid use of <layout:crumb> tag");	
		}
		lc_tag.printCrumb(buffer, lc_crumb);
		TagUtils.write(pageContext, buffer.toString());
		return SKIP_BODY;
	}
/**
 * Insert the method's description here.
 * Creation date: (17/07/2001 15:08:21)
 * @return java.lang.String
 */
public java.lang.String getKey() {
	return key;
}
/**
 * Insert the method's description here.
 * Creation date: (17/07/2001 15:08:21)
 * @return java.lang.String
 */
public java.lang.String getLink() {
	return link;
}
/**
 * Insert the method's description here.
 * Creation date: (17/07/2001 15:25:47)
 * @return java.lang.String
 */
public java.lang.String getTarget() {
	return target;
}
	public void release() {
		super.release();
		
		link = null;
		key = null;
		target = null;
		bundle = null;
	}
/**
 * Insert the method's description here.
 * Creation date: (17/07/2001 15:08:21)
 * @param newKey java.lang.String
 */
public void setKey(java.lang.String newKey) {
	key = newKey;
}
/**
 * Insert the method's description here.
 * Creation date: (17/07/2001 15:08:21)
 * @param newLink java.lang.String
 */
public void setLink(java.lang.String newLink) {
	link = newLink;
}
/**
 * Insert the method's description here.
 * Creation date: (17/07/2001 15:25:47)
 * @param newTarget java.lang.String
 */
public void setTarget(java.lang.String newTarget) {
	target = newTarget;
}
	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return bundle;
	}

	/**
	 * @param bundle The bundle to set.
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

}
