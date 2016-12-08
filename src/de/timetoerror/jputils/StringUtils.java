/*
 * Copyright 2016 - Jan-Philipp Roslan - Alle Rechte vorbehalten
 * Each line should be prefixed with  * 
 */
package de.timetoerror.jputils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Jackjan
 */
public class StringUtils
{

    public final static Character[] QWERTZ = {'Y', 'y','Z','z', '_', '-'};
    public final static Character[] QWERTY = {'Z', 'z','Y','y', '/', 'ß',};

    
    /**
     * Scans a string for integers via RegEx.
     * @param source
     * @return 
     */
    public static List<Integer> scanforIntegers(String source) {
        final ArrayList<Integer> result = new ArrayList<>();

        final Pattern integerPattern = Pattern.compile("(\\-?\\d+)");
        final Matcher matched = integerPattern.matcher(source);
        while (matched.find()) {
            result.add(Integer.valueOf(matched.group()));
        }
        return result;
    }
    
    /**
     * Scans a string for first integer occurence via RegEx.
     * @param source
     * @return 
     */
    public static int scanForFirstInteger(String source)
    {
        final ArrayList<Integer> result = new ArrayList<>();

        final Pattern integerPattern = Pattern.compile("(\\-?\\d+)");
        final Matcher matched = integerPattern.matcher(source);
        while (matched.find()) {
            return Integer.valueOf(matched.group());
        }
        return -1;
    }

    /**
     * Fills a String up to a given length with the filler key
     * Signifcatly more performance than String.format
     *
     * @param filler
     * @param ex
     * @param count - The count of chars the string should have at the end
     * @return
     */
    public static String leftfillTo(String filler, String ex, int count) {
        StringBuilder builder = new StringBuilder(ex);
        
        // Using reverse to append because StringBuilder.insert() would call System.arrayCopy() everytime
        builder.reverse();
        while (builder.length() != count) {
            builder.append(filler);
        }

        return builder.reverse().toString();
    }

    /**
     * 
     * @param text
     * @param from
     * @param to
     * @return 
     */
//    public static char switchLayout(char c, Character[] from, Character[] to) {
//
//            int pos = ArrayUtils.arrayIndexOf(from, c);
//
//            if (ArrayUtils.arrayIndexOf(from, c) >= 0) {
//                return to[pos];
//            } else {
//                return c;
//            }
//    }
}
