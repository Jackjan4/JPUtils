/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.janroslan.jputils.logging;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author jackjan
 */
public class LoggerUtils {

    /**
     *
     * @param logger
     * @param file
     * @param removeConsole
     * @param limit
     * @param count
     * @param append
     */
    public static void registerLoggerToFile(Logger logger, String file, boolean removeConsole, int limit, int count, boolean append) {

        logger.setUseParentHandlers(!removeConsole);

        if (!Files.exists(Paths.get(file))) {
            try {
                Files.createFile(Paths.get(file));
            } catch (IOException ex) {

            }
        }

        FileHandler fh;

        try {

            // This block configure the logger with handler and formatter  
            fh = new FileHandler(file, limit, count, append);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param logger
     * @param file
     * @param removeConsole
     * @param append
     */
    public static void registerLoggerToFile(Logger logger, String file, boolean removeConsole, boolean append) {

        logger.setUseParentHandlers(!removeConsole);

        if (!Files.exists(Paths.get(file))) {
            try {
                Files.createFile(Paths.get(file));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        
        FileHandler fh;

        try {

            // This block configure the logger with handler and formatter  
            fh = new FileHandler(file, append);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
