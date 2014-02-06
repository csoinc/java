package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;

import org.apache.struts.Globals;

import fr.improve.struts.taglib.layout.skin.Skin;
import fr.improve.struts.taglib.layout.util.LayoutUtils;

/**
 * @author: Jean-Noël Ribette
 */
public class ImageTag extends ActionTag {
	protected String src;
	protected String srcKey;
	protected String property ="";
	protected String pageKey;
	protected String page;
	protected String locale = Globals.LOCALE_KEY;
	protected String bundle = Globals.MESSAGES_KEY;
	protected String border;
	protected String altKey;
	protected String alt;
	protected String name;
	public ImageTag() {
		tag = new org.apache.struts.taglib.html.ImageTag();
	}
/**
 * Insert the method's description here.
 * Creation date: (20/04/01 10:53:47)
 * @return java.lang.String
 */
public java.lang.String getAlt() {
	return alt;
}
/**
 * Insert the method's description here.
 * Creation date: (20/04/01 10:53:47)
 * @return java.lang.String
 */
public java.lang.String getAltKey() {
	return altKey;
}
/**
 * Insert the method's description here.
 * Creation date: (20/04/01 10:53:47)
 * @return java.lang.String
 */
public java.lang.String getBorder() {
	return border;
}
/**
 * Insert the method's description here.
 * Creation date: (20/04/01 10:53:47)
 * @return java.lang.String
 */
public java.lang.String getBundle() {
	return bundle;
}
/**
 * Insert the method's description here.
 * Creation date: (20/04/01 10:53:47)
 * @return java.lang.String
 */
public java.lang.String getLocale() {
	return locale;
}
/**
 * Insert the method's description here.
 * Creation date: (20/04/01 10:53:47)
 * @return java.lang.String
 */
public java.lang.String getPage() {
	return page;
}
/**
 * Insert the method's description here.
 * Creation date: (20/04/01 10:53:47)
 * @return java.lang.String
 */
public java.lang.String getPageKey() {
	return pageKey;
}
/**
 * Insert the method's description here.
 * Creation date: (20/04/01 10:53:47)
 * @return java.lang.String
 */
public java.lang.String getProperty() {
	return property;
}
/**
 * Insert the method's description here.
 * Creation date: (20/04/01 10:53:47)
 * @return java.lang.String
 */
public java.lang.String getSrc() {
	return src;
}
/**
 * Insert the method's description here.
 * Creation date: (20/04/01 10:53:47)
 * @return java.lang.String
 */
public java.lang.String getSrcKey() {
	return srcKey;
}
	public void release() {
		super.release();
		src = null;
		srcKey = null;
		property = "";
		pageKey = null;
		page = null;
		locale = Globals.LOCALE_KEY;
		bundle = Globals.MESSAGES_KEY;
		altKey = null;
		alt = null;
		name = null;
		tag.release();
	}
/**
 * Insert the method's description here.
 * Creation date: (20/04/01 10:53:47)
 * @param newAlt java.lang.String
 */
public void setAlt(java.lang.String newAlt) {
	alt = newAlt;
}
/**
 * Insert the method's description here.
 * Creation date: (20/04/01 10:53:47)
 * @param newAltKey java.lang.String
 */
public void setAltKey(java.lang.String newAltKey) {
	altKey = newAltKey;
}
/**
 * Insert the method's description here.
 * Creation date: (20/04/01 10:53:47)
 * @param newBorder java.lang.String
 */
public void setBorder(java.lang.String newBorder) {
	border = newBorder;
}
/**
 * Insert the method's description here.
 * Creation date: (20/04/01 10:53:47)
 * @param newBundle java.lang.String
 */
public void setBundle(java.lang.String newBundle) {
	bundle = newBundle;
}
/**
 * Insert the method's description here.
 * Creation date: (20/04/01 10:53:47)
 * @param newLocale java.lang.String
 */
public void setLocale(java.lang.String newLocale) {
	locale = newLocale;
}
/**
 * Insert the method's description here.
 * Creation date: (20/04/01 10:53:47)
 * @param newPage java.lang.String
 */
public void setPage(java.lang.String newPage) {
	page = newPage;
}
/**
 * Insert the method's description here.
 * Creation date: (20/04/01 10:53:47)
 * @param newPageKey java.lang.String
 */
public void setPageKey(java.lang.String newPageKey) {
	pageKey = newPageKey;
}
/**
 * Insert the method's description here.
 * Creation date: (20/04/01 10:53:47)
 * @param newProperty java.lang.String
 */
public void setProperty(java.lang.String newProperty) {
	property = newProperty;
}
/**
 * Insert the method's description here.
 * Creation date: (20/04/01 10:53:47)
 * @param newSrc java.lang.String
 */
public void setSrc(java.lang.String newSrc) {
	src = newSrc;
}
/**
 * Insert the method's description here.
 * Creation date: (20/04/01 10:53:47)
 * @param newSrcKey java.lang.String
 */
public void setSrcKey(java.lang.String newSrcKey) {
	srcKey = newSrcKey;
}
	protected void copyProperties() throws JspException {
		// Super copy
		super.copyProperties();
		
		// copy struts-layout image src if set.
		if (name!=null) {
			// Create StringBuffer.
			StringBuffer lc_buffer = new StringBuffer();
			
			// Get skin
			Skin lc_skin = LayoutUtils.getSkin(pageContext.getSession());
			
			// Get image directory
			String lc_imgSrc = lc_skin.getImageDirectory(pageContext.getRequest());
			lc_buffer.append(lc_imgSrc);
			if (!lc_imgSrc.endsWith("/") && !name.startsWith("/")) {
				lc_buffer.append('/');
			}
			
			// Get image name
			lc_buffer.append(name);
			// Set image src.
			((org.apache.struts.taglib.html.ImageTag) tag).setSrc(lc_buffer.toString());
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
