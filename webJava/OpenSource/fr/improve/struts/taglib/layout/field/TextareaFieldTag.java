package fr.improve.struts.taglib.layout.field;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.taglib.html.TextareaTag;
/**
 * Insert the type's description here.
 * Creation date: (01/12/2001 02:18:46)
 * @author: Jean-Noël Ribette
 */
public class TextareaFieldTag extends AbstractFieldTag {
	TextareaTag textareaTag = new org.apache.struts.taglib.html.TextareaTag();
	boolean textAreaFilter;
	
	protected boolean doBeforeValue() throws javax.servlet.jsp.JspException {
    fieldTag = textareaTag;   
    textAreaFilter = filter;
    filter = false;
    return true;
}
protected Object getFieldValue() throws JspException {
    Object lc_value = super.getFieldValue();
    switch (getFieldDisplayMode()) {
        case MODE_EDIT :
            return lc_value;
        case MODE_NODISPLAY :
            return lc_value;
        case MODE_INSPECT :
        case MODE_INSPECT_ONLY :
        case MODE_INSPECT_PRESENT :
            if (lc_value == null) {
                return null;
            }
            // Remplacement des \n par des <br>, des \t par des escapes et coupure des mots trop longs.
            StringBuffer lc_formattedBuffer = new StringBuffer(lc_value.toString());
            int lc_numberOfConsecutiveCharacter = 0;
            for (int i = 0; i < lc_formattedBuffer.length(); i++) {
                lc_numberOfConsecutiveCharacter++;
                if (lc_formattedBuffer.charAt(i) == ' ') {
                    lc_numberOfConsecutiveCharacter = 0;
                    continue;
                }

                if (lc_numberOfConsecutiveCharacter >= 80) {
                    lc_formattedBuffer.insert(i, " ");
                    lc_numberOfConsecutiveCharacter = 0;
                    continue;
                }
                if (lc_formattedBuffer.charAt(i) == '\n') {
                    lc_formattedBuffer.replace(i, i + 1, "<br>");
                    i += 3;                    
                    lc_numberOfConsecutiveCharacter = 0;
                    continue;
                }
                if (lc_formattedBuffer.charAt(i) == '\t') {
                    lc_formattedBuffer.replace(i, i + 1, "&nbsp;&nbsp;&nbsp;&nbsp;");
                    lc_numberOfConsecutiveCharacter = 0;
                    continue;
                }
                if (lc_formattedBuffer.charAt(i) == '<' && textAreaFilter) {
                    lc_formattedBuffer.replace(i, i + 1, "&lt;");
                    continue;
                }
                if (lc_formattedBuffer.charAt(i) == '>' && textAreaFilter) {
                    lc_formattedBuffer.replace(i, i + 1, "&gt;");
                    continue;
                }
                if (lc_formattedBuffer.charAt(i) == '"') {
                	// Double quote breaks the hidden field in inspect mode.
                	lc_formattedBuffer.replace(i, i+1, "&quot;");
                	continue;
                }
            }
            return lc_formattedBuffer.toString();
    }
    return lc_value;
}
	
	protected void doAfterValue() throws JspException {
		filter = textAreaFilter;
	}

	protected void copyProperties(BaseHandlerTag in_dest) throws JspException {
		super.copyProperties(in_dest);
		textareaTag.setCols(getCols());
		textareaTag.setMaxlength(getMaxlength());
		textareaTag.setProperty(getProperty());
		textareaTag.setRows(getRows());
		textareaTag.setValue(getValue());
		textareaTag.setName(getName());
	}

}
