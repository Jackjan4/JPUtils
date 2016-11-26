/*
 * ConstruViewer
 * Copyright 2016, Jan-Philipp Roslan, Alle Rechte vorbehalten
 */
package de.timetoerror.jputils.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * * TODO: Common interface with ConfigurationFile with abstract interface AppConfiguration
 * @author Jackjan
 * @version: 0.9 (26.4.2016 00:40)
 */
public class ConfigurationFile
{

    private File file;
    private Properties config;
    private String comments;

    /**
     * Creates and loads a configuration with a given name in the given
     * directory
     * If the configuration file does not exist in the given folder a new, blank
     * file will be created
     *
     * @param file
     * @param comments
     */
    public ConfigurationFile(File file, String comments) {
        this.file = file;
        this.comments = comments;
        config = new Properties();

        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                config.load(fis);
            } catch (IOException ex) {
                System.out.println("Error: IOexception while reading a config.cfg (Constructor)");
            }
        } else {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException ex) {
                System.out.println("Error: IOException while creating a new config.cfg");
            }
        }
    }

    /**
     * Returns the value of a given key inside the configuration file
     *
     * @param name The name of the key which is wanted to be returned
     * @return The value of the given key or null if the key doesn't exist
     */
    public String getKey(String name) {
        return config.getProperty(name);
    }

    public boolean setKey(String key, String value) {
        config.setProperty(key, value);
        writeConfig();
        return true;
    }

    public boolean deleteKey(String key) {
        config.remove(key);
        writeConfig();
        return true;
    }

    /**
     *
     * @param key
     * @return
     */
    public boolean keyExists(String key) {
        return !(getKey(key) == null || getKey(key).equals(""));
    }

    /**
     *
     * @return Returns a boolean that indicates if the operation was successfull
     */
    private boolean writeConfig() {
        try (FileOutputStream os = new FileOutputStream(file)) {
            config.store(os, comments);
        } catch (FileNotFoundException ex) {
            System.out.println("A Configuration file could not be found while writing to it");
            return false;
        } catch (IOException ex) {
            System.out.println("Error: IO Exception while configuration writing");
            return false;
        }
        return true;
    }
}
