package fr.improve.struts.taglib.layout.field;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.util.LayoutUtils;
/**
 * This tag lets the user select a value from a collection by selecting a radio button.<br>
 * The tag works like the select tag expect that it generates radio buttons instead of select options.
 */
public class CheckboxesTag extends AbstractMultipleSelectTag {

/**
 * This method is called by the nested tags to add a value in the list.
 */
public boolean add1Choice(StringBuffer sb, String value) throws JspException {
    switch (getFieldDisplayMode()) {
        case MODE_INSPECT :
        	// If the value is selected, display the image of a selected checkbox.
            if (matches.contains(value)) {
                sb.append("<img src=\"");
                sb.append(getSkin().getImageDirectory(pageContext.getRequest()));
                sb.append("/");
                sb.append(getSkin().getProperty("layout.checkbox.checked"));
                sb.append("\" border=\"0\" alt=\"");
                sb.append(getSkin().getProperty("layout.checkbox.checked.label"));
                sb.append("\">");
                return true;
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
	            return false;
            }
        case MODE_EDIT :
        	// Display a checkbox.
            sb.append("<input type=\"checkbox\" name=\"");
            sb.append(property);
            sb.append("[");
            sb.append(index);
            sb.append("]\" value=\"");
            sb.append(value);
            sb.append("\"");
			if (getTooltip()!=null) {
				sb.append(" title=\"");
				sb.append(LayoutUtils.getLabel(getPageContext(), getBundle(), getTooltip(), null, false));
				sb.append("\"");		
			}
			if (onchange!=null) {
				sb.append(" onchange=\"");
				sb.append(onchange);
				sb.append("\"");
			}
			if (onclick!=null) {
				sb.append(" onclick=\"");
				sb.append(onclick);
				sb.append("\"");
			}
            if (matches.contains(value)) {
                sb.append(" checked>");
                return true;
            }
            sb.append(">");
            return false;
        default :
            return false;
    }
}
}
