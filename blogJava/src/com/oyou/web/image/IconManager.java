package com.oyou.web.image;

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
public class IconManager extends JFrame {
	public static final long serialVersionUID = 1;
	private static final Log log = LogFactory.getLog(IconManager.class);
	private Graphics graphics;
	private Toolkit toolkit;
	private BufferedImage bufferedImage;
	private String uploadName;
	private String iconName;
	private Image uploadImage;
	private Image iconImage;
	private boolean iconScaled;
	private int scaledWidth;
	private int width;
	private int height;
	private MediaTracker mediaTracker;

	public IconManager(String uploadName, String iconName) {
		this(uploadName, iconName, ImageConstants.ICON_SCALED, ImageConstants.ICON_SCALED_WIDTH);
	}

	public IconManager(String uploadName, String iconName, boolean iconScaled, int scaledWidth) {
		this.setUploadName(uploadName);
		this.setIconName(iconName);
		this.setScaledWidth(scaledWidth);
		this.setWidth(scaledWidth);
		this.setHeight(scaledWidth);
		this.setIconScaled(iconScaled);
		toolkit = Toolkit.getDefaultToolkit();
		mediaTracker = new MediaTracker(this);
		if (StringHelper.isNotEmpty(this.uploadName)) {
			uploadImage = toolkit.createImage(this.uploadName);
		}
	}

	private void drawIcon(Graphics g) {
		log.debug(">>drawPhoto()");
		this.prepareImage(iconImage, this);
		g.drawImage(iconImage, 0, 0, this);
		mediaTracker.addImage(iconImage, 1);
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

	public int getScaledWidth() {
		return scaledWidth;
	}

	public String getUploadName() {
		return uploadName;
	}

	public int getWidth() {
		return width;
	}

	public boolean isIconScaled() {
		return iconScaled;
	}

	public void paint(Graphics g) {
		this.repaint();
	}

	public void saveImage() {
		if (this.bufferedImage == null) {
			log.debug("==create buffered image.");
			this.bufferedImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
			this.graphics = bufferedImage.getGraphics();
			this.drawUpload(this.graphics);
			this.width = uploadImage.getWidth(this);
			this.height = uploadImage.getHeight(this);
			log.debug("==width " + width);
			log.debug("==height " + height);
			if (this.iconScaled) {
				if (this.width > this.scaledWidth) {
					iconImage = uploadImage.getScaledInstance(this.scaledWidth, -1, Image.SCALE_SMOOTH);
					log.debug("==icon scaled to " + this.scaledWidth);
					this.drawIcon(this.graphics);
					this.width = iconImage.getWidth(this);
					this.height = iconImage.getHeight(this);
					log.debug("==width " + width);
					log.debug("==height " + height);
				} else {
					log.debug("==no need scaled");
					iconImage = uploadImage;
				}
				this.bufferedImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
				this.graphics = bufferedImage.getGraphics();
				this.drawIcon(this.graphics);
			} else {
				iconImage = uploadImage;
				this.drawIcon(this.graphics);
			}
			this.graphics.drawImage(this.bufferedImage, 0, 0, this);
		}
		if (this.bufferedImage != null)
			saveImage(this.bufferedImage, this.iconName);
	}

	public void saveImage(BufferedImage image, String filename) {
		try {
			//BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filename));
			//javax.imageio.  JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder((OutputStream) out);
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

	public void setIconScaled(boolean iconScaled) {
		this.iconScaled = iconScaled;
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
