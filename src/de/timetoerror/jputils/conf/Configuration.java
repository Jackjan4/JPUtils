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
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import de.timetoerror.jputils.CommonUtils;

/**
 * A single, self-managing configuration file (config.cfg) which you can use to
 * save configuration information between application sessions.
 *
 * @author Jackjan
 * @version: 0.9 (26.4.2016 00:40)
 */
public class Configuration {

    private static Configuration instance;

    private boolean isOn = false;
    private final String file;
    private Properties config;
    
    // os-dependent?
    private static boolean os;
    
    // user dependent?
    private static boolean user;
    
    /**
     * Returns the value of a given key inside the configuration file
     *
     * @param name The name of the key which is wanted to be returned
     * @return The value of the given key or null if the key doesn't exist
     */
    private Configuration(boolean osDependent) {
        if (osDependent) {
            file = "config_" + CommonUtils.getOS() + ".cfg";
        } else {
            file = "config.cfg";
        }
    }

    public static Configuration getInstance(boolean osDependent) {
        if (os != osDependent || instance == null) {
            os = osDependent;
            instance = new Configuration(osDependent);
        }
        return instance;
    }
    
    
    
    

    /**
     * Gets the value of the given key. If the key doesn't exist, null will be
     * returned
     *
     * @param name
     * @return
     */
    public String getKey(String name) {
        if (!isOn) {
            readConfig();
            isOn = true;
        }
        
        String prop = config.getProperty(name);
        
        return (prop == null || "".equals(prop) ? null : prop);
    }

    public String getKey(String defaultVal, String name) {
        String key = getKey(name);

        if (key != null) {
            return key;
        } else {
            return defaultVal;
        }
    }

    public boolean setKey(String key, String value) {
        if (!isOn) {
            readConfig();
            isOn = true;
        }

        if (key == null || key.equals("") || value == null || value.equals("")) {
            return false;
        }
        config.setProperty(key, value);
        writeConfig();
        return true;
    }

    public boolean deleteKey(String key) {
        if (!isOn) {
            readConfig();
            isOn = true;
        }
        config.remove(key);
        writeConfig();
        return true;
    }

    // 
    public boolean exists() {

        File f = new File(new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParent() + "/configs/" + file);
        return f.exists();
    }

    // 
    public boolean keyExists(String key) {
        if (getKey(key) == null || getKey(key).equals("")) {
            return false;
        }
        return true;
    }

    private boolean readConfig() {
        InputStream is = null;
        try {
            File f = new File(new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParent() + File.separator + "configs" + File.separator + file);
            if (!f.exists()) {
                System.out.println("No config.cfg found");
                f.getParentFile().mkdirs();
                f.createNewFile();
                System.out.println("New config.cfg created");
            }
            is = new FileInputStream(f);
            config = new Properties();
            config.load(is);

        } catch (FileNotFoundException ex) {
            System.out.println("Error: Configuration-file could not be found");
            return false;

        } catch (IOException ex) {
            System.out.println("Error: IO exception occured while creating config file");
            ex.printStackTrace();
            return false;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    System.out.println("Error while closing inputstream for config");
                }
            }
        }
        return true;
    }

    /**
     *
     * @return Returns a boolean that indicates if the operation was successfull
     */
    private boolean writeConfig() {
        OutputStream os = null;
        try {
            os = new FileOutputStream(new File(new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParent() + "/configs/" + file));
            config.store(os, "Application configuration");
        } catch (FileNotFoundException ex) {
            System.out.println("Config.cfg could not be found");
            return false;
        } catch (IOException ex) {
            System.out.println("Error: IO Exception while configuration writing");
            return false;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException ex) {
                }
            }
        }
        return true;
    }
}
