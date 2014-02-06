package com.oyou.domain.blog;

import java.io.File;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.oyou.common.util.StringHelper;
import com.oyou.domain.blog.orm.BlogUser;

/**
 * 
 * @author	Owen Ou
 * Jun 1, 2007
 */
public class EmailManagerImpl implements EmailManager {
	private static final Log log = LogFactory.getLog(EmailManagerImpl.class);
	private JavaMailSender javaMailSender;
	private SimpleMailMessage message;

	public void emailMimeMessage(BlogUser sendUser, BlogUser receiveUser, String subject, String content, String[] attachFiles) {
		MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setTo(receiveUser.getEmail());
			helper.setFrom(sendUser.getEmail(), sendUser.getFirstname() + " " + sendUser.getLastname());
			if (StringHelper.isNotEmpty(subject)) {
				helper.setSubject(subject);
			}
			StringBuffer sb = new StringBuffer();
			sb.append(content + "\n\n");
			helper.setText(sb.toString(), true); 
			if (attachFiles != null) {
				for (int i = 0; i < attachFiles.length; i++) {
					helper.addAttachment(attachFiles[i], new File(attachFiles[i]));
				}
			}
			//FileSystemResource res = new FileSystemResource(new File(attachFiles[0]));
			//addInline("logo", res); //Image logo etc
			javaMailSender.send(mimeMessage);
		} catch (MailException me) {
			me.printStackTrace();
			log.error(me.getMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (UnsupportedEncodingException ue) {
			ue.printStackTrace();
			log.error(ue.getMessage());
		}
	}

	public void emailMimeMessage(BlogUser sendUser, BlogUser[] receiveUsers, String subject, String content, String[] attachFiles) {
		MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			String[] toUsers = new String[receiveUsers.length];
			for (int i = 0; i < receiveUsers.length; i++) {
				toUsers[i] = receiveUsers[i].getEmail();
			}
			helper.setTo(sendUser.getEmail());
			helper.setBcc(toUsers);
			helper.setFrom(sendUser.getEmail(), sendUser.getFirstname() + " " + sendUser.getLastname());
			if (StringHelper.isNotEmpty(subject)) {
				helper.setSubject(subject);
			}
			StringBuffer sb = new StringBuffer();
			sb.append(content + "\n\n");
			helper.setText(sb.toString(), true); 
			if (attachFiles != null) {
				for (int i = 0; i < attachFiles.length; i++) {
					helper.addAttachment(attachFiles[i], new File(attachFiles[i]));
				}
			}
			//FileSystemResource res = new FileSystemResource(new File(attachFiles[0]));
			//addInline("logo", res); //Image logo etc
			javaMailSender.send(mimeMessage);
		} catch (MailException me) {
			me.printStackTrace();
			log.error(me.getMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (UnsupportedEncodingException ue) {
			ue.printStackTrace();
			log.error(ue.getMessage());
		}
	}
	
	public void emailSimpleMailMessage(String toEmail, String fromEmail, String subject, String content) {
		log.debug("=emailUser()");
		this.message.setSubject(subject);
		SimpleMailMessage msg = new SimpleMailMessage(this.message);
		msg.setTo(toEmail);
		msg.setFrom(fromEmail);
		if (StringHelper.isNotEmpty(subject)) {
			msg.setSubject(subject);
		}
		StringBuffer sb = new StringBuffer();
		sb.append(content + "\n\n");
		msg.setText(sb.toString());
		try {
			javaMailSender.send(msg);
		} catch (MailException me) {
			me.printStackTrace();
			log.error(me.getMessage());
		}
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void setMessage(SimpleMailMessage message) {
		this.message = message;
	}

}