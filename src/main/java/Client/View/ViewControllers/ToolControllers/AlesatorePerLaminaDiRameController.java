package Client.View.ViewControllers.ToolControllers;

import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Exceptions.TimeExceededException;
import Shared.Exceptions.WrongInputException;
import Client.ClientRMI.RMIClientController;
import Client.View.CLI.CLIHUDItems.CLIScheme;
import Client.View.GUI.GUIStaticMethods;
import Client.View.ViewControllers.VIEWController;

import java.rmi.RemoteException;

public class AlesatorePerLaminaDiRameController implements ToolCardStrategyInterface{
    private VIEWController viewController;
    private RMIClientController controller;

    protected int alesatorePerLaminaDiRameX1;
    protected int alesatorePerLaminaDiRameY1;


    public AlesatorePerLaminaDiRameController(VIEWController viewController, RMIClientController controller){
        this.controller = controller;
        this.viewController = viewController;
    }
    @Override
    public boolean useToolCard() {
        try{
            if((viewController.getToolController().getToolCardUsed("Alesatore per lamina di rame")&&controller.getToolController().getFavours(viewController.getMainController().getPlayerName())>=2)||(!viewController.getToolController().getToolCardUsed("Alesatore per lamina di rame")&&controller.getToolController().getFavours(viewController.getMainController().getPlayerName())>=1)){
                if(!viewController.getMainController().isUsingCLI()){
                    viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona il dado sulla vetrata da prendere");
                    GUIStaticMethods.changeButtonGrid("alesatorePerLaminaDiRame1", viewController);
                }else{
                    try{
                        CLIScheme.showScheme(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
                        int X1 = CLIScheme.selectPositionRow(viewController, controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
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
                            controller.getToolController().useAlesatorePerLaminaDiRame(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()),X1, Y1, X2, Y2);
                            return true;
                        }else {
                            return false;
                        }

                    }catch (WrongInputException | CannotPlaceDiceException e){
                        return false;
                    }
                }
            }
        }catch (RemoteException| TimeExceededException e){
            return false;
        }
     return false;
    }
    public void setAlesatorePerLaminaDiRame1(int X, int Y){
        alesatorePerLaminaDiRameY1 = Y;
        alesatorePerLaminaDiRameX1 = X;
        viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona dove spostare il dado");
        GUIStaticMethods.changeButtonGrid("alesatorePerLaminaDiRame2", viewController);
    }

    public void setAlesatorePerLaminaDiRame2 (int X, int Y){
        try {
            try {
                controller.getToolController().useAlesatorePerLaminaDiRame(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()), alesatorePerLaminaDiRameX1, alesatorePerLaminaDiRameY1, X, Y);
                viewController.getMainController().getPlayerHUD().getTools().setCardUsed(true);
                viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa completata, seleziona la tua prossima mossa");
            } catch (WrongInputException | CannotPlaceDiceException e) {
                viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa non completata, seleziona la tua prossima mossa");
            }
            viewController.getMainController().getPlayerHUD().getPlayerFacade().updategrid(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
            viewController.getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(controller.getDraftPoolController().getDraftPool());
            GUIStaticMethods.setButtonFacadeNull(viewController);
            GUIStaticMethods.setButtonDraftPoolNull(viewController);
        }catch (RemoteException e){}

    }
}
