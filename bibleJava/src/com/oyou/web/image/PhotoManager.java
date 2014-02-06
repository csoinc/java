package com.oyou.web.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.common.util.StringHelper;
import com.oyou.web.swing.ImageConstants;

/**
 * 
 * @author	Owen Ou
 * Apr 25, 2007
 */
public class PhotoManager extends JFrame {
	public static final long serialVersionUID = 1;
	private static final Log log = LogFactory.getLog(PhotoManager.class);
	private Graphics graphics;
	private Toolkit toolkit;
	private BufferedImage bufferedImage;
	private String uploadName;
	private String photoName;
	private String iconName;
	private Image uploadImage;
	private Image photoImage;
	private Image iconImage;
	private boolean photoScaled;
	private int scaledWidth;
	private int width;
	private int height;
	private byte[] photoBytes;
	private MediaTracker mediaTracker;

	public PhotoManager(String uploadName, String photoName, String iconName) {
		this(uploadName, photoName, iconName, ImageConstants.PHOTO_SCALED, ImageConstants.PHOTO_SCALED_WIDTH);
	}

	public PhotoManager(String uploadName, String photoName, String iconName, boolean photoScaled, int scaledWidth) {
		this.setUploadName(uploadName);
		this.setPhotoName(photoName);
		this.setIconName(iconName);
		this.setScaledWidth(scaledWidth);
		this.setWidth(scaledWidth);
		this.setHeight(scaledWidth);
		this.setPhotoScaled(photoScaled);
		toolkit = Toolkit.getDefaultToolkit();
		mediaTracker = new MediaTracker(this);
		if (StringHelper.isNotEmpty(this.uploadName)) {
			uploadImage = toolkit.createImage(this.uploadName);
		}
		if (StringHelper.isNotEmpty(this.iconName)) {
			iconImage = toolkit.createImage(this.iconName);
		}
	}

	private void drawIcon(Graphics g) {
		log.debug(">>drawIcon()");
		g.drawString(ImageConstants.LOGO_TEXT, 5, 10);
		if (StringHelper.isNotEmpty(this.iconName)) {
			if (iconImage == null)
				iconImage = toolkit.createImage(this.iconName);
			g.drawImage(iconImage, 0, 0, this);
			mediaTracker.addImage(iconImage, 2);
			try {
				mediaTracker.waitForAll();
			} catch (InterruptedException e) {
				return;
			} finally {
				if (mediaTracker.isErrorAny()) {
					log.error("Error in draw icon.");
				}
			}
		}
		log.debug("<<drawIcon()");
	}

	private void drawPhoto(Graphics g) {
		log.debug(">>drawPhoto()");
		this.prepareImage(photoImage, this);
		g.drawImage(photoImage, 0, 0, this);
		mediaTracker.addImage(photoImage, 1);
		try {
			mediaTracker.waitForAll();
		} catch (InterruptedException e) {
			return;
		} finally {
			if (mediaTracker.isErrorAny()) {
				log.error("Error in draw photo.");
			}
		}
		log.debug("<<drawPhoto()");
	}

	private void drawUpload(Graphics g) {
		log.debug(">>drawUpload()");
		this.prepareImage(uploadImage, this);
		g.drawImage(uploadImage, 0, 0, this);
		mediaTracker.addImage(uploadImage, 0);
		try {
			mediaTracker.waitForAll();
		} catch (InterruptedException e) {
			return;
		} finally {
			if (mediaTracker.isErrorAny()) {
				log.error("Error in draw photo.");
			}
		}
		log.debug("<<drawUpload()");
	}

	public int getHeight() {
		return height;
	}

	public String getIconName() {
		return iconName;
	}

	public byte[] getPhotoBytes() {
		return photoBytes;
	}

	public String getPhotoName() {
		return photoName;
	}

	public int getScaledWidth() {
		return scaledWidth;
	}

	public String getUploadName() {
		return uploadName;
	}

	public int getWidth() {
		return width;
	}

	public boolean isPhotoScaled() {
		return photoScaled;
	}

	public void paint(Graphics g) {
		this.repaint();
	}

	public void saveImage() {
		if (this.bufferedImage == null) {
			log.debug("==create buffered image.");
			this.bufferedImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
			this.graphics = bufferedImage.getGraphics();
			Font font = new Font("font", Font.PLAIN, 6);
			this.graphics.setFont(font);
			this.graphics.setColor(Color.orange);
			this.drawUpload(this.graphics);
			this.width = uploadImage.getWidth(this);
			this.height = uploadImage.getHeight(this);
			log.debug("==width " + width);
			log.debug("==height " + height);
			if (this.photoScaled) {
				if (this.width > this.scaledWidth) {
					photoImage = uploadImage.getScaledInstance(this.scaledWidth, -1, Image.SCALE_SMOOTH);
					log.debug("==photo scaled to " + this.scaledWidth);
					this.drawPhoto(this.graphics);
					this.width = photoImage.getWidth(this);
					this.height = photoImage.getHeight(this);
					log.debug("==width " + width);
					log.debug("==height " + height);
				} else {
					log.debug("==no need scaled");
					photoImage = uploadImage;
				}
				this.bufferedImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
				this.graphics = bufferedImage.getGraphics();
				this.graphics.setFont(font);
				this.graphics.setColor(Color.orange);
				this.drawPhoto(this.graphics);
			} else {
				photoImage = uploadImage;
				this.drawPhoto(this.graphics);
			}
			this.drawIcon(this.graphics);
			this.graphics.drawImage(this.bufferedImage, 0, 0, this);
		}
		if (this.bufferedImage != null)
			saveImage(this.bufferedImage, this.photoName);
	}

	public void saveImage(BufferedImage image, String filename) {
		try {
			//BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filename));
			//JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder((OutputStream) out);
			//encoder.encode(image);
			//out.flush();
			//out.close();
            ImageIO.write(image, "jpeg", new File(filename));
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} finally {
			this.bufferedImage = null;
		}
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public void setPhotoBytes(byte[] photoBytes) {
		this.photoBytes = photoBytes;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public void setPhotoScaled(boolean photoScaled) {
		this.photoScaled = photoScaled;
	}

	public void setScaledWidth(int scaledWidth) {
		this.scaledWidth = scaledWidth;
	}

	public void setUploadName(String uploadName) {
		this.uploadName = uploadName;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
