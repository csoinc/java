package com.oyou.web.blog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import com.oyou.common.spring.SpringService;
import com.oyou.common.util.HTMLHelper;
import com.oyou.common.util.StringHelper;
import com.oyou.domain.blog.EmailManager;
import com.oyou.domain.blog.UserService;
import com.oyou.domain.blog.orm.BlogUser;
import com.oyou.domain.blog.orm.BlogUserType;
import com.oyou.web.blog.util.StrutsHelper;
import com.oyou.web.view.Selection;

public class EmailGroupAction extends BlogAction {
	private static final Log log = LogFactory.getLog(EmailGroupAction.class);
	protected static final String EMAIL_CHIEF = "Chief";
	protected static final String EMAIL_EDITOR = "Editor";
	protected static final String EMAIL_WRITER = "Writer";
	protected static final String EMAIL_VISITOR = "Visitor";

	public ActionForward emailUsers(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug("==emailUsers()");
		ActionMessages messages = new ActionMessages();
		BlogUser sendUser = StrutsHelper.getBlogUser(request);
		EmailGroupForm emailForm = (EmailGroupForm) form;
		String content = HTMLHelper.formatToHTML(emailForm.getContent());
		String subject = (emailForm.getSubject() == null)? DEFAULT_SUBJECT : emailForm.getSubject();
		//TODO: add attach file later
		if (StringHelper.isNotEmpty(content)) {
			EmailManager emailManager = (EmailManager)this.getService(EMAIL_MANAGER);
			String[] usersSelected = emailForm.getUsersSelected();
			if (usersSelected != null && usersSelected.length > 0) {
				BlogUser[] receiveUsers = new BlogUser[usersSelected.length];
				for (int i = 0; i < usersSelected.length; i++) {
					String id = usersSelected[i];
					BlogUser receiveUser = (BlogUser)this.getBlogService().getBlogUserByID(Long.valueOf(id));
					//emailManager.emailMimeMessage(sendUser, receiveUser, subject, content, null);
					receiveUsers[i] = receiveUser;
				}
				emailManager.emailMimeMessage(sendUser, receiveUsers, subject, content, null);

			}
			ActionMessage message = new ActionMessage("message.email.return");
			messages.add(ActionMessages.GLOBAL_MESSAGE, message);
			this.saveMessages(request, messages);
			emailForm.setContent(null);
		} else {
			ActionMessage message = new ActionMessage("message.email.empty");
			messages.add(ActionMessages.GLOBAL_MESSAGE, message);
			this.saveMessages(request, messages);
		}
		return mapping.findForward(FORWARD_SUCCESS);
	}
	
	public ActionForward send(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		return emailUsers(mapping, form, request, response);
	}
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug("==unspecified()");
		EmailGroupForm emailForm = (EmailGroupForm) form;
		if (emailForm.getUsers() == null) {
			List<Selection> list = new ArrayList<Selection>();
			Selection sel;
			List users = ((UserService)this.getService(SpringService.USER_SERVICE)).getBlogUsersByUserType(BlogUserType.LEADER);
			log.debug("Get users " + users.size());
			for (Iterator iter = users.iterator(); iter.hasNext();) {
				BlogUser element = (BlogUser) iter.next();
				sel = new Selection(element.getId().toString(), element.getFirstname() + " " + element.getLastname() + ": " + element.getEmail() + ": " + EMAIL_EDITOR);
				list.add(sel);
			} 
			users = ((UserService)this.getService(SpringService.USER_SERVICE)).getBlogUsersByUserType(BlogUserType.USER);
			log.debug("Get users " + users.size());
			for (Iterator iter = users.iterator(); iter.hasNext();) {
				BlogUser element = (BlogUser) iter.next();
				sel = new Selection(element.getId().toString(), element.getFirstname() + " " + element.getLastname() + ": " + element.getEmail() + ": " + EMAIL_WRITER);
				list.add(sel);
			} 
			emailForm.setUsers(list);
			emailForm.setUsersSelected(null);
			emailForm.setContent("");
		} 
		return mapping.findForward(FORWARD_SUCCESS);
	}

	
}
