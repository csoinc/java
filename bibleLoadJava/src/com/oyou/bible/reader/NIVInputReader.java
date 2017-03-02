package com.oyou.bible.reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.bible.model.Book;
import com.oyou.bible.model.InputLine;
import com.oyou.bible.model.Line;
import com.oyou.bible.util.BibleConstants;
import com.oyou.bible.util.NIVInputHelper;
import com.oyou.common.reader.ISO88591Reader;

public class NIVInputReader extends ISO88591Reader implements LineReader {
	private static final Log log = LogFactory.getLog(NIVInputReader.class);

	private Book currBook = null;
	private String currLine = "";
	private InputLine line = new InputLine();
	private StringBuffer content = new StringBuffer();
	private String buffer;
	private int currPSALM = -1;
	private static final String PSAML = "Psalms";
	private boolean isPSALM = false;

	public NIVInputReader() {
		super();
		this.setFilename(BibleConstants.getInstance().getProperty(BibleConstants.NIV_INPUT_FILE));
		this.setBufferedReader();
		this.readLine(); // for this reader need get off the first
	}

	public NIVInputReader(String filename) {
		super(filename);
		this.readLine(); // for this reader need get off the first
	}

	public Line getNextLine() {
		return null;
	}
	
	public InputLine getNextInputLine() {
		if (currBook == null) {
			//get the first book line
			while ((buffer = this.readLine()) != null) {
				if (NIVInputHelper.isLine(buffer)) {
					Book book = NIVInputHelper.getBookByName(buffer);
					if (book != null) {
						currBook = book;
						line = new InputLine();
						StringBuffer content = new StringBuffer();
						line.setNumber(0);
						line.setContent(buffer);
						return line;
					}
				}
			}
			return null;
		}
		if (currLine != null && currLine.length() > 0) {
			if (NIVInputHelper.isBook(currLine)) {
				Book book = NIVInputHelper.getBookByName(currLine);
				line = new InputLine();
				StringBuffer content = new StringBuffer();
				currBook = book;
				line.setNumber(0);
				line.setContent(currLine);
				if (PSAML.equals(book.getName())) isPSALM = true;
				else isPSALM = false;
				while ((buffer = this.readLine()) != null) {
					if (NIVInputHelper.isLine(buffer)) {
						currLine = buffer;
						break;
					}
				}
				return line;
			} else if (NIVInputHelper.isPsalmLine(currLine)) {
				currPSALM = NIVInputHelper.getPsalmChapter(currLine);
				while ((buffer = this.readLine()) != null) {
					if (NIVInputHelper.isLine(buffer)) {
						currLine = buffer;
						break;
					}
				}
				if (currLine == null) return null;
				else return this.getNextInputLine();
			} else if (NIVInputHelper.isDigitLine(currLine)) {
				line = new InputLine();
				StringBuffer content = new StringBuffer();
				String numberStr = NIVInputHelper.getDigit(currLine);
				content.append(currLine.substring(numberStr.length()));
				line.setNumber(Integer.parseInt(numberStr));
				line.setCode(currBook.getCode());
				if (isPSALM && Integer.parseInt(numberStr) == 1) {
					line.setNumber(currPSALM);
				}
				while ((buffer = this.readLine()) != null) {
					if (NIVInputHelper.isLine(buffer)) {
						if (NIVInputHelper.isBook(buffer)) {
							currLine = buffer;
							line.setContent(content.toString());
							return line;
						} else if (NIVInputHelper.isDigitLine(buffer)) {
							currLine = buffer;
							line.setContent(content.toString());
							return line;
						} else if (NIVInputHelper.isLetterLine(buffer)) {
							if (NIVInputHelper.isPsalmLine(buffer)) {
								currPSALM = NIVInputHelper.getPsalmChapter(buffer);
							} else {
								content.append(buffer);
							}
						}
					}
				}
				currLine = null;
				line.setContent(content.toString());
				return line;
			} else {
				while ((buffer = this.readLine()) != null) {
					if (NIVInputHelper.isLine(buffer)) {
						currLine = buffer;
						break;
					}
				}
				return this.getNextInputLine();
			}
		} else {
			while ((buffer = this.readLine()) != null) {
				if (NIVInputHelper.isLine(buffer)) {
					currLine = buffer;
					break;
				}
			}
			if (currLine == null) return null;
			else return this.getNextInputLine();
		}
	}
}
