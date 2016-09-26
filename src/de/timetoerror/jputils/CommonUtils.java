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
public class CommonUtils
{

    public static final String OS_WINDOWS = "windows";
    public static final String OS_MAC = "wac";
    public static final String OS_LINUX = "linux";

    /**
     * Prints out the OS the application is running on as String
     * "windows", "linux", "mac"
     *
     * @return
     */
    public static String getOS() {
        String str = System.getProperty("os.name");

        if (str.contains("win") || str.contains("Win")) {
            return "windows";
        } else if (str.contains("nix") || str.contains("nux")) {
            return "linux";
        } else if (str.contains("Mac")) {
            return "mac";
        }
        return null;
    }
    

    /**
     * Returns the application path as String if possible
     *
     * @param c The method needs a context to be executed. 
     * To prevent the need of an instance of CommonUtils a class can be given to get its path.
     * Please notice that the the method returns the folder on the hard disk of the class, so
     * if your class 'c' is inside the lib folderv for example, the returned path will link to this!
     * It is recommend to use the main class of the application.
     * @return The absolute path of the class as String or null if the
     * SecurityManager blocks the access
     */
    public static String getAppPath(Class c) {
        String result;
        try {
            result = c.getProtectionDomain().getCodeSource().getLocation().getPath();
        } catch (SecurityException ex) {
            return null;
        }
        return result;
    }

    /**
     * Returns the application path as File if possible
     *
     * @param c - The method needs a context to be executed. To prevent the need
     * of an instance of CommonUtils a class can be
     * @return
     */
    public static File getAppPathAsFile(Class c) {
        String res = getAppPath(c);
        if (res != null) {
            return new File(res);
        } else {
            return null;
        }

    }
}
