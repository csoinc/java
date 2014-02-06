package com.oyou.web.blog.console;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.oyou.common.util.DebugHelper;
import com.oyou.common.util.StrutsSession;
import com.oyou.domain.blog.CommonService;
import com.oyou.domain.blog.orm.BlogUser;
import com.oyou.domain.blog.orm.BlogUserType;
import com.oyou.web.blog.util.StrutsHelper;

import fr.improve.struts.taglib.layout.datagrid.Datagrid;

public class UserAdminAction extends ConsoleAction {
	private static final Log log = LogFactory.getLog(UserAdminAction.class);

	public ActionForward display(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse reponse) {
		log.debug(">>display()");
		UserAdminForm userAdminForm = (UserAdminForm) form;
		ActionMessages messages = new ActionMessages();
		BlogUser blogUser = StrutsHelper.getBlogUser(request);
		if (!BlogUserType.ADMIN.equals(blogUser.getBlogUserType().getId())) {
			log.debug("error.message.authority");
			messages.add(StrutsSession.KEY_BUSINESS, new ActionMessage("error.function.authority"));
			this.saveMessages(request, messages);
			return mapping.findForward(FORWARD_FAILED);
		}
		try {
			CommonService service = this.getCommonService();
			List users = service.listObjects(BlogUser.class);
			log.debug("Get users " + users.size());
			Datagrid datagrid = Datagrid.getInstance();
			datagrid.setDataClass(BlogUser.class);
			datagrid.setData(users);
			userAdminForm.setDatagrid(datagrid);
			userAdminForm.setUsers(users);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward(FORWARD_SUCCESS);
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		log.info(">>unspecified()");
		return display(mapping, form, request, response);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse reponse) {
		log.debug(">>update()");
		ActionMessages messages = new ActionMessages();
		UserAdminForm dForm = (UserAdminForm) form;
		Datagrid datagrid = dForm.getDatagrid();
		// Get the modified objects.
		Collection modifiedUsers = datagrid.getModifiedData();
		for (Iterator iter = modifiedUsers.iterator(); iter.hasNext();) {
			BlogUser blogUser = (BlogUser) iter.next();
			log.debug(DebugHelper.getJSONString(blogUser));
			//TODO: confirm and update
			//ActionMessage message = new ActionMessage("message.user.updated", blogUser.getLoginName());
			//messages.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		// Get the removed objects.
		//Collection removedObjects = datagrid.getDeletedData();
		// Get the added objects.
		//Collection addedObjects = datagrid.getAddedData();
		// Get the selected objects.
		//Collection selectedObjects = datagrid.getSelectedData();

		this.saveMessages(request, messages);
		return mapping.findForward(FORWARD_SUCCESS);
	}

	protected Map getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.add", "add");
		map.put("button.update", "update");
		map.put("button.remove", "remove");
		return map;
	}
	
}
