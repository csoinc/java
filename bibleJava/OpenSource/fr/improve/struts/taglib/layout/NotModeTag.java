package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import fr.improve.struts.taglib.layout.util.FormUtilsInterface;
import fr.improve.struts.taglib.layout.util.LayoutUtils;

/**
 * This tag is based on the struts-layout tag <code>ModeTag</code>
 * which selectively includes a body based on the form mode. This
 * tag is the reverse of the <code>ModeTag</code> as it only includes
 * its body if the specified mode and the current mode do not
 * match.
 *
 * @author gbenis1
 */
public class NotModeTag extends TagSupport {
	/**
	 * String representation of the Form mode. Can be
	 * "create", "edit" or "inspect".
	 */
	String value;

	/**
	 * Returns the form mode string value.
	 * @return Form mode string representation.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the form mode as string.
	 * @param value The form mode to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Performs the tag actions. If the mode is not equal
	 * to the specified mode, then the body is included,
	 * otherwise the body is ignored.
	 *
	 * @return EVAL_BODY_INCLUDE if the body is to be included.
	 */
	public int doStartTag() throws JspException {
		int mode = LayoutUtils.getSkin(pageContext.getSession()).getFormUtils().getFormDisplayMode(pageContext);
		int notDesiredMode = -1;

		if ("create".equalsIgnoreCase(value)) {
			notDesiredMode = FormUtilsInterface.CREATE_MODE;
		} else if ("edit".equalsIgnoreCase(value)) {
			notDesiredMode = FormUtilsInterface.EDIT_MODE;
		} else if ("inspect".equalsIgnoreCase(value)) {
			notDesiredMode = FormUtilsInterface.INSPECT_MODE;
		}

		if (notDesiredMode == -1) {
			throw new JspException(
				"Form mode "
					+ value
					+ " is not valid. Valid form modes are create, edit and inspect.");
		}

		if (notDesiredMode != mode) {
			return EVAL_BODY_INCLUDE;
		} else {
			return SKIP_BODY;
		}
	}

}
