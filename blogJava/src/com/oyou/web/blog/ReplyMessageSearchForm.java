package com.oyou.web.blog;

import java.util.List;

import com.oyou.domain.blog.orm.BlogMessage;

public class ReplyMessageSearchForm extends BlogForm {
	static final long serialVersionUID = 1;
	private BlogMessage blogMessage;
	private List messages;
	
	public List getMessages() {
		return messages;
	}

	public void reset() {
		super.reset();
	}

	public void setMessages(List messages) {
		this.messages = messages;
	}

	public BlogMessage getBlogMessage() {
		return blogMessage;
	}

	public void setBlogMessage(BlogMessage blogMessage) {
		this.blogMessage = blogMessage;
	}

}
