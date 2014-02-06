package com.oyou.jsf2spring3.util;

import java.util.Calendar;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ImageFrameTest extends TestCase {
	private static final Log log = LogFactory.getLog(ImageFrameTest.class);

	// For testing purpose only, will removed when production
	public static final String TEST_IMAGE_1 = "c:/temp/hp_pic_1.jpg";
	public static final String TEST_FLASH_IMAGE_1 = "c:/temp/hp_pic_1_flash.jpg";
	public static final String TEST_THUMBNAIL_IMAGE_1 = "c:/temp/hp_pic_1_thumb.jpg";

	public static final String TEST_IMAGE_2 = "c:/temp/hp_pic_2.jpg";
	public static final String TEST_FLASH_IMAGE_2 = "c:/temp/hp_pic_2_flash.jpg";
	public static final String TEST_THUMBNAIL_IMAGE_2 = "c:/temp/hp_pic_2_thumb.jpg";

	@Override
	public void setUp() {
	}

	public void testImage1Flash() {
		try {
			long startTime = Calendar.getInstance().getTimeInMillis();

			if (!ImageFrameUtil.convertFlash(TEST_IMAGE_1, TEST_FLASH_IMAGE_1)) {
				log.error("error");
			}

			if (!ImageFrameUtil.convertThumbnail(TEST_IMAGE_1, TEST_THUMBNAIL_IMAGE_1)) {
				log.error("error");
			}

			long endTime = Calendar.getInstance().getTimeInMillis();
			log.debug("Thumbnail:Time cost = " + (endTime - startTime)
					+ " millis ");

		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public void testImage2Flash() {
		try {
			long startTime = Calendar.getInstance().getTimeInMillis();

			if (!ImageFrameUtil.convertFlash(TEST_IMAGE_2, TEST_FLASH_IMAGE_2)) {
				log.error("error");
			}

			if (!ImageFrameUtil.convertThumbnail(TEST_IMAGE_2, TEST_THUMBNAIL_IMAGE_2)) {
				log.error("error");
			}

			long endTime = Calendar.getInstance().getTimeInMillis();
			log.debug("Thumbnail:Time cost = " + (endTime - startTime)
					+ " millis ");

		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

	}

}
