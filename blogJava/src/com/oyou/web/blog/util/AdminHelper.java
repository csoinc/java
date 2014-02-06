package com.oyou.web.blog.util;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.common.exception.BusinessException;
import com.oyou.common.util.HTMLHelper;
import com.oyou.domain.blog.BlogService;
import com.oyou.domain.blog.CommonService;
import com.oyou.domain.blog.orm.BlogInformation;

public class AdminHelper {
	private static final Log log = LogFactory.getLog(AdminHelper.class);
    protected static final int BUFFER = 1024 * 1024;

	public static void saveLog(String text, BlogService blogService, CommonService commonService) throws BusinessException {
		log.debug("==saveLog");
		BlogInformation blogInformation = blogService.getBlogInformationByID(BlogInformation.LOG_ID);
		StringBuffer logSB = new StringBuffer();
		logSB.append(Calendar.getInstance().getTime().toString() + ":" + text);
		logSB.append("<br>\r\n");
		logSB.append(HTMLHelper.formatToText(blogInformation.getInformation()));
		if (logSB.toString().length() >= BUFFER) {
			blogInformation.setInformation(HTMLHelper.formatToHTML(logSB.substring(0, BUFFER)));
		} else {
			blogInformation.setInformation(HTMLHelper.formatToHTML(logSB.toString()));
		}
		commonService.saveOrUpdate(blogInformation);
	}
	
}
