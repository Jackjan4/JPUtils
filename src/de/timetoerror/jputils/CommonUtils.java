/*
 * Copyright 2016 - Jan-Philipp Roslan - Alle Rechte vorbehalten
 * Each line should be prefixed with  * 
 */
package de.timetoerror.jputils;

import java.io.File;

/**
 *
 * @author Jackjan
 */
public class CommonUtils {

    public static final String OS_WINDOWS = "windows";
    public static final String OS_MAC = "mac";
    public static final String OS_LINUX = "linux";
    public static final String OS_UNIX = "unix";

    /**
     * Prints out the OS the application is running on as String
     *
     *
     * @return - "windows", "linux", "mac", "unix"
     */
    public static String getOS() {
        String str = System.getProperty("os.name");

        if (str.contains("win") || str.contains("Win")) {
            return "windows";
        } else if (str.contains("nux")) {
            return "linux";
        } else if (str.contains("nix")) {
            return "unix";
        } else if (str.contains("Mac") || str.contains("mac")) {
            return "mac";
        }
        return null;
    }

    /**
     * Returns the class path on the harddisk as String
     *
     * @param c The method needs a context to be executed. To prevent the need
     * of an instance of CommonUtils a class can be given to get its path.
     * Please notice that the the method returns the folder on the hard disk of
     * the class, so if your class 'c' is inside the lib folder for example, the
     * returned path will link to this! It is recommend to use the main class of
     * the application to get the application path
     * @return The absolute path of the class as String
     */
    public static String getClassPath(Class c) {
        String result;
        
        result = c.getProtectionDomain().getCodeSource().getLocation().getPath();
        
        return result;
    }

    /**
     * Returns the cass path as File
     *
     * @param c - The method needs a context to be executed. To prevent the need
     * of an instance of CommonUtils a class can be
     * @return
     */
    public static File getClassPathAsFile(Class c) {
        String res = getClassPath(c);
        if (res != null) {
            return new File(res);
        } else {
            return null;
        }

    }
}
