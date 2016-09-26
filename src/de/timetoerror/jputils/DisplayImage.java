/*
 * Copyright 2016, Jan-Philipp Roslan, Alle Rechte vorbehalten
 */
package de.timetoerror.jputils;

import java.awt.image.BufferedImage;

/**
 *
 * @author Jackjan
 */
public class DisplayImage
{
    private BufferedImage image;
    private boolean painted;

    public DisplayImage(BufferedImage image, boolean painted) {
        this.image = image;
        this.painted = painted;
    }

    
    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public boolean isPainted() {
        return painted;
    }

    public void setPainted(boolean painted) {
        this.painted = painted;
    }
    
    
}
