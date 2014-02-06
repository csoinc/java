package com.oyou.web.taglib;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.taglib.TagUtils;

import com.oyou.common.util.HTMLHelper;

public class DumpTag extends TagSupport {

	protected String locale;

	protected String bundle;

	protected boolean filter = false; // Filter HTML characters?

	protected boolean ignore = false; // Ignore missing beans and simply output nothing?

	protected String name; // Name of the bean that contains the data.

	protected String property; // Property to be accessed on the specified bean as determined by name parameter.

	protected String scope = PageContext.REQUEST; // Scope to be searched when looking up the specified bean as determined by name parameter.

	public int doStartTag() throws JspException {
		TagUtils tagUtils = TagUtils.getInstance();

		if (ignore && tagUtils.lookup(pageContext, name, scope) == null)
			return SKIP_BODY;

		StringBuffer sb = new StringBuffer();
		Object obj = tagUtils.lookup(pageContext, name, property, scope);
		if (obj != null) {
			if (obj instanceof List)
				sb.append(dumpObject((List) obj));
			else
				sb.append(dumpObject(obj));
		}

		if (filter)
			tagUtils.write(pageContext, tagUtils.filter(sb.toString()));
		else
			tagUtils.write(pageContext, sb.toString());
		return SKIP_BODY;
	}

	protected String dumpObject(Object obj) {
		return HTMLHelper.getHTMLTable(obj);
	}

	public String getBundle() {
		return bundle;
	}

	public String getLocale() {
		return locale;
	}

	public String getName() {
		return name;
	}

	public String getProperty() {
		return property;
	}

	public String getScope() {
		return scope;
	}

	public boolean isFilter() {
		return filter;
	}

	public boolean isIgnore() {
		return ignore;
	}

	public void release() {
		super.release();
		filter = true;
		ignore = false;
		name = null;
		property = null;
		scope = null;
	}

	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	public void setFilter(boolean filter) {
		this.filter = filter;
	}

	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

}
