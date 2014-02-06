package com.oyou.web.blog;

import java.util.List;

import com.oyou.domain.blog.orm.BlogGroup;

public class GroupForm extends BlogForm {
	static final long serialVersionUID = 1;
	private String groupName = "groupName";
	private String description = "description";
	private List groups;
	private BlogGroup blogGroup;
	private List securityTypes;
	private Long securityTypeId;

	public BlogGroup getBlogGroup() {
		return blogGroup;
	}

	public List getGroups() {
		return groups;
	}

	public Long getSecurityTypeId() {
		return securityTypeId;
	}

	public List getSecurityTypes() {
		return securityTypes;
	}

	public void reset() {
//		setGroupName(null);
//		setDescription(null);
		setMethod(null);
	}

	public void setBlogGroup(BlogGroup blogGroup) {
		this.blogGroup = blogGroup;
	}

	public void setGroups(List groups) {
		this.groups = groups;
	}

	public void setSecurityTypeId(Long securityTypeId) {
		this.securityTypeId = securityTypeId;
	}

	public void setSecurityTypes(List securityTypes) {
		this.securityTypes = securityTypes;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
