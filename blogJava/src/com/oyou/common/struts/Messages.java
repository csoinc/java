package com.oyou.common.struts;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cfg.Configuration;

import com.oyou.common.reader.Encoder;
import com.oyou.common.util.StringHelper;

public class Messages {
	private static final Log log = LogFactory.getLog(Messages.class);

	private Hashtable<String, String> messages = new Hashtable<String, String>();

	private Messages() {}
	
	public Messages(String filename, String encoder) {
		URL url = Configuration.class.getClassLoader().getResource(filename);
		String resource = null;
		if (url != null) {
			try {
				resource = URLDecoder.decode(url.getPath().trim(), Encoder.UTF8);
			} catch (UnsupportedEncodingException e) {
				resource = url.getPath();
				log.error(e.getMessage());
			}
			log.debug("Resoure: " + resource);
			PropertyReader reader = new PropertyReaderImpl(resource, encoder);
			Property prop = null;
			while ((prop = reader.getNextProperty()) != null) {
				//log.debug(DebugHelper.getJSONString(prop));
				messages.put(prop.getKey(), prop.getValue());
			}
		} else {
			log.fatal("Can't get resoure for " + filename);
		}
	}
	
	public String getProperty(String key) {
		if (messages != null && StringHelper.isNotEmpty(key) && messages.containsKey(key)) {
			String value = messages.get(key);
			//log.debug("==getProperty() for key " + key + " = " + value);
			return value;
		} 
		return null;
	}

	public Hashtable<String, String> getMessages() {
		return messages;
	}

	
}
