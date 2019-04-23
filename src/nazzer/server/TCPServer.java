package nazzer.server;

import nazzer.configuration.Configuration;
import nazzer.kahdari.handlers.TrayHandler;
import nazzer.kahdari.interfaces.Module;
import nazzer.log.LogType;
import nazzer.server.handlers.CommandHandler;
import nazzer.server.listeners.ConnectionListener;

import java.net.ServerSocket;
import java.util.ArrayList;

public class TCPServer extends Module {

    /**
     * ArrayList containing all clients connected to the server
     */
    public static ArrayList<ConnectionListener> clients = new ArrayList<>();
    private ServerSocket ss;

    /**
     * Super Constructor initiated through parent class
     * @param moduleName
     */
    public TCPServer(String moduleName) {
        super(moduleName);
        CommandHandler.init();
    }

    /**
     * Initiates a Socket Server
     */
    @Override
    public void run() {
        try {
            int serverPort = Configuration.getInt("serverport");
            ss = new ServerSocket(serverPort);
            if(ss.isBound()) {
                logData("Server has been started on port " + serverPort, LogType.SUCCESS);
            } else {
                logData("Server has failed to start on port " + serverPort, LogType.SEVERE);
                return;
            }

            if (Configuration.getBoolean("enable-ui")) {
                TrayHandler.pushTrayNotification("TCPServer initialized on port " + serverPort + ".");
            }

            while(!ss.isClosed()) {
                ConnectionListener connectionListener = new ConnectionListener(this, ss.accept());
                this.addClient(connectionListener);
                connectionListener.run();
            }
            clients.clear();

        } catch(Exception e) {
            logData(e.getLocalizedMessage(), LogType.SEVERE);
        }
    }

    public void addClient(ConnectionListener listener) {
        clients.add(listener);
        if (Configuration.getBoolean("debug")) {
            logData("Client Connected", LogType.DEBUG);
            logData("Number of Clients Connected - " + clients.size(), LogType.DEBUG);
        }
    }

    public void removeClient(ConnectionListener listener) {
        clients.remove(listener);
        if (Configuration.getBoolean("debug")) {
            logData("Client Disconnected", LogType.DEBUG);
            logData("Number of Clients Connected - " + clients.size(), LogType.DEBUG);
        }
    }

    @Override
    public void stopModule() {
        try {
            clients.clear();
            this.ss.close();
        } catch( Exception e) {}
    }

}
