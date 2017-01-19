package de.timetoerror.jputils.conf;

import java.io.File;
import java.util.Properties;
import de.timetoerror.jputils.CommonUtils;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A single, self-managing configuration file (config.cfg) which you can use to
 * save configuration information between application sessions.
 *
 * @author Jackjan
 * @version: 0.9 (26.4.2016 00:40)
 */
public class Configuration extends ConfigurationFile {

    private static Configuration instance;

    private boolean isOn = false;
    private Properties config;

    // os-dependent?
    private static boolean os;

    /**
     * Returns the value of a given key inside the configuration file
     *
     * @param name The name of the key which is wanted to be returned
     * @return The value of the given key or null if the key doesn't exist
     */
    private Configuration(boolean osDependent) {
        super(returnPath(osDependent), "");

    }

    private static Path returnPath(boolean os) {
        String s;
        s = CommonUtils.getClassPathAsFile(Configuration.class).getParent();

        if (os) {
            s += File.separator + "config" + File.separator + "config_" + CommonUtils.getOS() + ".cfg";
        } else {
            s += File.separator + "config" + File.separator + "config.cfg";
        }

        return Paths.get(s);
    }

    public static Configuration getInstance(boolean osDependent) {
        if (os != osDependent || instance == null) {
            os = osDependent;
            instance = new Configuration(osDependent);
        }
        return instance;
    }
}
