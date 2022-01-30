package Client.View.ViewControllers.ToolControllers;

import Shared.Exceptions.TimeExceededException;
import Shared.Model.Dice.Dice;
import Client.ClientRMI.RMIClientController;
import Client.View.CLI.CLIHUDItems.CLIDraftPool;
import Client.View.GUI.GUIStaticMethods;
import Client.View.ViewControllers.VIEWController;

import java.rmi.RemoteException;

public class TamponeDiamantatoController implements ToolCardStrategyInterface {
    protected int tamponeDiamantato;

    private VIEWController viewController;
    private RMIClientController controller;
    public TamponeDiamantatoController(VIEWController viewController, RMIClientController controller){
        this.controller = controller;
        this.viewController = viewController;
    }
    @Override
    public boolean useToolCard() {
        try{
            if((viewController.getToolController().getToolCardUsed("Tampone Diamantato")&&controller.getToolController().getFavours(viewController.getMainController().getPlayerName())>=2)||(!viewController.getToolController().getToolCardUsed("Tampone Diamantato")&&controller.getToolController().getFavours(viewController.getMainController().getPlayerName())>=1)){
                if(!viewController.getMainController().isUsingCLI()){
                    viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona un dado dalla riserva");
                    GUIStaticMethods.changeButtonDraftPool("tamponeDiamantato", viewController);
                }else{
                    try {
                        Dice dice = CLIDraftPool.selectDiceFromDraftpool(controller.getDraftPoolController().getDraftPool(), viewController);
                        if(dice!=null){
                            if(viewController.getMainController().isMyTurn()){
                                controller.getToolController().useTamponeDiamantato(dice);
                                return true;
                            }else {
                                return false;
                            }
                        }
                    }catch (RemoteException | TimeExceededException e){
                        return false;
                    }
                }
            }

        }catch (RemoteException e){
            return false;
        }
    return false;
    }
    public void setTamponeDiamantato(String val){
        try{
            tamponeDiamantato = Integer.parseInt(val.substring(val.length()-1));
            controller.getToolController().useTamponeDiamantato(controller.getDraftPoolController().getDraftPool().get(tamponeDiamantato));
            viewController.getMainController().getPlayerHUD().getTools().setCardUsed(true);
            viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa completata, seleziona la prossima mossa");
            viewController.getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(controller.getDraftPoolController().getDraftPool());
            GUIStaticMethods.setButtonDraftPoolNull(viewController);
        }catch (RemoteException e){
            viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa non completata, seleziona la prossima mossa");

        }
        GUIStaticMethods.setButtonDraftPoolNull(viewController);


    }
}
