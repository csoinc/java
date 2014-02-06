package com.oyou.domain.blog;

import java.util.List;

import com.oyou.common.domain.PageList;
import com.oyou.domain.blog.orm.BlogMessage;
import com.oyou.domain.blog.orm.BlogUser;

public class MessagePageList extends PageList {
	private Long groupId;
	private List<BlogMessage> resultSet;
	private BlogUser blogUser;

	public List<BlogMessage> getResultSet() {
		return resultSet;
	}

	public void setResultSet(List<BlogMessage> resultSet) {
		this.resultSet = resultSet;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public BlogUser getBlogUser() {
		return blogUser;
	}

	public void setBlogUser(BlogUser blogUser) {
		this.blogUser = blogUser;
	}
}
