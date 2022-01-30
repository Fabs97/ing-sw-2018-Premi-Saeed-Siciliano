package Client.View.ViewControllers.ToolControllers;

import Shared.Exceptions.CannotUseCardException;
import Client.ClientRMI.RMIClientController;
import Client.View.ViewControllers.VIEWController;

import java.rmi.RemoteException;

public class MartellettoController implements ToolCardStrategyInterface {
    private RMIClientController controller;
    private VIEWController viewController;

    public MartellettoController(VIEWController viewController, RMIClientController controller){
        this.controller = controller;
        this.viewController = viewController;
    }
    @Override
    public boolean useToolCard() {
        try{
            try {
                if((viewController.getToolController().getToolCardUsed("Martelletto")&&controller.getToolController().getFavours(viewController.getMainController().getPlayerName())>=2)||(!viewController.getToolController().getToolCardUsed("Martelletto")&&controller.getToolController().getFavours(viewController.getMainController().getPlayerName())>=1)) {

                    if(viewController.getMainController().isMyTurn()){
                        controller.getToolController().useMartelletto(viewController.getTurnController().isFirstTurn());
                        if(!viewController.getMainController().isUsingCLI()){
                            viewController.getMainController().getPlayerHUD().getTools().setCardUsed(true);
                            viewController.getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(controller.getDraftPoolController().getDraftPool());
                            viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa completata, seleziona la tua prossima mossa");
                        }
                        return true;
                    }else{
                        return false;
                    }

                }
            }catch (CannotUseCardException e){
                viewController.getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(controller.getDraftPoolController().getDraftPool());
                viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa non completata, seleziona la tua prossima mossa");

                return false;
            }
        }catch (RemoteException e){}

        return false;
    }
}
