package com.oyou.web.struts;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.oyou.common.exception.BusinessException;
import com.oyou.common.util.StringHelper;
import com.oyou.common.util.StrutsSession;
import com.oyou.web.util.StrutsHelper;

public class LanguageAction extends StrutsAction {
	private static final Log log = LogFactory.getLog(LanguageAction.class);

	public ActionForward language(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws BusinessException {
		String lang = request.getParameter("lang");
		if (StringHelper.isEmpty(lang)) {
			lang = request.getParameter("language");
		}
		log.debug("==set language: " + lang);
		if (StringHelper.isNotEmpty(lang)) {
			if (lang.equalsIgnoreCase(StrutsSession.LANGUAGE_EN) || lang.equalsIgnoreCase(StrutsSession.LANGUAGE_CN)
					|| lang.equalsIgnoreCase(StrutsSession.LANGUAGE_TW)) {
				StrutsHelper.setLanguage(request, lang.toUpperCase());
			} else {
				StrutsHelper.setLanguage(request, StrutsSession.LANGUAGE_EN);
			}
		} else {
			StrutsHelper.setLanguage(request, StrutsSession.LANGUAGE_EN);
		}
		return mapping.findForward(FORWARD_SUCCESS);
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws BusinessException {
		return this.language(mapping, form, request, response);
	}

	protected Map getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.create", "create");
		map.put("button.update", "update");
		map.put("button.delete", "delete");
		map.put("button.search", "list");
		return map;
	}
	
}
