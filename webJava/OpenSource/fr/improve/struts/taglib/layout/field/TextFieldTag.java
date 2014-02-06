package fr.improve.struts.taglib.layout.field;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.taglib.html.TextTag;
import org.apache.struts.util.ResponseUtils;

import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.formatter.FormatException;
import fr.improve.struts.taglib.layout.util.TagUtils;
/**
 * A simple text field tag, but that can use a formatter to customized the displayed value.
 * @author: Jean-Noël Ribette
 */
public class TextFieldTag extends AbstractFieldTag {
	private TextTag textFieldTag = new org.apache.struts.taglib.html.TextTag();
	
	/**
	 * Inspect formatter.
	 */
	protected String format;
	
	/**
	 * Edit formater
	 */
	protected String editFormat;
	
	protected String jspFormat;
	protected String jspEditFormat;
	
	/**
	 * This method is called before displaying the value.
	 * This is the place to write something before the value, and set the fieldTag value.
	 * @return true - process the tag<br>
	 *		   false - skip the tag
	 */
	protected boolean doBeforeValue() throws javax.servlet.jsp.JspException {
		fieldTag = textFieldTag;
		return true;
	}
	/**
	 * Return the value(s) that will be displayed.
	 */
	protected java.lang.Object getFieldValue() throws JspException {
		Object lc_value = super.getFieldValue();
		if (lc_value != null) {
			switch (getFieldDisplayMode()) {
				case MODE_EDIT:
					if (editFormat!=null) try {
						lc_value = getSkin().getFormatter().format(lc_value, editFormat, pageContext);
					} catch (FormatException fe) {
						TagUtils.saveException(pageContext, fe);
						throw new JspException("Format " + editFormat + "failed: " + fe.getMessage());
					}
					break;
				default:
					if (format!=null) try {
						lc_value = getSkin().getFormatter().format(lc_value, format, pageContext);
					} catch (FormatException fe) {
						TagUtils.saveException(pageContext, fe);
						throw new JspException("Format " + format + "failed: " + fe.getMessage());
					}
					break;
			}						 	
		}
		return lc_value;
	}

	public int doStartInspectField() throws JspException {
		// Maybe do something before dispaying the value.
		if (!doBeforeValue())
			return SKIP_BODY;

		Object lc_value = getFieldValue();
		if (lc_value != null) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("<span class=\"");
			buffer.append(styleClass);
			buffer.append("\">");
			if (format == null) {
				if (filter) {
					buffer.append(ResponseUtils.filter(lc_value.toString()));
				} else {
					buffer.append(lc_value.toString());
				}
			} else {
				buffer.append(lc_value);
			}
			buffer.append("</span>");
			TagUtils.write(pageContext, buffer.toString());
		}

		doAfterValue();

		return EVAL_BODY_INCLUDE;
	}

	public void release() {
		super.release();
		format = null;
		editFormat = null;
	}
	public void setType(String type) {
		format = type;
	}
	public void setEditType(String type) {
		editFormat = type;
	}
	public String getType() {
		return format;
	}
	
	protected void copyProperties(BaseHandlerTag in_dest) throws JspException {
		super.copyProperties(in_dest);
		textFieldTag.setCols(getCols());
		textFieldTag.setMaxlength(getMaxlength());
		textFieldTag.setProperty(getProperty());
		textFieldTag.setRows(getRows());
		textFieldTag.setValue(getValue());
		textFieldTag.setAccept(getAccept());
		textFieldTag.setName(getName());
	}

	protected void initDynamicValues() {		
		super.initDynamicValues();
		
		// format is an EL.
		jspFormat = format;
		format = Expression.evaluate(format, pageContext);
		
		// editoFormat also.
		jspEditFormat = editFormat;
		editFormat = Expression.evaluate(editFormat, pageContext);
	}
	
	protected void reset() {
		// format is an EL
		format = jspFormat;
		jspFormat = null;
		
		// editoFormat also.
		editFormat = jspEditFormat;
		jspEditFormat = null;
		
		super.reset();
	}
}
