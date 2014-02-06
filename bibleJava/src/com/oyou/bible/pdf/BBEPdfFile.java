package com.oyou.bible.pdf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.bible.util.BibleConstants;

public class BBEPdfFile implements PdfFile {
	private static final Log log = LogFactory.getLog(BBEPdfFile.class);

	public void createPdfFile() {
		BBEPdfWriter writer = new BBEPdfWriter(
				BibleConstants.getInstance().getProperty(BibleConstants.BBE_PDF_FILE), 
				BibleConstants.getInstance().PDF_AUTHOR, 
				BibleConstants.getInstance().getProperty(BibleConstants.BBE_IMAGE_FILE), 
				Words.LICENSED_TO+" "+Words.MCBC);
		
		writer.createCover();
		writer.createContent();
		log.info("Success");
	}

	public void createPdfSlideShowFile() {
		BBEPdfWriter writer = new BBEPdfWriter(
				BibleConstants.getInstance().getProperty(BibleConstants.BBE_PDF_FILE), 
				BibleConstants.getInstance().PDF_AUTHOR, 
				BibleConstants.getInstance().getProperty(BibleConstants.BBE_IMAGE_FILE), 
				Words.LICENSED_TO+" "+Words.MCBC);
		
		writer.createCover();
		writer.createContentSlideShow();
		log.info("Success");
	}

}
