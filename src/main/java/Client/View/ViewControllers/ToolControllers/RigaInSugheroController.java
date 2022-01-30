package Client.View.ViewControllers.ToolControllers;

import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Exceptions.TimeExceededException;
import Shared.Exceptions.WrongInputException;
import Shared.Model.Dice.Dice;
import Client.ClientRMI.RMIClientController;
import Client.View.CLI.CLIHUDItems.CLIDraftPool;
import Client.View.CLI.CLIHUDItems.CLIScheme;
import Client.View.GUI.GUIStaticMethods;
import Client.View.ViewControllers.VIEWController;

import java.rmi.RemoteException;

public class RigaInSugheroController implements ToolCardStrategyInterface{
    private int rigaInSugheroDicePosition;
    private VIEWController viewController;
    private RMIClientController controller;
    public RigaInSugheroController(VIEWController viewController, RMIClientController controller){
        this.controller = controller;
        this.viewController = viewController;
    }

    @Override
    public boolean useToolCard() {
        try{
            if((viewController.getToolController().getToolCardUsed("Riga in Sughero")&&controller.getToolController().getFavours(viewController.getMainController().getPlayerName())>=2)||(!viewController.getToolController().getToolCardUsed("Riga in Sughero")&&controller.getToolController().getFavours(viewController.getMainController().getPlayerName())>=1)){
                if(!viewController.getMainController().isUsingCLI()){
                    viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Scegli un dado dalla riserva");
                    GUIStaticMethods.changeButtonDraftPool("rigaInSughero1", viewController);
                }else{
                    try {
                        CLIDraftPool.showDraftPool(controller.getDraftPoolController().getDraftPool());
                        Dice dice = CLIDraftPool.selectDiceFromDraftpool(controller.getDraftPoolController().getDraftPool(), viewController);
                        if(dice!=null){
                            CLIScheme.showScheme(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
                            int X = CLIScheme.insertPositionRow(viewController, controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
                            if(X==-1){
                                return false;
                            }
                            int Y = CLIScheme.insertPositionColumn(viewController, controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
                            if(Y==-1){
                                return false;
                            }
                            if(viewController.getMainController().isMyTurn()){
                                controller.getToolController().useRigaInSughero(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()), dice, X, Y);
                                return true;
                            }else {
                                return false;
                            }
                        }
                    }catch (RemoteException | WrongInputException | CannotPlaceDiceException | TimeExceededException e){
                        return false;
                    }
                }

            }

        }catch (RemoteException e){
            return false;
        }
        return false;
    }
    public void setRigaInSughero1(String val){
        rigaInSugheroDicePosition = Integer.parseInt(val.substring(val.length()-1));
        viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona dove mettere dado sulla vetrata");
        GUIStaticMethods.changeButtonGrid("rigaInSughero2", viewController);
    }
    public void setRigaInSughero2(int X, int Y){
        try{
            try {
                controller.getToolController().useRigaInSughero(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()), controller.getDraftPoolController().getDraftPool().get(rigaInSugheroDicePosition), X, Y);
                viewController.getMainController().getPlayerHUD().getTools().setCardUsed(true);
                viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa completata, seleziona la tua prossima mossa");
            }catch (WrongInputException|CannotPlaceDiceException e){
                viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa non completata, seleziona la tua prossima mossa");
            }
            viewController.getMainController().getPlayerHUD().getPlayerFacade().updategrid(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
            viewController.getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(controller.getDraftPoolController().getDraftPool());
            GUIStaticMethods.setButtonFacadeNull(viewController);
            GUIStaticMethods.setButtonDraftPoolNull(viewController);
        }catch (RemoteException e){}
    }

}
