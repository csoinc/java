package com.oyou.domain.blog.orm;

import java.sql.Timestamp;
import java.util.Collection;


import com.oyou.common.Entity;
import com.oyou.common.util.StringHelper;

public class BlogMessage extends Entity {
	static final long serialVersionUID = 1;
	public static String MESSAGE_DEFAULT_VALUE = "";
	private BlogGroup blogGroup;
	private BlogUser blogUser;
	private BlogMessageType blogMessageType;
	private String uploadFile;
	private String linkURL;
	private String title;
	private String summary;
	private String message;
	private Timestamp createTime;
	private Timestamp updateTime;
	private boolean status;
	private BlogMessageStatistic blogMessageStatistic;
	private Collection blogReplyMessages;

	public BlogGroup getBlogGroup() {
		return blogGroup;
	}

	public BlogMessageType getBlogMessageType() {
		return blogMessageType;
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

	public boolean isStatus() {
		return status;
	}

	public void setBlogGroup(BlogGroup blogGroup) {
		this.blogGroup = blogGroup;
	}

	public void setBlogMessageType(BlogMessageType blogMessageType) {
		this.blogMessageType = blogMessageType;
	}

	public void setBlogUser(BlogUser blogUser) {
		this.blogUser = blogUser;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getLinkURL() {
		return StringHelper.filterXSS(linkURL);
	}

	public void setLinkURL(String linkURL) {
		this.linkURL = StringHelper.filterXSS(linkURL);
	}

	public String getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(String uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getMessage() {
		return StringHelper.filterXSS(message);
	}

	public void setMessage(String message) {
		this.message = StringHelper.filterXSS(message);
	}

	public BlogMessageStatistic getBlogMessageStatistic() {
		return blogMessageStatistic;
	}

	public void setBlogMessageStatistic(BlogMessageStatistic blogMessageStatistic) {
		this.blogMessageStatistic = blogMessageStatistic;
	}

	public Collection getBlogReplyMessages() {
		return blogReplyMessages;
	}

	public void setBlogReplyMessages(Collection blogReplyMessages) {
		this.blogReplyMessages = blogReplyMessages;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
