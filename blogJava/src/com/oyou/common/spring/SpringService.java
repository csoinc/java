package com.oyou.common.spring;

import org.springframework.context.ApplicationContextAware;

public interface SpringService extends ApplicationContextAware {
	String USER_SERVICE = "userService";
	String BIBLE_SERVICE = "bibleService";
	String BLOG_SERVICE = "blogService";
	String COMMON_SERVICE = "commonService";

	String BACKUP_BLOG_SERVICE = "backupBlogService";
	String BACKUP_BIBLE_SERVICE = "backupBibleService";
	String BACKUP_USER_SERVICE = "backupUserService";
	String BACKUP_COMMON_SERVICE = "backupCommonService";
	
	String USER_DAO = "userDAO";
	String BIBLE_DAO = "bibleDAO";
	String BLOG_DAO = "blogDAO";
	String EMAIL_MANAGER = "emailManager";

}
