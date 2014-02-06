package com.oyou.web.blog.console;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import com.oyou.common.util.HTMLHelper;
import com.oyou.common.util.StringHelper;
import com.oyou.common.util.StrutsSession;
import com.oyou.domain.blog.BlogService;
import com.oyou.domain.blog.orm.BlogInformation;
import com.oyou.domain.blog.orm.BlogUser;
import com.oyou.domain.blog.orm.BlogUserType;
import com.oyou.web.blog.util.StrutsHelper;
import com.oyou.web.view.Selection;

public class HQLAction extends ConsoleAction {
	private static final Log log = LogFactory.getLog(SQLCommanderAction.class);

	public ActionForward executeHQL(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug("==executeSQLScript()");
		HQLForm sqlForm = (HQLForm) form;
		ActionMessages messages = new ActionMessages();
		BlogUser blogUser = StrutsHelper.getBlogUser(request);
		if (!BlogUserType.ADMIN.equals(blogUser.getBlogUserType().getId())) {
			log.debug("error.message.authority");
			messages.add(StrutsSession.KEY_BUSINESS, new ActionMessage("error.function.authority"));
			this.saveMessages(request, messages);
			return mapping.findForward(FORWARD_FAILED);
		}
		String hql = sqlForm.getHql();
		if (StringHelper.isNotEmpty(hql)) {
			String database = sqlForm.getDatabase();
			BlogService service = null;
			if (StringHelper.isNotEmpty(database) && database.equals(DB_BACKUP)) {
				service = this.getBackupBlogService();
			} else {
				service = this.getBlogService();
			}
			BlogInformation blogInformation = this.getBlogService().getBlogInformationByID(BlogInformation.HSQL_ID);
			StringBuffer sqlSB = new StringBuffer();
			sqlSB.append(hql);
			sqlSB.append("<br>\r\n");
			sqlSB.append(HTMLHelper.formatToText(blogInformation.getInformation()));
			if (sqlSB.toString().length() >= BUFFER) {
				blogInformation.setInformation(HTMLHelper.formatToHTML(sqlSB.substring(0, BUFFER)));
			} else {
				blogInformation.setInformation(HTMLHelper.formatToHTML(sqlSB.toString()));
			}
			this.getCommonService().saveOrUpdate(blogInformation);
			
			StringBuffer sb = new StringBuffer();
			StringBuffer esb = new StringBuffer();
			log.debug("==Run: " + hql);
			sqlForm.setResults("");
			try {
				List list = service.executeHQL(hql);
				sb.append(HTMLHelper.getHTMLTable(list));
				sb.append("\n");
			} catch (SQLException e) {
				log.error(e.getMessage());
				esb.append(hql+": " + e.getMessage());
				esb.append("\n");
			}
			sqlForm.setResults(sb.toString());
			if (esb.toString().length() > 0) {
				ActionMessage message = new ActionMessage("message.sqlcommander.return", esb.toString());
				messages.add(ActionMessages.GLOBAL_MESSAGE, message);
				this.saveMessages(request, messages);
			}
		} else {
			ActionMessage message = new ActionMessage("message.sqlcommander.empty");
			messages.add(ActionMessages.GLOBAL_MESSAGE, message);
			this.saveMessages(request, messages);
		}
		return mapping.findForward(FORWARD_SUCCESS);
	}
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug("==unspecified()");
		HQLForm sqlForm = (HQLForm) form;
		if (sqlForm.getDatabases() == null) {
			List<Selection> list = new ArrayList<Selection>();
			Selection sel = new Selection("", "Select Database");
			list.add(sel);
			sel = new Selection(DB_PROD, "Production Database");
			list.add(sel);
			sel = new Selection(DB_BACKUP, "Backup Database");
			list.add(sel);
			sqlForm.setDatabases(list);
			sqlForm.setDatabase("");
			sqlForm.setHql("");
			sqlForm.setResults("");
		}
		return mapping.findForward(FORWARD_SUCCESS);
	}
	
}
