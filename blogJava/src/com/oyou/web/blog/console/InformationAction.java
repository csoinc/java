package com.oyou.web.blog.console;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.oyou.common.exception.BusinessException;
import com.oyou.common.util.HTMLHelper;
import com.oyou.common.util.StrutsSession;
import com.oyou.domain.blog.orm.BlogInformation;
import com.oyou.domain.blog.orm.BlogUser;
import com.oyou.domain.blog.orm.BlogUserType;
import com.oyou.web.blog.util.StrutsHelper;

public class InformationAction extends ConsoleAction {
	private static final Log log = LogFactory.getLog(InformationAction.class);

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug(">>update()");
		InformationForm infoForm = (InformationForm) form;
		ActionMessages messages = new ActionMessages();
		BlogUser blogUser = StrutsHelper.getBlogUser(request);
		if (!BlogUserType.ADMIN.equals(blogUser.getBlogUserType().getId())) {
			log.debug("error.message.authority");
			messages.add(StrutsSession.KEY_BUSINESS, new ActionMessage("error.function.authority"));
			this.saveMessages(request, messages);
			return mapping.findForward(FORWARD_FAILED);
		}
		
		BlogInformation blogInformation = infoForm.getBlogInformation();
		blogInformation.setInformation(HTMLHelper.formatToHTML(infoForm.getInformation()));
		this.getCommonService().saveOrUpdate(blogInformation);
		ActionMessage message = new ActionMessage("message.information.updated");
		messages.add(ActionMessages.GLOBAL_MESSAGE, message);
		this.saveMessages(request, messages);
		return mapping.findForward(FORWARD_SUCCESS);
	}

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		InformationForm infoForm = (InformationForm) form;
		ActionMessages messages = new ActionMessages();
		String id = request.getParameter("id");
		if (id == null || id.equals("")) {
			messages.add(StrutsSession.KEY_BUSINESS, new ActionMessage("error.system"));
			return mapping.findForward(FORWARD_LOGIN);
		} else {
			BlogInformation blogInformation = getBlogService().getBlogInformationByID(Long.valueOf(id));
			infoForm.reset();
			infoForm.setBlogInformation(blogInformation);
			infoForm.setInformation(HTMLHelper.formatToText(blogInformation.getInformation()));
		}	
    	return super.unspecified(mapping, infoForm, request, response);
    }
	
}
