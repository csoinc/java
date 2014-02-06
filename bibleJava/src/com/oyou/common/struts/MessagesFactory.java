package com.oyou.common.struts;

import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.common.reader.Encoder;
import com.oyou.common.util.StringHelper;
import com.oyou.common.util.StrutsSession;

public class MessagesFactory {
	private static final Log log = LogFactory.getLog(MessagesFactory.class);

	private static MessagesFactory messagesFactory = null;
	public static Hashtable<String, Messages> properties = new Hashtable<String, Messages>();

	/**
	 * Get the instance
	 * @return
	 */
	public static MessagesFactory getInstance() {
		if (messagesFactory == null) {
			messagesFactory = new MessagesFactory();
		}	
		return messagesFactory;
	}

	public String MESSAGE_CN = "/Resources/ApplicationResources_zh_CN.prop";
	public String MESSAGE_TW = "/Resources/ApplicationResources_zh_TW.prop";

	public String MESSAGE = "/Resources/ApplicationResources.properties";

	private MessagesFactory() {
	}
	
	public String getProperty(String key) {
		return this.getProperty(null, null, key);
	}

	public String getProperty(String language, String key) {
		return this.getProperty(language, null, key);
	}
	
	public String getProperty(String language, String bundle, String key) {
		Messages messages = null;
		if (StringHelper.isNotEmpty(bundle)) {
			messages = properties.get(bundle);
			if (messages == null || messages.getMessages().isEmpty()) {
				if (bundle.equals(Encoder.GB2312)) {
					log.debug("====" + Encoder.GB2312);
					messages = new Messages(MESSAGE_CN, Encoder.UTF8);
					properties.put(bundle, messages);
				} else if (bundle.equals(Encoder.BIG5)) {
					messages = new Messages(MESSAGE_TW, Encoder.UTF8);
					properties.put(bundle, messages);
				} 
				else {
					messages = new Messages(MESSAGE, Encoder.ISO88591);
					properties.put(Encoder.ISO88591, messages);
				}
			}
		} else {
			if (StrutsSession.LANGUAGE_EN.equalsIgnoreCase(language)) {
				messages = properties.get(Encoder.ISO88591);
				if (messages == null || messages.getMessages().isEmpty()) {
					log.debug("====" + Encoder.ISO88591);
					messages = new Messages(MESSAGE, Encoder.ISO88591);
					properties.put(Encoder.ISO88591, messages);
				}
			} else if (StrutsSession.LANGUAGE_CN.equalsIgnoreCase(language)) {
				messages = properties.get(Encoder.GB2312);
				if (messages == null || messages.getMessages().isEmpty()) {
					log.debug("====" + Encoder.GB2312);
					messages = new Messages(MESSAGE_CN, Encoder.UTF8);
					properties.put(Encoder.GB2312, messages);
				}
				if (messages.getProperty(key) == null) {
					messages = properties.get(Encoder.ISO88591);
					if (messages == null || messages.getMessages().isEmpty()) {
						log.debug("====" + Encoder.ISO88591);
						messages = new Messages(MESSAGE, Encoder.ISO88591);
						properties.put(Encoder.ISO88591, messages);
					}
				}
			} else {
				messages = properties.get(Encoder.BIG5);
				if (messages == null || messages.getMessages().isEmpty()) {
					log.debug("====" + Encoder.BIG5);
					messages = new Messages(MESSAGE_TW, Encoder.UTF8);
					properties.put(Encoder.BIG5, messages);
				}
				if (messages.getProperty(key) == null) {
					messages = properties.get(Encoder.ISO88591);
					if (messages == null || messages.getMessages().isEmpty()) {
						log.debug("====" + Encoder.ISO88591);
						messages = new Messages(MESSAGE, Encoder.ISO88591);
						properties.put(Encoder.ISO88591, messages);
					}
				}
			}
		}
		if (messages != null) return messages.getProperty(key);
		return null;
	}
	
}
