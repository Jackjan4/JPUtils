/*
 * Copyright 2016, Jan-Philipp Roslan, Alle Rechte vorbehalten
 */
package de.janroslan.jputils.collections;

/**
 *
 * @author Jackjan
 */
public class ArrayUtils {

    /**
     * Clears an object array
     *
     * @param array
     */
    public static void clearArray(Object[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
    }

    
    // Clears a byte array
    public static void clearByteArray(byte[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = 0;
        }
    }
}
