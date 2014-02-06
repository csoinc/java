package com.oyou.common.process;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

/**
 * @author owen.ou
 * 
 * @since 2010-03-18
 * @version 1.0
 * record the output stream of some kind of batch long time processing
 * 
 */
public class StreamGobblerThread extends Thread {
	private static final Logger logger = Logger.getLogger(StreamGobblerThread.class);
	
	public static final String STDERR = "stderr";
	public static final String STDOUT = "stdout";

	private BufferedReader br = null;
	private String type;

	public StreamGobblerThread(InputStream is, String type) {
		br = new BufferedReader(new InputStreamReader(is));
		this.type = type;
	}

	public void run() {
		try {
			String line = null;
			while ((line = br.readLine()) != null) {
				if (STDERR.equals(type)) {
					logger.info(line);
				} else {
					logger.debug(line);
				}
			}
			br.close();
		} catch (Exception e) {
			logger.error(type + ":" + e.getMessage());
		}
	}
	
}
