package com.oyou.common.reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * reader base class
 * @author	Owen Ou
 *
 */
public class Reader {
	protected static final Log log = LogFactory.getLog(Reader.class);
	protected BufferedReader bufferedReader;
	protected String filename;
	protected String encoder;

	public Reader() {
	}

	public Reader(String filename, String encoder) {
		this.setFilename(filename);
		this.setEncoder(encoder);
		this.setBufferedReader(filename);
	}
	
	public void close() {
		try {
			bufferedReader.close();
		} catch (IOException ioe) {
			log.error(ioe.getMessage());
		}

	}
	
	public BufferedReader getBufferedReader() {
		return bufferedReader;
	}
	
	public String getEncoder() {
		return encoder;
	}

	public String getFilename() {
		return filename;
	}
	
	public String readLine() {
		try {
			return bufferedReader.readLine();
		} catch (IOException ioe) {
			log.error(ioe.getMessage());
		}
		return null;
	}

	protected void setBufferedReader() {
		try {
			InputStream inputStream = new FileInputStream(filename);
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream, encoder));
		} catch (IOException ioe) {
			log.error("==Can't set buffered Reader " + ioe.getMessage());
		}
	}

	public void setBufferedReader(BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
	}

	protected void setBufferedReader(String filename) {
		this.setFilename(filename);
		this.setBufferedReader();
	}

	public void setEncoder(String encoder) {
		this.encoder = encoder;
	}

	public void setFilename(String filename) {
		this.filename = filename;
		log.debug("==set filename " + filename);
	}
	
}
