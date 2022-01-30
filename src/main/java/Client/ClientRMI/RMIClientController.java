package Client.ClientRMI;

import Shared.RMIInterface.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class RMIClientController {
    /*-------------------------Controllers-------------------------------*/
    private ConnectionInterface connectionController;
    private DraftPoolInterface draftPoolController;
    private PublicObjectiveInterface publicObjectiveController;
    private PrivateObjectiveInterface privateObjectiveController;
    private SchemeInterface schemeController;
    private TurnInterface turnController;
    private ToolControllerInterface toolController;
    private RoundTraceInterface roundTraceController;
    /*-------------------------Controllers-------------------------------*/

    /**
     * @param registry RMI registry of the server
     * @exception RemoteException if error with registry occurs
     * @exception NotBoundException if registry is not bound
     * @author Fabrizio Siciliano
     * */
    public RMIClientController(Registry registry) throws RemoteException, NotBoundException{
        downloadControllers(registry);
        System.out.println("[CLIENT]\tSuccessful controller download");
    }

    /**
     * downloads all controllers from RMI registry
     * @param registry RMI registry of the server
     * @exception RemoteException if error with registry occurs
     * @exception NotBoundException if registry is not bound
     * @author Fabrizio Siciliano, Marco Premi
     * */
    private void downloadControllers(Registry registry) throws RemoteException, NotBoundException {
        connectionController = (ConnectionInterface) registry.lookup("ConnectionController");
        draftPoolController = (DraftPoolInterface) registry.lookup("DraftPoolController");
        publicObjectiveController = (PublicObjectiveInterface) registry.lookup("PublicObjectiveController");
        privateObjectiveController = (PrivateObjectiveInterface)  registry.lookup("PrivateObjectiveController");
        schemeController = (SchemeInterface) registry.lookup("SchemeController");
        turnController = (TurnInterface) registry.lookup("TurnController");
        toolController = (ToolControllerInterface) registry.lookup("ToolController");
        roundTraceController = (RoundTraceInterface) registry.lookup("RoundTraceController");

    }

    /**
     * @return controller used for connection related functions
     * @author Fabrizio Siciliano
     * */
    public ConnectionInterface getConnectionController(){
        return connectionController;
    }

    /**
     * @return controller used for draftpool related functions
     * @author Fabrizio Siciliano
     * */
    public DraftPoolInterface getDraftPoolController(){
        return draftPoolController;
    }

    /**
     * @return controller used for public objective related functions
     * @author Fabrizio Siciliano
     * */
    public PublicObjectiveInterface getPublicObjectiveController() {
        return publicObjectiveController;
    }

    /**
     * @return controller used for private objective related functions
     * @author Fabrizio Siciliano
     * */
    public PrivateObjectiveInterface getPrivateObjectiveController(){
        return privateObjectiveController;
    }

    /**
     * @return controller used for scheme related functions
     * @author Fabrizio Siciliano
     * */
    public SchemeInterface getSchemeController(){
        return schemeController;
    }

    /**
     * @return controller used for turn related functions
     * @author Fabrizio Siciliano
     * */
    public TurnInterface getTurnController(){ return turnController;}

    /**
     * @return controller used for tool related functions
     * @author Fabrizio Siciliano
     * */
    public ToolControllerInterface getToolController() {
        return toolController;
    }

    /**
     * @¶eturn controller used for roundtrace related functions
     * @author Fabrizio Siciliano
     * */
    public RoundTraceInterface getRoundTraceController(){return  roundTraceController;}
}
