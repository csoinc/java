package com.oyou.web.blog.console;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.oyou.common.exception.BusinessException;
import com.oyou.common.spring.SpringService;
import com.oyou.common.util.StrutsSession;
import com.oyou.domain.blog.BlogService;
import com.oyou.domain.blog.CommonService;
import com.oyou.domain.blog.orm.BlogUser;
import com.oyou.domain.blog.orm.BlogUserType;
import com.oyou.web.blog.BlogAction;
import com.oyou.web.blog.util.StrutsHelper;
import com.oyou.web.blog.util.UploadAdminThread;

public class UploadAdminAction extends BlogAction {
	private static final Log log = LogFactory.getLog(UploadAdminAction.class);

	private UploadAdminThread uploadAdminThread = null;

	private Thread thread = null;

	private CommonService commonService = null;

	public ActionForward upload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug("==upload()");
		ActionMessages messages = new ActionMessages();
		BlogUser bbsUser = StrutsHelper.getBlogUser(request);
		if (!BlogUserType.ADMIN.equals(bbsUser.getBlogUserType().getId())) {
			log.debug("error.message.authority");
			messages.add(StrutsSession.KEY_BUSINESS, new ActionMessage("error.function.authority"));
			this.saveMessages(request, messages);
			return mapping.findForward(FORWARD_FAILED);
		}
		try {
			if (thread != null && thread.isAlive()) {
				log.debug("==Processing upload admin thread is alive, so wait......");
				return mapping.findForward(FORWARD_SUCCESS);
			} else {
				String uploadPath = this.getUploadRoot();
				File path = new File(uploadPath);
				if (path.exists()) {
					uploadAdminThread = new UploadAdminThread(path, this.getBlogService(), this.getCommonService());
					thread = new Thread(uploadAdminThread);
					thread.start();
				}
			}
			Thread.sleep(5000);
			ActionMessage message = new ActionMessage("message.upload.maintain.confirmed");
			messages.add(ActionMessages.GLOBAL_MESSAGE, message);
			this.saveMessages(request, messages);
			return mapping.findForward(FORWARD_SUCCESS);
		} catch (InterruptedException ie) {
			messages.add(StrutsSession.KEY_BUSINESS, new ActionMessage("error.system"));
			this.saveMessages(request, messages);
			return mapping.findForward(FORWARD_LOGIN);
		}
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug("==unspecified()");
		return upload(mapping, form, request, response);
	}

	/**
	 * @deprecated Move to UploadAdminHelper
	 * @param path
	 * @param messages
	 */
	protected void processPath(File path, ActionMessages messages) {
		if (path.exists()) {
			if (path.isDirectory()) {
				File[] files = path.listFiles();
				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					if (file.exists()) {
						processPath(file, messages);
					}
				}
			} else {
				log.debug("File need verify against DB: " + path.getName());
				BlogService service = this.getBlogService();
				if ((service.getBlogMessageByUploadName(path.getName()) == null) && (service.getBlogReplyMessageByUploadName(path.getName()) == null)) {
					log.debug("File need delete " + path.getName());
					path.delete();
					ActionMessage message = new ActionMessage("message.upload.deleted.confirmed", path.getName());
					messages.add(ActionMessages.GLOBAL_MESSAGE, message);
				}
			}
		}
	}

	public CommonService getCommonService() {
		if (commonService == null) {
			Object obj = getService(SpringService.COMMON_SERVICE);
			if (obj != null) {
				commonService = (CommonService) obj;
			}
		}
		return commonService;
	}

}
