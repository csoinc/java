package com.oyou.web.blog;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

import com.oyou.common.util.StrutsSession;
import com.oyou.domain.blog.orm.BlogLanguageType;
import com.oyou.web.blog.resources.CNWords;
import com.oyou.web.blog.resources.TWWords;
import com.oyou.web.blog.resources.Words;
import com.oyou.web.view.Selection;

public class BlogForm extends ValidatorForm {
	static final long serialVersionUID = 1;
	public static final String UPDATE_MESSAGE = "message";
	public static final String UPDATE_ALL = "all";
	public static final String UPDATE_PUBLISHED = "published";
	public static final String ORDER_BY_VIEW_TIMES = "1";
	public static final String ORDER_BY_UPDATE_TIME = "2";
	protected static final int DEFAULT_MAX_LINE = 10;

	protected static List<Selection> imageAsIconOptList = new ArrayList<Selection>();
	protected static List<Selection> imageAsIconOptListEN = new ArrayList<Selection>();
	static { 
		imageAsIconOptListEN.add(new Selection("", Words.SELECTION));
		imageAsIconOptListEN.add(new Selection(StrutsSession.PHOTO, Words.PHOTO));
		imageAsIconOptListEN.add(new Selection(StrutsSession.ICON, Words.ICON));
	}

	protected static List<Selection> imageAsIconOptListCN = new ArrayList<Selection>();
	static { 
		imageAsIconOptListCN.add(new Selection("", CNWords.SELECTION));
		imageAsIconOptListCN.add(new Selection(StrutsSession.PHOTO, CNWords.PHOTO));
		imageAsIconOptListCN.add(new Selection(StrutsSession.ICON, CNWords.ICON));
	}
	
	protected static List<Selection> imageAsIconOptListTW = new ArrayList<Selection>();
	static { 
		imageAsIconOptListTW.add(new Selection("", TWWords.SELECTION));
		imageAsIconOptListTW.add(new Selection(StrutsSession.PHOTO, TWWords.PHOTO));
		imageAsIconOptListTW.add(new Selection(StrutsSession.ICON, TWWords.ICON));
	}
	
	protected static List<Selection> pageMaxLinesOptList = new ArrayList<Selection>();
	protected static List<Selection> pageMaxLinesOptListEN = new ArrayList<Selection>();
	static {
		pageMaxLinesOptListEN.add(new Selection("", Words.SELECTION));
		for (int i = 1; i <= 50; i++) {
			pageMaxLinesOptListEN.add(new Selection(Integer.toString(i), Words.LINE + ": " + i));
		}
	}
	protected static List<Selection> pageMaxLinesOptListCN = new ArrayList<Selection>();
	static {
		pageMaxLinesOptListCN.add(new Selection("", CNWords.SELECTION));
		for (int i = 1; i <= 50; i++) {
			pageMaxLinesOptListCN.add(new Selection(Integer.toString(i), CNWords.LINE + ": " + i));
		}
	}
	protected static List<Selection> pageMaxLinesOptListTW = new ArrayList<Selection>();
	static {
		pageMaxLinesOptListTW.add(new Selection("", TWWords.SELECTION));
		for (int i = 1; i <= 50; i++) {
			pageMaxLinesOptListTW.add(new Selection(Integer.toString(i), TWWords.LINE + ": " + i));
		}
	}

	protected static List<Selection> groupTypeOptList = new ArrayList<Selection>();
	
	protected static List<Selection> languageOptList = new ArrayList<Selection>();
	protected static List<Selection> languageOptListEN = new ArrayList<Selection>();
	static {
		languageOptListEN.add(new Selection("", Words.SELECTION));
		languageOptListEN.add(new Selection(BlogLanguageType.MANDARIN.toString(), Words.MANDARIN));
		languageOptListEN.add(new Selection(BlogLanguageType.CANTONESE.toString(), Words.CANTONESE));
		languageOptListEN.add(new Selection(BlogLanguageType.ENGLISH.toString(), Words.ENGLISH));
	}
	protected static List<Selection> languageOptListCN = new ArrayList<Selection>();
	static {
		languageOptListCN.add(new Selection("", CNWords.SELECTION));
		languageOptListCN.add(new Selection(BlogLanguageType.MANDARIN.toString(), CNWords.MANDARIN));
		languageOptListCN.add(new Selection(BlogLanguageType.CANTONESE.toString(), CNWords.CANTONESE));
		languageOptListCN.add(new Selection(BlogLanguageType.ENGLISH.toString(), CNWords.ENGLISH));
	}
	protected static List<Selection> languageOptListTW = new ArrayList<Selection>();
	static {
		languageOptListTW.add(new Selection("", TWWords.SELECTION));
		languageOptListTW.add(new Selection(BlogLanguageType.MANDARIN.toString(), TWWords.MANDARIN));
		languageOptListTW.add(new Selection(BlogLanguageType.CANTONESE.toString(), TWWords.CANTONESE));
		languageOptListTW.add(new Selection(BlogLanguageType.ENGLISH.toString(), TWWords.ENGLISH));
	}
	
