package com.oyou.web.blog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.oyou.common.exception.BusinessException;
import com.oyou.common.util.StringHelper;
import com.oyou.domain.blog.orm.BlogMessage;
import com.oyou.domain.blog.orm.BlogMessageType;
import com.oyou.domain.blog.orm.BlogReplyMessage;

/**
 * 
 * @author	Owen Ou
 * May 29, 2007
 */
public class LinkAction extends BlogAction {
	private static final Log log = LogFactory.getLog(LinkAction.class);

	public ActionForward link(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		String id = request.getParameter("mid");
		String linkURL = null;
		Long messageTypeId = BlogMessageType.LINK;
		String fileName = null;
		String filePath = null;
		
		if (StringHelper.isNotEmpty(id)) {
			BlogMessage blogMessage = this.getBlogService().getBlogMessageByID(Long.valueOf(id));
			this.getBlogService().increaseMessageViewTimes(blogMessage.getId());
			messageTypeId = blogMessage.getBlogMessageType().getId();
			if (messageTypeId.equals(BlogMessageType.LINK)) {
				linkURL = blogMessage.getLinkURL();
				log.debug("==Blog = " + linkURL);
			} else {
				//linkURL = UploadHelper.getInstance().getUploadWebPath(messageTypeId, blogMessage.getUpdateTime());
				//linkURL += blogMessage.getUploadFile();
				//linkURL = StrutsHelper.getWebRootPath(request, response) + linkURL;
				filePath = this.getUploadPath(messageTypeId, blogMessage.getUpdateTime());
				fileName = blogMessage.getUploadFile();
			} 
			this.getBlogService().increaseMessageViewTimes(blogMessage.getId());
		} else {
			id = request.getParameter("rid");
			if (StringHelper.isNotEmpty(id)) {
				BlogReplyMessage blogMessage = this.getBlogService().getBlogReplyMessageByID(Long.valueOf(id));
				this.getBlogService().increaseReplyMessageViewTimes(blogMessage.getId());
				messageTypeId = blogMessage.getBlogMessageType().getId();
				if (messageTypeId.equals(BlogMessageType.LINK)) {
					linkURL = blogMessage.getLinkURL();
					log.debug("==Blog = " + linkURL);
				} else {
					//linkURL = UploadHelper.getInstance().getUploadWebPath(messageTypeId, blogMessage.getUpdateTime());
					//linkURL += blogMessage.getUploadFile();
					//linkURL = StrutsHelper.getWebRootPath(request, response) + linkURL;
					filePath = this.getUploadPath(messageTypeId, blogMessage.getUpdateTime());
					fileName = blogMessage.getUploadFile();
				} 
				this.getBlogService().increaseReplyMessageViewTimes(blogMessage.getId());
			}
		}
		try {
			if (BlogMessageType.LINK.equals(messageTypeId)) {
				log.debug("==link URL " + linkURL);
				response.sendRedirect(linkURL);
			} else {
				File file = new File(filePath, fileName);
				if (file.exists()) {
					String mimeType = getServletContext().getMimeType(file.getAbsolutePath());
					if (mimeType == null) {
						log.error("Could not get MIME type of " + file.getAbsolutePath());
						response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						return null;
					}
					response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "\";");
					response.setContentType(mimeType);
					response.setContentLength((int) file.length());
					try {
						FileInputStream in = new FileInputStream(file);
						OutputStream out = response.getOutputStream();
						byte[] buf = new byte[1024];
						int count = 0;
						while ((count = in.read(buf)) >= 0) {
							out.write(buf, 0, count);
						}
						in.close();
						out.close();
					} catch (IOException ie) {
						ie.printStackTrace();
						log.error(ie.getMessage());
					}
				}
			}
		} catch (IOException ie) {
			ie.printStackTrace();
			log.error(ie.getMessage());
		}
		return null;
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		return link(mapping, form, request, response);
	}

}
