package nazzer.kahdari.handlers;

import nazzer.kahdari.Main;
import nazzer.kahdari.interfaces.Module;
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
                MenuItem item1 = new MenuItem("Open");
                MenuItem item2 = new MenuItem("Exit");
                popupMenu.add(item1);
                popupMenu.addSeparator();
                popupMenu.add(item2);
                item1.addActionListener((ActionEvent e) -> {

                });
                item2.addActionListener((ActionEvent e) -> {
                    Main.shutdown();
                });
                try {
                    systemTray.add(trayIcon);
                    logData("TrayIcon has Initialized", LogType.SUCCESS);
                } catch (AWTException e) {
                    logData(e.getLocalizedMessage(), LogType.SEVERE);
                }
                trayIcon.displayMessage("Kahdari", "Kahdari has started.", TrayIcon.MessageType.NONE);
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
