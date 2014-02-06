package com.oyou.web.blog;

import com.oyou.domain.blog.SearchPageList;

public class MessageSearchForm extends BlogForm {
	//static final long serialVersionUID = 1;
	private SearchPageList pageList = new SearchPageList();;

	public void reset() {
		if (this.getPageList() != null) {
			this.getPageList().setResultSet(null);
		}
	}

	public SearchPageList getPageList() {
		return pageList;
	}

	public void setPageList(SearchPageList pageList) {
		this.pageList = pageList;
	}

}
