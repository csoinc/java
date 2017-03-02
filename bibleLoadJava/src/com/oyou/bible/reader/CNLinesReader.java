package com.oyou.bible.reader;

import java.util.StringTokenizer;


import com.oyou.bible.model.CNLine;
import com.oyou.bible.model.Line;
import com.oyou.bible.util.BibleConstants;
import com.oyou.common.reader.GB2312Reader;

public class CNLinesReader extends GB2312Reader implements LineReader {

	public CNLinesReader() {
		super();
		this.setFilename(BibleConstants.getInstance().getProperty(BibleConstants.CN_TEXT_FILE));
		this.setBufferedReader();
		this.readLine(); // for this reader need get off the first  
	}

	public CNLinesReader(String filename) {
		super(filename);
		this.readLine(); // for this reader need get off the first
	}
	
	public Line getNextLine() {
		CNLine line = new CNLine();
		String buffer;
		if ((buffer = this.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(buffer, " ");
			String token = null;
			if (st.hasMoreTokens()) {
				token = st.nextToken();
				line.setBook(token);
			}
			if (st.hasMoreTokens()) {
				token = st.nextToken();
				line.setChapter(Integer.parseInt(token.substring(0, token.indexOf(":"))));
				line.setSection(Integer.parseInt(token.substring(token.indexOf(":")+1)));
			}
			String content = "";
			while (st.hasMoreTokens()) {
				content = st.nextToken().trim();
			}
			while (st.hasMoreTokens()) {
				content += st.nextToken();
			}
			line.setContent(content);
		}
		else {
			line = null;
		}
		return line;
	}

}
