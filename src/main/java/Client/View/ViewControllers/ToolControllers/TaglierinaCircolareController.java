package Client.View.ViewControllers.ToolControllers;

import Shared.Exceptions.IllegalRoundException;
import Shared.Exceptions.TimeExceededException;
import Shared.Model.Dice.Dice;
import Client.ClientRMI.RMIClientController;
import Client.View.CLI.CLIHUDItems.CLIDraftPool;
import Client.View.CLI.CLIHUDItems.CLIRoundTrace;
import Client.View.GUI.GUIAlertBox;
import Client.View.GUI.GUIStaticMethods;
import Client.View.ViewControllers.VIEWController;

import java.rmi.RemoteException;

public class TaglierinaCircolareController implements ToolCardStrategyInterface{
    protected int taglierinaCircolareX;
    protected int taglierinaCircolareY;
    protected int taglierinaCircolareDraftPool;

    private VIEWController viewController;
    private RMIClientController controller;

    public TaglierinaCircolareController(VIEWController viewController, RMIClientController controller){
        this.controller = controller;
        this.viewController = viewController;
    }
    @Override
    public boolean useToolCard() {
        try{
            if((viewController.getToolController().getToolCardUsed("Taglierina circolare")&&controller.getToolController().getFavours(viewController.getMainController().getPlayerName())>=2)||(!viewController.getToolController().getToolCardUsed("Taglierina circolare")&&controller.getToolController().getFavours(viewController.getMainController().getPlayerName())>=1)){
                if(viewController.getMainController().isUsingCLI()){
                    viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona un dado dalla riserva");
                    GUIStaticMethods.changeButtonDraftPool("taglierinaCircolare1", viewController);
                }else{
                    try{
                        Dice dice = CLIDraftPool.selectDiceFromDraftpool(controller.getDraftPoolController().getDraftPool(), viewController);
                        if(dice!=null){
                            CLIRoundTrace.showRoundTrace(controller.getRoundTraceController().getRoundTrace());
                            int round = CLIRoundTrace.selectRound(controller.getRoundTraceController().getRoundTrace(), viewController);
                            if(round==-1){
                                return false;
                            }
                            int index = CLIRoundTrace.selectIndex(controller.getRoundTraceController().getRoundTrace(), round-1, viewController);
                            if(index==-1){
                                return false;
                            }
                            try {
                                if(viewController.getMainController().isMyTurn()){
                                    controller.getToolController().useTaglierinaCircolare(dice,round-1, index);
                                    return true;
                                }else {
                                    return false;
                                }

                            }catch (IllegalRoundException e){
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

    public void setTaglierinaCircolare1(String val){
        taglierinaCircolareDraftPool =Integer.parseInt(val.substring(val.length()-1));
        viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona un dado dal tracciato dei round");
        GUIAlertBox.showAllRounds("taglierinaCircolare2", viewController);
    }

    public void setTaglierinaCircolare2(String val){
        try{
            try{
                taglierinaCircolareY=  Integer.parseInt(val.substring(val.length()-1));
                taglierinaCircolareX = Integer.parseInt(val.substring(val.length()-2,val.length()-1));
                controller.getToolController().useTaglierinaCircolare(controller.getDraftPoolController().getDraftPool().get(taglierinaCircolareDraftPool), taglierinaCircolareX, taglierinaCircolareY);
                viewController.getMainController().getPlayerHUD().getTools().setCardUsed(true);
                viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Azione completata, scegliere nuova azione");
            }catch (IllegalRoundException e){
                viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Impossibile completare, scegliere nuova azione");
            }
            viewController.getMainController().getPlayerHUD().getPlayerFacade().updategrid(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
            viewController.getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(controller.getDraftPoolController().getDraftPool());
            GUIStaticMethods.setButtonFacadeNull(viewController);
            GUIStaticMethods.setButtonDraftPoolNull(viewController);
        }catch (RemoteException e2){}

    }
}
