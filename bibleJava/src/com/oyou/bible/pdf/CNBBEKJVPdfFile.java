package com.oyou.bible.pdf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.bible.util.BibleConstants;

public class CNBBEKJVPdfFile implements PdfFile {
	private static final Log log = LogFactory.getLog(CNBBEKJVPdfFile.class);

	public void createPdfFile() {
		CNBBEKJVPdfWriter writer = new CNBBEKJVPdfWriter(
				BibleConstants.getInstance().getProperty(BibleConstants.CNBBEKJV_PDF_FILE), 
				BibleConstants.getInstance().PDF_AUTHOR, 
				BibleConstants.getInstance().getProperty(BibleConstants.CN_IMAGE_FILE), 
				Words.VERSION + CNWords.VERSION);
		
		writer.createCover();
		writer.createContent();
		log.info("Success");
	}

	public void createPdfSlideShowFile() {
		// TODO Auto-generated method stub
		
	}

}
