package nazzer.kahdari.handlers;

import nazzer.kahdari.Main;
import nazzer.log.LogHandler;
import nazzer.log.LogType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrayHandler extends Thread implements Runnable {

    private static SystemTray systemTray = null;
    private static TrayIcon trayIcon = null;
    private static PopupMenu popupMenu = null;

    /**
     * Initialization method for tray handler
     */
    public static void initialize() {
        Runnable runner = () -> {
            if(SystemTray.isSupported()) {
                systemTray = SystemTray.getSystemTray();
                popupMenu = new PopupMenu();
                trayIcon = new TrayIcon(imageForTray(systemTray), "Kahdari", popupMenu);
                try {
                    systemTray.add(trayIcon);
                    logData("System tray has initialized", LogType.SUCCESS);
                } catch (AWTException e) {
                    logData(e.getLocalizedMessage(), LogType.SEVERE);
                }

                addNewModule("Kahdari");
                addMenuItem("Close", (ActionEvent e) -> Main.shutdown());

                pushTrayNotification("Kahdari Tray has Initialized");
            } else {
                System.exit(-1);
            }
        };
        EventQueue.invokeLater(runner);
    }

    /**
     *
     * @param theTray
     * @return
     */
    private static Image imageForTray(@org.jetbrains.annotations.NotNull SystemTray theTray){

        Image trayImage = ImageHandler.images.get("logo");
        Dimension trayIconSize = theTray.getTrayIconSize();
        trayImage = trayImage.getScaledInstance(trayIconSize.width, trayIconSize.height, Image.SCALE_SMOOTH);

        return trayImage;
    }

    /**
     * Adds a splitter to the tray menu
     * Followed by the module name
     */
    public static void addNewModule(String moduleName) {
        popupMenu.addSeparator();
        popupMenu.add(moduleName);
    }

    /**
     * Allows programatic actionListeners
     */
    public static void addMenuItem(String menuItemLabel, ActionListener actionListener) {
        try {
            MenuItem temp = new MenuItem(menuItemLabel);
            temp.addActionListener(actionListener);
            popupMenu.add(temp);
        } catch (Exception e) {
            logData(e.getLocalizedMessage(), LogType.SEVERE);
        }
    }

    /**
     * Tray Icon Notification
     */
    public static void pushTrayNotification(String msg) {
        trayIcon.displayMessage("Kahdari", msg, TrayIcon.MessageType.NONE);
    }

    public static void close() {
        for(ActionListener a : trayIcon.getActionListeners()) {
            trayIcon.removeActionListener(a);
        }
        systemTray.remove(trayIcon);
    }

    public static void logData(String msg, LogType type) {
        LogHandler.logMessage(msg, type, TrayHandler.class);
    }

}
