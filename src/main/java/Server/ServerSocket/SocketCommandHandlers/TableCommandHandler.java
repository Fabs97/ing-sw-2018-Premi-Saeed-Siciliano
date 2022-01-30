package Server.ServerSocket.SocketCommandHandlers;

import Server.MainServer;
import Server.RMIInterfaceImplementation.DraftPoolController;
import Server.RMIInterfaceImplementation.PrivateObjectiveController;
import Server.RMIInterfaceImplementation.PublicObjectiveController;
import Server.RMIInterfaceImplementation.SchemeController;
import Shared.Model.Dice.Dice;
import Shared.Model.ObjectiveCard.PrivateObjective;
import Shared.Model.RoundTrace.RoundTrace;
import Shared.Model.Schemes.SchemeCard;

import java.io.PrintWriter;
import java.util.ArrayList;

public class TableCommandHandler {
    private DraftPoolController draftPoolController;
    private PrivateObjectiveController privateObjectiveController;
    private PublicObjectiveController publicObjectiveController;
    private SchemeController schemeController;
    private PrintWriter out;

    public TableCommandHandler(PrintWriter out){
        this.draftPoolController = MainServer.getDraftPoolController();
        this.publicObjectiveController = MainServer.getPublicObjectiveController();
        this.privateObjectiveController = MainServer.getPrivateObjectiveController();
        this.schemeController = MainServer.getSchemeController();
        this.out = out;
    }

    public void updateDraftPool(ArrayList<Dice> draftPool){

    }

    public void updateRoundTrace(RoundTrace roundTrace){

    }

    public void getPrivateObjective(String name){
        PrivateObjective p = privateObjectiveController.getPrivateObjective1(name);
        if(p==null)
            out.println("null");
        else{
            out.println(PrivateObjective.fromPrivateToJSON(p));
        }
    }

    public void getSchemeCard(){
        SchemeCard card = schemeController.getSchemeCard();
        if(card == null){
            out.println("null");
        } else {
            out.println(SchemeCard.fromCardToJSON(card));
        }
    }
}
