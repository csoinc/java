/*
 * Created on 22 mars 2004
 * 
 * Copyright Improve SA.
 * All rights reserved.
 */
package fr.improve.struts.taglib.layout.menu;

import java.io.Serializable;

/**
 * This class modelize an action that can be added to a menu element.
 * 
 * @author jnribette
 */
public class MenuAction implements Serializable {
		
	/**
	 * The href this actions links to.
	 */
	private String href;
	
	/**
	 * The target of the link.
	 */
	private String target;
	
	/**
	 * The action image url.
	 */
	private String imgSrc;
	
	/**
	 * @return Returns the href.
	 */
	public String getHref() {
		return href;
	}
	/**
	 * @param href The href to set.
	 */
	public void setHref(String href) {
		this.href = href;
	}
	/**
	 * @return Returns the imgSrc.
	 */
	public String getImgSrc() {
		return imgSrc;
	}
	/**
	 * @param imgSrc The imgSrc to set.
	 */
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	/**
	 * @return Returns the target.
	 */
	public String getTarget() {
		return target;
	}
	/**
	 * @param target The target to set.
	 */
	public void setTarget(String target) {
		this.target = target;
	}
}
