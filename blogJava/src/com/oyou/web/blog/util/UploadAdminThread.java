package com.oyou.web.blog.util;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMessage;

import com.oyou.common.exception.BusinessException;
import com.oyou.domain.blog.BlogService;
import com.oyou.domain.blog.CommonService;

public class UploadAdminThread implements Runnable {
	private static final Log log = LogFactory.getLog(UploadAdminThread.class);
	private BlogService blogService;
	private CommonService commonService;
	private File path;
	
	private UploadAdminThread() {}
	
	public UploadAdminThread(File path, BlogService blogService, CommonService commonService) {
		this.path = path;
		this.blogService = blogService;
		this.commonService = commonService;
	}

	public void run() {
		log.debug("==backup()");
		try {
			UploadAdminHelper.processPath(path, blogService, commonService);
			ActionMessage message = new ActionMessage("message.upload.maintain.confirmed");
			UploadAdminHelper.saveLog(StrutsHelper.getFormattedMessage(message), blogService, commonService);
		} catch (BusinessException be) {
			log.error(be.getMessage());
		}

	}

}
