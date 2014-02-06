package com.oyou.web.bible.struts;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;

import com.oyou.common.spring.SpringProcessor;
import com.oyou.web.util.StrutsHelper;

public class StrutsRequestProcessor extends SpringProcessor {
	private static final Log log = LogFactory.getLog(StrutsRequestProcessor.class);
	public static boolean SYSTEM_DEBUG_MODE = false;

	public StrutsRequestProcessor() {
		super();
	}

	protected boolean processRoles(HttpServletRequest request, HttpServletResponse response, ActionMapping mapping)
			throws IOException, ServletException {
		log.info(">>processRoles()");
			log.info("<<processRoles(): path OK");
		return true;
	}

	protected void processLocale(HttpServletRequest request, HttpServletResponse response) {
		log.debug("==processLocale()");
		StrutsHelper.processEncoding(request, response);
		super.processLocale(request, response);
	}

	protected void processException(HttpServletRequest request, HttpServletResponse response) {
	}


}
