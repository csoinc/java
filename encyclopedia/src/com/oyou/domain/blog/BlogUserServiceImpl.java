package com.oyou.domain.blog;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author owen.ouyang
 * @since 	Oct 12, 2011
 */
public class BlogUserServiceImpl extends UserServiceImpl implements BlogUserService {
	private static final Log log = LogFactory.getLog(BlogUserServiceImpl.class);

	public void sendSimpleMailMessage(String toEmail, String toFirstname, String toPassword, String fromEmail, String remoteAddr, String emailType) {
		log.debug("==send email");
		String subject;
		StringBuffer contentSb = new StringBuffer();
		if (USER_REGISTER.equals(emailType)) {
			subject = "Register - Encyclopedia";
			contentSb.append("Hi " + toFirstname + ",\n\n");
			contentSb.append("Thank you for using Encyclopedia,\n\n");
			contentSb.append("your login name: " + toEmail + "\n\n");
			contentSb.append("your password: " + toPassword + "\n\n");
			contentSb.append("======================================================================\n");
			contentSb.append("Welcome to visit us again.\n\n");
			contentSb.append("Encyclopedia.\n\n");
			this.emailManager.emailSimpleMailMessage(toEmail, fromEmail, subject, contentSb.toString());
		} else if (USER_LOGIN.equals(emailType)) {
			subject = "Login - Encyclopedia";
			contentSb.append("Hi " + this.getAdminName() + ",\n\n");
			contentSb.append("This email address: " + toEmail + " login to Encyclopedia.\n\n");
			contentSb.append("The firstname: " + toFirstname + "\n\n");
			contentSb.append("Login from: " + remoteAddr + "\n\n");
			contentSb.append("======================================================================\n");
			contentSb.append("Encyclopedia.\n\n");
			this.emailManager.emailSimpleMailMessage(this.getAdminEmail(), fromEmail, subject, contentSb.toString());
		} else if (USER_PASSWORD.equals(emailType)) {
			subject = "Password - Encyclopedia";
			contentSb.append("Hi " + toFirstname + ",\n\n");
			contentSb.append("Thank you for using Encyclopedia,\n\n");
			contentSb.append("your login name: " + toEmail + "\n\n");
			contentSb.append("your password: " + toPassword + "\n\n");
			contentSb.append("======================================================================\n");
			contentSb.append("Welcome to visit us again.\n\n");
			contentSb.append("Encyclopedia.\n\n");
			this.emailManager.emailSimpleMailMessage(toEmail, fromEmail, subject, contentSb.toString());
		}

	}

}