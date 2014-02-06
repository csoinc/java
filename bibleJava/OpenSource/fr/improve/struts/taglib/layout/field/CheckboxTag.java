package fr.improve.struts.taglib.layout.field;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.BaseHandlerTag;

import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;
/**
 * A simple checkbox tag.
 * @author: Jean-Noël Ribette
 */
public class CheckboxTag extends AbstractFieldTag {
	org.apache.struts.taglib.html.CheckboxTag checkboxTag = new org.apache.struts.taglib.html.CheckboxTag();
protected boolean doBeforeValue() {
	fieldTag = checkboxTag;
    return true;
}
public int doStartInspectField() throws JspException {     
      // Maybe do something before displaying the value.
      if (!doBeforeValue()) return SKIP_BODY;                 

      doAfterValue();
     
      return EVAL_BODY_INCLUDE;    
}

 

protected Object getFieldValue() throws JspException {
    Object lc_value = value;
    if (lc_value==null) lc_value = "on";
    switch (getFieldDisplayMode()) {
        case MODE_EDIT :
            break;
        case MODE_NODISPLAY :
            break;
        case MODE_INSPECT :
            return super.getFieldValue();
    }
    return lc_value;
}

protected void doAfterValue() throws JspException {
    Object lc_value = value;
    if (lc_value==null) lc_value = "on";
    switch (getFieldDisplayMode()) {
        case MODE_EDIT :
        	break;
        case MODE_NODISPLAY :
            break;
        case MODE_INSPECT :        
            if (isChecked()) {
                StringBuffer buffer = new StringBuffer();
                buffer.append("<img src=\"");
                buffer.append(getSkin().getImageDirectory(pageContext.getRequest()));
                buffer.append("/");
                buffer.append(getSkin().getProperty("layout.checkbox.checked"));
                buffer.append("\" border=\"0\" alt=\"");
                buffer.append(getSkin().getProperty("layout.checkbox.checked.label"));
                buffer.append("\">");
                TagUtils.write(pageContext, buffer.toString());
            } else {
				String lc_imgsrc = null;
				String lc_label = "unchecked";
            	try {
            		lc_imgsrc = getSkin().getProperty("layout.checkbox.unchecked");
            		lc_label = getSkin().getProperty("layout.checkbox.unchecked.label");
            	} catch(java.util.MissingResourceException me) {
            		// Nothing            		
            	}
            	if (null != lc_imgsrc && lc_imgsrc.length()!=0) { 
               		StringBuffer buffer = new StringBuffer();
               		buffer.append("<img src=\"");
               		buffer.append(getSkin().getImageDirectory(pageContext.getRequest()));
               		buffer.append("/");
               		buffer.append(lc_imgsrc);
               		buffer.append("\" border=\"0\" alt=\"");
               		buffer.append(lc_label);
               		buffer.append("\">");
                	TagUtils.write(pageContext, buffer.toString());
            	}            	            	
            }
	    }
	}
	/**
	 * @see fr.improve.struts.taglib.layout.field.AbstractModeFieldTag#printSimpleHiddenValue(Object)
	 */
	protected void printSimpleHiddenValue(Object in_value) throws JspException {
		// A checkbox is a fucking widget: we must not render anytinh if it is not checked.
		if (isChecked()) {
			super.printSimpleHiddenValue(in_value);
		}
	}
	
	protected boolean isChecked() throws JspException {
		Object lc_value = LayoutUtils.getBeanFromPageContext(pageContext, name, property);
        return lc_value != null && (lc_value.toString().equals("on") || lc_value.toString().equals("yes") || lc_value.toString().equals("true"));
	}
	
	protected void copyProperties(BaseHandlerTag in_dest) throws JspException {
		super.copyProperties(in_dest);	
		checkboxTag.setName(getName());
		checkboxTag.setProperty(getProperty());		
		checkboxTag.setValue(getValue());
		checkboxTag.setReadonly(isReadonly());
	}

}
