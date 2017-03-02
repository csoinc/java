package com.oyou.bible.writer;

import com.oyou.bible.util.BibleConstants;
/**
 * Create NIV output file
 * 
 * @author	Owen Ou
 * 
 */
public class NIVOutputFileWriter extends Writer {

	public NIVOutputFileWriter() {
		super(BibleConstants.getInstance().getProperty(BibleConstants.NIV_OUTPUT_FILE));
	}

}
