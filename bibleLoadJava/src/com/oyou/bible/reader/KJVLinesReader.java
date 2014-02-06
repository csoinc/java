package com.oyou.bible.reader;

import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.bible.model.Line;
import com.oyou.bible.util.BibleConstants;
import com.oyou.common.reader.ISO88591Reader;

public class KJVLinesReader extends ISO88591Reader implements LineReader {
	private static final Log log = LogFactory.getLog(KJVLinesReader.class);

	public KJVLinesReader() {
		super();
		this.setFilename(BibleConstants.getInstance().getProperty(BibleConstants.KJV_TEXT_FILE));
		this.setBufferedReader();
		this.readLine(); // for this reader need get off the first  
	}

	public KJVLinesReader(String filename) {
		super(filename);
		this.readLine(); // for this reader need get off the first
	}
	
	public Line getNextLine() {
		Line line = new Line();
		try {
		String buffer;
		if ((buffer = this.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(buffer, " ");
			String token = null;
			if (st.hasMoreTokens()) {
				token = st.nextToken();
				String book = this.getBookFromToken(token);
				if (book == null) log.error("Book is null " + token);
				else {
					line.setBook(book);
				}
				String chapter = this.getChapterFromToken(token);
				if (chapter == null) log.error("Chapter is null " + token);
				else {
					line.setChapter(Integer.parseInt(chapter.substring(0, chapter.indexOf(":"))));
					line.setSection(Integer.parseInt(chapter.substring(chapter.indexOf(":")+1)));
				}
			}
			String content = "";
			if (st.hasMoreTokens()) {
				content = st.nextToken().trim();
			}
			while (st.hasMoreTokens()) {
				content = content + " " + st.nextToken();
			}
			line.setContent(content+" ");
		}
		else {
			line = null;
		}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return line;
	}

	private String getBookFromToken(String token) {
		boolean hasChar = false;
		StringBuffer sb = new StringBuffer();
		if (token == null || token.length() == 0) return null;
		for (int i=0; i<token.length(); i++) {
			char c = token.charAt(i);
			if (!hasChar) {
				sb.append(c);
				hasChar = true;
			} else {
				if (Character.isDigit(c)) {
					break;
				} else {	
					hasChar = true;
					sb.append(c);
				} 
			}	
		}
		return sb.toString();
	}
	
	private String getChapterFromToken(String token) {
		boolean hasChar = false;
		boolean hasDigit = false;
		StringBuffer sb = new StringBuffer();
		if (token == null || token.length() == 0) return null;
		for (int i=0; i<token.length(); i++) {
			char c = token.charAt(i);
			if (hasChar) {
				if (hasDigit) {
					sb.append(c);
				} else {
					if (Character.isDigit(c)) {
						sb.append(c);
						hasDigit = true;
					}
				}
			} else {
				if (Character.isLetter(c)) {
					hasChar = true;
				} 
			}
		}
		return sb.toString();
	}
	
}
