package Client.View.ViewControllers;

import Client.ClientRMI.RMIClientController;
import Shared.Model.ObjectiveCard.PrivateObjective;
import Shared.Model.ObjectiveCard.PublicObjective;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Schemes.SchemeCard;
import Shared.Model.Tools.ToolCard;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class PPCardsController{
    private VIEWController viewController;
    /*----------------RMI controller----------------*/
    private RMIClientController controller;
    /*----------------Socket controller----------------*/
    private BufferedReader in;
    private PrintWriter out;

    public PPCardsController(VIEWController viewController, BufferedReader in, PrintWriter out){
        this.viewController = viewController;
        this.in = in;
        this.out = out;
    }

    public PPCardsController(VIEWController viewController, RMIClientController controller){
        this.viewController = viewController;
        this.controller = controller;
    }

    public PrivateObjective getPrivateObjective(){
        if(viewController.getMainController().isUsingRMI()) {
            try {
                return controller.getPrivateObjectiveController().getPrivateObjective1(viewController.getMainController().getPlayerName());
            } catch (RemoteException r) {
                System.err.println("Catched Remote exception in PPCardsController :: getPrivateObjective");
                return null;
            }
        } else{
            out.println("getprivateobjective" +
                    viewController.getMainController().getSocketClient().getRegex() +
                    viewController.getMainController().getPlayerName());
            try {
                String object =  in.readLine();
                return PrivateObjective.fromJSONToPrivate(new JSONObject(object));
            } catch (IOException i){
                viewController.getMainController().getSocketClient().handleDisconnection();
                //TODO: replace this thing
                return null;
            }
        }
    }

    public SchemeCard getSchemeCard(){
        if(viewController.getMainController().isUsingRMI()) {
            try {
                return controller.getSchemeController().getSchemeCard();
            } catch (RemoteException r) {
                System.err.println("Catched Remote exception in PPCardsController :: getSchemeCard");
            }
        } else {
            out.println("getschemecard");
            try{
                String object = in.readLine();
                return SchemeCard.fromJSONToCard(new JSONObject(object));
            } catch (IOException i){
                viewController.getMainController().getSocketClient().handleDisconnection();
                //TODO: replace this thing
                return null;
            }
        }

        return null;
    }

    public ArrayList<PublicObjective> getPublicObjectives(){
        if(viewController.getMainController().isUsingRMI()) {
            try {
                return controller.getPublicObjectiveController().getPublicObjective();
            } catch (RemoteException r) {
                System.err.println("Catched Remote exception in PPCardsController :: getPublicObjectives");
                return null;
            }
        } else {
            out.println("getpublicobjectives");
            try {
                String object = in.readLine();
                ArrayList<PublicObjective> publicObjectives = new ArrayList<>();
                JSONArray array = new JSONArray(object);
                for(Object obj : array){
                    publicObjectives.add(PublicObjective.fromJSONtoPublic((JSONObject) obj));
                }
                return publicObjectives;
            } catch (IOException i){
                viewController.getMainController().getSocketClient().handleDisconnection();
                //TODO: replace this thing
                return null;
            }
        }
    }

    /**
     * @param schemeName name of the scheme to be set on server
     * @author Fabrizio Siciliano
     * */
    public void setScheme(String schemeName){
        try {
            controller.getSchemeController().setScheme(viewController.getMainController().getPlayerName(), schemeName);
        } catch(RemoteException r){
            System.err.println("Catched Remote exception in PPCardsController :: setScheme");
        }
    }

    /**
     * @return player's scheme
     * @author Fabrizio Siciliano
     * */
    public Scheme getScheme(){
        try{
            return controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName());
        } catch(RemoteException r){
            System.err.println("Catched Remote exception in PPCardsController :: getScheme");
        }

        return null;
    }

    public ArrayList<ToolCard> getToolCards(){
        try{
            return controller.getToolController().getToolCard();
        } catch(RemoteException r){
            System.err.println("Catched Remote exception in PPCardsController :: getToolCards");
        }

        return null;
    }
}
