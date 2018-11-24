/*
 * Copyright 2016 - Jan-Philipp Roslan - Alle Rechte vorbehalten
 * Each line should be prefixed with  * 
 */
package de.janroslan.jputils.images;


import de.janroslan.jputils.common.MathUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Jackjan
 */
public class ImageUtils {

    public static boolean DEBUG = false;

    /**
     * Copies a BufferedImage via creating a new image and drawing the old into
     * the new
     *
     * @param bi
     * @return
     */
    public static BufferedImage deepCopy(BufferedImage bi) {
        long startTime = System.nanoTime();
        BufferedImage newImg = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());

        Graphics2D g = newImg.createGraphics();
        g.drawImage(bi, 0, 0, null);
        g.dispose();

        if (DEBUG) {
            System.out.println((System.nanoTime() - startTime) / 1000000.0);
        }
        return newImg;
    }

    /**
     * Copies a BufferedImage via its ColorModel and Raster
     *
     * @param bi
     * @return
     */
    public static BufferedImage deepCopy2(BufferedImage bi) {
        long startTime = System.nanoTime();
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);

        if (DEBUG) {
            System.out.println((System.nanoTime() - startTime) / 1000000.0);
        }
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    /**
     * Replaces a color in an image with the given new color
     *
     * @param img
     * @param old
     * @param newC
     */
    public static void replaceColor(BufferedImage img, Color old, Color newC) {
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color c = new Color(img.getRGB(i, j));
                if (c.equals(old)) {
                    img.setRGB(i, j, newC.getRGB());
                }
            }
        }
    }

    public static void setDebug(boolean debug) {
        DEBUG = debug;
    }

    public static byte[] getPixels(BufferedImage img) {
        return ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
    }

    public static int[] getRGB(byte[] imgPixel) {
        return null;
    }

    public int getIntFromColor(int Red, int Green, int Blue) {
        Red = (Red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        Green = (Green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
        Blue = Blue & 0x000000FF; //Mask out anything not blue.

        return 0xFF000000 | Red | Green | Blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }

    /**
     *
     * @param imageData
     * @return
     */
    public static BufferedImage createImageFromBytes(byte[] imageData) {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        try {
            return ImageIO.read(bais);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a tile image of the given images. The output image is trying to
     * be as quadratic as possible. If the count of the input images is prime
     * (and so not dividble for a rectangular image) the ouput image will be in
     * only one line
     *
     * @param files
     * @return
     */
    public static BufferedImage tile(List<File> files) {
        // The amount of rows & columns
        int imgX = 0, imgY = 0;
        BufferedImage result;

        // Calculate tiling ratio
        int[] ratio = MathUtils.equalSquare(files.size());
        imgX = ratio[0];
        imgY = ratio[1];

        // Read images
        BufferedImage[] images = new BufferedImage[files.size()];
        int bigX = 0;
        int bigY = 0;
        for (int i = 0; i < images.length; i++) {

            try {
                images[i] = ImageIO.read(files.get(i));
                if (images[i].getWidth() > bigX) {
                    bigX = images[i].getWidth();
                }
                if (images[i].getHeight() > bigY) {
                    bigY = images[i].getHeight();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
        }

        // Writing new image
        result = new BufferedImage(imgX * bigX, imgY * bigY, BufferedImage.TYPE_INT_ARGB);
        Graphics g = result.getGraphics();
        for (int i = 0; i < images.length; i++) {
            int x = i % imgX;
            int y = i / imgX;
            g.drawImage(images[i], x * bigX, y * bigY, null);
        }

        return result;
    }
}
