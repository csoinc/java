package test.oyou.web.layout;

import java.util.List;

import org.apache.struts.action.ActionForm;

import fr.improve.struts.taglib.layout.datagrid.Datagrid;

/**
 * @author  Owen Ou
 */
public class DatagridForm extends ActionForm {

	private Datagrid datagrid;
	private List books;
	
	public List getBooks() {
		return books;
	}
	public Datagrid getDatagrid() {
		return datagrid;
	}
	public void setBooks(List books) {
		this.books = books;
	}
	public void setDatagrid(Datagrid in_datagrid) {
		datagrid = in_datagrid;
	}
}
