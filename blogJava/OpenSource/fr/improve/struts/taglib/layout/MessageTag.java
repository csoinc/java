package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Custom tag that retrieves an internationalized messages string (with
 * optional parametric replacement) from the <code>ActionResources</code>
 * object stored as a context attribute by our associated
 * <code>ActionServlet</code> implementation.
 * <br>
 * <br>
 * Modify to display the key if the label is not present<br>
 * The tag can be nested in panel and line tags if the styleClass attribute is set.
 *
 * @author Craig R. McClanahan
 * @author Jean-Noël Ribette
 */

public class MessageTag extends LabelledTag {
	protected boolean layout = true;
	/**
	 * Process the start tag.
	 *
	 * @exception JspException if a JSP exception has occurred
	 */
	public int doStartLayoutTag() throws JspException {		
		doStartLayout();
		String key = this.key;
		if (key == null) {
			// Look up the requested property value
			Object value =
				TagUtils.lookup(pageContext, name, property, null);
			if (value != null && !(value instanceof String)) {
				JspException e = new JspException
					(messages.getMessage("message.property", key));
				TagUtils.saveException(pageContext, e);
				throw e;
			}
			key = (String)value;
		}
		String lc_message = getLabel();
		TagUtils.write(pageContext, lc_message);

		doEndLayout();	
		// Continue processing this page
		return (SKIP_BODY);
	}
	/**
	 * Release any acquired resources.
	 */
	public void release() {
		super.release();	
		layout = true;	
	}
	/**
	 * Print the required HTML code so that the tag can be nested in panel and line tags.<br>
	 * This won't do anything if styleClass is null.
	 */
	private void doStartLayout() throws JspException {
		if (layout) {			
			StringBuffer lc_buffer = new StringBuffer("<th");
			if (styleClass!=null) {
				lc_buffer.append(" class=\"");
				lc_buffer.append(styleClass);
				lc_buffer.append("\"");
			}
			lc_buffer.append(" colspan=\"");
			lc_buffer.append(LayoutUtils.getSkin(pageContext.getSession()).getFieldInterface().getColumnNumber());
			lc_buffer.append("\">");								
			new StartLayoutEvent(this, lc_buffer.toString()).send();
		}
	}
	
	/**
	 * Print the required HTML code so that the tag can be nested in panel and line tags.<br>
	 * This won't do anything if styleClass is null.
	 */	
	private void doEndLayout() throws JspException {
		if (layout) {
			new EndLayoutEvent(this,"</th>").send();
		}
	}

	/**
	 * Sets the layout.
	 * @param layout The layout to set
	 */
	public void setLayout(boolean layout) {
		this.layout = layout;
	}

}
