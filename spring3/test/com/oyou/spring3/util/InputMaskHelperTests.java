package com.oyou.spring3.util;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author ouow(Owen)
 *
 * @date 2012-08-01
 */
public class InputMaskHelperTests extends TestCase{

	Logger log = LoggerFactory.getLogger(InputMaskHelperTests.class);
	

	@Override
	public void setUp() {
	}
	
	@SuppressWarnings("unchecked")
	public void ntestFilterOutPhoneMask() throws Exception {
		String phoneWithMask = "(905) 887-3356";
		log.debug("Before:" + phoneWithMask);

		String phoneNumber = InputMaskHelper.filterOutPhoneMask(phoneWithMask);
		log.debug("After:" + phoneNumber);
		
		assertEquals("9058873356", phoneNumber);
	}

	@SuppressWarnings("unchecked")
	public void testApplyOnPhoneMask() throws Exception {
		String phoneWithoutMask = "9058873356";
		log.debug("Before:" + phoneWithoutMask);

		String phoneNumber = InputMaskHelper.applyOnPhoneMask(phoneWithoutMask);
		log.debug("After:" + phoneNumber);
		
		assertEquals("(905) 887-3356", phoneNumber);
	}
	
	
}
