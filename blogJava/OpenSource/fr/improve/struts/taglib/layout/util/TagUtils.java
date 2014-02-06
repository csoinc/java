package fr.improve.struts.taglib.layout.util;

import java.net.MalformedURLException;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.apache.struts.config.ModuleConfig;

import com.oyou.common.struts.MessagesFactory;
import com.oyou.common.util.StringHelper;
import com.oyou.common.util.StrutsSession;
import com.oyou.web.util.StrutsHelper;

public class TagUtils {
	public static void write(PageContext in_pg, String in_string) throws JspException {
		org.apache.struts.taglib.TagUtils.getInstance().write(in_pg, in_string);
	}

	public static void writePrevious(PageContext in_pg, String in_string) throws JspException {
		org.apache.struts.taglib.TagUtils.getInstance().writePrevious(in_pg, in_string);
	}

	public static void saveException(PageContext in_pg, Throwable in_throwable) {
		org.apache.struts.taglib.TagUtils.getInstance().saveException(in_pg, in_throwable);
	}

	public static boolean present(PageContext pageContext, String bundle, String locale, String key) throws JspException {
		return org.apache.struts.taglib.TagUtils.getInstance().present(pageContext, bundle, locale, key);
	}

	public static String message(PageContext pageContext, String bundle, String locale, String key) throws JspException {
		String language = StrutsHelper.getLanguage(pageContext.getSession());
		String message = null;
		if (StringHelper.isNotEmpty(language) && language.equalsIgnoreCase(StrutsSession.LANGUAGE_EN)) {
			message = MessagesFactory.getInstance().getProperty(language, key);
		} else {
			message = MessagesFactory.getInstance().getProperty(key);
		}
		if (StringHelper.isNotEmpty(message)) return message; 
		return org.apache.struts.taglib.TagUtils.getInstance().message(pageContext, bundle, locale, key);
	}

	public static ModuleConfig getModuleConfig(PageContext pageContext) {
		return org.apache.struts.taglib.TagUtils.getInstance().getModuleConfig(pageContext);
	}

	public static String getActionMappingName(String action) {
		return org.apache.struts.taglib.TagUtils.getInstance().getActionMappingName(action);
	}

	public static Object lookup(PageContext pageContext, String name, String property, String scope) throws JspException {
		return org.apache.struts.taglib.TagUtils.getInstance().lookup(pageContext, name, property, scope);
	}

	public static String computeURL(PageContext pageContext, String forward, String href, String page, String action, String module, Map params, String anchor,
			boolean redirect) throws MalformedURLException {
		return org.apache.struts.taglib.TagUtils.getInstance().computeURL(pageContext, forward, href, page, action, module, params, anchor, redirect);
	}

	public static Map computeParameters(PageContext pageContext, String paramId, String paramName, String paramProperty, String paramScope, String name,
			String property, String scope, boolean transaction) throws JspException {
		return org.apache.struts.taglib.TagUtils.getInstance().computeParameters(pageContext, paramId, paramName, paramProperty, paramScope, name, property,
				scope, transaction);
	}
}
