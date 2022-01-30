package Client.View.ViewControllers;

import Client.ClientRMI.RMIClientController;
import Client.ClientSocket.SocketClientListener;
import Shared.Color;
import Shared.Exceptions.LobbyFullException;
import Shared.Exceptions.ObjectNotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetupController{
    private VIEWController viewController;
    /*----------------RMI controller----------------*/
    private RMIClientController controller;
    /*----------------Socket controller----------------*/
    private BufferedReader in;
    private PrintWriter out;

    public SetupController(VIEWController viewController, BufferedReader in, PrintWriter out){
        this.viewController = viewController;
        this.in = in;
        this.out = out;
    }

    /**
     * @param viewController controller for Client.View-Controller functions
     * @param controller controller for client-server comms
     * @author Fabrizio Siciliano
     * */
    public SetupController(VIEWController viewController, RMIClientController controller){
        this.viewController = viewController;
        this.controller = controller;
    }

    /**
     * @return login successful value
     * @author Fabrizio Siciliano
     * */
    public boolean login(){
        if(viewController.getMainController().isUsingRMI()) {
            while (true) {
                try {
                    //giving the RMI server a copy of this client
                    return controller.getConnectionController().login(viewController.getMainController().getPlayerName(), viewController.getMainController().getClient());
                } catch (RemoteException r) {
                    System.err.println("Object export failed " + r.getMessage());
                }
            }
        } else{
            try{
                out.println("login" +
                        viewController.getMainController().getSocketClient().getRegex() +
                        viewController.getMainController().getPlayerName());
                if((in.readLine()).equals("true"))
                    return true;
                else
                    return false;
            } catch (IOException i){
                if(!viewController.getMainController().getSocketClient().handleDisconnection())
                    return false;
                else{
                    //reconnected successfully
                    //TODO: replace this thing
                    return false;
                }
            }
        }
    }

    /**
     * @exception LobbyFullException if lobby joined is already full
     * @exception ObjectNotFoundException if client is not found on lobby (in game exception)
     * @exception RemoteException error in client-server comms
     * @author Fabrizio Siciliano
     * */
    public void joinGame() throws LobbyFullException, ObjectNotFoundException, RemoteException {
        if(viewController.getMainController().isUsingRMI())
            viewController.getMainController().setIdLobby(controller.getConnectionController().joinGame(viewController.getMainController().getPlayerName(), true));
        else{
            try{
                out.println("joingame" +
                        viewController.getMainController().getSocketClient().getRegex() +
                        viewController.getMainController().getPlayerName());
                String returnValue = in.readLine();
                if(returnValue.equals("LobbyFullException"))
                    throw new LobbyFullException();
                else if(returnValue.equals("ObjectNotFoundException"))
                    throw new ObjectNotFoundException();
                else {
                    viewController.getMainController().setIdLobby(returnValue);
                    new Thread(new SocketClientListener(viewController, in, out)).start();
                }
            } catch (IOException i){
                viewController.getMainController().getSocketClient().handleDisconnection();
            }
        }
    }

    /**
     * @return game ended ranking
     * @author Fabrizio Siciliano
     * */
    public Map<String, Integer> getRanking(){
        if(viewController.getMainController().isUsingRMI()) {
            try {
                return controller.getConnectionController().getRanking();
            } catch (RemoteException r) {
                System.err.println("RemoteException in SetupController");
                return null;
            }
        }
        else {
            try{
                out.println("getranking");
                String returnValue = in.readLine();
                //TODO: check ranking map handling!
                if(!returnValue.equals("null")) {
                    HashMap<String, Integer> map = new HashMap<>();
                    List<String> trimmed = Arrays.asList(returnValue.split("/"));
                    for (int i = 0; i < trimmed.size(); i++) {
                        map.put(trimmed.get(i).split(":")[0], Integer.parseInt(trimmed.get(i).split(":")[1]));
                    }

                    return map;
                } else return null;
            } catch (IOException i){
                viewController.getMainController().getSocketClient().handleDisconnection();
                //TODO: replace this thing
                return null;
            }
        }
    }

    /**
     * @return player's color
     * @author Fabrizio Siciliano
     * */
    public Color getPlayerColor(){
        if(viewController.getMainController().isUsingRMI()) {
            try {
                return controller.getConnectionController().getPlayerColor(viewController.getMainController().getPlayerName());
            } catch (RemoteException r) {
                System.err.println("RemoteException in SetupController");
                return null;
            }
        } else{
            try {
                out.println("getcolor" +
                        viewController.getMainController().getSocketClient().getRegex() +
                        viewController.getMainController().getPlayerName());
                String returValue = in.readLine();
                if(returValue.equals("null"))
                    return null;
                else
                    return Color.stringToColor(returValue);
            } catch (IOException i){
                viewController.getMainController().getSocketClient().handleDisconnection();
                //TODO: replace this thing
                return null;
            }
        }
    }

    /**
     * saves custom card on server
     * @param names new card's schemes' names
     * @param favors new card's schemes' favors
     * @param scheme1 new card's first scheme
     * @param scheme2 new card's second scheme
     * @author Fabrizio Siciliano
     * */
    public void saveCustomCard(String[] names, String[] favors, String[] scheme1, String[] scheme2){
        try {
            controller.getSchemeController().saveCardOnServer(names, favors, scheme1, scheme2);
        } catch (RemoteException r){
            System.err.println("RemoteException in SetupController");
        }
    }
}
