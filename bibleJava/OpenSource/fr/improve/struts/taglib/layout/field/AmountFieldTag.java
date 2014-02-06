package fr.improve.struts.taglib.layout.field;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;
/**
 * TextFieldTag modified to display an anmount symbol after the value and format it with the "amount" formatter.
 *
 * @author: Jean-Noël Ribette
 */
public class AmountFieldTag extends TextFieldTag {
	AmountFieldTag() {
		format = "amount";
	}
protected void doAfterValue() throws JspException {
	TagUtils.write(pageContext,"&nbsp;");
    TagUtils.write(pageContext,LayoutUtils.getLabel(pageContext, "layout.amount", null));
}
	public void release() {
		super.release();
		format = "amount";
	}
}
