/*
 * Copyright 2016, Jan-Philipp Roslan, Alle Rechte vorbehalten
 */
package de.timetoerror.jputils.collections;

/**
 *
 * @author Jackjan
 */
public class ArrayUtils {

    public static void clearArray(Object[] array) {

    }

    public static void clearByteArray(byte[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = 0;
        }
    }
}
