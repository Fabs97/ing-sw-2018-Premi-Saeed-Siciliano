package Client.View.ViewControllers.ToolControllers;

import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Exceptions.TimeExceededException;
import Client.ClientRMI.RMIClientController;
import Client.View.CLI.CLIHUDItems.CLIScheme;
import Client.View.GUI.GUIStaticMethods;
import Client.View.ViewControllers.VIEWController;

import java.rmi.RemoteException;

public class LathekinController implements ToolCardStrategyInterface{
    private VIEWController viewController;
    private RMIClientController controller;

    protected int lathekinX1;
    protected int lathekinY1;
    protected int lathekinX2;
    protected int lathekinY2;
    protected int lathekinX3;
    protected int lathekinY3;


    public LathekinController(VIEWController viewController, RMIClientController controller){
        this.controller = controller;
        this.viewController = viewController;
    }
    @Override
    public boolean useToolCard() {
        try{
            if((viewController.getToolController().getToolCardUsed("Lathekin")&&controller.getToolController().getFavours(viewController.getMainController().getPlayerName())>=2)||(!viewController.getToolController().getToolCardUsed("Lathekin")&&controller.getToolController().getFavours(viewController.getMainController().getPlayerName())>=1)) {
                if (!viewController.getMainController().isUsingCLI()) {
                    viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona il dado sulla vetrata da spostare per primo");
                    GUIStaticMethods.changeButtonGrid("lathekin1", viewController);
                } else {
                    try {
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
                        int Y4 = CLIScheme.insertPositionColumn(viewController, controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
                        if(Y4==-1){
                            return false;
                        }
                        if(viewController.getMainController().isMyTurn()){
                            controller.getToolController().useLathekin(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()), X1, Y1, X2, Y2, X3, Y3, X4, Y4);
                            return true;
                        }else {
                            return false;
                        }

                    } catch (RemoteException | CannotPlaceDiceException e) {
                        return false;
                    }
                }
            }
        }catch (RemoteException| TimeExceededException e) {
            return false;
        }

    return false;
    }

    public void setLathekin1 (int X, int Y){
        lathekinY1 = Y;
        lathekinX1 = X;
        viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona la posizione dove spostare il dado");
        GUIStaticMethods.changeButtonGrid("lathekin2", viewController);
    }
    public void setLathekin2 (int X, int Y){
        lathekinY2 =  Y;
        lathekinX2 = X;viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona il secondo dado sulla vetrata da prendere");

        GUIStaticMethods.changeButtonGrid("lathekin3", viewController);
    }

    public void setLathekin3 (int X, int Y){
        lathekinY3 =  Y;
        lathekinX3 = X;
        viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona la posizione dove spostare il dado");
        GUIStaticMethods.changeButtonGrid("lathekin4",viewController);
    }

    public void setLathekin4 (int X, int Y) {
        try{
            try{
                controller.getToolController().useLathekin(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()), lathekinX1,lathekinY1,lathekinX2,lathekinY2,lathekinX3, lathekinY3, X, Y);
                viewController.getMainController().getPlayerHUD().getTools().setCardUsed(true);
                viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa completata, seleziona la tua prossima mossa");
            }catch (CannotPlaceDiceException e){
                viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa non completata, seleziona la tua prossima mossa");
            }
            viewController.getMainController().getPlayerHUD().getPlayerFacade().updategrid(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
            viewController.getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(controller.getDraftPoolController().getDraftPool());
            GUIStaticMethods.setButtonFacadeNull(viewController);
            GUIStaticMethods.setButtonDraftPoolNull(viewController);
        }catch (RemoteException e){}


    }
}
