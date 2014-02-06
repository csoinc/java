package com.oyou.jsf2spring3.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ImageText draw text paragraph
 * 
 * @author ouow
 * @since Aug 30, 2012
 * 
 */
public class ImageText {
    private static final Log log = LogFactory.getLog(ImageText.class);

    private BufferedImage bufferedImage;
    private byte[] imageBytes;
    private String imageType = "png";

    private int screenWidth;
    private int screenHeight;

    private LineBreakMeasurer lineMeasurer;

    private int paragraphStart;

    private int paragraphEnd;

    private static final Hashtable<TextAttribute, Object> map = new Hashtable<TextAttribute, Object>();

    static {
        map.put(TextAttribute.FAMILY, "Serif");
        map.put(TextAttribute.SIZE, new Float(12.0));
    }

    private static AttributedString paragraphText;

    public ImageText(String text, int screenWidth, int screenHeight) {
        paragraphText = new AttributedString(text, map);
        this.setScreenWidth(screenWidth);
        this.setScreenHeight(screenHeight);

        this.bufferedImage = new BufferedImage(this.screenWidth, this.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = this.bufferedImage.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.setBackground(Color.WHITE);
        this.drawParagraph(g2d);
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

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
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

    public void drawParagraph(Graphics2D g2d) {
        if (lineMeasurer == null) {
            AttributedCharacterIterator paragraph = paragraphText.getIterator();
            paragraphStart = paragraph.getBeginIndex();
            paragraphEnd = paragraph.getEndIndex();
            FontRenderContext frc = g2d.getFontRenderContext();
            lineMeasurer = new LineBreakMeasurer(paragraph, frc);
        }

        float breakWidth = (float) this.screenWidth;
        float drawPosY = 0;
        lineMeasurer.setPosition(paragraphStart);

        while (lineMeasurer.getPosition() < paragraphEnd) {

            TextLayout layout = lineMeasurer.nextLayout(breakWidth);

            float drawPosX = layout.isLeftToRight() ? 0 : breakWidth - layout.getAdvance();

            drawPosY += layout.getAscent();

            layout.draw(g2d, drawPosX, drawPosY);

            drawPosY += layout.getDescent() + layout.getLeading();
        }
    }

}