	protected static List<Selection> orderByOptList = new ArrayList<Selection>();
	protected static List<Selection> orderByOptListEN = new ArrayList<Selection>();
	static {
		orderByOptListEN.add(new Selection("", Words.SELECTION));
		orderByOptListEN.add(new Selection(ORDER_BY_VIEW_TIMES, Words.ORDER_BY_VIEW_TIMES));
		orderByOptListEN.add(new Selection(ORDER_BY_UPDATE_TIME, Words.ORDER_BY_UPDATE_TIME));
	}
	protected static List<Selection> orderByOptListCN = new ArrayList<Selection>();
	static {
		orderByOptListCN.add(new Selection("", CNWords.SELECTION));
		orderByOptListCN.add(new Selection(ORDER_BY_VIEW_TIMES, CNWords.ORDER_BY_VIEW_TIMES));
		orderByOptListCN.add(new Selection(ORDER_BY_UPDATE_TIME, CNWords.ORDER_BY_UPDATE_TIME));
	}
	protected static List<Selection> orderByOptListTW = new ArrayList<Selection>();
	static {
		orderByOptListTW.add(new Selection("", TWWords.SELECTION));
		orderByOptListTW.add(new Selection(ORDER_BY_VIEW_TIMES, TWWords.ORDER_BY_VIEW_TIMES));
		orderByOptListTW.add(new Selection(ORDER_BY_UPDATE_TIME, TWWords.ORDER_BY_UPDATE_TIME));
	}
	
	private String method;
	private Long id;
	private String imageAsIconOpt = StrutsSession.ICON;
	private String pageMaxLinesOpt = Integer.toString(DEFAULT_MAX_LINE);
	private String languageOpt = BlogLanguageType.MANDARIN.toString();
	private String groupTypeOpt;
	private String orderByOpt = ORDER_BY_UPDATE_TIME;
	private boolean imageAsIcon = true;
	private int pageMaxLines = DEFAULT_MAX_LINE;
	private String updateOpt;
	private Long language = BlogLanguageType.MANDARIN;
	private Long groupType;
	private boolean orderByTimes = false;
	private String region;
	private String statusOpt;

	public Long getId() {
		return id;
	}

	public String getImageAsIconOpt() {
		return imageAsIconOpt;
	}

	public List<Selection> getImageAsIconOptList() {
		return imageAsIconOptList;
	}

	public String getLanguageOpt() {
		return languageOpt;
	}

	public List<Selection> getLanguageOptList() {
		return languageOptList;
	}

	public String getMethod() {
		return method;
	}

	public int getPageMaxLines() {
		if (this.pageMaxLines < 1) {
			this.pageMaxLines = DEFAULT_MAX_LINE;
		}
		return pageMaxLines;
	}

	public String getPageMaxLinesOpt() {
		return pageMaxLinesOpt;
	}

	public List<Selection> getPageMaxLinesOptList() {
		return pageMaxLinesOptList;
	}

	public String getUpdateOpt() {
		return updateOpt;
	}

	public boolean isImageAsIcon() {
		return imageAsIcon;
	}

