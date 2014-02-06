package fr.improve.struts.taglib.layout.field;

import javax.servlet.jsp.JspException;
/**
 * Implemented by tags that allow the user to pick up a choice from a list of values.
 * <br>
 * Example: the tag RadiosTag generates a list of radio buttons. Its method addChoice()
 * generate the code to render one button, and is called by the nested tag defining 
 * the buttons.
 *
 * @author: Jean-Noël Ribette
 */
public interface ChoiceTag {
	/**
	 * Generate the HTML code to add a choice.
	 * @deprecated
	 *
	 * @param sb      Buffer to print the HTML code to.
	 * @param value   The value to send when this choice is selected
	 * @param label   The label to display for this choice.
	 */
	public void addChoice(StringBuffer sb,String value, String label) throws JspException;
	
	/**
	 * Generate the HTML code to add a choice.	
	 * @throws JspException
	 */
	public void addChoice(StringBuffer buffer, Choice choice) throws JspException;
}
