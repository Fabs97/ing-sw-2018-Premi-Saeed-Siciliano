package Client.View.ViewControllers.ToolControllers;

import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Exceptions.TimeExceededException;
import Shared.Model.Dice.Dice;
import Client.ClientRMI.RMIClientController;
import Client.View.CLI.CLIHUDItems.CLIDraftPool;
import Client.View.CLI.CLIHUDItems.CLIScheme;
import Client.View.GUI.GUIStaticMethods;
import Client.View.ViewControllers.VIEWController;

import java.rmi.RemoteException;

public class NormalMoveController implements ToolCardStrategyInterface {
    private int draftpoolPosition;
    private VIEWController viewController;
    private RMIClientController controller;
    public NormalMoveController(VIEWController viewController, RMIClientController controller){
        this.controller = controller;
        this.viewController = viewController;
    }

    @Override
    public boolean useToolCard() {
        if(!viewController.getMainController().isUsingCLI()){
            viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona il dado da prendere");
            GUIStaticMethods.changeButtonDraftPool("normalMove1", viewController);
        }else{
            try {
                CLIScheme.showScheme(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
                Dice dice = null;
                dice=CLIDraftPool.selectDiceFromDraftpool(controller.getDraftPoolController().getDraftPool(), viewController);
                if(dice!=null){
                    int X = CLIScheme.insertPositionRow(viewController, controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
                    if(X==-1){
                        return false;
                    }
                    int Y = CLIScheme.insertPositionColumn(viewController,controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
                    if(Y==-1){
                        return false;
                    }
                    if(viewController.getMainController().isMyTurn()){
                        controller.getToolController().normalMove(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()), dice, X, Y);
                        return true;
                    }else {
                        return false;
                    }
                }
             }catch (RemoteException | CannotPlaceDiceException | TimeExceededException e){
                return false;
            }

        }
        return false;
    }

    public void setNormalMove1(String val){
        draftpoolPosition = Integer.parseInt(val.substring(val.length()-1));
        viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona la cella in cui mettere il dado");
        GUIStaticMethods.changeButtonGrid("normalMove2", viewController);
    }

    public void setNormalMove2(int X, int Y){
        try{
            try {
                controller.getToolController().normalMove(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()), controller.getDraftPoolController().getDraftPool().get(draftpoolPosition), X,Y);
                viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Dado piazzato, seleziona prossima mossa!");
                viewController.getMainController().getPlayerHUD().getButtonMoves().setDiceMoved(true);
            }catch (CannotPlaceDiceException e){
                viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Impossibile completare, seleziona prossima mossa");
            }
            viewController.getMainController().getPlayerHUD().getPlayerFacade().updategrid(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
            viewController.getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(controller.getDraftPoolController().getDraftPool());
            GUIStaticMethods.setButtonFacadeNull(viewController);
            GUIStaticMethods.setButtonDraftPoolNull(viewController);
        }catch (RemoteException e){}


    }
}
