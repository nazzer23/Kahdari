package nazzer.kahdari;

import nazzer.configuration.Configuration;
import nazzer.kahdari.handlers.ImageHandler;
import nazzer.kahdari.handlers.ModuleHandler;
import nazzer.kahdari.handlers.TrayHandler;
import nazzer.kahdari.handlers.UIHandler;
import nazzer.log.LogHandler;
import nazzer.log.LogType;

import java.util.ArrayList;

public class Main {

    public static ArrayList<String> ConfigurationKeysToCheck = new ArrayList<>();
    public static ModuleHandler moduleHandler;

    /**
     * Main function that will run on launch
     * @param args
     */
    public static void main(String[] args) {
        // Initialize Configuration
        Configuration.init();

        // ModuleHandler Thread Creation
        moduleHandler = new ModuleHandler();
        moduleHandler.start();

        // ImageHandler Initialize
        ImageHandler.initImages();

        // TrayHandler Initialize
        TrayHandler.initialize();

        // UIHandler Initialize
        UIHandler.initialize();
    }

    /**
     * Logs the data to the log handler
     * @param msg
     * @param info
     */
    private static void logData(String msg, LogType info) {
        LogHandler.logMessage(msg, info, Main.class);
    }

    public static void shutdown() {
        moduleHandler.shutdown();
        TrayHandler.close();
    }

}
