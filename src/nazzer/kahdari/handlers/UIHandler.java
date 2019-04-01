package nazzer.kahdari.handlers;

import nazzer.kahdari.frames.UI_Main;
import nazzer.log.LogHandler;
import nazzer.log.LogType;

public class UIHandler {

    public static UI_Main mainUIObj;

    public static void initialize() {
        mainUIObj = new UI_Main();

        logData("UIHandler has initialized", LogType.SUCCESS);
    }

    public static void logData(String msg, LogType type) {
        LogHandler.logMessage(msg, type, UIHandler.class);
    }

}
