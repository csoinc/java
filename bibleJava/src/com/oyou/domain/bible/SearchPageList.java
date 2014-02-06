package com.oyou.domain.bible;

import java.util.List;

import com.oyou.common.domain.PageList;
import com.oyou.domain.bible.orm.BibleLine;

@SuppressWarnings("serial")
public class SearchPageList extends PageList {
	private String what = "";
	private String where = "";
	private List<String> codes;

	private List<BibleLine> resultSet;

	public List<BibleLine> getResultSet() {
		return resultSet;
	}

	public void setResultSet(List<BibleLine> resultSet) {
		this.resultSet = resultSet;
	}

	public List<String> getCodes() {
		return codes;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	public String getWhat() {
		return what;
	}

	public void setWhat(String what) {
		this.what = what;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

}
