package com.oyou.bible.util;

import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BibleConstants {
	private static final Log log = LogFactory.getLog(BibleConstants.class);

	private static BibleConstants bibleConstants = null;
	private static Hashtable<String, String> properties = new Hashtable<String, String>();
	
	public static final String CN_TEXT_FILE = "CNText";
	public static final String TW_TEXT_FILE = "TWText";
	public static final String BBE_TEXT_FILE = "BBEText";
	public static final String KJV_TEXT_FILE = "KJVText";
	public static final String NIV_TEXT_FILE = "NIVText";
	public static final String CN_INDEX_FILE = "CNIndex";
	public static final String TW_INDEX_FILE = "TWIndex";
	public static final String NIV_INDEX_FILE = "NIVIndex";
	public static final String BBE_INDEX_FILE = "BBEIndex";
	public static final String KJV_INDEX_FILE = "KJVIndex";
	public static final String INDEX_FILE = "BooksIndex";
	public static final String CN_PDF_FILE = "CNPDF";
	public static final String TW_PDF_FILE = "TWPDF";
	public static final String BBE_PDF_FILE = "BBEPDF";
	public static final String KJV_PDF_FILE = "KJVPDF";
	public static final String CN_IMAGE_FILE = "CNImage";
	public static final String TW_IMAGE_FILE = "TWImage";
	public static final String BBE_IMAGE_FILE = "BBEImage";
	public static final String KJV_IMAGE_FILE = "KJVImage";
	public static final String CNBBEKJV_PDF_FILE = "CNBBEKJVPDF";
	public static final String TWBBEKJV_PDF_FILE = "TWBBEKJVPDF";
	public static final String NIV_INPUT_FILE = "NIVInput";
	public static final String NIV_OUTPUT_FILE = "NIVOutput";
	
	/**
	 * Get the instance
	 * @return
	 */
	public static BibleConstants getInstance() {
		if (bibleConstants == null)
			bibleConstants = new BibleConstants();
		return bibleConstants;
	}

	/**
	 * Paragraph per chapter or section control switch
	 * SECTION_MODE = true : Paragraph per section
	 */
	public boolean SECTION_MODE = true;
	public String CHAPTER_SECTION_SEPARATOR = ":";
	public String PDF_AUTHOR = "OYOU";
	public String USER_DIR_KEY = "user.dir";
	public String WEBAPP_ROOT_KEY = "webapp.root";
	public String USER_LANGUAGE_KEY = "user.language";

	private String rootPath = null; 

	private BibleConstants() {
		properties.put(CN_TEXT_FILE, "/data/bible/text/hgb.txt");
		properties.put(TW_TEXT_FILE, "/data/bible/text/hb5.txt");
		properties.put(BBE_TEXT_FILE, "/data/bible/text/bbe.txt");
		properties.put(KJV_TEXT_FILE, "/data/bible/text/kjv.txt");
		properties.put(NIV_TEXT_FILE, "/data/bible/text/niv.txt");
		properties.put(CN_INDEX_FILE, "/data/bible/index/books-CN.txt");
		properties.put(TW_INDEX_FILE, "/data/bible/index/books-TW.txt");
		properties.put(NIV_INDEX_FILE, "/data/bible/index/books-NIV.txt");
		properties.put(BBE_INDEX_FILE, "/data/bible/index/books-BBE.txt");
		properties.put(KJV_INDEX_FILE, "/data/bible/index/books-KJV.txt");
		properties.put(INDEX_FILE, "/data/bible/index/books.txt");
		properties.put(CN_PDF_FILE, "/data/bible/pdf/hgb-slide-v2.2.pdf");
		properties.put(TW_PDF_FILE, "/data/bible/pdf/hb5-slide-v2.1.pdf");
		properties.put(BBE_PDF_FILE, "/data/bible/pdf/bbe-slide-v2.1.pdf");
		properties.put(KJV_PDF_FILE, "/data/bible/pdf/kjv-v2.1.pdf");
		properties.put(CN_IMAGE_FILE, "/data/bible/img/coverB.jpg");
		properties.put(TW_IMAGE_FILE, "/data/bible/img/coverA.jpg");
		properties.put(BBE_IMAGE_FILE, "/data/bible/img/coverC.jpg");
		properties.put(KJV_IMAGE_FILE, "/data/bible/img/coverD.jpg");
		properties.put(CNBBEKJV_PDF_FILE, "/data/bible/pdf/hgb-kjv-bbe-v2.1.pdf");
		properties.put(TWBBEKJV_PDF_FILE, "/data/bible/pdf/hb5-kjv-bbe-v2.1.pdf");
		properties.put(NIV_INPUT_FILE, "/data/bible/input/niv.txt");
		properties.put(NIV_OUTPUT_FILE, "/data/bible/output/niv.txt");
	}
	
	public String getProperty(String key) {
		if (properties.containsKey(key)) {
			log.debug("==getProperty() for key " + key + " value " + properties.get(key));
			return this.rootPath + properties.get(key);
		}
		log.error("==getProperty() not property for key " + key);
		return null;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	
	
}