	public void reset() {
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setImageAsIcon(boolean imageAsIcon) {
		this.imageAsIcon = imageAsIcon;
	}

	public void setImageAsIconOpt(String imageAsIconOpt) {
		this.imageAsIconOpt = imageAsIconOpt;
	}

	public void setImageAsIconOptList(List<Selection> imageAsIconOptList) {
		BlogForm.imageAsIconOptList = imageAsIconOptList;
	}

	public void initImageAsIconOptList(String lang) {
		if (StrutsSession.LANGUAGE_EN.equalsIgnoreCase(lang)) {
			BlogForm.imageAsIconOptList = imageAsIconOptListEN;
		} else if (StrutsSession.LANGUAGE_CN.equalsIgnoreCase(lang)) {
			BlogForm.imageAsIconOptList = imageAsIconOptListCN;
		} else {
			BlogForm.imageAsIconOptList = imageAsIconOptListTW;
		}
	}
	
	public void setLanguageOpt(String languageOpt) {
		this.languageOpt = languageOpt;
	}

	public void setLanguageOptList(List<Selection> languageOptList) {
		BlogForm.languageOptList = languageOptList;
	}

	public void initLanguageOptList(String lang) {
		if (StrutsSession.LANGUAGE_EN.equalsIgnoreCase(lang)) {
			BlogForm.languageOptList = languageOptListEN;
		} else if (StrutsSession.LANGUAGE_CN.equalsIgnoreCase(lang)) {
			BlogForm.languageOptList = languageOptListCN;
		} else {
			BlogForm.languageOptList = languageOptListTW;
		}
	}
	
	public void setMethod(String method) {
		this.method = method;
	}

	public void setPageMaxLines(int pageMaxLines) {
		this.pageMaxLines = pageMaxLines;
	}

	public void setPageMaxLinesOpt(String pageMaxLinesOpt) {
		this.pageMaxLinesOpt = pageMaxLinesOpt;
	}

	public void setPageMaxLinesOptList(List<Selection> pageMaxLinesOptList) {
		BlogForm.pageMaxLinesOptList = pageMaxLinesOptList;
	}

	public void initPageMaxLinesOptList(String lang) {
		if (StrutsSession.LANGUAGE_EN.equalsIgnoreCase(lang)) {
			BlogForm.pageMaxLinesOptList = pageMaxLinesOptListEN;
		} else if (StrutsSession.LANGUAGE_CN.equalsIgnoreCase(lang)) {
			BlogForm.pageMaxLinesOptList = pageMaxLinesOptListCN;
		} else {
			BlogForm.pageMaxLinesOptList = pageMaxLinesOptListTW;
		}
	}
	
	public void setUpdateOpt(String updateOpt) {
		this.updateOpt = updateOpt;
	}

	public Long getLanguage() {
		return language;
	}

	public void setLanguage(Long language) {
		this.language = language;
	}

	public String getOrderByOpt() {
		return orderByOpt;
	}

	public void setOrderByOpt(String orderByOpt) {
		this.orderByOpt = orderByOpt;
	}

	public List<Selection> getOrderByOptList() {
		return orderByOptList;
	}

	public void setOrderByOptList(List<Selection> orderByOptList) {
		BlogForm.orderByOptList = orderByOptList;
	}

	public void initOrderByOptList(String lang) {
		if (StrutsSession.LANGUAGE_EN.equalsIgnoreCase(lang)) {
			BlogForm.orderByOptList = orderByOptListEN;
		} else if (StrutsSession.LANGUAGE_CN.equalsIgnoreCase(lang)) {
			BlogForm.orderByOptList = orderByOptListCN;
		} else {
			BlogForm.orderByOptList = orderByOptListTW;
		}
	}
	
	public boolean isOrderByTimes() {
		return orderByTimes;
	}

	public void setOrderByTimes(boolean orderByTimes) {
		this.orderByTimes = orderByTimes;
	}

	public String getGroupTypeOpt() {
		return groupTypeOpt;
	}

	public void setGroupTypeOpt(String groupTypeOpt) {
		this.groupTypeOpt = groupTypeOpt;
	}

	public List<Selection> getGroupTypeOptList() {
		return groupTypeOptList;
	}

	public void setGroupTypeOptList(List<Selection> groupTypeOptList) {
		BlogForm.groupTypeOptList = groupTypeOptList;
	}

	public Long getGroupType() {
		return groupType;
	}

	public void setGroupType(Long groupType) {
		this.groupType = groupType;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getStatusOpt() {
		return statusOpt;
	}

	public void setStatusOpt(String statusOpt) {
		this.statusOpt = statusOpt;
	}

}
