package com.oyou.bible.writer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.bible.model.InputLine;
import com.oyou.bible.reader.NIVInputReader;

public class NIVOutputFile {
	private static final Log log = LogFactory.getLog(NIVOutputFile.class);

	public void createNIVOutputFile() {
		log.info(">>createNIVOutputFile()");
		NIVInputReader reader = new NIVInputReader();
		NIVOutputFileWriter writer = new NIVOutputFileWriter();
		writer.writeLine("Holy Bible, Bible in New Intenational Version (NIV). Textfile 4028K.");
		InputLine currLine = null;
		InputLine nextLine = null;
		int currChapter = 0;
		int currSection = 0;
		while ((nextLine = (InputLine) reader.getNextInputLine()) != null) {
			int nextNumber = nextLine.getNumber();
			if (nextNumber == 0) {
				if (currLine != null) {
					currLine.setChapter(currChapter);
					currLine.setSection(currLine.getNumber());
					writer.writeLine(currLine.getCode() + " " + currLine.getChapter() + ":" + currLine.getSection() + " "
							+ currLine.getContent());
					currLine = null;
				}
				continue;
			} else {
				if (currLine == null) {
					currLine = nextLine;
					currChapter = 1;
					currSection = 1;
					currLine.setChapter(currChapter);
					currLine.setSection(currSection);
				} else {
					int currNumber = currLine.getNumber();
					// process all number 2 node
					if (nextNumber == 2) {
						if (nextNumber > currChapter) {
							if (nextNumber > currSection) {
								// 1:2
								currChapter = currNumber;
								currSection = 1;
							} else {
								if (nextNumber < currNumber) {
									// 2:1 node
									currSection = currNumber;
								} else {
									// 2:2
									currChapter = currNumber;
									currSection = 1;
								}
							}
						} else {
							// [2-n]:2 node
							currChapter = currNumber;
							currSection = 1;
						}
					} else {
						currSection = currNumber;
					}
					if (nextNumber == currChapter + 1 || nextNumber == currSection + 1) {
						currLine.setChapter(currChapter);
						currLine.setSection(currSection);
						writer.writeLine(currLine.getCode() + " " + currLine.getChapter() + ":" + currLine.getSection() + " "
								+ currLine.getContent());
						currLine = nextLine;
					} else {
						if (nextNumber > currSection + 1) {
							if (nextNumber == currSection + 2) {
								log.info("==" + nextLine.getCode() + ":" + currChapter + ":" + currSection +
										":" + nextNumber + nextLine.getContent());
								currLine.setChapter(currChapter);
								currLine.setSection(currSection);
								writer.writeLine(currLine.getCode() + " " + currLine.getChapter() + ":" + currLine.getSection()
										+ " " + currLine.getContent());
								currLine = nextLine;
							} else {
								int preferNumber = currSection + 1;
								int preferLength = Integer.toString(preferNumber).length();
								String nextNumberStr = Integer.toString(nextNumber);
								String nextNumberPrefixStr = nextNumberStr.substring(0, preferLength);
								String nextNumberSuffixStr = nextNumberStr.substring(preferLength);
								int nextNumberPrefix = Integer.parseInt(nextNumberPrefixStr);
								if (nextNumberPrefix == currSection + 1) {
									currLine.setChapter(currChapter);
									currLine.setSection(currSection);
									writer.writeLine(currLine.getCode() + " " + currLine.getChapter() + ":"
											+ currLine.getSection() + " " + currLine.getContent());
									nextLine.setNumber(nextNumberPrefix);
									nextLine.setContent(nextNumberSuffixStr + nextLine.getContent());
									currLine = nextLine;
								} else {
									log.info(">>" + nextLine.getCode() + ":" + currChapter + ":" + currSection +
											":" + nextNumber + nextLine.getContent());
									currLine.setContent(currLine.getContent() + nextLine.getNumber() + nextLine.getContent());
								}
							}
						} else {
							log.info("<<" + nextLine.getCode() + ":" + currChapter + ":" + currSection +
									":" + nextNumber + nextLine.getContent());
							currLine.setContent(currLine.getContent() + nextLine.getNumber() + nextLine.getContent());
						}
					}
				}
			}
		}
		currLine.setChapter(currChapter);
		currLine.setSection(currLine.getNumber());
		writer.writeLine(currLine.getCode() + " " + currLine.getChapter() + ":" + currLine.getSection() + " "
				+ currLine.getContent());
		reader.close();
		writer.close();
		log.info("<<createNIVOutputFile()");
	}

}
