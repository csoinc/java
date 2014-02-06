package fr.improve.struts.taglib.layout;

import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.taglib.html.Constants;

import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Iterate over the specified collection and render the body of the tag in different cells. <br>
 * The number of cell per lines can be fixed by the user. <br>
 * @author: Jean-Noël Ribette
 */
public class TableTag extends BodyTagSupport {
	protected String name = Constants.BEAN_KEY;
	protected String property;
	protected String id;
	protected String indexId;
	
	protected String width;
	protected String align;
	protected int col = 2;

	protected Iterator iterator = null;
	protected int index = 0;
	public int doAfterBody() throws JspException {

		// Render the output from this iteration to the output stream
		if (bodyContent != null) {
			TagUtils.writePrevious(pageContext, "<td>");
			TagUtils.writePrevious(pageContext, bodyContent.getString());
			TagUtils.writePrevious(pageContext, "</td>");
			if (index % col == 0) TagUtils.writePrevious(pageContext, "</tr><tr>");
			bodyContent.clearBody();
		}

		// Decide whether to iterate or quit
		if (iterator.hasNext()) {
		    Object element = iterator.next();
			if (element == null)
				pageContext.removeAttribute(id);
			else
				pageContext.setAttribute(id, element);
		    index++;
			if (indexId != null)
				pageContext.setAttribute(indexId, new Integer(index));
		    return (EVAL_BODY_TAG);
		} else
		    return (SKIP_BODY);

	}
	public int doEndTag() throws JspException {
		TagUtils.write(pageContext,"</tr></table>");
		return EVAL_PAGE;
	}
	public int doStartTag() throws JspException {
		Object collection = LayoutUtils.getBeanFromPageContext(pageContext, name, property);
		iterator = LayoutUtils.getIterator(collection);

		if (iterator.hasNext()) {
		    Object element = iterator.next();
			if (element == null)
				pageContext.removeAttribute(id);
			else
				pageContext.setAttribute(id, element);
		    index++;
			if (indexId != null)
				pageContext.setAttribute(indexId, new Integer(index));

			StringBuffer sb = new StringBuffer();
			sb.append("<table border=\"0\"");
			if (width!=null) {
				sb.append(" width=\"");
				sb.append(width);
				sb.append("\"");
			}
			if (align!=null) {
				sb.append(" align=\"");
				sb.append(align);
				sb.append("\"");
			}
			sb.append("><tr>");
			TagUtils.write(pageContext,sb.toString());
			return EVAL_BODY_TAG;
		}
		return SKIP_BODY;
	}
/**
 * Creation date: (03/07/01 15:15:15)
 * @return java.lang.String
 */
public java.lang.String getAlign() {
	return align;
}
/**
 * Creation date: (03/07/01 15:15:15)
 * @return java.lang.String
 */
public java.lang.String getId() {
	return id;
}
/**
 * Creation date: (03/07/01 15:15:15)
 * @return java.lang.String
 */
public java.lang.String getIndexId() {
	return indexId;
}
/**
 * Creation date: (03/07/01 15:15:15)
 * @return java.lang.String
 */
public java.lang.String getName() {
	return name;
}
/**
 * Creation date: (03/07/01 15:15:15)
 * @return java.lang.String
 */
public java.lang.String getProperty() {
	return property;
}
/**
 * Creation date: (03/07/01 15:15:15)
 * @return java.lang.String
 */
public java.lang.String getWidth() {
	return width;
}
	public void release() {
		name = Constants.BEAN_KEY;
		property = null;
		id = null;
		indexId = null;

		width = null;
		align = null;
		col = 2;

		iterator = null;
		index = 0;
	}
/**
 * Creation date: (03/07/01 15:15:15)
 * @param newAlign java.lang.String
 */
public void setAlign(java.lang.String newAlign) {
	align = newAlign;
}
	public void setCol(String col) throws JspException {
		try {
			this.col = Integer.parseInt(col);
			if (this.col<1) throw new Exception();
		} catch (Exception e) {
			throw new JspException("Invalid attribute for TableTag: row most be a positive integer");
		}			
			
	}
/**
 * Creation date: (03/07/01 15:15:15)
 * @param newId java.lang.String
 */
public void setId(java.lang.String newId) {
	id = newId;
}
/**
 * Creation date: (03/07/01 15:15:15)
 * @param newIndexId java.lang.String
 */
public void setIndexId(java.lang.String newIndexId) {
	indexId = newIndexId;
}
/**
 * Creation date: (03/07/01 15:15:15)
 * @param newName java.lang.String
 */
public void setName(java.lang.String newName) {
	name = newName;
}
/**
 * Creation date: (03/07/01 15:15:15)
 * @param newProperty java.lang.String
 */
public void setProperty(java.lang.String newProperty) {
	property = newProperty;
}
/**
 * Creation date: (03/07/01 15:15:15)
 * @param newWidth java.lang.String
 */
public void setWidth(java.lang.String newWidth) {
	width = newWidth;
}
}
