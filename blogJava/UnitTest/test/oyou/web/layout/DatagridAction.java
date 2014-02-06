package test.oyou.web.layout;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.oyou.common.reader.Encoder;
import com.oyou.web.struts.StrutsAction;

import fr.improve.struts.taglib.layout.datagrid.Datagrid;

public class DatagridAction extends StrutsAction {
	private static final Log log = LogFactory.getLog(DatagridAction.class);

	private static final String BEAN_BIBLE_SERVICE = "bibleService";

	public ActionForward display(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse reponse) {
		log.debug(">>display()");
		try {
			DatagridForm dForm = (DatagridForm) form;

			Datagrid datagrid = Datagrid.getInstance();

			dForm.setDatagrid(datagrid);
			request.setCharacterEncoding(Encoder.GB2312);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("success");
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		log.info(">>unspecified()");
		return display(mapping, form, request, response);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse reponse) {
		// Cast the form.
		//DatagridForm dForm = (DatagridForm) form;

		// Get the datagrid object.
		//Datagrid datagrid = dForm.getDatagrid();

		// Get the modified objects.
		//Collection modifiedObjects = datagrid.getModifiedData();

		// Get the removed objects.
		//Collection removedObjects = datagrid.getDeletedData();

		// Get the added objects.
		//Collection addedObjects = datagrid.getAddedData();

		// Get the selected objects.
		//Collection selectedObjects = datagrid.getSelectedData();
		return mapping.findForward("success");

	}

	protected Map getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.create", "create");
		map.put("button.update", "update");
		map.put("button.delete", "delete");
		return map;
	}
	
}
