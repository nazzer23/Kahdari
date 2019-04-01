package nazzer.server.listeners;

import java.net.Socket;

public class ConnectionListener extends Thread implements Runnable {

    /**
     * Connected Users Socket
     */
    private Socket client;

    /**
     * Constructor for Connection
     * @param client
     */
    public ConnectionListener(Socket client) {
        this.client = client;
    }

}
