package fr.improve.struts.taglib.layout.field;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.LabelledTag;
import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Tag used to add a value to a select or radios tag.
 * Same syntax as the org.apache.struts.taglib.html.OptionTag.
 *
 * @author: Jean-Noël Ribette
 */
public class OptionTag extends LabelledTag implements Choice {
	/**
	 * Value of the option.
	 */
	protected String value;
	
	/**
	 * Tooltip of the option
	 */
	protected String tooltip;
	
	/**
	 * The computed label.
	 */
	protected String choiceLabel;
	
	/**
	 * The computed tooltip.
	 */
	protected String choiceTooltip;
	
	public int doEndLayoutTag() throws JspException {
		StringBuffer buffer = new StringBuffer();		
		if (key!=null) choiceLabel = getLabel(); else choiceLabel = value;
		choiceTooltip = LayoutUtils.getLabel(pageContext, bundle, tooltip, null, false);
		ChoiceTag choiceTag = (ChoiceTag) findAncestorWithClass(this, ChoiceTag.class);
		if (choiceTag==null) {
			throw new JspException("<layout:option> is not in a <layout:select> or <layout:radios> tag");
		}
		choiceTag.addChoice(buffer,this);
		TagUtils.write(pageContext, buffer.toString());
		return EVAL_PAGE;
	}
	public int doStartLayoutTag() {
		return EVAL_BODY_INCLUDE;
	}
	public void release() {
		super.release();
		value = null;
		tooltip = null;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}	
	public String getTooltip() {
		return tooltip;
	}
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	
	/**
	 * @see fr.improve.struts.taglib.layout.field.Choice#getChoiceLabel()
	 */
	public String getChoiceLabel() {
		return choiceLabel;
	}
	/**
	 * @see fr.improve.struts.taglib.layout.field.Choice#getChoiceTooltip()
	 */
	public String getChoiceTooltip() {
		return choiceTooltip;
	}
	/**
	 * @see fr.improve.struts.taglib.layout.field.Choice#getChoiceValue()
	 */
	public String getChoiceValue() {
		return Expression.evaluate(value, pageContext);
	}
}
