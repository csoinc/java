package com.oyou.domain.blog;


/**
 * 
 * @author	Owen Ou
 * @since 	Oct 12, 2011
 */
public interface BlogUserService extends UserService {

	public void sendSimpleMailMessage(String toEmail, String toFirstname, String toPassword, String fromEmail, String remoteAddr, String emailType);

}