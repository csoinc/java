package com.oyou.web.blog.taglib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.jsp.JspException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.logic.IterateTag;
import org.apache.struts.util.MessageResources;

import com.oyou.common.exception.BusinessException;
import com.oyou.common.reader.Encoder;
import com.oyou.common.util.StringHelper;
import com.oyou.domain.blog.orm.BlogMessage;
import com.oyou.domain.blog.orm.BlogMessageType;
import com.oyou.domain.blog.orm.BlogReplyMessage;
import com.oyou.web.blog.spring.BlogTagSupport;
import com.oyou.web.blog.util.ConfigHelper;
import com.oyou.web.blog.util.StrutsHelper;
import com.oyou.web.blog.util.UploadHelper;

import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.policy.AbstractPolicy;
import fr.improve.struts.taglib.layout.skin.Skin;
import fr.improve.struts.taglib.layout.util.FormUtilsInterface;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.ParentFinder;
import fr.improve.struts.taglib.layout.util.TagUtils;

public final class PlainTag extends BlogTagSupport {
	static final long serialVersionUID = 1;
	private static final Log log = LogFactory.getLog(PlainTag.class);
	private static final String PLAIN = "plain";
	/**
	 * The message resources for this package.
	 */
	protected static MessageResources messages = MessageResources.getMessageResources(Constants.Package + ".LocalStrings");
	/**
	 * The body content of this tag (if any).
	 */
	protected String text = null;
	/**
	 * The anchor to be added to the end of the generated hyperlink.
	 */
	protected String anchor = null;
	/**
	 * The logical forward name from which to retrieve the hyperlink URI.
	 */
	protected String forward = null;
	/**
	 * The hyperlink URI.
	 */
	protected String href = null;
	protected String jspHref;
	/**
	 * The link name for named links.
	 */
	protected String linkName = null;
	/**
	 * The JSP bean name for query parameters.
	 */
	protected String name = null;
	/**
	 * The context-relative page URL (beginning with a slash) to which this hyperlink will be rendered.
	 */
	protected String page = null;
	protected String action;
	protected String module;
	protected String bundle;
	
	/**
	 * The single-parameter request parameter name to generate.
	 */
	protected String paramId = null;
	/**
	 * The single-parameter JSP bean name.
	 */
	protected String paramName = null;
	/**
	 * The single-parameter JSP bean property.
	 */
	protected String paramProperty = null;
	/**
	 * The single-parameter JSP bean scope.
	 */
	protected String paramScope = null;
	/**
	 * The JSP bean property name for query parameters.
	 */
	protected String property = null;
	/**
	 * The scope of the bean specified by the name property, if any.
	 */
	protected String scope = null;
	/**
	 * The window target.
	 */
	protected String target = null;
	/**
	 * Include transaction token (if any) in the hyperlink?
	 */
	protected boolean transaction = false;
	/**
	 * Name of parameter to generate to hold index number
	 */
	protected String indexId = null;
	protected boolean layout = true;
	protected boolean display = true;
	protected boolean showLink = true;
	protected String policy = null;

	/**
	 * Prepare to display the link.
	 */
	protected void beginLinkLayout(StringBuffer in_buffer) throws JspException {
	}

	/**
	 * Save the associated label from the body content.
	 * 
	 * @exception JspException if a JSP exception has occurred
	 */
	public int doAfterBody() throws JspException {
		if (bodyContent != null) {
			String value = bodyContent.getString().trim();
			if (value.length() > 0)
				text = value;
		}
		return (SKIP_BODY);
	}

	/**
	 * Render the end of the hyperlink.
	 * 
	 * @exception JspException if a JSP exception has occurred
	 */
	public int doEndLayoutTag() throws JspException {
		// do nothing if the tag is no displayed
		if (!display) {
			display = true;
			return EVAL_PAGE;
		}
		// Prepare the textual content and ending element of this hyperlink
		StringBuffer results = new StringBuffer();
		if (text != null)
			results.append(text);
		// Render the remainder to the output stream
		TagUtils.write(pageContext, results.toString());
		if (isLayout()) {
			results.setLength(0);
			endLinkLayout(results);
			new EndLayoutEvent(this, results.toString()).send();
		}
		// Evaluate the remainder of this page
		return (EVAL_PAGE);
	}

	public final int doEndTag() throws JspException {
		try {
			return doEndLayoutTag();
		} finally {
			reset();
			ParentFinder.deregisterTag(pageContext);
		}
	}

