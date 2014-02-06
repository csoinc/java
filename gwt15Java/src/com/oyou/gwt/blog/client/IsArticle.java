package com.oyou.gwt.blog.client;

/**
 * 
 * @author	Owen Ou
 * @version $Id: IsArticle.java,v 1.1 2008/06/29 14:23:48 oyou Exp $
 * @since Dec 5, 2007
 */
public final class IsArticle extends Entity {
	private String title;
	private String summary;
	private String content;
	private String nickname;
	private String time;
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
