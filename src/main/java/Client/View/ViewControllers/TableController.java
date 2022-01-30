package Client.View.ViewControllers;

import Shared.Model.Dice.Dice;
import Shared.Model.RoundTrace.RoundTrace;
import Client.ClientRMI.RMIClientController;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;

import static Client.View.CLI.CLIHUDItems.CLIScheme.showScheme;

public class TableController{
    private Controller viewController;
    /*----------------RMI controller----------------*/
    private RMIClientController controller;
    /*----------------Socket controller----------------*/
    private BufferedReader in;
    private PrintWriter out;

    public TableController(Controller viewController, BufferedReader in, PrintWriter out){
        this.viewController = viewController;
        this.in = in;
        this.out = out;
    }

    public TableController(Controller viewController, RMIClientController controller){
        this.viewController = viewController;
        this.controller = controller;
    }

    public void showOtherPlayerSchemes() throws RemoteException {
        for(String player : controller.getConnectionController().getOtherPlayerName(viewController.getPlayerName())){
            showScheme(controller.getSchemeController().getScheme(player));
        }
    }

    public ArrayList<Dice> getDraftPool(){
        try {
            return controller.getDraftPoolController().getDraftPool();
        } catch (RemoteException r){
            System.err.println("Remote Exception catched in TableController");
        }

        return null;
    }
    public RoundTrace getRoundTrace(){
        try {
            return controller.getRoundTraceController().getRoundTrace();
        } catch (RemoteException r){
            System.err.println("Remote Exception catched in TableController");
        }

        return null;
    }

    public ArrayList<String> getOtherPlayersNames(){
        try{
            return controller.getConnectionController().getOtherPlayerName(viewController.getPlayerName());
        } catch (RemoteException r){
            System.err.println("Remote Exception catched in TableController");
        }

        return null;
    }

    public void playerNotAvailable(String name){
        System.out.println("Player " + name + " has disconnected");
    }

    //la sto provando io, saeed
    public void playerAgainAvaible(String name){
        System.out.println("Player " + name + " has reconnected");
    }
}
