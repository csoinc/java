package com.oyou.bible.pdf;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;

/**
 * <pre> 
 * Language Fonts CMap names 
 * ------------------------------------------------------------- 
 * Chinese (Simplified) 	STSong-Light 		UniGB-UCS2-H 
 * 							STSongStd-Light		UniGB-UCS2-V 
 * Chinese (Traditional) 	MHei-Medium 		UniCNS-UCS2-H 
 * 							MSung-Light 		UniCNS-UCS2-V 
 * 							MSungStd-Light 
 * </pre>
 */
public class FontFactory {
	private static final Log log = LogFactory.getLog(FontFactory.class);
	private static FontFactory instance = null;
	private static final String TW_FONTS = "MHei-Medium";
	private static final String TW_CMAP = "UniCNS-UCS2-H";
	private static final String CN_FONTS = "STSong-Light";
	private static final String CN_CMAP = "UniGB-UCS2-H";

	public static FontFactory getInstance() {
		if (instance == null) {
			instance = new FontFactory();
		}
		return instance;
	}

	private FontFactory() {
	}

	public Font getTWSongLight10() {
		Font font = null;
		try {
			BaseFont bf = BaseFont.createFont(TW_FONTS, TW_CMAP, BaseFont.NOT_EMBEDDED);
			font = new Font(bf, 10);
		} catch (IOException ioe) {
			log.error(ioe.getMessage());
		} catch (DocumentException de) {
			log.error(de.getMessage());
		}
		return font;
	}

	public Font getTWSongLight12() {
		Font font = null;
		try {
			BaseFont bf = BaseFont.createFont(TW_FONTS, TW_CMAP, BaseFont.NOT_EMBEDDED);
			font = new Font(bf, 12);
		} catch (IOException ioe) {
			log.error(ioe.getMessage());
		} catch (DocumentException de) {
			log.error(de.getMessage());
		}
		return font;
	}

	public Font getTWSongLight8() {
		Font font = null;
		try {
			BaseFont bf = BaseFont.createFont(TW_FONTS, TW_CMAP, BaseFont.NOT_EMBEDDED);
			font = new Font(bf, 8);
		} catch (IOException ioe) {
			log.error(ioe.getMessage());
		} catch (DocumentException de) {
			log.error(de.getMessage());
		}
		return font;
	}

	public Font getTWSongLightSlideShow() {
		Font font = null;
		try {
			BaseFont bf = BaseFont.createFont(TW_FONTS, TW_CMAP, BaseFont.NOT_EMBEDDED);
			font = new Font(bf, 48);
		} catch (IOException ioe) {
			log.error(ioe.getMessage());
		} catch (DocumentException de) {
			log.error(de.getMessage());
		}
		return font;
	}

	public Font getTimeRoman10() {
		Font font = null;
		try {
			BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
			font = new Font(bf, 10);
		} catch (IOException ioe) {
			log.error(ioe.getMessage());
		} catch (DocumentException de) {
			log.error(de.getMessage());
		}
		return font;
	}

	public Font getTimeRoman12() {
		Font font = null;
		try {
			BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
			font = new Font(bf, 12);
		} catch (IOException ioe) {
			log.error(ioe.getMessage());
		} catch (DocumentException de) {
			log.error(de.getMessage());
		}
		return font;
	}

	public Font getTimeRoman8() {
		Font font = null;
		try {
			BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
			font = new Font(bf, 8);
		} catch (IOException ioe) {
			log.error(ioe.getMessage());
		} catch (DocumentException de) {
			log.error(de.getMessage());
		}
		return font;
	}

	public Font getTimeRomanSlideShow() {
		Font font = null;
		try {
			BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
			font = new Font(bf, 40);
		} catch (IOException ioe) {
			log.error(ioe.getMessage());
		} catch (DocumentException de) {
			log.error(de.getMessage());
		}
		return font;
	}

	public Font getCNSongLight10() {
		Font font = null;
		try {
			BaseFont bf = BaseFont.createFont(CN_FONTS, CN_CMAP, BaseFont.NOT_EMBEDDED);
			font = new Font(bf, 10);
		} catch (IOException ioe) {
			log.error(ioe.getMessage());
		} catch (DocumentException de) {
			log.error(de.getMessage());
		}
		return font;
	}

	public Font getCNSongLight12() {
		Font font = null;
		try {
			BaseFont bf = BaseFont.createFont(CN_FONTS, CN_CMAP, BaseFont.NOT_EMBEDDED);
			font = new Font(bf, 12);
		} catch (IOException ioe) {
			log.error(ioe.getMessage());
		} catch (DocumentException de) {
			log.error(de.getMessage());
		}
		return font;
	}

	public Font getCNSongLight8() {
		Font font = null;
		try {
			BaseFont bf = BaseFont.createFont(CN_FONTS, CN_CMAP, BaseFont.NOT_EMBEDDED);
			font = new Font(bf, 8);
		} catch (IOException ioe) {
			log.error(ioe.getMessage());
		} catch (DocumentException de) {
			log.error(de.getMessage());
		}
		return font;
	}

	public Font getCNSongLightSlideShow() {
		Font font = null;
		try {
			BaseFont bf = BaseFont.createFont(CN_FONTS, CN_CMAP, BaseFont.NOT_EMBEDDED);
			font = new Font(bf, 46);
		} catch (IOException ioe) {
			log.error(ioe.getMessage());
		} catch (DocumentException de) {
			log.error(de.getMessage());
		}
		return font;
	}
}
