package nazzer.kahdari.handlers;

import nazzer.kahdari.frames.UI_Main;
import nazzer.log.LogHandler;
import nazzer.log.LogType;

import javax.swing.*;

public class UIHandler {

    public static UI_Main mainUIObj;

    public static void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            logData(e.getLocalizedMessage(), LogType.SEVERE);
        }


        mainUIObj = new UI_Main();

        logData("UIHandler has initialized", LogType.SUCCESS);
        TrayHandler.pushTrayNotification("UIHandler has initialized");
    }

    public static void logData(String msg, LogType type) {
        LogHandler.logMessage(msg, type, UIHandler.class);
    }

}
