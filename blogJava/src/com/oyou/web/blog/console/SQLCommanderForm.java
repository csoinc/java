package com.oyou.web.blog.console;

import java.util.List;

public class SQLCommanderForm extends ConsoleForm {
	static final long serialVersionUID = 1;
	protected List databases;
	
	protected String commanders;
	
	protected String database;
	
	protected String errors;
	
	protected String results;

	public String getCommanders() {
		return commanders;
	}

	public String getDatabase() {
		return database;
	}

	public List getDatabases() {
		return databases;
	}

	public String getErrors() {
		return errors;
	}

	public void setCommanders(String commanders) {
		this.commanders = commanders;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public void setDatabases(List databases) {
		this.databases = databases;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}


}
