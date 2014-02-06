package com.oyou.bible.model;

import java.io.Serializable;

public class Line implements Serializable {
	static final long serialVersionUID = 1; 
	
	private String key; //book/index.chapter.section for query
	private int id; //id per book
	private String book;
	private int chapter;
	private int section;
	private String content;
	public String getBook() {
		return book;
	}
	public int getChapter() {
		return chapter;
	}
	public String getContent() {
		return content;
	}
	public int getId() {
		return id;
	}
	public String getKey() {
		return key;
	}
	public int getSection() {
		return section;
	}
	public void setBook(String book) {
		this.book = book;
	}
	public void setChapter(int chapter) {
		this.chapter = chapter;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public void setSection(int section) {
		this.section = section;
	}
	
}
