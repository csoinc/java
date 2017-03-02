package com.oyou.bible.pdf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.bible.util.BibleConstants;

public class KJVPdfFile implements PdfFile {
	private static final Log log = LogFactory.getLog(KJVPdfFile.class);

	public void createPdfFile() {
		KJVPdfWriter writer = new KJVPdfWriter(
				BibleConstants.getInstance().getProperty(BibleConstants.KJV_PDF_FILE), 
				BibleConstants.getInstance().PDF_AUTHOR, 
				BibleConstants.getInstance().getProperty(BibleConstants.KJV_IMAGE_FILE), 
				Words.LICENSED_TO+" "+Words.MCBC);
		
		writer.createCover();
		writer.createContent();
		log.info("Success");
	}

	public void createPdfSlideShowFile() {
		// TODO Auto-generated method stub
		
	}

}
