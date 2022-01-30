package Server;

import Server.Configuration.ConfigurationLoader;
import Server.Lobby.Lobby;
import Server.RMIInterfaceImplementation.*;
import Server.ServerSocket.SocketServer;

import java.io.IOException;
import java.net.*;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainServer {
    /*-------------------------------Controllers-------------------------------*/
    private static ConnectionController connectionController;
    private static DraftPoolController draftPoolController;
    private static PrivateObjectiveController privateObjectiveController;
    private static PublicObjectiveController publicObjectiveController;
    private static RoundTraceController roundTraceController;
    private static SchemeController schemeController;
    private static ToolController toolController;
    private static TurnController turnController;

    public MainServer()throws RemoteException, NotBoundException{}

    private static String address;
    private final static int RMIPORT = 10000;
    private final static int SOCKETPORT = 10001;

    private static long lobbyTimer;

    //private static ArrayList<Lobby> lobbyList;

    private static Lobby lobby;

    public static void main(String[] args) {
        lobby = null;
        try{
            ConfigurationLoader configurationLoader = new ConfigurationLoader();
            lobbyTimer = configurationLoader.getLobbyTimer();
        }
        catch (IOException i){
            System.err.println("[Main Server]\tIOException in main\n" + i.getMessage());
        }
        startRMIServer(RMIPORT);
        startSocketServer(SOCKETPORT);
    }

    /**
     * starts server socket
     * @param SOCKETPORT port where socket server is instatiated
     * @author Fabrizio Siciliano
     * */
    private static void startSocketServer(int SOCKETPORT){
        ExecutorService executor = Executors.newCachedThreadPool();
        System.out.println("[Socket Server]\tBooting server...");
        try (ServerSocket serverSocket = new ServerSocket(SOCKETPORT)){
            System.out.println("[Socket Server]\tServer succesfully booted on port " + SOCKETPORT);

            while(true){
                Socket socket = serverSocket.accept();
                System.out.println("[Socket Server]\tAccepted connection from " + socket.getInetAddress().toString());
                executor.submit(new SocketServer(socket));
            }
        } catch (IOException i){
            if(i instanceof BindException) {
                System.err.println("[Socket Server]\tNon sono riuscito ad accettare la connessione sulla porta" + SOCKETPORT);
            }
            System.err.println("[Socket Server]\tIOException catched");
        }
    }

    public static void startRMIServer(int RMIPORT){
        System.out.println("[RMI Server]\tBooting server...");
        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while(e.hasMoreElements())
            {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                if(!n.isLoopback()) {
                    Enumeration ee = n.getInetAddresses();
                    while (ee.hasMoreElements()) {
                        InetAddress i = (InetAddress) ee.nextElement();
                        if(i.getHostAddress().startsWith("192.168.")||i.getHostAddress().startsWith("10.")){
                            address=i.getHostAddress();
                            System.out.println(address);
                        }
                    }
                }
            }

        }
        catch (Exception e) {
            System.out.println("[RMI Server]\tCan't get inet address.");
        }
        System.out.println("[RMI Server]\tServer address: " + address + ", port=" + RMIPORT);
        try {

            System.out.println("[RMI Server]\tCreating registry...");
            Registry registry = LocateRegistry.createRegistry(RMIPORT);
            System.out.println("[RMI Server]\tRegistry creation successful!");
            bindAllControllers(registry);
            System.out.println("[RMI Server]\tSuccessful bind\n");
        }
        catch (RemoteException | AlreadyBoundException e) {
            if(e instanceof RemoteException)
                System.err.println("[RMI Server]\tRemoteException " + e);
            if(e instanceof AlreadyBoundException)
                System.err.println("[RMI Server]\tAlreadyBoundException " + e);
        }
    }

    private static void bindAllControllers(Registry registry) throws RemoteException,AlreadyBoundException {
        //System.setProperty("java.rmi.server.hostname",address);
        connectionController = new ConnectionController();
        draftPoolController = new DraftPoolController();
        privateObjectiveController = new PrivateObjectiveController();
        publicObjectiveController = new PublicObjectiveController();
        roundTraceController = new RoundTraceController();
        schemeController = new SchemeController();
        toolController = new ToolController();
        turnController = new TurnController();
        registry.bind("ConnectionController", connectionController);
        registry.bind("DraftPoolController", draftPoolController);
        registry.bind("PrivateObjectiveController", privateObjectiveController);
        registry.bind("PublicObjectiveController", publicObjectiveController);
        registry.bind("RoundTraceController", roundTraceController);
        registry.bind("SchemeController", schemeController);
        registry.bind("ToolController", toolController);
        registry.bind("TurnController", turnController);
    }

    /*public  static  ArrayList<Lobby> getLobbyList(){
        return lobbyList;
    }*/

    /*public static Lobby getCurrentLobby(String idLobby){
        for (Lobby lobby: lobbyList) {
            if(lobby.getLobbyId().equals(idLobby)){
                return lobby;
            }
        }
        return null;
    }*/

    public static void setLobby(Lobby lobby1){
         lobby = lobby1;
    }

    public static Lobby getLobby(){
        return lobby;
    }

    public static long getLobbyTimer(){
        return lobbyTimer;
    }

    public static ConnectionController getConnectionController() {
        return connectionController;
    }

    public static DraftPoolController getDraftPoolController() {
        return draftPoolController;
    }

    public static PrivateObjectiveController getPrivateObjectiveController() {
        return privateObjectiveController;
    }

    public static PublicObjectiveController getPublicObjectiveController() {
        return publicObjectiveController;
    }

    public static RoundTraceController getRoundTraceController() {
        return roundTraceController;
    }

    public static SchemeController getSchemeController() {
        return schemeController;
    }

    public static ToolController getToolController() {
        return toolController;
    }

    public static TurnController getTurnController() {
        return turnController;
    }
}
