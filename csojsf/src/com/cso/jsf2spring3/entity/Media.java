package com.cso.jsf2spring3.entity;

import java.io.Serializable;

import org.springframework.stereotype.Component;

/**
 * 
 * @author ouow(Owen)
 *
 * @since 2012-08-20
 */
@Component
public class Media implements Serializable {
	private static final long serialVersionUID = 4480149046814897879L;
	// Fields
	private Integer id;
	private String filename;
	private String title;
	private String contentType;

	// Constructors
	/** default constructor */
	public Media() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


}