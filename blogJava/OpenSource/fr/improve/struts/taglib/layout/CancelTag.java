package fr.improve.struts.taglib.layout;

import org.apache.struts.taglib.html.Constants;

/**
 * @author: Jean-Noël Ribette
 */
public class CancelTag extends ActionTag {
	public CancelTag() {
		tag = new org.apache.struts.taglib.html.CancelTag();
		property = Constants.CANCEL_PROPERTY;
	}
	public void release() {
		super.release();
		tag.release();
		property = Constants.CANCEL_PROPERTY;
	}
}
