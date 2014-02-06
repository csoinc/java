package com.oyou.web.bible;

import java.util.List;

import com.oyou.domain.bible.orm.BibleLine;
import com.oyou.web.view.Selection;

public class SelectionForm extends BibleForm {
	static final long serialVersionUID = 1;
	protected List<BibleLine> lines;
	protected List<Selection> testaments;
	protected List<Selection> books;
	protected List<Selection> chapterList;
	protected List<Selection> languageList;
	protected String testament;
	protected String book;
	protected String[] chapters;
	protected String english;
	protected String chineseCN;
	protected String chineseTW;
	protected String[] languages;

	public String getBook() {
		return book;
	}

	public List<Selection> getBooks() {
		return books;
	}

	public String[] getChapters() {
		return chapters;
	}

	public List<Selection> getChapterList() {
		return chapterList;
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

	public List<Selection> getLanguageList() {
		return languageList;
	}

	public String[] getLanguages() {
		return languages;
	}

	public List<BibleLine> getLines() {
		return lines;
	}

	public String getTestament() {
		return testament;
	}

	public List<Selection> getTestaments() {
		return testaments;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public void setBooks(List<Selection> books) {
		this.books = books;
	}

	public void setChapters(String[] chapters) {
		this.chapters = chapters;
	}

	public void setChapterList(List<Selection> list) {
		this.chapterList = list;
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

	public void setLanguageList(List<Selection> languageList) {
		this.languageList = languageList;
	}

	public void setLanguages(String[] languages) {
		this.languages = languages;
	}

	public void setLines(List<BibleLine> lines) {
		this.lines = lines;
	}

	public void setTestament(String testament) {
		this.testament = testament;
	}

	public void setTestaments(List<Selection> testaments) {
		this.testaments = testaments;
	}
}
