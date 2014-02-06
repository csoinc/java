package fr.improve.struts.taglib.layout.field;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.BaseHandlerTag;

import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;
/**
 * A struts file tag modifief to display a link to the file if there is one already in the system.
 *
 * @author: JeanNoël Ribette
 */
public class FileTag extends AbstractFieldTag {
	protected String fileName;
	protected String fileKey;
	protected String filePath;
	protected String target;
	
protected boolean doBeforeValue() throws JspException {
    StringBuffer buffer = new StringBuffer();    
    if (filePath!=null) {
    	// display a link to the already existing file
	    buffer.append("<a href=\"");
	    Object lc_url = LayoutUtils.getBeanFromPageContext(pageContext, name, filePath);
	    buffer.append(LayoutUtils.computeURL(pageContext, null, lc_url==null ? "" : lc_url.toString(), null, null, null, null, null, false, target));
	    buffer.append("\" class=\"");
	    buffer.append(styleClass);
	    if (target!=null) {
	    	buffer.append("\" target=\"");
	    	buffer.append(target);	
	    }
	    buffer.append("\">");
	    if (fileName != null)
	        buffer.append(LayoutUtils.getBeanFromPageContext(pageContext, name, fileName));
	    else
	        buffer.append(LayoutUtils.getLabel(pageContext, fileKey, null));
	    buffer.append("</a>");
    }
    value = null;

    switch (getFieldDisplayMode()) {
        case MODE_EDIT :
        	if (filePath!=null) {
	            buffer.append("<br>");
        	}
            TagUtils.write(pageContext, buffer.toString());
            break;
        case MODE_INSPECT :
            TagUtils.write(pageContext, buffer.toString());
            return false;
    }

    // set the inner tag to be a FileTag
    fieldTag = new org.apache.struts.taglib.html.FileTag();
    return true;
}
/**
 * Return the value to display.
 */
protected Object getFieldValue() throws JspException {
    return fileName;
}
/**
 * Insert the method's description here.
 * Creation date: (06/09/2001 18:12:17)
 * @return java.lang.String
 */
java.lang.String getFileKey() {
	return fileKey;
}
/**
 * Insert the method's description here.
 * Creation date: (06/09/2001 18:12:19)
 * @return java.lang.String
 */
java.lang.String getFileName() {
	return fileName;
}
/**
 * Insert the method's description here.
 * Creation date: (06/09/2001 18:12:19)
 * @return java.lang.String
 */
java.lang.String getFilePath() {
	return filePath;
}
	public void release() {
		super.release();
		fileName = null;
		fileKey = null;
		filePath = null;
		target = null;
	}
/**
 * Insert the method's description here.
 * Creation date: (06/09/2001 18:12:17)
 * @param newFileKey java.lang.String
 */
public void setFileKey(java.lang.String newFileKey) {
	fileKey = newFileKey;
}
/**
 * Insert the method's description here.
 * Creation date: (06/09/2001 18:12:19)
 * @param newFileName java.lang.String
 */
public void setFileName(java.lang.String newFileName) {
	fileName = newFileName;
}
/**
 * Insert the method's description here.
 * Creation date: (06/09/2001 18:12:19)
 * @param newFilePath java.lang.String
 */
public void setFilePath(java.lang.String newFilePath) {
	filePath = newFilePath;
}
	/**
	 * Returns the target.
	 * @return String
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * Sets the target.
	 * @param target The target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}
	
	protected void copyProperties(BaseHandlerTag in_dest) throws JspException {
		super.copyProperties(in_dest);
		org.apache.struts.taglib.html.FileTag tag = (org.apache.struts.taglib.html.FileTag) in_dest;
		tag.setAccept(getAccept());
		tag.setName(getName());
		
		tag.setCols(getCols());
		tag.setMaxlength(getMaxlength());		
		tag.setProperty(getProperty());
		tag.setRows(getRows());
		tag.setValue(getValue());		
			
	}

}
