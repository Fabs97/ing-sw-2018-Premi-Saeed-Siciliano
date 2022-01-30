package Client.View.ViewControllers;

import Client.ClientRMI.RMIClientController;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.rmi.RemoteException;

public class TurnController{
    private Controller viewController;
    /*----------------RMI controller----------------*/
    private RMIClientController controller;
    /*----------------Socket controller----------------*/
    private BufferedReader in;
    private PrintWriter out;

    public TurnController(Controller viewController, BufferedReader in, PrintWriter out){
        this.viewController = viewController;
        this.in = in;
        this.out = out;
    }

    public TurnController(Controller viewController, RMIClientController controller){
        this.viewController = viewController;
        this.controller = controller;
    }

    public boolean isGameEnded(){
        try {
            return controller.getTurnController().isGameEnded();
        } catch(RemoteException r){
            Controller.handleServerDisconnected();
        }
        return false;
    }

    public boolean isFirstTurn(){
        return false; //TODO: collegare a server
    }

    public boolean arePlayersReady(){
        try {
            return controller.getConnectionController().arePlayersReady();
        } catch (RemoteException r){
            Controller.handleServerDisconnected();
        }
        return false;
    }

    public void endMyTurn(){
        try{
            controller.getTurnController().endMyTurn(viewController.getPlayerName());
        } catch(RemoteException r){
            Controller.handleServerDisconnected();
        }
    }

    public String whatRoundIs(){
        try{
            return String.valueOf(controller.getTurnController().round());
        } catch (RemoteException r) {
            Controller.handleServerDisconnected();
        }

        return null;
    }

    public void startTurn(){
        viewController.setMyTurn(true);
    }
}
