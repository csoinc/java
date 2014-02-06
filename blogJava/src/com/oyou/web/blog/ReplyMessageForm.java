package com.oyou.web.blog;

import java.util.List;
import org.apache.struts.upload.FormFile;

import com.oyou.domain.blog.orm.BlogMessage;
import com.oyou.domain.blog.orm.BlogReplyMessage;

public class ReplyMessageForm extends BlogForm {
	static final long serialVersionUID = 1;
	private BlogMessage blogMessage;
	private Long userId;
	private String uploadName;
	private String message;
	private BlogReplyMessage blogReplyMessage;
	private FormFile uploadFile;
	private byte[] uploadContent;
	private List messages;
	private String linkURL;
	private Long groupTypeId;

	public BlogMessage getBlogMessage() {
		return blogMessage;
	}

	public BlogReplyMessage getBlogReplyMessage() {
		return blogReplyMessage;
	}

	public Long getGroupTypeId() {
		return groupTypeId;
	}

	public String getLinkURL() {
		return linkURL;
	}

	public String getMessage() {
		return message;
	}

	public List getMessages() {
		return messages;
	}

	public byte[] getUploadContent() {
		return uploadContent;
	}

	public FormFile getUploadFile() {
		return uploadFile;
	}

	public String getUploadName() {
		return uploadName;
	}

	public Long getUserId() {
		return userId;
	}

	public void reset() {
		super.reset();
		this.setMessage(null);
		this.setMethod(null);
		this.setBlogReplyMessage(null);
		this.setLinkURL(null);
		this.setUploadContent(null);
		this.setUploadFile(null);
		this.setUploadName(null);
	}

	public void setBlogMessage(BlogMessage blogMessage) {
		this.blogMessage = blogMessage;
	}

	public void setBlogReplyMessage(BlogReplyMessage blogReplyMessage) {
		this.blogReplyMessage = blogReplyMessage;
	}

	public void setGroupTypeId(Long groupTypeId) {
		this.groupTypeId = groupTypeId;
	}

	public void setLinkURL(String linkURL) {
		this.linkURL = linkURL;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setMessages(List messages) {
		this.messages = messages;
	}

	public void setUploadContent(byte[] uploadContent) {
		this.uploadContent = uploadContent;
	}

	public void setUploadFile(FormFile uploadFile) {
		this.uploadFile = uploadFile;
	}

	public void setUploadName(String uploadName) {
		this.uploadName = uploadName;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
