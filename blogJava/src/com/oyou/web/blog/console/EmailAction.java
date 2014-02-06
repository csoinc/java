package com.oyou.web.blog.console;

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
import com.oyou.common.util.StringHelper;
import com.oyou.common.util.StrutsSession;
import com.oyou.domain.blog.BlogService;
import com.oyou.domain.blog.CommonService;
import com.oyou.domain.blog.EmailManager;
import com.oyou.domain.blog.UserService;
import com.oyou.domain.blog.orm.BlogUser;
import com.oyou.domain.blog.orm.BlogUserType;
import com.oyou.web.blog.util.EmailThread;
import com.oyou.web.blog.util.StrutsHelper;
import com.oyou.web.view.Selection;

public class EmailAction extends ConsoleAction {
	private static final Log log = LogFactory.getLog(EmailAction.class);
	private static final String EMAIL_MANAGER = "emailManager";
	private EmailThread emailThread = null;
	private Thread thread = null;

	public ActionForward emailUsers(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug("==emailUsers()");
		ActionMessages messages = new ActionMessages();
		//TODO: security may move to request processor
		BlogUser blogUser = StrutsHelper.getBlogUser(request);
		if (!BlogUserType.ADMIN.equals(blogUser.getBlogUserType().getId())) {
			log.debug("error.message.authority");
			messages.add(StrutsSession.KEY_BUSINESS, new ActionMessage("error.function.authority"));
			this.saveMessages(request, messages);
			return mapping.findForward(FORWARD_FAILED);
		}
		try {
			if (thread != null && thread.isAlive()) {
				log.debug("==Processing upload admin thread is alive, so wait......");
				return mapping.findForward(FORWARD_SUCCESS);
			} else {
				EmailForm emailForm = (EmailForm) form;
				String content = emailForm.getContent();
				String subject = emailForm.getSubject();  
				if (StringHelper.isNotEmpty(content)) {
					UserService userService = this.getUserService();
					EmailManager emailManager = (EmailManager)this.getService(EMAIL_MANAGER);
					String[] usersSelected = emailForm.getUsersSelected();
					BlogService blogService = this.getBlogService();
					CommonService commonService = this.getCommonService();

					emailThread = new EmailThread(userService, emailManager, blogUser, usersSelected, subject, content, blogService, commonService);
					thread = new Thread(emailThread);
					thread.start();
					
					Thread.sleep(2000);
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
		} catch (InterruptedException ie) {
			messages.add(StrutsSession.KEY_BUSINESS, new ActionMessage("error.system"));
			this.saveMessages(request, messages);
			return mapping.findForward(FORWARD_LOGIN);
		}
		
	}
	
	public ActionForward send(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		return emailUsers(mapping, form, request, response);
	}
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug("==unspecified()");
		ActionMessages messages = new ActionMessages();
		//TODO: security may move to request processor
		BlogUser blogUser = StrutsHelper.getBlogUser(request);
		if (!BlogUserType.ADMIN.equals(blogUser.getBlogUserType().getId())) {
			log.debug("error.message.authority");
			messages.add(StrutsSession.KEY_BUSINESS, new ActionMessage("error.function.authority"));
			this.saveMessages(request, messages);
			return mapping.findForward(FORWARD_FAILED);
		}
		EmailForm emailForm = (EmailForm) form;
		if (emailForm.getUsers() == null) {
			UserService service = this.getUserService();
			List<Selection> list = new ArrayList<Selection>();
			Selection sel = new Selection("all", "All: All Listing Users");
			list.add(sel);
			List users = service.getBlogUsersByUserType(BlogUserType.LEADER);
			log.debug("Get leaders " + users.size());
			for (Iterator iter = users.iterator(); iter.hasNext();) {
				BlogUser element = (BlogUser) iter.next();
				sel = new Selection(element.getId().toString(), element.getFirstname() + " " + element.getLastname() + ": " + element.getEmail() + ": " + BlogUserType.LEADER_ROLE);
				list.add(sel);
			} 
			users = service.getBlogUsersByUserType(BlogUserType.USER);
			log.debug("Get users " + users.size());
			for (Iterator iter = users.iterator(); iter.hasNext();) {
				BlogUser element = (BlogUser) iter.next();
				sel = new Selection(element.getId().toString(), element.getFirstname() + " " + element.getLastname() + ": " + element.getEmail() + ": " + BlogUserType.USER_ROLE);
				list.add(sel);
			} 
			users = service.getBlogUsersByUserType(BlogUserType.GUEST);
			log.debug("Get guests " + users.size());
			for (Iterator iter = users.iterator(); iter.hasNext();) {
				BlogUser element = (BlogUser) iter.next();
				sel = new Selection(element.getId().toString(), element.getFirstname() + " " + element.getLastname() + ": " + element.getEmail() + ": " + BlogUserType.GUEST_ROLE);
				list.add(sel);
			} 
			emailForm.setUsers(list);
			emailForm.setUsersSelected(null);
			emailForm.setContent("");
		} 
		return mapping.findForward(FORWARD_SUCCESS);
	}

	
}
