package com.oyou.web.bible;

import java.util.List;

import com.oyou.domain.bible.SearchPageList;
import com.oyou.web.view.Selection;

public class BibleSearchForm extends BibleForm {
	static final long serialVersionUID = 1;
	protected SearchPageList pageList = new SearchPageList();
	protected List<Selection> bookList;
	protected String[] books;
	protected List<Selection> catList;
	protected String[] cats;

	
	public SearchPageList getPageList() {
		return pageList;
	}

	public void setPageList(SearchPageList pageList) {
		this.pageList = pageList;
	}

	public List<Selection> getBookList() {
		return bookList;
	}

	public void setBookList(List<Selection> bookList) {
		this.bookList = bookList;
	}

	public String[] getBooks() {
		return books;
	}

	public void setBooks(String[] books) {
		this.books = books;
	}

	public List<Selection> getCatList() {
		return catList;
	}

	public void setCatList(List<Selection> catList) {
		this.catList = catList;
	}

	public String[] getCats() {
		return cats;
	}

	public void setCats(String[] cats) {
		this.cats = cats;
	}

}
