package nazzer.kahdari.interfaces;

import nazzer.configuration.Configuration;
import nazzer.kahdari.handlers.TrayHandler;
import nazzer.log.LogHandler;
import nazzer.log.LogType;

public class Module extends Thread implements Runnable {

    /**
     * Stores the name of the module
     */
    public String moduleName = "";

    /**
     * Parent Constructor
     * @param moduleName
     */
    public Module(String moduleName) {
        this.moduleName = moduleName;
        if (Configuration.getBoolean("enable-ui")) {
            TrayHandler.addNewModule(this.moduleName);
        }
    }

    /**
     * Starts the module
     */
    public void startModule() {
        logData("Starting", LogType.INFO);
        this.run();
    }

    /**
     * Stops the Module
     */
    public void stopModule() {
        logData("Stopping", LogType.INFO);
    }

    /**
     * Parental Logging
     * @param msg
     * @param type
     */
    protected void logData(String msg, LogType type) {
        LogHandler.logMessage("[" + moduleName + "] " + msg, type, Module.class);
    }
}
