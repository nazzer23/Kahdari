package nazzer.configuration;

import nazzer.log.LogHandler;
import nazzer.log.LogType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Configuration {

    public static HashMap<String, Object> config = new HashMap<>();

    /**
     * Initializes the Configuration class
     */
    public static void init() {

        logData("Initializing configuration.", LogType.INFO);

        try {
            File f = new File("conf/config.conf");
            Scanner s = new Scanner(f);
            String d;
            while (s.hasNextLine()) {
                d = s.nextLine();
                if (d.contains("=") && !d.startsWith("#")) {
                    String[] a = d.split("=");
                    config.put(a[0].toLowerCase(), a[1]);
                }
            }

            logData("Configuration has been initialized.", LogType.SUCCESS);
            logData(config.size() + " configuration parameters were loaded.", LogType.INFO);
        } catch (FileNotFoundException e) {
            logData(e.getLocalizedMessage(), LogType.SEVERE);
        }

        // Initialize System Constraints
        initSystemSettings();
    }

    private static void initSystemSettings() {
        logData("Initializing System Constraints", LogType.INFO);

        String value;
        if(System.getProperty("os.name").startsWith("Windows")) {
            value = "Windows";
        } else {
            value = System.getProperty("os.name");
        }
        StaticVars.operatingSystemSettings.put("os", value);

    }

    /**
     * Pulls a string from the config HashMap
     * @param key
     * @return
     */
    public static String getString(String key) {
        key = key.toLowerCase();
        if(config.containsKey(key)) {
            return String.valueOf(config.get(key));
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * References getString providing the hashmap contains the key and parses the string to an integer value
     * @param key
     * @return
     */
    public static int getInt(String key) {
        key = key.toLowerCase();
        if(config.containsKey(key)){
            return Integer.parseInt(getString(key));
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * References getString providing the hashmap contains the key and parses the string to a boolean value
     * @param key
     * @return
     */
    public static boolean getBoolean(String key) {
        key = key.toLowerCase();
        if(config.containsKey(key)) {
            return Boolean.parseBoolean(getString(key));
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Logs the data to the log handler
     * @param msg
     * @param info
     */
    public static void logData(String msg, LogType info) {
        LogHandler.logMessage(msg, info, Configuration.class);
    }

}
