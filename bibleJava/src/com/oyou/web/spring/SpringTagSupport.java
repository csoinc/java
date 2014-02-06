package com.oyou.web.spring;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.html.BaseHandlerTag;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import fr.improve.struts.taglib.layout.LayoutTag;
import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.LayoutEventListener;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;

public class SpringTagSupport extends BaseHandlerTag implements LayoutTag, LayoutEventListener {
	private static final Log log = LogFactory.getLog(SpringTagSupport.class);

	public final Object processEndLayoutEvent(EndLayoutEvent in_event) throws JspException {
		return Boolean.FALSE;
	}

	public final Object processStartLayoutEvent(StartLayoutEvent in_event) throws JspException {
		return Boolean.FALSE;
	}

	public final PageContext getPageContext() {
		return pageContext;
	}

	public final Object getService(String service) {
		Object obj = null;
		try {
			WebApplicationContext ctx = this.getWebApplicationContext();
			if (ctx != null)
				if (ctx.containsBean(service)) {
					obj = ctx.getBean(service);
				}
			if (obj == null) {
				log.fatal(">>Can't get service from Application Context");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("Exception on get context " + e.getMessage());
		}
		return obj;
	}

	/**
	 * 
	 * @return
	 */
	public final WebApplicationContext getWebApplicationContext() {
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext());
		return ctx;
	}

}
