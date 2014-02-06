package com.oyou.web.blog;

import com.oyou.domain.blog.orm.BlogReplyMessage;

public class ViewReplyMessageForm extends BlogForm {
	static final long serialVersionUID = 1;
	private BlogReplyMessage blogReplyMessage;

	public BlogReplyMessage getBlogReplyMessage() {
		return blogReplyMessage;
	}

	public void reset() {
		super.reset();
		this.setMethod(null);
		this.setBlogReplyMessage(null);
	}

	public void setBlogReplyMessage(BlogReplyMessage blogReplyMessage) {
		this.blogReplyMessage = blogReplyMessage;
	}
}
