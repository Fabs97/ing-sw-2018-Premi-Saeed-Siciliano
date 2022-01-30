package Client.View.ViewControllers;

import Client.ClientRMI.RMIClientController;
import Client.View.GUI.GUIStaticMethods;
import Client.View.ViewControllers.ToolControllers.*;
import Shared.Model.Tools.ToolCard;

import java.rmi.RemoteException;

public class ToolCardsController{

    private VIEWController viewController;
    private Controller mainController;
    private RMIClientController controller;

    //TOOL CONTROLLERS-----------------------------------------------------------------------------------------
    private AlesatorePerLaminaDiRameController alesatorePerLaminaDiRameController;
    private DiluentePerPastaSaldaController diluentePerPastaSaldaController;
    private LathekinController lathekinController;
    private MartellettoController martellettoController;
    private NormalMoveController normalMoveController;
    private PennelloPerEglomiseController pennelloPerEglomiseController;
    private PennelloPerPastaSaldaController pennelloPerPastaSaldaController;
    private PinzaSgrossatriceController pinzaSgrossatriceController;
    private RigaInSugheroController rigaInSugheroController;
    private TaglierinaCircolareController taglierinaCircolareController;
    private TaglierinaManualeController taglierinaManualeController;
    private TamponeDiamantatoController tamponeDiamantatoController;
    private TenagliaARotelleController tenagliaARotelleController;
    //--------------------------------------------------------------------------------------------------------


    public ToolCardsController(Controller mainController, RMIClientController controller, VIEWController viewController){
        this.controller = controller;
        this.mainController = mainController;
        this.viewController = viewController;
        this.alesatorePerLaminaDiRameController = new AlesatorePerLaminaDiRameController(viewController, controller);
        this.diluentePerPastaSaldaController = new DiluentePerPastaSaldaController(viewController, controller);
        this.lathekinController = new LathekinController(viewController, controller);
        this.martellettoController = new MartellettoController(viewController, controller);
        this.normalMoveController = new NormalMoveController(viewController, controller);
        this.pennelloPerEglomiseController = new PennelloPerEglomiseController(viewController, controller);
        this.pennelloPerPastaSaldaController = new PennelloPerPastaSaldaController(viewController, controller);
        this.pinzaSgrossatriceController = new PinzaSgrossatriceController(viewController, controller);
        this.rigaInSugheroController = new RigaInSugheroController(viewController, controller);
        this.taglierinaCircolareController = new TaglierinaCircolareController(viewController, controller);
        this.taglierinaManualeController = new TaglierinaManualeController(viewController, controller);
        this.tamponeDiamantatoController = new TamponeDiamantatoController(viewController, controller);
        this.tenagliaARotelleController = new TenagliaARotelleController(viewController, controller);
    }

    public boolean getToolCardUsed(String id){
        try {
            for(ToolCard toolCard: controller.getToolController().getToolCard()){
                if(toolCard.getName().equals(id)){
                    return toolCard.isUsed();
                }
            }
        }catch (RemoteException e){}

        return false;
    }

    public RMIClientController getRMIController(){
        return this.controller;
    }

    public boolean useToolCard(String id){
        System.out.println("Sto mandando al server");
        if(!mainController.isUsingCLI()){
            try{
                viewController.getMainController().getPlayerHUD().getPlayerFacade().updategrid(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
                GUIStaticMethods.setButtonFacadeNull(viewController);
                GUIStaticMethods.setButtonDraftPoolNull(viewController);
            }catch (RemoteException e){}

        }

        switch (id){
            case "Pinza Sgrossatrice":
                ToolCardStrategyController.setStrategy(pinzaSgrossatriceController);
                return ToolCardStrategyController.executeStrategy();
            case "Pennello per Eglomise":{
                ToolCardStrategyController.setStrategy(pennelloPerEglomiseController);
                return ToolCardStrategyController.executeStrategy();

            }
            case "Alesatore per lamina di rame":{
                ToolCardStrategyController.setStrategy(alesatorePerLaminaDiRameController);
                return ToolCardStrategyController.executeStrategy();

            }
            case "Lathekin":{
                ToolCardStrategyController.setStrategy(lathekinController);
                return ToolCardStrategyController.executeStrategy();

            }
            case "Taglierina circolare":{
                ToolCardStrategyController.setStrategy(taglierinaCircolareController);
                return ToolCardStrategyController.executeStrategy();

            }
            case "Pennello per pasta salda":{
                ToolCardStrategyController.setStrategy(pennelloPerPastaSaldaController);
                return ToolCardStrategyController.executeStrategy();

            }
            case "Martelletto":{
                ToolCardStrategyController.setStrategy(martellettoController);
                return ToolCardStrategyController.executeStrategy();

            }
            case "Tenaglia a Rotelle":{
                ToolCardStrategyController.setStrategy(tenagliaARotelleController);
                return ToolCardStrategyController.executeStrategy();

            }
            case "Riga in Sughero":{
                ToolCardStrategyController.setStrategy(rigaInSugheroController);
                return ToolCardStrategyController.executeStrategy();

            }
            case "Tampone Diamantato":{
                ToolCardStrategyController.setStrategy(tamponeDiamantatoController);
                return ToolCardStrategyController.executeStrategy();

            }
            case "Diluente per Pasta Salda":{
                ToolCardStrategyController.setStrategy(diluentePerPastaSaldaController);
                return ToolCardStrategyController.executeStrategy();

            }
            case "Taglierina Manuale":{
                ToolCardStrategyController.setStrategy(taglierinaManualeController);
                return ToolCardStrategyController.executeStrategy();

            }
            case "Normal Move":{
                ToolCardStrategyController.setStrategy(normalMoveController);
                return ToolCardStrategyController.executeStrategy();

            }
        }
      return false;
    }

    public AlesatorePerLaminaDiRameController getAlesatorePerLaminaDiRameController(){
        return alesatorePerLaminaDiRameController;
    }

    public DiluentePerPastaSaldaController getDiluentePerPastaSaldaController() {
        return diluentePerPastaSaldaController;
    }

    public LathekinController getLathekinController() {
        return lathekinController;
    }

    public MartellettoController getMartellettoController() {
        return martellettoController;
    }

    public NormalMoveController getNormalMoveController() {
        return normalMoveController;
    }

    public PennelloPerEglomiseController getPennelloPerEglomiseController() {
        return pennelloPerEglomiseController;
    }

    public PennelloPerPastaSaldaController getPennelloPerPastaSaldaController() {
        return pennelloPerPastaSaldaController;
    }

    public PinzaSgrossatriceController getPinzaSgrossatriceController() {
        return pinzaSgrossatriceController;
    }

    public RigaInSugheroController getRigaInSugheroController() {
        return rigaInSugheroController;
    }

    public TaglierinaCircolareController getTaglierinaCircolareController() {
        return taglierinaCircolareController;
    }

    public TaglierinaManualeController getTaglierinaManualeController() {
        return taglierinaManualeController;
    }

    public TamponeDiamantatoController getTamponeDiamantatoController() {
        return tamponeDiamantatoController;
    }

    public TenagliaARotelleController getTenagliaARotelleController() {
        return tenagliaARotelleController;
    }



}

