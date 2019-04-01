package nazzer.server;

import nazzer.configuration.Configuration;
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
            ss = new ServerSocket(Configuration.getInt("serverport"));
            if(ss.isBound()) {
                logData("Server has been started on port " + Configuration.getInt("serverport"), LogType.SUCCESS);
            } else {
                logData("Server has failed to start on port " + Configuration.getInt("serverport"), LogType.SEVERE);
                return;
            }

            while(!ss.isClosed()) {
                ConnectionListener connectionListener = new ConnectionListener(ss.accept());
                clients.add(connectionListener);
                connectionListener.run();
            }

        } catch(Exception e) {
            logData(e.getLocalizedMessage(), LogType.SEVERE);
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
