package com.oyou.gwt.blog.client;

/**
 * 
 * @author	Owen Ou
 * @version $Id: IsPicture.java,v 1.1 2008/06/29 14:23:44 oyou Exp $
 * @since Dec 5, 2007
 */
public class IsPicture extends Entity {
	private String icon;
	private String image;
	private String comment;
	private String nickname;
	private String time;
	private String summary;
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
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
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}

}
