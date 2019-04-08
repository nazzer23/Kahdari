package nazzer.kahdari.handlers;

import nazzer.configuration.Configuration;
import nazzer.configuration.StaticVars;
import nazzer.kahdari.Main;
import nazzer.kahdari.interfaces.Module;
import nazzer.log.LogHandler;
import nazzer.log.LogType;
import nazzer.server.TCPServer;

public class ModuleHandler extends Thread implements Runnable{

    /**
     * Thread Override with custom run method
     */
    @Override
    public void run() {
        // Initialize Modules
        this.init();

        // Perform Configuration Checks
        boolean configurationValid = checkConfigurationKeys();
        if(!configurationValid) {
            return;
        }

        // Start Modules on Start Up
        this.startup();
    }

    /**
     * Validates that the configuration keys exist within the configuration file
     * @return
     */
    private static boolean checkConfigurationKeys() {
        // Iterates through the keysToCheck array to get all of the keys that need to be checked.
        for(String temp : Main.ConfigurationKeysToCheck) {
            if(!Configuration.config.containsKey(temp.toLowerCase())) {
                logData("A configuration value wasn't found. Key: " + temp, LogType.SEVERE);
                return false;
            }
        }

        return true;
    }

    /**
     * Populates the module HashMap providing that the suitable configuration options are selected
     */
    public void init() {
        if (Configuration.getBoolean("tcpserver")) {
            StaticVars.modules.put("tcpserver", new TCPServer("TCPServer"));
            Main.ConfigurationKeysToCheck.add("serverport");
        }
    }

    /**
     * Runs on application startup
     */
    public void startup() {
        for(int i=0; i<StaticVars.modules.size(); i++) {
            startModule((String) StaticVars.modules.keySet().toArray()[i]);
        }
    }

    /**
     * Starts Individual Modules
     * @param key
     */
    public void startModule(String key) {
        if(StaticVars.modules.containsKey(key)) {
            Module module = StaticVars.modules.get(key);
            module.startModule();
        }
    }

    /**
     * Stops modules on demand
     * @param key
     */
    public void stopModule(String key) {
        if(StaticVars.modules.containsKey(key)) {
            Module module = StaticVars.modules.get(key);
            module.stopModule();
        }
    }

    /**
     * Stops all Modules
     */
    public void shutdown() {
        for(int i=0; i<StaticVars.modules.size(); i++) {
            stopModule((String) StaticVars.modules.keySet().toArray()[i]);
        }
    }

    /**
     * Logs the data to the log handler
     * @param msg
     * @param info
     */
    private static void logData(String msg, LogType info) {
        LogHandler.logMessage(msg, info, ModuleHandler.class);
    }

}
