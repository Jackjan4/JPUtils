/*
 * ConstruViewer
 * Copyright 2016, Jan-Philipp Roslan, Alle Rechte vorbehalten
 */
package de.timetoerror.jputils.conf;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Properties;


/**
 *
 * * TODO: Common interface with ConfigurationFile with abstract interface
 * AppConfiguration
 *
 * @author Jackjan
 * @version: 0.9 (26.4.2016 00:40)
 */
public class ConfigurationFile {


    private Path file;

    private Properties config;
    private String comments;


    /**
     * Creates and loads a configuration with a given name in the given
     * directory If the configuration file does not exist in the given folder a
     * new, blank file will be created
     *
     * @param file
     * @param comments
     */
    public ConfigurationFile(Path file, String comments) {
        this.file = file;
        this.comments = comments;
        config = new Properties();

        if (Files.exists(file)) {

            // Read config and load props into the 'config'-Field
            readConfig();
        } else {

            // Create file if not existent
            try {
                Files.createDirectories(file.getParent());
                Files.createFile(file);
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


    public Properties getInternalProperties() {
        return config;
    }


    protected void changeFile(Path file) {
        this.file = file;

        readConfig();
    }


    private void readConfig() {
        // Reload config
        try (InputStream fis = Files.newInputStream(file, StandardOpenOption.CREATE)) {
            config.load(fis);
        } catch (IOException ex) {
            System.out.println("Error: IOexception while reading a config.cfg (Constructor)");
        }
    }


    /**
     * Returns true if the key exists inside the config. If the value of the key
     * is set to "", it also exists (and returns true).
     *
     * @param key
     * @return - true if the key exists, false if not
     */
    public boolean keyExists(String key) {
        return !(getKey(key) == null);
    }


    /**
     * Returns true if the key exists inside the config and has a value other
     * than "".
     *
     * @param key
     * @return - true if the key exists with a given value, false if not
     */
    public boolean keyAvailable(String key) {
        return !(getKey(key) == null || getKey(key).equals(""));
    }


    /**
     *
     * @return Returns a boolean that indicates if the operation was successfull
     */
    private boolean writeConfig() {
        try (OutputStream os = Files.newOutputStream(file)) {
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


    public Path getFile() {
        return file;
    }


    public String getFileName() {
        return file.getFileName().toString();
    }


    /**
     *
     * @return
     */
    public boolean isEmpty() {
        return config.isEmpty();
    }
}
