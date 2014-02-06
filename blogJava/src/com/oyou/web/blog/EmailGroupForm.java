package com.oyou.web.blog;

import java.util.List;

public class EmailGroupForm extends BlogForm {
	static final long serialVersionUID = 1;
	protected List users;
	protected String subject;
	protected String content;
	protected String[] usersSelected;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List getUsers() {
		return users;
	}

	public void setUsers(List users) {
		this.users = users;
	}

	public String[] getUsersSelected() {
		return usersSelected;
	}

	public void setUsersSelected(String[] usersSelected) {
		this.usersSelected = usersSelected;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
