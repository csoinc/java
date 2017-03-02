package com.cso.jsf2spring3.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *  ImageConvertor resize the image.
 *  
 * @author ouow
 * @since Aug 16, 2012
 * 
 */
public class ImageConvertor {
	private static final Log log = LogFactory.getLog(ImageConvertor.class);

	private BufferedImage bufferedImage;
	private byte[] imageBytes;
	private String imageType = "png";

	private int imageWidth;
	private int imageHeight;
	private int screenWidth;
	private int screenHeight;
	private int scaleWidth;
	private int scaleHeight;
	private boolean ratio;
	
	public ImageConvertor(int screenWidth, int screenHeight, boolean ratio) {
		this.setScreenWidth(screenWidth);
		this.setScreenHeight(screenHeight);
		this.setRatio(ratio);
	}

	public ImageConvertor(byte[] imageBytes, int screenWidth, int screenHeight, boolean ratio) {
		this.setImageBytes(imageBytes);
		this.setScreenWidth(screenWidth);
		this.setScreenHeight(screenHeight);
		this.setRatio(ratio);
	}
	
	/**
	 * 
	 * @param image
	 * @param filename
	 */
	public void saveImageFile(String filename) {
		try {
			ImageIO.write(this.bufferedImage, this.imageType, new File(filename));
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public void scaleImage() {
		this.applyRatio();
		Image image = this.bufferedImage.getScaledInstance(this.scaleWidth, this.scaleHeight, BufferedImage.SCALE_SMOOTH);
		Graphics g = this.bufferedImage.getGraphics();
		g.drawImage(image, 0, 0, Color.white, null);
		BufferedImage scaledBufferedImage = new BufferedImage(this.scaleWidth, this.scaleHeight, BufferedImage.TYPE_INT_ARGB);
		g = scaledBufferedImage.getGraphics();
		g.drawImage(image, 0, 0, Color.white, null);
		this.bufferedImage = scaledBufferedImage;
	}
	
	
	public void scale2DImage() {
		this.applyRatio();
		BufferedImage scaledBufferedImage = new BufferedImage(this.scaleWidth, this.scaleHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = scaledBufferedImage.createGraphics();
		g.drawImage(this.bufferedImage, 0, 0, this.scaleWidth, this.scaleHeight, Color.white, null);
		this.bufferedImage = scaledBufferedImage;
	}
	
	public byte[] getImageBytes() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(this.bufferedImage, this.imageType, baos);
			baos.flush();
			this.imageBytes = baos.toByteArray();
			baos.close();
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return imageBytes;
	}

	public void setImageBytes(byte[] imageBytes) {
		this.imageBytes = imageBytes;
		try {
			BufferedImage bi = ImageIO.read(new ByteArrayInputStream(imageBytes));
			this.imageWidth = bi.getWidth();
			this.imageHeight = bi.getHeight();
			this.bufferedImage = new BufferedImage(this.imageWidth, this.imageHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = this.bufferedImage.createGraphics();
			g.drawImage(bi, 0, 0, Color.white, null);
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	private void filerRescaleOp() {
		/*
		 * Create a rescale filter op that makes the image 50% opaque. Draw the image, applying the filter
		 */
		float[] scales = { 1f, 1f, 1f, 1f };
		float[] offsets = new float[4];
		RescaleOp rop = new RescaleOp(scales, offsets, null);

		Graphics2D g2d = this.bufferedImage.createGraphics();
		g2d.drawImage(this.bufferedImage, rop, 0, 0);

	}

	public BufferedImage getBufferedImage() {
		this.filerRescaleOp();
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}

	private void applyRatio() {
		if (isRatio()) {
			double screenRatio = (double) this.screenWidth
					/ (double) screenHeight;
			double imageRatio = (double) this.imageWidth
					/ (double) this.imageHeight;
			if (screenRatio < imageRatio) {
				this.scaleHeight = (int) (this.screenWidth / imageRatio);
				this.scaleWidth = this.screenWidth;
			} else {
				this.scaleWidth = (int) (this.screenHeight * imageRatio);
				this.scaleHeight = this.screenHeight;
			}
		} else {
			this.scaleWidth = this.screenWidth;
			this.scaleHeight = this.screenHeight;
		}
	}

	/**
	 * @return the imageWidth
	 */
	public int getImageWidth() {
		return imageWidth;
	}

	/**
	 * @param imageWidth the imageWidth to set
	 */
	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	/**
	 * @return the imageHeight
	 */
	public int getImageHeight() {
		return imageHeight;
	}

	/**
	 * @param imageHeight the imageHeight to set
	 */
	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	/**
	 * @return the screenWidth
	 */
	public int getScreenWidth() {
		return screenWidth;
	}

	/**
	 * @param screenWidth the screenWidth to set
	 */
	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	/**
	 * @return the screenHeight
	 */
	public int getScreenHeight() {
		return screenHeight;
	}

	/**
	 * @param screenHeight the screenHeight to set
	 */
	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	/**
	 * @return the scaleWidth
	 */
	public int getScaleWidth() {
		return scaleWidth;
	}

	/**
	 * @param scaleWidth the scaleWidth to set
	 */
	public void setScaleWidth(int scaleWidth) {
		this.scaleWidth = scaleWidth;
	}

	/**
	 * @return the scaleHeight
	 */
	public int getScaleHeight() {
		return scaleHeight;
	}

	/**
	 * @param scaleHeight the scaleHeight to set
	 */
	public void setScaleHeight(int scaleHeight) {
		this.scaleHeight = scaleHeight;
	}

	/**
	 * @return the ratio
	 */
	public boolean isRatio() {
		return ratio;
	}

	/**
	 * @param ratio the ratio to set
	 */
	public void setRatio(boolean ratio) {
		this.ratio = ratio;
	}
	
}
