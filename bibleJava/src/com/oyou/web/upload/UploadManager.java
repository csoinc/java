package com.oyou.web.upload;

import java.sql.Date;
import java.sql.Timestamp;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.common.util.DateHelper;

abstract public class UploadManager {
	private static final Log log = LogFactory.getLog(UploadManager.class);

	public static String getContextRealPath(ServletContext ctx) {
		return ctx.getRealPath("/");
	}

	abstract public String getMessageTypeRelativePath(Long mType);

	/**
	 * @param mType
	 * @return
	 */
	public String getUploadRelativePath(Long mType) {
		String year = DateHelper.formatSQLDate(DateHelper.getCurrentDate(), DateHelper.YYYY);
		String uploadPath = getUploadRelativeRoot() + year + "/";
		if (mType != null) {
			uploadPath += getMessageTypeRelativePath(mType);
		}
		log.debug("====Upload path = " + uploadPath);
		return uploadPath;
	}

	public String getUploadRelativePath(Long mType, Timestamp updateTime) {
		Date cDate = null;
		if (updateTime != null)
			cDate = new Date(updateTime.getTime());
		else
			cDate = DateHelper.getCurrentDate();
		String year = DateHelper.formatSQLDate(cDate, DateHelper.YYYY);
		String uploadPath = getUploadRelativeRoot() + year + "/";
		if (mType != null) {
			uploadPath += getMessageTypeRelativePath(mType);
		}
		log.debug("====Upload path = " + uploadPath);
		return uploadPath;
	}

	abstract public String getUploadRelativeRoot();

	public String getUploadWebPath(Long mType, Timestamp updateTime) {
		Date cDate = null;
		if (updateTime != null)
			cDate = new Date(updateTime.getTime());
		else
			cDate = DateHelper.getCurrentDate();
		String year = DateHelper.formatSQLDate(cDate, DateHelper.YYYY);
		String uploadPath = getUploadRelativeRoot() + year + "/";
		if (mType != null) {
			uploadPath += getMessageTypeRelativePath(mType);
		}
		log.debug("====Web path = " + uploadPath);
		return uploadPath;
	}

	abstract public String getWebRootPath(ServletRequest request, ServletResponse response);
}
