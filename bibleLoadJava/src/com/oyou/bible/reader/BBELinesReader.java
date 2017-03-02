package com.oyou.bible.reader;

import java.util.StringTokenizer;

import com.oyou.bible.model.Line;
import com.oyou.bible.util.BibleConstants;
import com.oyou.common.reader.ISO88591Reader;

public class BBELinesReader extends ISO88591Reader implements LineReader {

	public BBELinesReader() {
		super();
		this.setFilename(BibleConstants.getInstance().getProperty(BibleConstants.BBE_TEXT_FILE));
		this.setBufferedReader();
		this.readLine(); // for this reader need get off the first  
	}

	public BBELinesReader(String filename) {
		super(filename);
		this.readLine(); // for this reader need get off the first
	}
	
	public Line getNextLine() {
		Line line = new Line();
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
				content = st.nextToken();
			}	
			while (st.hasMoreTokens()) {
				content = content + " " + st.nextToken();
			}
			line.setContent(content+" ");
		}
		else {
			line = null;
		}
		return line;
	}

}
