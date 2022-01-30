package Client.View.ViewControllers.ToolControllers;

import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Exceptions.IllegalRoundException;
import Shared.Exceptions.TimeExceededException;
import Shared.Exceptions.WrongInputException;
import Client.ClientRMI.RMIClientController;
import Client.View.CLI.CLIHUDItems.CLIScheme;
import Client.View.GUI.GUIStaticMethods;
import Client.View.ViewControllers.VIEWController;

import java.rmi.RemoteException;

public class TaglierinaManualeController implements ToolCardStrategyInterface {
    private int taglierinaManualeX1;
    private int taglierinaManualeY1;
    private int taglierinaManualeX2;
    private int taglierinaManualeY2;
    private int taglierinaManualeX3;
    private int taglierinaManualeY3;


    private VIEWController viewController;
    private RMIClientController controller;
    public TaglierinaManualeController(VIEWController viewController, RMIClientController controller){
        this.controller = controller;
        this.viewController = viewController;
    }
    @Override
    public boolean useToolCard() {
        try{
            if((viewController.getToolController().getToolCardUsed("Taglierina Manuale")&&controller.getToolController().getFavours(viewController.getMainController().getPlayerName())>=2)||(!viewController.getToolController().getToolCardUsed("Taglerina Manuale")&&controller.getToolController().getFavours(viewController.getMainController().getPlayerName())>=1)){
                if(!viewController.getMainController().isUsingCLI()){
                    viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona il primo dado da spostare");
                    GUIStaticMethods.changeButtonGrid("taglierinaManuale1", viewController);
                }else {
                    try {
                        CLIScheme.showScheme(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
                        int X1 = CLIScheme.selectPositionRow(viewController,controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
                        if(X1==-1){
                            return false;
                        }
                        int Y1 = CLIScheme.selectPositionColumn(viewController,controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
                        if(Y1==-1){
                            return false;
                        }
                        int X2 = CLIScheme.insertPositionRow(viewController,controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
                        if(X2==-1){
                            return false;
                        }
                        int Y2 = CLIScheme.insertPositionColumn(viewController, controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
                        if(Y2==-1){
                            return false;
                        }
                        int X3 = CLIScheme.selectPositionRow(viewController, controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
                        if(X3==-1){
                            return false;
                        }
                        int Y3 = CLIScheme.selectPositionColumn(viewController, controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
                        if(Y3==-1){
                            return false;
                        }
                        int X4 = CLIScheme.insertPositionRow(viewController, controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
                        if(X4==-1){
                            return false;
                        }
                        int Y4 = CLIScheme.insertPositionColumn(viewController,controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
                        if(Y4==-1){
                            return false;
                        }
                        if(viewController.getMainController().isMyTurn()){
                            controller.getToolController().useTaglierinaManuale(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()), X1, Y1, X2, Y2, X3, Y3, X4, Y4);
                            return true;
                        }else {
                            return false;
                        }
                    }catch (RemoteException | CannotPlaceDiceException | IllegalRoundException | WrongInputException | TimeExceededException e){
                        return false;
                    }
                }
            }

        }catch (RemoteException e){
            return false;
        }
        return false;
    }

    public void setTaglierinaManuale1 (int X, int Y){
        taglierinaManualeY1 =  Y;
        taglierinaManualeX1 = X;
        viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona dove spostare il primo dado");
        GUIStaticMethods.changeButtonGrid("taglierinaManuale2", viewController);
    }

    public void setTaglierinaManuale2 (int X, int Y){
        taglierinaManualeY2 =  Y;
        taglierinaManualeX2 = X;
        viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona il secondo dado da spostare");
        GUIStaticMethods.changeButtonGrid("taglierinaManuale3", viewController);
    }
    public void setTaglierinaManuale3 (int X, int Y){
        taglierinaManualeY3 =  Y;
        taglierinaManualeX3 = X;
        viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona dove spostare il secondo dado");
        GUIStaticMethods.changeButtonGrid("taglierinaManuale4", viewController);

    }
    public void setTaglierinaManuale4 (int X, int Y) {
        try{
            try{
                controller.getToolController().useTaglierinaManuale(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()), taglierinaManualeX1, taglierinaManualeY1, taglierinaManualeX2, taglierinaManualeY2, taglierinaManualeX3,taglierinaManualeY3, X, Y);
                viewController.getMainController().getPlayerHUD().getTools().setCardUsed(true);
                viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa completata, seleziona la tua prossima mossa");
            }catch (WrongInputException|CannotPlaceDiceException|IllegalRoundException e){
                viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa non completata, seleziona la tua prossima mossa");
            }
            viewController.getMainController().getPlayerHUD().getPlayerFacade().updategrid(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
            GUIStaticMethods.setButtonFacadeNull(viewController);
        }catch (RemoteException e){}

    }
}
