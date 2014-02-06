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

public class IconAction extends BlogAction {
	private static final Log log = LogFactory.getLog(IconAction.class);
	private static final String DEFAULT_ICON = "icon.jpg";

	public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		String id = request.getParameter("mid");
		String photoName = null;
		String photoPath = null;
		if (StringHelper.isNotEmpty(id)) {
			BlogMessage blogMessage = this.getBlogService().getBlogMessageByID(Long.valueOf(id));
			photoPath = this.getIconUploadPath(BlogMessageType.IMAGE, blogMessage.getUpdateTime());
			photoName = blogMessage.getUploadFile();
		} else {
			id = request.getParameter("rid");
			if (StringHelper.isNotEmpty(id)) {
				BlogReplyMessage blogMessage = this.getBlogService().getBlogReplyMessageByID(Long.valueOf(id));
				photoPath = this.getIconUploadPath(BlogMessageType.IMAGE, blogMessage.getUpdateTime());
				photoName = blogMessage.getUploadFile();
			}	
		}	
		File file = new File(photoPath, photoName);
		if (file.exists()) {
			String mimeType = getServletContext().getMimeType(file.getAbsolutePath());
			if (mimeType == null) {
				log.error("Could not get MIME type of " + file.getAbsolutePath());
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return null;
			}
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
		} else {
			file = new File(this.getImagePath(), DEFAULT_ICON);
			if (file.exists()) {
				String mimeType = getServletContext().getMimeType(file.getAbsolutePath());
				if (mimeType == null) {
					log.error("Could not get MIME type of " + file.getAbsolutePath());
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					return null;
				}
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
			} else {
				log.error("==Default icon file is not exist: " + DEFAULT_ICON);
			}
		}
		return null;
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		download(mapping, form, request, response);
		return null;
	}

}
