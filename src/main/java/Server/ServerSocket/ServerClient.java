package Server.ServerSocket;

import Shared.RMIInterface.RMIClientInterface;

public  class ServerClient {
    private volatile String username;
    private volatile RMIClientInterface rmiClient;
    private volatile SocketClientHandler socketClient;
    private volatile boolean isRMI;

    public ServerClient(String name, RMIClientInterface client){
        this.username = name;
        this.rmiClient = client;
        this.socketClient = null;
        this.isRMI = true;
    }

    public ServerClient(String name, SocketClientHandler client){
        this.username = name;
        this.rmiClient = null;
        this.socketClient = client;
        this.isRMI = false;
    }

    public boolean isRMI() {
        return isRMI;
    }

    public SocketClientHandler getSocketClient(){
        return socketClient;
    }

    public RMIClientInterface getRmiClient(){
        return rmiClient;
    }

    public String getUsername(){
        return username;
    }
}
