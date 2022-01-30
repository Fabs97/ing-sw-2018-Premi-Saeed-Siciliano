package Client.ClientSocket;

import Client.View.ViewControllers.VIEWController;

import java.io.*;
import java.net.Socket;

public class SocketClient {
    private Socket socket;
    private BufferedReader socketInput;
    private PrintWriter socketOutput;
    private VIEWController mainController;

    private int sequenceNumber;
    private String commandPrefix;

    private static final String regex = "/";

    public SocketClient(String address, int port) throws IOException {
        this.socket = new Socket(address, port);
    }

    public void startSocketClient(){
        try {
            socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            socketOutput = new PrintWriter(socket.getOutputStream(), true);
            mainController = new VIEWController(socketInput, socketOutput);
            mainController.getMainController().setSocketClient(this);
        } catch (IOException i){
            System.err.println("Errore nella connessione socket");
        }
    }

    public BufferedReader getSocketInput() {
        return socketInput;
    }

    public PrintWriter getSocketOutput() {
        return socketOutput;
    }

    public VIEWController getMainController(){
        return mainController;
    }

    public String getRegex(){
        return regex;
    }

    public boolean handleDisconnection(){
        //TODO: complete it
        return false;
    }
}
