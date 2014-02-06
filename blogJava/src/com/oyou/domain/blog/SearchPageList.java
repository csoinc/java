package com.oyou.domain.blog;

import java.util.List;

import com.oyou.common.domain.PageList;
import com.oyou.domain.blog.orm.BlogMessage;
import com.oyou.domain.blog.orm.BlogUser;

public class SearchPageList extends PageList {
	private String word = "";
	private List<BlogMessage> resultSet;
	private BlogUser blogUser;

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public List<BlogMessage> getResultSet() {
		return resultSet;
	}

	public void setResultSet(List<BlogMessage> resultSet) {
		this.resultSet = resultSet;
	}

	public BlogUser getBlogUser() {
		return blogUser;
	}

	public void setBlogUser(BlogUser blogUser) {
		this.blogUser = blogUser;
	}

}
