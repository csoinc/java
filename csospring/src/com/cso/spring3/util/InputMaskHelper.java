package com.cso.spring3.util;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Input mask utility helper, the mask pattern EL:
 * 
 * a - Represents an alpha character (A-Z,a-z)
 * 9 - Represents a numeric character (0-9)
 * * - Represents an alphanumeric character (A-Z,a-z,0-9)
 * ? - Anything listed after '?' within the mask is considered optional user input
 * 
 * Example: phone mask as (999) 999-9999
 *  
 * @author ouow(Owen)
 * 
 * @date 2012-08-01
 */
public class InputMaskHelper {
	private static Logger log = LoggerFactory.getLogger(InputMaskHelper.class);

	private final static String PHONE_MASK_DELIMITER = "\\D+";
	private final static String PHONE_PATTERN = "(999) 999-9999";
	private final static char NUMERIC_CHAR = '9';
	private final static char ALPHA_CHAR = 'a';

	public static String filterOutPhoneMask(String phoneNumber) {
		try {
			Scanner scanner = new Scanner(phoneNumber);
			scanner.useDelimiter(PHONE_MASK_DELIMITER);
			StringBuilder sb = new StringBuilder();
			while (scanner.hasNext()) {
				if (scanner.hasNextLong()) {
					sb.append(scanner.nextLong());
				} else {
					String str = scanner.next();
				}
			}
			return sb.toString();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return phoneNumber;
	}

	public static String applyOnPhoneMask(String phoneNumber) {
		try {

			// 1. Validation pattern
			if (validatePhoneWithPattern(phoneNumber)) {
				StringBuilder sb = new StringBuilder();
				char[] patternChars = PHONE_PATTERN.toCharArray();
				int idx = 0;
				for (char c : patternChars) {
					if (NUMERIC_CHAR == c) {
						sb.append(phoneNumber.charAt(idx++));
					} else {
						sb.append(c);
					}
				}
				return sb.toString();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return phoneNumber;
	}

	public static boolean validatePhoneWithPattern(String phoneNumber) {
		char[] patternChars = PHONE_PATTERN.toCharArray();
		int length = 0;
		for (char c : patternChars) {
			if (NUMERIC_CHAR == c) {
				length++;
			}
		}
		if (length == phoneNumber.length())
			return true;
		else
			return false;
	}

}
