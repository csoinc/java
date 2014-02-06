package com.oyou.jsf2spring3.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author ouow
 * @since Aug 17, 2012
 */
public final class ImageConvertorUtil {
	private static final Log log = LogFactory.getLog(ImageConvertorUtil.class);
	public static final int FLASH_IMAGE_WIDTH = 500;
	public static final int FLASH_IMAGE_HEIGHT = 475;
	public static final int THUMBNAIL_IMAGE_WIDTH = 100;
	public static final int THUMBNAIL_IMAGE_HEIGHT = 95;
	public static final String IMAGE_TYPE = "png";

	private ImageConvertorUtil() {
	}

	/**
	 * 
	 * @param filename
	 * @param flashfile
	 * @return
	 */
	public static boolean convertFlash(String filename, String flashfile) {
		byte[] fileBytes = getFileBytes(filename);
		if (fileBytes != null) {
			long startTime = Calendar.getInstance().getTimeInMillis();
			ImageConvertor ic = new ImageConvertor(fileBytes, FLASH_IMAGE_WIDTH, FLASH_IMAGE_HEIGHT, true);
			ic.setImageType(IMAGE_TYPE);
			ic.scale2DImage();
			ic.saveImageFile(flashfile);
			long endTime = Calendar.getInstance().getTimeInMillis();
			log.debug("Flash:Time cost = " + (endTime - startTime) + " millis ");
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param filename
	 * @param thumbnailfile
	 * @return
	 */
	public static boolean convertThumbnail(String filename, String thumbnailfile) {
		byte[] fileBytes = getFileBytes(filename);
		if (fileBytes != null) {
			long startTime = Calendar.getInstance().getTimeInMillis();
			ImageConvertor ic = new ImageConvertor(fileBytes, THUMBNAIL_IMAGE_WIDTH, THUMBNAIL_IMAGE_HEIGHT, false);
			ic.setImageType(IMAGE_TYPE);
			ic.scale2DImage();
			ic.saveImageFile(thumbnailfile);
			long endTime = Calendar.getInstance().getTimeInMillis();
			log.debug("Thumbnail:Time cost = " + (endTime - startTime) + " millis ");
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param fileContent
	 * @return
	 */
	public static byte[] convertFlash(byte[] imageBytes) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		ImageConvertor ic = new ImageConvertor(imageBytes, FLASH_IMAGE_WIDTH, FLASH_IMAGE_HEIGHT, true);
		ic.setImageType(IMAGE_TYPE);
		ic.scale2DImage();
		byte[] iBytes = ic.getImageBytes();
		long endTime = Calendar.getInstance().getTimeInMillis();
		log.debug("Flash:Time cost = " + (endTime - startTime) + " millis ");
		return iBytes;
	}

	/**
	 * 
	 * @param fileContent
	 * @return
	 */
	public static byte[] convertThumbnail(byte[] imageBytes) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		ImageConvertor ic = new ImageConvertor(imageBytes, THUMBNAIL_IMAGE_WIDTH, THUMBNAIL_IMAGE_HEIGHT, false);
		ic.setImageType(IMAGE_TYPE);
		ic.scale2DImage();
		byte[] iBytes = ic.getImageBytes();
		long endTime = Calendar.getInstance().getTimeInMillis();
		log.debug("Thumbnail:Time cost = " + (endTime - startTime) + " millis ");
		return iBytes;
	}

	/**
	 * 
	 * @param filename
	 * @return
	 */
	public static byte[] getFileBytes(String filename) {
		try {
			long startTime = Calendar.getInstance().getTimeInMillis();
			File file = new File(filename);
			FileInputStream fin = new FileInputStream(file);
			byte[] fileBytes = new byte[(int) file.length()];
			fin.read(fileBytes);
			long endTime = Calendar.getInstance().getTimeInMillis();
			log.debug("getImageBytes:Time cost = " + (endTime - startTime) + " millis ");
			return fileBytes;
		} catch (FileNotFoundException fnfe) {
			log.error(fnfe.getMessage());
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			log.error(ioe.getMessage());
			ioe.printStackTrace();
		}
		return null;
	}
}
