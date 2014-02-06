package com.oyou.web.blog.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMessage;

import com.oyou.common.exception.BusinessException;
import com.oyou.domain.blog.BlogService;
import com.oyou.domain.blog.CommonService;
import com.oyou.domain.blog.EmailManager;
import com.oyou.domain.blog.UserService;
import com.oyou.domain.blog.orm.BlogUser;

public class EmailThread implements Runnable {
	private static final Log log = LogFactory.getLog(EmailThread.class);
	private BlogService blogService;
	private CommonService commonService;
	private UserService userService;
	private EmailManager emailManager;
	private BlogUser blogUser;
	private String[] usersSelected;
	private String subject;
	private String content;
	
	private EmailThread() {}
	
	public EmailThread(UserService userService, EmailManager emailManager, BlogUser blogUser, String[] usersSelected, String subject,
			String content, BlogService blogService, CommonService commonService) {
		this.userService = userService;
		this.emailManager = emailManager;
		this.blogUser = blogUser;
		this.usersSelected = usersSelected;
		this.subject = subject;
		this.content = content;
		this.blogService = blogService;
		this.commonService = commonService;
	}

	public void run() {
		log.debug("==email()");
		try {
			EmailHelper.processEmail(userService, emailManager, blogUser, usersSelected, subject, content);
			ActionMessage message = new ActionMessage("message.email.return");  
			EmailHelper.saveLog(StrutsHelper.getFormattedMessage(message), blogService, commonService);
		} catch (BusinessException be) {
			log.error(be.getMessage());
		}
	}

}
