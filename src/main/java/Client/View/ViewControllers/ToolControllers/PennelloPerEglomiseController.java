package Client.View.ViewControllers.ToolControllers;

import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Exceptions.TimeExceededException;
import Shared.Exceptions.WrongInputException;
import Client.ClientRMI.RMIClientController;
import Client.View.CLI.CLIHUDItems.CLIScheme;
import Client.View.GUI.GUIStaticMethods;
import Client.View.ViewControllers.VIEWController;

import java.rmi.RemoteException;

public class PennelloPerEglomiseController implements ToolCardStrategyInterface {
    private int pennelloPerEglomiseX1;
    private int pennelloPerEglomiseY1;
    private VIEWController viewController;
    private RMIClientController controller;
    public PennelloPerEglomiseController(VIEWController viewController, RMIClientController controller){
        this.controller = controller;
        this.viewController = viewController;
    }
    @Override
    public boolean useToolCard() {
        try{
            if((viewController.getToolController().getToolCardUsed("Pennello per Eglomise")&&controller.getToolController().getFavours(viewController.getMainController().getPlayerName())>=2)||(!viewController.getToolController().getToolCardUsed("Pennello per Eglomise")&&controller.getToolController().getFavours(viewController.getMainController().getPlayerName())>=1)){
                if(!viewController.getMainController().isUsingCLI()){
                    viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona il dado sulla vetrata da prendere");
                    GUIStaticMethods.changeButtonGrid("pennelloPerEglomise1", viewController);
                }else{
                    try{
                        CLIScheme.showScheme(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
                        int X1 = CLIScheme.selectPositionRow(viewController,controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
                        if(X1==-1){
                            return false;
                        }
                        int Y1 = CLIScheme.selectPositionColumn(viewController, controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
                        if(Y1==-1){
                            return false;
                        }
                        int X2 = CLIScheme.insertPositionRow(viewController, controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
                        if(X2==-1){
                            return false;
                        }
                        int Y2 = CLIScheme.insertPositionColumn(viewController, controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
                        if(Y2==-1){
                            return false;
                        }
                        if(viewController.getMainController().isMyTurn()){
                            controller.getToolController().usePennelloPerEglomise(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()),X1, Y1, X2, Y2);
                            return true;
                        }else{
                            return false;
                        }
                    }catch (WrongInputException | CannotPlaceDiceException | TimeExceededException e){
                        return false;
                    }
                }
            }

        }catch (RemoteException e){
            return false;
        }
        return false;
    }
    public void setPennelloPerEglomise1(int X, int Y){
        pennelloPerEglomiseY1 = Y;
        pennelloPerEglomiseX1 = X;
        viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona dove mettere il dado sulla vetrata");
        GUIStaticMethods.changeButtonGrid("pennelloPerEglomise2", viewController);
    }

    public  void setPennelloPerEglomise2 (int X, int Y) {
        try{
            try{
                controller.getToolController().usePennelloPerEglomise(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()),pennelloPerEglomiseX1,pennelloPerEglomiseY1, X, Y);
                viewController.getMainController().getPlayerHUD().getTools().setCardUsed(true);
                viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa completata, seleziona la tua prossima mossa");
            }catch (WrongInputException|CannotPlaceDiceException e){
                viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa completata, seleziona la tua prossima mossa");
            }
            viewController.getMainController().getPlayerHUD().getPlayerFacade().updategrid(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
            GUIStaticMethods.setButtonFacadeNull(viewController);
        }catch (RemoteException e){}

    }
}
