package com.oyou.domain.blog.orm;

import java.sql.Timestamp;
import java.util.Collection;

import com.oyou.common.Entity;
import com.oyou.common.util.StringHelper;

public class BlogGroup extends Entity {
	static final long serialVersionUID = 1;
	private String groupName;
	private BlogSecurityType blogSecurityType;
	private BlogLanguageType blogLanguageType;
	private BlogGroupType blogGroupType;
	private String description;
	private Timestamp createTime;
	private Timestamp updateTime;
	private boolean status;
	private Collection blogUsers;
	private Collection blogMessages;

	public BlogLanguageType getBlogLanguageType() {
		return blogLanguageType;
	}

	public BlogSecurityType getBlogSecurityType() {
		return blogSecurityType;
	}

	public Collection getBlogUsers() {
		return blogUsers;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public String getDescription() {
		return StringHelper.filterXSS(description);
	}

	public String getGroupName() {
		return StringHelper.filterXSS(groupName);
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public boolean isStatus() {
		return status;
	}

	public void setBlogLanguageType(BlogLanguageType blogLanguageType) {
		this.blogLanguageType = blogLanguageType;
	}

	public void setBlogSecurityType(BlogSecurityType blogSecurityType) {
		this.blogSecurityType = blogSecurityType;
	}

	public void setBlogUsers(Collection blogUsers) {
		this.blogUsers = blogUsers;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public void setDescription(String description) {
		this.description = StringHelper.filterXSS(description);
	}

	public void setGroupName(String groupName) {
		this.groupName = StringHelper.filterXSS(groupName);
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Collection getBlogMessages() {
		return blogMessages;
	}

	public void setBlogMessages(Collection blogMessages) {
		this.blogMessages = blogMessages;
	}

	public BlogGroupType getBlogGroupType() {
		return blogGroupType;
	}

	public void setBlogGroupType(BlogGroupType blogGroupType) {
		this.blogGroupType = blogGroupType;
	}
}
