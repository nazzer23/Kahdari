package nazzer.server.listeners;

import nazzer.log.LogHandler;
import nazzer.log.LogType;
import nazzer.server.TCPServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ConnectionListener extends Thread implements Runnable {

    /**
     * Constructor Variables
     */
    public Socket client;
    public TCPServer tcpServer;

    /**
     * Socket Data Handlers
     */
    private PrintWriter dataOut;
    private Scanner dataIn;


    /**
     * Constructor for Connection
     * @param client
     */
    public ConnectionListener(TCPServer tcpServer, Socket client) {
        this.client = client;
        this.tcpServer = tcpServer;
    }


    /**
     * Is the Overrided Method that is ran on Thread Start
     */
    @Override
    public void run() {
        // Initialize the Socket Data Handlers
        this.initSocketDataHandlers();

        // Read Data
        while (this.dataIn.hasNextLine()) {
            String s = this.dataIn.nextLine();
            logData("Client sending data - " + s, LogType.DEBUG);
        }
        this.killUser();
        this.tcpServer.removeClient(this);
    }

    /**
     * Initialize PrintWriter and Scanner
     */
    private void initSocketDataHandlers() {
        try {
            this.dataIn = new Scanner(client.getInputStream());
            this.dataOut = new PrintWriter(client.getOutputStream(), true);
        } catch (Exception e) {
            logData(e.getMessage(), LogType.SEVERE);
        }
    }

    /**
     * Class Data Logger Function
     */
    private void logData(String msg, LogType type) {
        LogHandler.logMessage(msg, type, ConnectionListener.class);
    }

    /**
     * Disconnect User Function
     */
    public void killUser() {
        try {
            this.dataIn.close();
            this.dataOut.close();
            this.client.close();
        } catch (IOException ex) {
            logData(ex.getLocalizedMessage(), LogType.SEVERE);
        }
    }

}
