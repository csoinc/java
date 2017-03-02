package com.oyou.gwt.blog.client;

/**
 * 
 * @author	Owen Ou
 * @version $Id: IsComment.java,v 1.1 2008/06/29 14:23:44 oyou Exp $
 * @since Dec 5, 2007
 */
public final class IsComment extends Entity {
	private String comment;
	private String nickname;
	private String time;
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

}
