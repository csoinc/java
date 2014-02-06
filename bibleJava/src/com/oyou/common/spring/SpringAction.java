package com.oyou.common.spring;

import java.util.Map;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.struts.LookupDispatchActionSupport;

public abstract class SpringAction extends LookupDispatchActionSupport {

    public SpringAction() {
        super();
    }

    abstract protected Map getKeyMethodMap();
    
	public Object getService(String bean) {
		Object obj = null;
		try {
			WebApplicationContext ctx = getWebApplicationContext();
			if (ctx != null && ctx.containsBean(bean)) {
				obj = ctx.getBean(bean);
			}
			if (obj == null) {
				log.fatal(">>Can't get Service from Application Context " + bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("Exception on get service " + e.getMessage());
		}
		return obj;
	}
    
}
