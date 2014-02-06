package com.oyou.web.blog.util;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.common.exception.BusinessException;
import com.oyou.domain.blog.EmailManager;
import com.oyou.domain.blog.UserService;
import com.oyou.domain.blog.orm.BlogUser;
import com.oyou.domain.blog.orm.BlogUserType;

public class EmailHelper extends AdminHelper {
	private static final Log log = LogFactory.getLog(UploadAdminHelper.class);
	private static final String SELECTED_ALL = "all";

	public static void processEmail(UserService userService, EmailManager emailManager, BlogUser blogUser, String[] usersSelected, String subject,
			String content) throws BusinessException {
		if (usersSelected != null && usersSelected.length > 0) {
			if (usersSelected[0].toLowerCase().equals(SELECTED_ALL)) {
				List users = userService.getBlogUsersByUserType(BlogUserType.ADMIN);
				log.debug("Get admins " + users.size());
				for (Iterator iter = users.iterator(); iter.hasNext();) {
					BlogUser user = (BlogUser) iter.next();
					emailManager.emailSimpleMailMessage(user.getEmail(), userService.getFromEmail(), subject, content);
				}
				users = userService.getBlogUsersByUserType(BlogUserType.LEADER);
				log.debug("Get users " + users.size());
				for (Iterator iter = users.iterator(); iter.hasNext();) {
					BlogUser user = (BlogUser) iter.next();
					emailManager.emailSimpleMailMessage(user.getEmail(), userService.getFromEmail(), subject, content);
				}
				users = userService.getBlogUsersByUserType(BlogUserType.USER);
				log.debug("Get users " + users.size());
				for (Iterator iter = users.iterator(); iter.hasNext();) {
					BlogUser user = (BlogUser) iter.next();
					emailManager.emailSimpleMailMessage(user.getEmail(), userService.getFromEmail(), subject, content);
				}
				users = userService.getBlogUsersByUserType(BlogUserType.GUEST);
				log.debug("Get users " + users.size());
				for (Iterator iter = users.iterator(); iter.hasNext();) {
					BlogUser user = (BlogUser) iter.next();
					emailManager.emailSimpleMailMessage(user.getEmail(), userService.getFromEmail(), subject, content);
				}
			} else {
				for (int i = 0; i < usersSelected.length; i++) {
					String id = usersSelected[i];
					BlogUser user = (BlogUser) userService.getBlogUserByID(Long.valueOf(id));
					emailManager.emailSimpleMailMessage(user.getEmail(), userService.getFromEmail(), subject, content);
				}
			}
		}
	}

}
