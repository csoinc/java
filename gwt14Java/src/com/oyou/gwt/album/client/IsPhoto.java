package com.oyou.gwt.album.client;

/**
 * 
 * @author	Owen Ou
 * @version $Id: IsPhoto.java,v 1.1 2008/06/29 14:24:25 oyou Exp $
 * @since Nov 14, 2007
 */
public final class IsPhoto extends Entity {
//	public static int ICON = 0;
//	public static int IMAGE = 1;
//	public static int COMMENT = 2;
//	public static int NICKNAME = 3;
//	public static int TIME = 4;
	private String icon;
	private String image;
	private String comment;
	private String nickname;
	private String time;
	
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

}
