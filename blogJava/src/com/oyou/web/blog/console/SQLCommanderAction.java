package com.oyou.web.blog.console;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
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

public class SQLCommanderAction extends ConsoleAction {
	private static final Log log = LogFactory.getLog(SQLCommanderAction.class);

	public ActionForward executeSQLScript(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug("==executeSQLScript()");
		SQLCommanderForm sqlForm = (SQLCommanderForm) form;
		ActionMessages messages = new ActionMessages();
		BlogUser blogUser = StrutsHelper.getBlogUser(request);
		if (!BlogUserType.ADMIN.equals(blogUser.getBlogUserType().getId())) {
			log.debug("error.message.authority");
			messages.add(StrutsSession.KEY_BUSINESS, new ActionMessage("error.function.authority"));
			this.saveMessages(request, messages);
			return mapping.findForward(FORWARD_FAILED);
		}
		String commanders = sqlForm.getCommanders();
		if (StringHelper.isNotEmpty(commanders)) {
			String database = sqlForm.getDatabase();
			BlogService service = null;
			if (StringHelper.isNotEmpty(database) && database.equals(DB_BACKUP)) {
				service = this.getBackupBlogService();
			} else {
				service = this.getBlogService();
			}
			BlogInformation blogInformation = this.getBlogService().getBlogInformationByID(BlogInformation.SQL_ID);
			StringBuffer sqlSB = new StringBuffer();
			sqlSB.append(commanders);
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
			Vector<String> vSQL = this.parseSQLScript(commanders);
			boolean flag = false;
			for (Iterator iter = vSQL.iterator(); iter.hasNext();) {
				String sql = (String)iter.next();
				if (flag) sb.append("<br/>");
				try {
					Object obj = service.executeSQLScript(sql);
					if (obj instanceof List) {
						sb.append(HTMLHelper.getHTMLTableFromMap((List)obj));
						sb.append("\n");
					} else {
						sb.append(sql + ": "  + obj.toString());
						sb.append("\n");
					}	

				} catch (SQLException e) {
					log.error(e.getMessage());
					esb.append(sql+": " + e.getMessage());
					esb.append("\n");
				}
				flag = true;
			}
			sqlForm.setResults(sb.toString());
			if (esb.length() > 0) {
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
	
	public ActionForward executeSQL(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		return executeSQLScript(mapping, form, request, response);
	}
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		log.debug("==unspecified()");
		SQLCommanderForm sqlForm = (SQLCommanderForm) form;
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
			sqlForm.setCommanders("");
		}
		return mapping.findForward(FORWARD_SUCCESS);
	}

	public Vector<String> parseSQLScript(String commanders) {
		Vector<String> vSQL = new Vector<String>();
		StringTokenizer st = new StringTokenizer(commanders, "\n");
		StringBuffer sb = new StringBuffer();
		while(st.hasMoreTokens()) {
			String line = st.nextToken().trim();
			if (!line.startsWith("--")) {
				sb.append(line);
				if (line.endsWith(";")) {
					vSQL.add(sb.toString());
					sb = new StringBuffer();
				}
			}
		}
		return vSQL;
	}
	
	
	
}
