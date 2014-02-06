package com.oyou.web.blog;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.oyou.domain.blog.MessagePageList;
import com.oyou.domain.blog.orm.BlogGroup;
import com.oyou.domain.blog.orm.BlogMessage;
import com.oyou.web.view.Selection;

public class MessageForm extends BlogForm {
	static final long serialVersionUID = 1;
	private BlogGroup blogGroup;
	private Long userId;
	private String uploadName;
	private String title;
	private String summary;
	private String message;
	private BlogMessage blogMessage;
	private FormFile uploadFile;
	private byte[] uploadContent;
	private MessagePageList pageList = new MessagePageList();
	private String linkURL;
	private Long groupTypeId;
	protected List<Selection> groupOptList = new ArrayList<Selection>();
	private String groupOpt;
	private Long group;

	public BlogGroup getBlogGroup() {
		return blogGroup;
	}

	public BlogMessage getBlogMessage() {
		return blogMessage;
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

	public String getSummary() {
		return summary;
	}

	public String getTitle() {
		return title;
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
		this.setTitle(null);
		this.setSummary(null);
		this.setMessage(null);
		this.setMethod(null);
		this.setBlogMessage(null);
		this.setLinkURL(null);
		this.setUploadContent(null);
		this.setUploadFile(null);
		this.setUploadName(null);
	}

	public void setBlogGroup(BlogGroup blogGroup) {
		this.blogGroup = blogGroup;
	}

	public void setBlogMessage(BlogMessage blogMessage) {
		this.blogMessage = blogMessage;
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

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public MessagePageList getPageList() {
		return pageList;
	}

	public void setPageList(MessagePageList pageList) {
		this.pageList = pageList;
	}

	public List<Selection> getGroupOptList() {
		return groupOptList;
	}

	public void setGroupOptList(List<Selection> groupOptList) {
		this.groupOptList = groupOptList;
	}

	public String getGroupOpt() {
		return groupOpt;
	}

	public void setGroupOpt(String groupOpt) {
		this.groupOpt = groupOpt;
	}

	public Long getGroup() {
		return group;
	}

	public void setGroup(Long group) {
		this.group = group;
	}
}
