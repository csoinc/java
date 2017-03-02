package com.cso.jsf2spring3.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author ouow
 * @since Aug 15, 2012
 * 
 */
public class ImageFrame extends JFrame {
	private static final long serialVersionUID = -7292502069961493250L;
	private static final Log log = LogFactory.getLog(ImageFrame.class);
	private static final int DEFAULT_SCREEN_WIDTH = 500;
	private static final int DEFAULT_SCREEN_HEIGHT = 475;

	private Graphics2D graphics;
	private Toolkit toolkit;
	private BufferedImage bufferedImage;
	private Image image;
	private int imageWidth;
	private int imageHeight;
	private int screenWidth;
	private int screenHeight;
	private int scaleWidth;
	private int scaleHeight;
	private boolean ratio;

	private MediaTracker mediaTracker;

	public ImageFrame(byte[] imageBytes, boolean ratio) {
		initBuffer(imageBytes, DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT,
				ratio);
	}

	public ImageFrame(byte[] imageBytes, int screenWidth, int screenHeight,
			boolean ratio) {
		initBuffer(imageBytes, screenWidth, screenHeight, ratio);
	}

	public void initBuffer(byte[] imageBytes, int screenWidth,
			int screenHeight, boolean ratio) {
		this.toolkit = Toolkit.getDefaultToolkit();
		this.mediaTracker = new MediaTracker(this);
		setImage(this.toolkit.createImage(imageBytes));
		setScreenWidth(screenWidth);
		setScreenHeight(screenHeight);
		setRatio(ratio);
	}

	private void loadImage(Graphics2D g) {
		log.debug(">>loadImage()");
		this.prepareImage(this.image, this);
		g.drawImage(this.image, 0, 0, null);
		// g.drawString("Ontario", 10, 10);
		this.mediaTracker.addImage(this.image, 0);
		try {
			this.mediaTracker.waitForAll();
		} catch (InterruptedException e) {
			return;
		} finally {
			if (this.mediaTracker.isErrorAny()) {
				log.error("Error in draw image.");
			}
		}
		log.debug("<<loadImage()");
	}

	@Override
	public final void paint(Graphics g) {
		this.repaint();
	}

	public final BufferedImage scale2DImage() {
		if (this.bufferedImage == null) {
			log.debug("==create buffered image.");
			this.bufferedImage = new BufferedImage(this.screenWidth,
					this.screenHeight, BufferedImage.TYPE_INT_RGB);
		}
		this.graphics = this.bufferedImage.createGraphics();
		this.loadImage(this.graphics);
		this.imageWidth = this.image.getWidth(this);
		this.imageHeight = this.image.getHeight(this);
		log.debug("==imageWidth " + this.imageWidth);
		log.debug("==imageHeight " + this.imageHeight);
		
		this.applyRatio();

		this.bufferedImage = new BufferedImage(this.scaleWidth, this.scaleHeight,
				BufferedImage.TYPE_INT_RGB);
		this.graphics = this.bufferedImage.createGraphics();
		Font font = new Font("Serif", Font.BOLD, 8);
		this.graphics.setFont(font);
		this.graphics.setColor(Color.orange);
		this.graphics.drawImage(this.image, 0, 0, this.scaleWidth, this.scaleHeight, this);
		this.graphics.drawImage(this.bufferedImage, 0, 0, this.scaleWidth, this.scaleHeight, this);
		return this.bufferedImage;
	}

	/**
	 * 
	 * @param sWidth
	 * @return
	 */
	public final BufferedImage scaleImage() {
		if (this.bufferedImage == null) {
			log.debug("==create buffered image.");
			this.bufferedImage = new BufferedImage(this.screenWidth, this.screenHeight,
					BufferedImage.TYPE_INT_RGB);
		}
		this.graphics = this.bufferedImage.createGraphics();
		this.loadImage(this.graphics);
		this.imageWidth = this.image.getWidth(this);
		this.imageHeight = this.image.getHeight(this);
		log.debug("==imageWidth " + this.imageWidth);
		log.debug("==imageHeight " + this.imageHeight);
		
		this.applyRatio();
		
		this.image = this.image.getScaledInstance(this.scaleWidth, this.scaleHeight,
				Image.SCALE_SMOOTH);

		this.loadImage(this.graphics);

		this.bufferedImage = new BufferedImage(this.scaleWidth, this.scaleHeight,
				BufferedImage.TYPE_INT_RGB);
		this.graphics = this.bufferedImage.createGraphics();
		Font font = new Font("Serif", Font.BOLD, 8);
		this.graphics.setFont(font);
		this.graphics.setColor(Color.orange);
		this.loadImage(this.graphics);
		this.graphics.drawImage(this.bufferedImage, 0, 0, this);
		return this.bufferedImage;
	}


	/**
	 * 
	 * @param bImage
	 * @param filename
	 */
	public final void saveImageFile(BufferedImage bImage, String filename) {
		try {
			ImageIO.write(bImage, ImageFrameUtil.IMAGE_TYPE, new File(filename));
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} finally {
			this.bufferedImage = null;
		}
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
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * @return the imageWidth
	 */
	public int getImageWidth() {
		return imageWidth;
	}

	/**
	 * @param imageWidth
	 *            the imageWidth to set
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
	 * @param imageHeight
	 *            the imageHeight to set
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
	 * @param screenWidth
	 *            the screenWidth to set
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
	 * @param screenHeight
	 *            the screenHeight to set
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
	 * @param scaleWidth
	 *            the scaleWidth to set
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
	 * @param scaleHeight
	 *            the scaleHeight to set
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
	 * @param ratio
	 *            the ratio to set
	 */
	public void setRatio(boolean ratio) {
		this.ratio = ratio;
	}

}
