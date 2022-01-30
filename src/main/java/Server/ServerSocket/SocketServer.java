package Server.ServerSocket;

import java.io.*;
import java.net.Socket;


public class SocketServer implements Runnable{
    private Socket socket;

    /**
     * @param socket socket of the server
     * @author Fabrizio Siciliano
     * */
    public SocketServer(Socket socket) {
        this.socket = socket;
    }

    /**
     * runs the server socket
     * @author Fabrizio Siciliano
     * */
    @Override
    public void run() {
        try {
            SocketClientHandler socketClientHandler = new SocketClientHandler(new BufferedReader(new InputStreamReader(socket.getInputStream())), new PrintWriter(socket.getOutputStream(), true));
            socketClientHandler.handleClient();
        } catch (IOException i){
            System.err.println("[Socket Server]\tError handling client's " + socket.getInetAddress() + "IO streams");
        }

    }
}
