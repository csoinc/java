package com.oyou.web.blog.spring;

import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.common.spring.SpringService;
import com.oyou.domain.blog.BlogService;
import com.oyou.web.blog.util.UploadHelper;

public class BlogTagSupport extends com.oyou.web.spring.SpringTagSupport {
	private static final Log log = LogFactory.getLog(BlogTagSupport.class);
	private BlogService blogService;

	public final BlogService getBlogService() {
		this.blogService = (BlogService)getService(SpringService.BLOG_SERVICE);
		return this.blogService;
	}

	/**
	 * @param mType
	 * @return
	 */
	public final String getUploadPath(Long mType) {
		return UploadHelper.getContextRealPath(this.pageContext.getServletContext()) + UploadHelper.getInstance().getUploadRelativePath(mType);
	}

	/**
	 * 
	 * @param mType
	 * @param updateTime
	 * @return
	 */
	public final String getUploadPath(Long mType, Timestamp updateTime) {
		return UploadHelper.getContextRealPath(this.pageContext.getServletContext()) + UploadHelper.getInstance().getUploadRelativePath(mType, updateTime);
	}

	/**
	 * 
	 * @param mType
	 * @param updateTime
	 * @return
	 */
	public final String getUploadWebPath(Long mType, Timestamp updateTime) {
		return UploadHelper.getInstance().getUploadWebPath(mType, updateTime);
	}
	
}