	/**
	 * Render the beginning of the hyperlink. Indexed property since 1.1
	 * 
	 * @exception JspException if a JSP exception has occurred
	 */
	public int doStartLayoutTag() throws JspException {
		// if the action is about to be displayed, check the authorization first
		if (policy != null) {
			Skin lc_currentSkin = LayoutUtils.getSkin(pageContext.getSession());
			AbstractPolicy lc_policy = lc_currentSkin.getPolicy();
			String lc_action = null;
			if (getHref() != null) {
				lc_action = getHref();
			} else if (getPage() != null) {
				lc_action = getPage();
			} else if (getForward() != null) {
				lc_action = getForward();
			} else {
				throw new JspException("You must specify exactly one of the following attributes for the Link tag: href, page or forward.");
			}
			switch (lc_policy.getAuthorizedDisplayMode(getPolicy(), lc_action, getProperty(), pageContext)) {
			case AbstractPolicy.MODE_EDIT:
				break;
			case AbstractPolicy.MODE_NODISPLAY:
				display = false;
				break;
			case AbstractPolicy.MODE_INSPECT:
				showLink = false;
				break;
			default:
				throw new IllegalStateException(lc_policy.getClass().getName() + " returns an illegal value");
			}
		}
		// do nothing if the action is not displayed in this mode.
		if (!display)
			return SKIP_BODY;
		if (isLayout()) {
			StringBuffer lc_buffer = new StringBuffer();
			beginLinkLayout(lc_buffer);
			new StartLayoutEvent(this, lc_buffer.toString()).send();
		}
		// Generate the hyperlink URL
		Map params = LayoutUtils.computeParameters(pageContext, paramId, paramName, paramProperty, paramScope, name, property, scope, transaction);
		// if "indexed=true", add "index=x" parameter to query string
		// since 1.1
		if (indexId != null) {
			// look for outer iterate tag
			IterateTag iterateTag = (IterateTag) findAncestorWithClass(this, IterateTag.class);
			if (iterateTag == null) {
				// this tag should only be nested in iteratetag, if it's not, throw exception
				JspException e = new JspException(messages.getMessage("indexed.noEnclosingIterate"));
				TagUtils.saveException(pageContext, e);
				throw e;
			}
			// calculate index, and add as a parameter
			if (params == null) {
				params = new HashMap(); // create new HashMap if no other params
			}
			params.put(indexId, Integer.toString(iterateTag.getIndex()));
		}
		String url = LayoutUtils.computeURL(pageContext, forward, href, page, action, module, params, anchor, false, target);
		StringBuffer htmlContent = new StringBuffer();
		if (PLAIN.equals(this.action)) {
			// TODO: convert url here
			log.debug("==Start converting layout link for LinkAction");
			Map map = StrutsHelper.getURLParams(url);
			if (map != null) {
				String id = (String) map.get("mid");
				StringBuffer uploadFile = new StringBuffer();
				if (StringHelper.isNotEmpty(id)) {
					try {
						BlogMessage blogMessage = this.getBlogService().getBlogMessageByID(Long.valueOf(id));
						this.getBlogService().increaseMessageViewTimes(blogMessage.getId());
						//uploadFile.append(UploadHelper.getContextRealPath(this.getPageContext().getServletContext()));
						uploadFile.append(this.getBlogService().getBlogProperties(ConfigHelper.UPLOAD_PATH).getValue() + File.separator);
						uploadFile.append(UploadHelper.getInstance().getUploadRelativePath(BlogMessageType.PLAIN, blogMessage.getUpdateTime()));
						uploadFile.append(blogMessage.getUploadFile());
					} catch (BusinessException be) {
						log.error("==BusinessException " + be.getMessage());
					}
				} else {
					id = (String) map.get("rid");
					if (StringHelper.isNotEmpty(id)) {
						try {
							BlogReplyMessage blogMessage = this.getBlogService().getBlogReplyMessageByID(Long.valueOf(id));
							this.getBlogService().increaseReplyMessageViewTimes(blogMessage.getId());
							//uploadFile.append(UploadHelper.getContextRealPath(this.getPageContext().getServletContext()));
							uploadFile.append(this.getBlogService().getBlogProperties(ConfigHelper.UPLOAD_PATH).getValue() + File.separator);
							uploadFile.append(UploadHelper.getInstance().getUploadRelativePath(BlogMessageType.PLAIN, blogMessage.getUpdateTime()));
							uploadFile.append(blogMessage.getUploadFile());
						} catch (BusinessException be) {
							log.error("==BusinessException " + be.getMessage());
						}
					}
				}
				try {
					if (StringHelper.isEmpty(this.bundle)) this.bundle = Encoder.UTF8;
					InputStream inputStream = new FileInputStream(uploadFile.toString());
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, bundle));
					String line = null;
					while ((line = bufferedReader.readLine()) != null) {
						htmlContent.append(line + "<br/>");
					}
				} catch (IOException ioe) {
					log.error("==Can't read upload file " + uploadFile.toString());
					log.error("==Exception catched: " + ioe.getMessage());
				}
			}
		}
		log.debug("==End converting layout link for LinkAction");
		// Print this element to our output writer
		TagUtils.write(pageContext, htmlContent.toString());
		// Evaluate the body of this tag
		this.text = null;
		return (EVAL_BODY_TAG);
	}

	public final int doStartTag() throws JspException {
		ParentFinder.registerTag(pageContext, this);
		initDynamicValues();
		return doStartLayoutTag();
	}

	/**
	 * End the display of the action.
	 */
	protected void endLinkLayout(StringBuffer in_buffer) {
	}

	public String getAction() {
		return action;
	}

	public String getAnchor() {
		return (this.anchor);
	}

	public String getForward() {
		return (this.forward);
	}

	public String getHref() {
		return (this.href);
	}

	public String getIndexId() {
		return (this.indexId);
	}

	public String getLinkName() {
		return (this.linkName);
	}

	public String getModule() {
		return module;
	}

	public String getName() {
		return (this.name);
	}

	public String getPage() {
		return (this.page);
	}

	public String getParamId() {
		return (this.paramId);
	}

	public String getParamName() {
		return (this.paramName);
	}

	public String getParamProperty() {
		return (this.paramProperty);
	}

	public String getParamScope() {
		return (this.paramScope);
	}

	/**
	 * @return Returns the policy.
	 */
	public String getPolicy() {
		return policy;
	}

	public String getProperty() {
		return (this.property);
	}

	public String getScope() {
		return (this.scope);
	}

	public String getTarget() {
		return (this.target);
	}

	public boolean getTransaction() {
		return (this.transaction);
	}

	/**
	 * Init dynamic values.
	 */
	protected void initDynamicValues() {
		jspHref = href;
		href = Expression.evaluate(jspHref, pageContext);
	}

	public boolean isLayout() {
		return layout;
	}

	/**
	 * Release any acquired resources.
	 */
	public void release() {
		super.release();
		anchor = null;
		forward = null;
		href = null;
		action = null;
		module = null;
		linkName = null;
		name = null;
		page = null;
		paramId = null;
		paramName = null;
		paramProperty = null;
		paramScope = null;
		property = null;
		scope = null;
		target = null;
		text = null;
		transaction = false;
		policy = null;
		display = true;
		layout = true;
	}

	protected void reset() {
		href = jspHref;
		jspHref = null;
		showLink = true;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	public void setLayout(boolean in_layout) {
		layout = in_layout;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	/**
	 * Set in wich form modes the action should be displayed or not. The format form in_mode is X,Y,Z where allowed values are D (Displayed) and N (not
	 * displayed) in the same order as the input field tags.
	 */
	public void setMode(String in_mode) {
		if (in_mode == null || in_mode.length() != 5) {
			throw new IllegalArgumentException("The specified mode" + in_mode + " is invalid");
		}
		int lc_formMode = LayoutUtils.getSkin(pageContext.getSession()).getFormUtils().getFormDisplayMode(pageContext);
		char lc_displayMode;
		switch (lc_formMode) {
		case FormUtilsInterface.CREATE_MODE:
			lc_displayMode = in_mode.charAt(0);
			break;
		case FormUtilsInterface.EDIT_MODE:
			lc_displayMode = in_mode.charAt(2);
			break;
		case FormUtilsInterface.INSPECT_MODE:
			lc_displayMode = in_mode.charAt(4);
			break;
		default:
			lc_displayMode = 'D';
		}
		display = lc_displayMode == 'D' || lc_displayMode == 'd';
	}

	public void setModule(String module) {
		this.module = module;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public void setParamProperty(String paramProperty) {
		this.paramProperty = paramProperty;
	}

	public void setParamScope(String paramScope) {
		this.paramScope = paramScope;
	}

	/**
	 * @param policy The policy to set.
	 */
	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public void setTransaction(boolean transaction) {
		this.transaction = transaction;
	}
	
	
}
