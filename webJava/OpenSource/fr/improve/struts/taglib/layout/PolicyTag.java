package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.taglib.html.Constants;

import fr.improve.struts.taglib.layout.policy.AbstractPolicy;
import fr.improve.struts.taglib.layout.util.LayoutUtils;

/**
 * Evaluate the body of the tag in function of the policy.
 * 
 * @author jer80876
 */
public class PolicyTag extends TagSupport {
	private String property;
	private String policy;

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException {
		AbstractPolicy lc_policy = LayoutUtils.getSkin(pageContext.getSession()).getPolicy();
		short lc_mode = lc_policy.getAuthorizedDisplayMode(policy, Constants.BEAN_KEY, property, pageContext);

		if (lc_mode==AbstractPolicy.MODE_EDIT) {
			return EVAL_BODY_INCLUDE;
		} else {
			return SKIP_BODY;
		}
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#release()
	 */
	public void release() {
		super.release();
		property = null;
		policy = null;
	}

	/**
	 * Sets the policy.
	 * @param policy The policy to set
	 */
	public void setPolicy(String policy) {
		this.policy = policy;
	}

	/**
	 * Sets the property.
	 * @param property The property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}

}
