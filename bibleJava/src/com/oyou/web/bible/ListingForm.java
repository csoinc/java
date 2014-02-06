package com.oyou.web.bible;

import java.util.List;

import com.oyou.domain.bible.orm.BibleBook;
import com.oyou.domain.bible.orm.BibleLine;

public class ListingForm extends BibleForm {
	static final long serialVersionUID = 1;
	protected BibleBook book;

	protected List<BibleLine> lines;
	
	protected String chapter;
	
	protected String english;
	
	protected String chineseCN;
	
	protected String chineseTW;
	
	protected String[] languages;
	
	protected String menu;

	public BibleBook getBook() {
		return book;
	}

	public String getChapter() {
		return chapter;
	}

	public String getChineseCN() {
		return chineseCN;
	}

	public String getChineseTW() {
		return chineseTW;
	}

	public String getEnglish() {
		return english;
	}

	public String[] getLanguages() {
		return languages;
	}

	public List<BibleLine> getLines() {
		return lines;
	}

	public String getMenu() {
		return menu;
	}

	public void reset() {
		setBook(null);
		setChapter(null);
	}

	public void setBook(BibleBook book) {
		this.book = book;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	public void setChineseCN(String chineseCN) {
		this.chineseCN = chineseCN;
	}

	public void setChineseTW(String chineseTW) {
		this.chineseTW = chineseTW;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	public void setLanguages(String[] languages) {
		this.languages = languages;
	}

	public void setLines(List<BibleLine> lines) {
		this.lines = lines;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}
	
}
