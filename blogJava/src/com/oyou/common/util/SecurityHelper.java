package com.oyou.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SecurityHelper {
	private static final Log log = LogFactory.getLog(SecurityHelper.class);

	private static String getMD5Hash(String value) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			value = value == null ? "" : value;
			String rawStr = value;
			log.debug("MD5(" + rawStr + ")=");
			byte[] buffer = rawStr.getBytes();
			md.update(buffer, 0, buffer.length);
			byte[] raw = md.digest();
			Base64 encoder = new Base64();
			String base64 = encoder.encode(raw).toString();
			log.debug(base64);
			result = base64;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			log.error("==Exception when MD5 hash: " + e.getMessage());
		}
		return result;
	}

	private static char scramble(char kChar, char pChar, int idx) {
		int kVal = kChar;
		int pVal = pChar;
		double d1 = kVal * 1.7 + pVal * idx * 3.79;
		int i1 = (int)d1;
		int i2 = (i1 % 26) + 97;
		char c1 = (char)i2;
		return c1;
	}

	public static String encode(String passStr) {
		log.debug("Encode input:" + passStr);
		StringBuffer passSb = new StringBuffer();
		String keyStr = "H7d80S6c7F80sK7d5a8D";
        for (int i = 1; i <= keyStr.length(); i++) {
            char keyChar = keyStr.charAt(i-1);
            for (int j = 1; j <= passStr.length(); j++) {
            	char passChar = passStr.charAt(j-1);
            	keyChar = scramble(keyChar, passChar, j);
            }
        	passSb.append(keyChar);
        }
		log.debug("Encode output:" + passSb.toString());
        return passSb.toString();
	}
	
}
