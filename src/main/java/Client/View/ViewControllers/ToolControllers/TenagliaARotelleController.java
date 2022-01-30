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

public class TenagliaARotelleController implements ToolCardStrategyInterface {
    private int tenagliaARotelleDraftPool;


    private VIEWController viewController;
    private RMIClientController controller;
    public TenagliaARotelleController(VIEWController viewController, RMIClientController controller){
        this.controller = controller;
        this.viewController = viewController;
    }
    @Override
    public boolean useToolCard() {
        try{
            if((viewController.getToolController().getToolCardUsed("Tenaglia a Rotelle")&&controller.getToolController().getFavours(viewController.getMainController().getPlayerName())>=2)||(!viewController.getToolController().getToolCardUsed("Tenaglia a Rotelle")&&controller.getToolController().getFavours(viewController.getMainController().getPlayerName())>=1)){
                if(!viewController.getMainController().isUsingCLI()){
                    viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Scegli un dado dalla riserva");
                    GUIStaticMethods.changeButtonDraftPool("tenagliaARotelle1", viewController);
                }else{
                    try{
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
                                controller.getToolController().useTenagliaARotelle(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()), dice, X, Y);
                                return true;
                            }else {
                                return false;
                            }
                        }
                    }catch (RemoteException | CannotPlaceDiceException | TimeExceededException e){
                        return false;
                    }

                }

            }

        }catch (RemoteException e){
            return false;
        }
        return false;
    }

    public void setTenagliaARotelle1(String val){
        tenagliaARotelleDraftPool = Integer.parseInt(val.substring(val.length()-1));
        viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Scegli dove mettere il dado sulla vetrata");
        GUIStaticMethods.changeButtonGrid("tenagliaARotelle2",viewController);
    }
    public void setTenagliaARotelle2(int X, int Y){
        try {
            try{
                controller.getToolController().useTenagliaARotelle(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()),controller.getDraftPoolController().getDraftPool().get(tenagliaARotelleDraftPool), X, Y);
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
