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
     * Clears an array
     *
     * @param array
     */
    public static <T> void clearArray(T[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
    }
}
