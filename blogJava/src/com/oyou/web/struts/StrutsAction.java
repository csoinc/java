package com.oyou.web.struts;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.oyou.common.exception.BusinessError;
import com.oyou.common.exception.BusinessException;
import com.oyou.common.spring.SpringAction;
import com.oyou.common.util.StrutsSession;

public abstract class StrutsAction extends SpringAction {
	private static final Log log = LogFactory.getLog(StrutsAction.class);
	protected static final String FORWARD_SUCCESS = "success";
	protected static final String FORWARD_FAILED = "failed";
	protected static final String FORWARD_LOGIN = "login";
	protected static final String FORWARD_HOME = "home";

	public StrutsAction() {
		super();
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		log.info(">>execute()");
		ActionForward forward = null;
		try {
			forward = super.execute(mapping, form, request, response);
		} catch (BusinessException e) {
			log.debug("==process business exception");
			ActionMessages errors = new ActionMessages();
			this.saveBusinessErrors((BusinessException) e, errors);
			request.setAttribute(StrutsSession.KEY_BUSINESS, errors);
			forward = mapping.findForward(FORWARD_HOME);
		} catch (Exception e) {
			log.debug("==process other exception");
			ActionMessages errors = new ActionMessages();
			errors.add(StrutsSession.KEY_BUSINESS, new ActionMessage("error.system"));
			e.printStackTrace();
			log.error(e.getMessage());
			request.setAttribute(StrutsSession.KEY_BUSINESS, errors);
			forward = mapping.findForward(FORWARD_HOME);
		}
		if (forward == null) {
			forward = mapping.findForward(FORWARD_LOGIN);
		}
		log.info("<<execute()");
		return forward;
	}

	abstract protected Map getKeyMethodMap();

	/**
	 * 
	 * @param request
	 * @param bundleName
	 * @param messageKey
	 * @return
	 */
	protected String getMessage(HttpServletRequest request, String bundleName, String messageKey) {
		log.info(">>getMessage()");
		Locale locale = (Locale) request.getSession().getAttribute(Globals.LOCALE_KEY);
		MessageResources resources = getResources(request, bundleName);
		log.info("<<getMessage()");
		return resources.getMessage(locale, messageKey);
	}

	protected String getMethodName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String parameter)
			throws Exception {
		log.info(">>getMethodName()");
		String methodKey = request.getParameter(parameter);
		log.debug("==get action with parameter = " + parameter + ", value = " + methodKey);
		String methodName = null;
		if (methodKey != null && !methodKey.equals("")) {
			methodName = (String) getKeyMethodMap().get(methodKey);
			log.debug("==1. methodName = " + methodName);
		} else {
			methodName = "unspecified";
			log.debug("==return method = " + methodName);
			return methodName;
		}
		if (methodName == null || methodName.equals("")) {
			boolean isMethodName = false;
			Class actionClass = this.getClass();
			Class[] PARAMS = { ActionMapping.class, ActionForm.class, HttpServletRequest.class, HttpServletResponse.class };
			try {
				//methodKey = methodKey.toLowerCase();
				Method method = actionClass.getMethod(methodKey, PARAMS);
				if (method != null) {
					log.debug("==2. get method = " + method.getName());
					isMethodName = true;
					methodName = methodKey;
					log.debug("==3. methodName = " + methodName);
				}
			} catch (NoSuchMethodException ne) {
				log.debug(ne.getMessage());
			}
			if (!isMethodName) {
				methodName = getLookupMapName(request, methodName, mapping);
			}
		}
		if (methodName == null || methodName.equals(""))
			methodName = "unspecified";
		log.debug("==return method = " + methodName);
		log.info("<<getMethodName()");
		return methodName;
	}

	protected void saveBusinessErrors(BusinessException be, ActionMessages messages) {
		log.info(">>saveBusinessErrors(BusinessException, ActionMessages)");
		List errors = be.getBusinessErrors().getList();
		Iterator iter = errors.iterator();
		while (iter.hasNext()) {
			BusinessError err = (BusinessError) iter.next();
			messages.add(StrutsSession.KEY_BUSINESS, new ActionMessage(err.getKey(), err.getValues()));
		}
		log.info("<<saveBusinessErrors(BusinessException, ActionMessages)");
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		return mapping.findForward(FORWARD_SUCCESS);
	}
}
