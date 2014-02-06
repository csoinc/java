package com.oyou.bible.writer;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.common.reader.Encoder;
/**
 * Writer base class
 * @author	Owen Ou
 * 
 */
public class Writer {
	protected static final Log log = LogFactory.getLog(Writer.class);
	protected BufferedWriter bufferedWriter;
	protected String filename;
	protected String encode;

	public Writer() {
	}

	public Writer(String filename) {
		this.setFilename(filename);
		this.setEncode(Encoder.UTF8);
		this.setBufferedWriter();
	}
	
	public Writer(String filename, String encode) {
		this.setFilename(filename);
		this.setEncode(encode);
		this.setBufferedWriter();
	}
	
	public void close() {
		try {
			bufferedWriter.close();
		} catch (IOException ioe) {
			log.error(ioe.getMessage());
		}

	}

	public BufferedWriter getBufferedWriter() {
		return bufferedWriter;
	}

	public String getEncode() {
		return encode;
	}

	public String getFilename() {
		return filename;
	}

	public void setBufferedWriter() {
		try {
			OutputStream outputStream = new FileOutputStream(this.filename);
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, this.encode));
		} catch (IOException ioe) {
			log.error(ioe.getMessage());
		}
	}
	
	public void setBufferedWriter(BufferedWriter bufferedWriter) {
		this.bufferedWriter = bufferedWriter;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void writeLine(String line) {
		try {
			bufferedWriter.write(line+"\n");	
			bufferedWriter.flush();
		} catch (IOException ioe) {
			log.error(ioe.getMessage());
		}
	}

}
