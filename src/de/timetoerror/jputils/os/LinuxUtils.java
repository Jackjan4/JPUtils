/*
 * Copyright 2016, Jan-Philipp Roslan, Alle Rechte vorbehalten
 */
package de.timetoerror.jputils.os;

import de.timetoerror.jputils.CommonUtils;
import java.io.IOException;

/**
 * Utility functions for Linux systems
 * @author Jackjan
 */
public class LinuxUtils
{

    /**
     * Gives the specific user access to the given folder.
     * Can only be used if the application was started in "sudo"
     * Is mostly useful when Java is running as sudo on Linux and the normal
     * user has to access the folder (e.g. Raspberry Pi)
     * The method is a no-op if it is not running on Linux
     *
     * @param user
     * @param path
     * @return - Indicates if the operation was successfull. It is not
     * sucessfull when the OS is not Linux or an IOException occured.
     */
    public static boolean giveUserAccess(String user, String path) {
        // Standardnutzer auf Linux Rechte auf den Ordner geben
        if (CommonUtils.getOS().equals(CommonUtils.OS_LINUX)) {
            try {
                // Giving user pi access to the folder
                Runtime.getRuntime().exec("sudo chown -R " + user + ":" + user + " " + path);
            } catch (IOException ex) {
                System.out.println("Error: IOException while executing bash command - (Giving a specific Linux user access to a folder)");
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}
