package com.oyou.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.taglib.TagUtils;

import com.oyou.common.struts.MessagesFactory;
import com.oyou.common.util.StringHelper;
import com.oyou.web.util.StrutsHelper;

public final class MessageTag extends TagSupport {
	//private static final Log log = LogFactory.getLog(MessageTag.class);
	private static final long serialVersionUID = 1L;

	protected String bundle;

	protected String key;

	public final int doStartTag() throws JspException {
		StringBuffer sb = new StringBuffer();
		String language = StrutsHelper.getLanguage(this.pageContext.getSession());
		//log.debug("==language: " + language);
		if (StringHelper.isEmpty(this.bundle)) {
			sb.append(MessagesFactory.getInstance().getProperty(language, key));
		} else {
			sb.append(MessagesFactory.getInstance().getProperty(this.bundle, key));
		}
		TagUtils.getInstance().write(pageContext, sb.toString());
		return SKIP_BODY;
	}

	public String getBundle() {
		return bundle;
	}

	public String getKey() {
		return key;
	}

	public final void release() {
		super.release();
		this.bundle = null;
		this.key = null;
	}

	public final void setBundle(String bundle) {
		this.bundle = bundle;
	}

	public void setKey(String key) {
		this.key = key;
	}


}
