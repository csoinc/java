package com.oyou.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.misc.BASE64Encoder;

public class SecurityHelper {
	private static final Log log = LogFactory.getLog(SecurityHelper.class);

	public static String getMD5Hash(String value) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			value = value == null ? "" : value;
			String rawStr = value;
			log.debug("MD5(" + rawStr + ")=");
			byte[] buffer = rawStr.getBytes();
			md.update(buffer, 0, buffer.length);
			byte[] raw = md.digest();
			BASE64Encoder encoder = new BASE64Encoder();
			String base64 = encoder.encode(raw);
			log.debug(base64);
			result = base64;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			log.error("==Exception when MD5 hash: " + e.getMessage());
		}
		return result;
	}

}
