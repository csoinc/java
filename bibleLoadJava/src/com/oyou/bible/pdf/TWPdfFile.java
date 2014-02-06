package com.oyou.bible.pdf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.bible.util.BibleConstants;

public class TWPdfFile implements PdfFile {
	private static final Log log = LogFactory.getLog(TWPdfFile.class);

	public void createPdfFile() {
		TWPdfWriter writer = new TWPdfWriter(
				BibleConstants.getInstance().getProperty(BibleConstants.TW_PDF_FILE), 
				BibleConstants.getInstance().PDF_AUTHOR, 
				BibleConstants.getInstance().getProperty(BibleConstants.TW_IMAGE_FILE), 
				TWWords.LICENSED_TO+TWWords.MCBC);
		
		writer.createCover();
		writer.createContent();
		log.info("Success");
	}

	public void createPdfSlideShowFile() {
		TWPdfWriter writer = new TWPdfWriter(
				BibleConstants.getInstance().getProperty(BibleConstants.TW_PDF_FILE), 
				BibleConstants.getInstance().PDF_AUTHOR, 
				BibleConstants.getInstance().getProperty(BibleConstants.TW_IMAGE_FILE), 
				TWWords.LICENSED_TO+TWWords.MCBC);
		
		writer.createCover();
		writer.createContentSlideShow();
		log.info("Success");
	}

}
