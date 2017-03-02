package com.oyou.bible.reader;

import java.util.StringTokenizer;


import com.oyou.bible.model.Line;
import com.oyou.bible.model.TWLine;
import com.oyou.bible.util.BibleConstants;
import com.oyou.common.reader.BIG5Reader;

public class TWLinesReader extends BIG5Reader implements LineReader {

	public TWLinesReader() {
		super();
		this.setFilename(BibleConstants.getInstance().getProperty(BibleConstants.TW_TEXT_FILE));
		this.setBufferedReader();
		this.readLine(); // for this reader need get off the first  
	}

	public TWLinesReader(String filename) {
		super(filename);
		this.readLine(); // for this reader need get off the first
	}
	
	public Line getNextLine() {
		TWLine line = new TWLine();
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
			if (st.hasMoreTokens()) {
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
