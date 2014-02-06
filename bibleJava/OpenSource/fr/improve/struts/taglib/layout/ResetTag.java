package fr.improve.struts.taglib.layout;

/**
 * @author: Jean-Noël Ribette
 */
public class ResetTag extends ActionTag {
	public ResetTag() {
		tag = new org.apache.struts.taglib.html.ResetTag();
	}
	public void release() {
		super.release();
		tag.release();
	}
}
