package com.oyou.web.bible;

import org.apache.struts.validator.ValidatorForm;

public class BibleForm extends ValidatorForm {
	static final long serialVersionUID = 1;
	public static String MENU_BIBLE_ENGLISH = "bible_english";
	public static String MENU_BIBLE_CHINESE_CN = "bible_chinese_cn";
	public static String MENU_BIBLE_CHINESE_TW = "bible_chinese_tw";
	public static String MENU_BIBLE_ENGLISH_CHINESE_CN = "bible_english_chinese_cn";
	public static String MENU_BIBLE_ENGLIG_CHINESE_TW = "bible_english_chinese_tw";
	public static String LANGUAGE_ENGLISH = "english";
	public static String LANGUAGE_CHINESE_CN = "chinese_cn";
	public static String LANGUAGE_CHINESE_TW = "chinese_tw";
	protected static final int DEFAULT_MAX_LINE = 21;
	
	private String method;
	private String language;
	
	private Long id;
	
	public Long getId() {
		return id;
	}

	public String getMethod() {
		return method;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	
}
