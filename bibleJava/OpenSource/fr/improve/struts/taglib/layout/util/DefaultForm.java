package fr.improve.struts.taglib.layout.util;

import org.apache.struts.action.ActionForm;

/**
 * Dummy action form doing nothing.
 * @author: Jean-Noël Ribette
 */
public class DefaultForm extends ActionForm {
	protected String property;
	public String getProperty() {
		return "default value";
	}
	public void setProperty(String property) {
		this.property = property;
	}
}
