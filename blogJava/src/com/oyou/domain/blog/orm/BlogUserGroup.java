package com.oyou.domain.blog.orm;

import java.sql.Timestamp;

import com.oyou.common.Entity;

public class BlogUserGroup extends Entity {
	static final long serialVersionUID = 1;
	private BlogUser blogUser;
	private BlogGroup blogGroup;
	private boolean groupOwner;
	private Timestamp createTime;
	private Timestamp updateTime;
	private boolean status;

	public BlogGroup getBlogGroup() {
		return blogGroup;
	}

	public BlogUser getBlogUser() {
		return blogUser;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public boolean isGroupOwner() {
		return groupOwner;
	}

	public boolean isStatus() {
		return status;
	}

	public void setBlogGroup(BlogGroup blogGroup) {
		this.blogGroup = blogGroup;
	}

	public void setBlogUser(BlogUser blogUser) {
		this.blogUser = blogUser;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public void setGroupOwner(boolean groupOwner) {
		this.groupOwner = groupOwner;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
}
