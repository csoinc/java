package com.oyou.web.blog.console;

import java.util.List;

import fr.improve.struts.taglib.layout.datagrid.Datagrid;

/**
 * @author  Owen Ou
 */
public class UserAdminForm extends ConsoleForm {
	static final long serialVersionUID = 1;
	private Datagrid datagrid;
	private List users;
	
	public Datagrid getDatagrid() {
		return datagrid;
	}

	public List getUsers() {
		return users;
	}

	public void setDatagrid(Datagrid in_datagrid) {
		datagrid = in_datagrid;
	}

	public void setUsers(List users) {
		this.users = users;
	}
}
