package com.oyou.domain.blog.orm;

import java.sql.Timestamp;

import com.oyou.common.Entity;
import com.oyou.common.util.StringHelper;

public class BlogReplyMessage extends Entity {
	static final long serialVersionUID = 1;
	public static String MESSAGE_DEFAULT_VALUE = "";
	private BlogMessage blogMessage;
	private BlogUser blogUser;
	private BlogMessageType blogMessageType;
	private String uploadFile;
	private String linkURL;
	private String message;
	private Timestamp createTime;
	private Timestamp updateTime;
	private boolean status;
	private BlogReplyMessageStatistic blogReplyMessageStatistic;

	public BlogMessage getBlogMessage() {
		return blogMessage;
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

	public void setBlogMessage(BlogMessage blogMessage) {
		this.blogMessage = blogMessage;
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

	public BlogReplyMessageStatistic getBlogReplyMessageStatistic() {
		return blogReplyMessageStatistic;
	}

	public void setBlogReplyMessageStatistic(BlogReplyMessageStatistic blogReplyMessageStatistic) {
		this.blogReplyMessageStatistic = blogReplyMessageStatistic;
	}
}
