package Client.View.ViewControllers;

import Client.ClientRMI.RMIClientController;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class VIEWController {
    private Controller mainController;
    private SetupController setupController;
    private PPCardsController cardsController;
    private TurnController turnController;
    private TableController tableController;
    private ToolCardsController toolController;     //TODO: link in socket!

    public VIEWController(BufferedReader in, PrintWriter out){
        this.mainController = new Controller();
        this.setupController = new SetupController(this, in, out);
        this.cardsController = new PPCardsController(this, in, out);
    }

    /**
     * @param controller main controller for client-server comms
     * @author Fabrizio Siciliano
     * */
    public VIEWController(RMIClientController controller){
        /*----------------------------create all controllers---------------------------------*/
        this.mainController = new Controller();
        this.setupController = new SetupController(this, controller);
        this.cardsController = new PPCardsController(this, controller);
        this.turnController = new TurnController(mainController, controller);
        this.tableController = new TableController(mainController, controller);
        this.toolController = new ToolCardsController(mainController, controller, this);
        /*------------------------------ended controller creation----------------------------*/
    }

    /**
     * @return controller used for client values functions
     * @author Fabrizio Siciliano
     * */
    public Controller getMainController(){
        return mainController;
    }

    /**
     * @return controller used for tool functions usage in Client.View
     * @author Fabrizio Siciliano
     * */
    public ToolCardsController getToolController(){
        return toolController;
    }

    /**
     * @return controller used for setup functions usage in Client.View
     * @author Fabrizio Siciliano
     * */
    public SetupController getSetupController(){
        return setupController;
    }

    /**
     * @return controller used for cards functions usage in Client.View
     * @author Fabrizio Siciliano
     * */
    public PPCardsController getCardsController() {
        return cardsController;
    }

    /**
     * @return controller used for turn functions usage in Client.View
     * @author Fabrizio Siciliano
     * */
    public TurnController getTurnController() {
        return turnController;
    }

    /**
     * @return controller used for table functions usage in Client.View
     * @author Fabrizio Siciliano
     * */
    public TableController getTableController() {
        return tableController;
    }

}
