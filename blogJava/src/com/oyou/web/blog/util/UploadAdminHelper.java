package com.oyou.web.blog.util;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMessage;

import com.oyou.common.exception.BusinessException;
import com.oyou.domain.blog.BlogService;
import com.oyou.domain.blog.CommonService;

public class UploadAdminHelper extends AdminHelper {
	private static final Log log = LogFactory.getLog(UploadAdminHelper.class);

	public static void processPath(File path, BlogService blogService, CommonService commonService) throws BusinessException {
		if (path.exists()) {
			if (path.isDirectory()) {
				File[] files = path.listFiles();
				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					if (file.exists()) {
						processPath(file, blogService, commonService);
					}
				}
			} else {
				if ((blogService.getBlogMessageByUploadName(path.getName()) == null) && (blogService.getBlogReplyMessageByUploadName(path.getName()) == null)) {
					log.debug("File need delete " + path.getName());
					path.delete();
					ActionMessage message = new ActionMessage("message.upload.deleted.confirmed", path.getName());
					saveLog(StrutsHelper.getFormattedMessage(message), blogService, commonService);
				}
			}
		}
	}
	
	
}
