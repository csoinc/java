package com.oyou.bible.pdf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.bible.util.BibleConstants;

public class CNPdfFile implements PdfFile {
	private static final Log log = LogFactory.getLog(CNPdfFile.class);

	public void createPdfFile() {
		CNPdfWriter writer = new CNPdfWriter(
				BibleConstants.getInstance().getProperty(BibleConstants.CN_PDF_FILE), 
				BibleConstants.getInstance().PDF_AUTHOR, 
				BibleConstants.getInstance().getProperty(BibleConstants.CN_IMAGE_FILE), 
				CNWords.LICENSED_TO+CNWords.MCBC);
		
		writer.createCover();
		writer.createContent();
		log.info("Success");
	}

	public void createPdfSlideShowFile() {
		CNPdfWriter writer = new CNPdfWriter(
				BibleConstants.getInstance().getProperty(BibleConstants.CN_PDF_FILE), 
				BibleConstants.getInstance().PDF_AUTHOR, 
				BibleConstants.getInstance().getProperty(BibleConstants.CN_IMAGE_FILE), 
				CNWords.LICENSED_TO+CNWords.MCBC);
		
		writer.createCover();
		writer.createContentSlideShow();
		log.info("Success");
	}
	
}
