package fr.improve.struts.taglib.layout.field;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.BaseHandlerTag;

import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * This tag displays one radio button and works like the one in the html tag library.
 *
 * @author: Jean-Noel Ribette
 * @version: 0.2
 **/
public class RadioTag extends AbstractFieldTag {
	private org.apache.struts.taglib.html.RadioTag radioTag =
		new org.apache.struts.taglib.html.RadioTag();

	protected boolean doBeforeValue() throws javax.servlet.jsp.JspException {
		fieldTag = radioTag;
		return true;
	}
	
	/**
	 * Override doStartInspectField to display the right images.
	 */
	public int doStartInspectField() throws JspException {	
	// Maybe do something before dispaying the value.
	if (!doBeforeValue()) return SKIP_BODY;
			
	StringBuffer sb = new StringBuffer();
	// If the value is selected, display the image of a selected checkbox.		
	if (isChecked()) {
		sb.append("<img src=\"");
		sb.append(getSkin().getImageDirectory(pageContext.getRequest()));
		sb.append("/");
		sb.append(getSkin().getProperty("layout.checkbox.checked"));
		sb.append("\" border=\"0\" alt=\"");
		sb.append(getSkin().getProperty("layout.checkbox.checked.label"));
		sb.append("\">");	
	} else {
		String lc_imgsrc = getSkin().getProperty("layout.checkbox.unchecked");
    	if (lc_imgsrc!=null && lc_imgsrc.length()>0) {
    		sb.append("<img src=\"");
            sb.append(getSkin().getImageDirectory(pageContext.getRequest()));
            sb.append("/");
            sb.append(lc_imgsrc);
            sb.append("\" border=\"0\" alt=\"");
            sb.append(getSkin().getProperty("layout.checkbox.unchecked.label"));
            sb.append("\">");
    	}
	}
	TagUtils.write(pageContext, sb.toString());

	doAfterValue();
	
	return EVAL_BODY_INCLUDE;	
}
	/**
	 * @see fr.improve.struts.taglib.layout.field.AbstractModeFieldTag#printSimpleHiddenValue(Object)
	 */
	protected void printSimpleHiddenValue(Object in_value) throws JspException {
		// we must not render anything if the radio button is not selected.
		if (isChecked()) {
			super.printSimpleHiddenValue(in_value);
		}
	}
	
	private boolean isChecked() throws JspException {
		Object lc_value = LayoutUtils.getBeanFromPageContext(pageContext, name, property);
		return lc_value==null && value==null || lc_value!=null && lc_value.toString().equals(value);
	}

	/**
	 * @see fr.improve.struts.taglib.layout.field.AbstractModeFieldTag#isFill(Object)
	 */
	public boolean isFill(Object lc_value) throws JspException {
		return isChecked();
	}
	
	protected void copyProperties(BaseHandlerTag in_dest) throws JspException {	
		super.copyProperties(in_dest);		
		radioTag.setProperty(getProperty());
		radioTag.setValue(getValue());
		radioTag.setName(getName());
	}

}