package com.oyou.domain.bible.orm;

import com.oyou.common.Entity;

public class BibleLine extends Entity {
	static final long serialVersionUID = 1;
	private Integer sequence;

	private String code;

	private Integer chapter;

	private Integer section;

	private String content;

	private String contentCN;

	private String contentTW;

	private BibleBook bibleBook;

	public BibleBook getBibleBook() {
		return bibleBook;
	}

	public Integer getChapter() {
		return chapter;
	}

	public String getCode() {
		return code;
	}

	public String getContent() {
		return content;
	}

	public String getContentCN() {
		return contentCN;
	}

	public String getContentTW() {
		return contentTW;
	}

	public Integer getSection() {
		return section;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setBibleBook(BibleBook bibleBook) {
		this.bibleBook = bibleBook;
	}

	public void setChapter(Integer chapter) {
		this.chapter = chapter;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setContentCN(String contentCN) {
		this.contentCN = contentCN;
	}

	public void setContentTW(String contentTW) {
		this.contentTW = contentTW;
	}

	public void setSection(Integer section) {
		this.section = section;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

}
