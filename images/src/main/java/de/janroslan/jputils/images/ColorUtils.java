package de.janroslan.jputils.images;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jackjan
 */
public class ColorUtils {

    
    public static int toIntColor(byte r, byte g, byte b) {
        int c = (int) r;
        c = (c << 8) | g;
        c = (c << 8) | b;
        return c;
    }

    
    public static byte red(int c) {
        return (byte) (c >> 16 & 0xFF);
    }

    
    public static byte green(int c) {
        return (byte) (c >> 8 & 0xFF);
    }

    
    public static byte blue(int c) {
        return (byte) (c & 0xFF);
    }
}
