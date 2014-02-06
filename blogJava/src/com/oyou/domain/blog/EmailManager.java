package com.oyou.domain.blog;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.oyou.domain.blog.orm.BlogUser;

public interface EmailManager {
	
	public void setJavaMailSender(JavaMailSender javaMailSender);

	public void setMessage(SimpleMailMessage message);

	public void emailSimpleMailMessage(String toEmail, String fromEmail, String subject, String content);

	public void emailMimeMessage(BlogUser sendUser, BlogUser receiveUser, String subject, String content, String[] attachFiles);

	public void emailMimeMessage(BlogUser sendUser, BlogUser[] receiveUsers, String subject, String content, String[] attachFiles);
	
}