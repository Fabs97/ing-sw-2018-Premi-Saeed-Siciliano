package Client.ClientRMI;

import Client.View.ViewControllers.VIEWController;
import Shared.RMIInterface.RMIClientInterface;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient extends UnicastRemoteObject implements RMIClientInterface, Serializable {
    private VIEWController mainController;
    private String serverAddress;
    private int RMIPORT;

    public RMIClient() throws RemoteException{}

    public RMIClient(String address, int port) throws RemoteException{
        RMIPORT = port;
        serverAddress = address;
    }

    public void startRMICLient() throws RemoteException {
        try {
                System.setProperty("java.rmi.server.hostname", serverAddress);
            Registry registry = LocateRegistry.getRegistry(serverAddress, RMIPORT);
            RMIClientController controller = new RMIClientController(registry);
            mainController = new VIEWController(controller);
            mainController.getMainController().setClient(this);
        } catch (NotBoundException e) {
            System.err.println("Could not locate RMI registry, NotBoundException");
        }
    }

    /**
     * @return main client controller
     * @author Fabrizio Siciliano
     * */
    public VIEWController getMainController(){
        return mainController;
    }

    /**
     * pings client
     * @author Fabrizio Siciliano
     * */
    public void ping(){
        System.out.println("Pinged from server");
    }

    /**
     * @return client's username
     * @author Fabrizio Siciliano
     * */
    public String getName(){
        return mainController.getMainController().getPlayerName();
    }

    /**
     * updates client on game started flag
     * @author Fabrizio Siciliano
     * */
    public void startGame(){
        mainController.getMainController().setGameStarted(true);
    }

    /**
     * updates client on turn ended flag
     * @author Fabrizio Siciliano
     * */
    public void notifyEndTurn(){
        mainController.getMainController().setMyTurn(false);
        if(mainController.getTurnController().isGameEnded()){
            mainController.getMainController().getPlayerHUD().setGameEnded(true);
        }
    }

    public void notifyStartTurn() {
        mainController.getTurnController().startTurn();
    }

    public void update(){
        if(!mainController.getMainController().isUsingCLI()) {
            try {
                mainController.getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(mainController.getTableController().getDraftPool());
                mainController.getMainController().getPlayerHUD().getRoundTrace().updateRoundTrace(mainController.getTableController().getRoundTrace());
            } catch (NullPointerException n){

            }
        }
    }

    @Override
    public void playerNotAvailable(String name) {
        mainController.getTableController().playerNotAvailable(name);
    }

    @Override
    public void playerAgainAvailable(String name){
        mainController.getTableController().playerAgainAvaible(name);
    }

    public void continuePlaying(){
        mainController.getMainController().setContinueGame(true);
    }
}
