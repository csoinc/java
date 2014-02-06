package com.oyou.web.blog.util;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.domain.blog.orm.BlogMessageType;
import com.oyou.web.upload.UploadManager;

public class UploadHelper extends UploadManager {
	private static final Log log = LogFactory.getLog(UploadHelper.class);
	private static UploadHelper helper = null;

	public static UploadHelper getInstance() {
		if (helper == null) {
			helper = new UploadHelper();
		}
		return helper;
	}

	private UploadHelper() {
	}

	public String getUploadRelativeRoot() {
		//return "/UPLOAD-INF/";
		return "";
	}

	/**
	 * @deprecated
	 */
	public String getWebRootPath(ServletRequest request, ServletResponse response) {
		log.debug("==getWebRootPath()");
		StringBuffer sb = new StringBuffer();
		String ctxPath = request.getRealPath("/");
		sb.append(ctxPath);
		return sb.toString();
	}
	
	public String getMessageTypeRelativePath(Long mType) {
		String relativePath = "";
		if (mType.equals(BlogMessageType.IMAGE)) {
			relativePath = "image/";
		} else if (mType.equals(BlogMessageType.MUSIC)) {
			relativePath = "music/";
		} else if (mType.equals(BlogMessageType.WORD)) {
			relativePath = "word/";
		} else if (mType.equals(BlogMessageType.HTML)) {
			relativePath = "html/";
		} else if (mType.equals(BlogMessageType.PLAIN)) {
			relativePath = "plain/";
		} else if (mType.equals(BlogMessageType.FILE)) {
			relativePath = "file/";
		}
		return relativePath;
	}
}
