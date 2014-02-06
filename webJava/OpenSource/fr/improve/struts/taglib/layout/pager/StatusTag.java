package fr.improve.struts.taglib.layout.pager;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.LayoutTagSupport;
import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * @author jnribette
 */
public class StatusTag extends LayoutTagSupport {
	protected int numberOfPage;
	protected int currentPageNumber;
	protected int size;
	protected int itemsPerPage;
	protected String styleClass;
	protected String key;
	protected String width;
		
	public int doStartLayoutTag() throws JspException {
		boolean lc_isNested = Boolean.TRUE.equals(new StartLayoutEvent(this, null).send());
		Integer[] lc_infos = (Integer[]) new PagerStatusEvent(this, null).send();
		currentPageNumber = lc_infos[0].intValue();
		numberOfPage = lc_infos[1].intValue();
		size = lc_infos[2].intValue();
		itemsPerPage = lc_infos[3].intValue();
		
		if (numberOfPage > 1) {
			StringBuffer lc_buffer = new StringBuffer("");
			if (!lc_isNested) {
				lc_buffer.append("<table border=\"0\" cellspacing=\"1\" cellpadding=\"1\"");
				if (width!=null) {
					lc_buffer.append(" width=\"");
					lc_buffer.append(width);
					lc_buffer.append("\"");
				}
				lc_buffer.append(" class=\"PAGER\"><tr>");
			}
			lc_buffer.append("<td");
			if (styleClass!=null) {
				lc_buffer.append(" class=\"");
				lc_buffer.append(styleClass);
				lc_buffer.append("\"");					
			}
			lc_buffer.append(">");
			if (key!=null) {
				String[] lc_args = new String[5];
				lc_args[0] = String.valueOf(currentPageNumber + 1);
				lc_args[1] = String.valueOf(numberOfPage);
				lc_args[2] = String.valueOf(size);
				lc_args[3] = String.valueOf(currentPageNumber * itemsPerPage +1);
				int lc_lastItemDisplay = (currentPageNumber+1) * itemsPerPage;
				if (lc_lastItemDisplay > size) {
					lc_lastItemDisplay = size;	
				}
				lc_args[4] = String.valueOf(lc_lastItemDisplay);
				lc_buffer.append(LayoutUtils.getLabel(pageContext, key, lc_args));
			} else {
				lc_buffer.append(currentPageNumber+1);
				lc_buffer.append("/");
				lc_buffer.append(numberOfPage);				
			}
			lc_buffer.append("</td>");
			if (!lc_isNested) {
				lc_buffer.append("</tr></table>");
			}
			TagUtils.write(pageContext, lc_buffer.toString());
		}
		
		return EVAL_BODY_INCLUDE;
	}
	public int doEndLayoutTag() throws JspException {
		new EndLayoutEvent(this, null).send();
		
		numberOfPage = 0;
		currentPageNumber = 0;
		size = 0;
		itemsPerPage = 0;
				
		return EVAL_PAGE;	
	}
	public void release() {
		super.release();
		styleClass = null;
		key = null;	
	}
	
	/**
	 * Sets the styleClass.
	 * @param styleClass The styleClass to set
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	/**
	 * Sets the key.
	 * @param key The key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Sets the width.
	 * @param width The width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

}
