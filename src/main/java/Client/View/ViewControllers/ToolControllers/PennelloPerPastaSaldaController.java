package Client.View.ViewControllers.ToolControllers;

import Shared.Exceptions.CannotPlaceDiceException;
import  Shared.Exceptions.TimeExceededException;
import Shared.Model.Dice.Dice;
import Client.ClientRMI.RMIClientController;
import Client.View.CLI.CLIHUDItems.CLIDraftPool;
import Client.View.CLI.CLIHUDItems.CLIScheme;
import Client.View.GUI.GUIStaticMethods;
import Client.View.ViewControllers.VIEWController;
import org.fusesource.jansi.Ansi;

import java.rmi.RemoteException;

import static Client.View.CLI.CLI.getAnsiColor;
import static Client.View.CLI.CLI.getDiceUnicode;
import static org.fusesource.jansi.Ansi.ansi;

public class PennelloPerPastaSaldaController implements ToolCardStrategyInterface {
    private int pennelloPerPastaSalda;

    private VIEWController viewController;
    private RMIClientController controller;

    public PennelloPerPastaSaldaController(VIEWController viewController, RMIClientController controller){
        this.controller = controller;
        this.viewController = viewController;
    }
    @Override
    public boolean useToolCard() {
        try{
            if((viewController.getToolController().getToolCardUsed("Pennello per pasta salda")&&controller.getToolController().getFavours(viewController.getMainController().getPlayerName())>=2)||(!viewController.getToolController().getToolCardUsed("Pennello per pasta salda")&&controller.getToolController().getFavours(viewController.getMainController().getPlayerName())>=1)){
                if(viewController.getMainController().isUsingCLI()){
                    viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona il dado dalla riserva");
                    GUIStaticMethods.changeButtonDraftPool("pennelloPerPastaSalda2", viewController);
                }else{
                    try{
                        int index = CLIDraftPool.selectIndexDiceFromDraftpool(controller.getDraftPoolController().getDraftPool(),viewController);
                        controller.getToolController().usePennelloPerPastaSaldaRoll(controller.getDraftPoolController().getDraftPool().get(index));
                        System.out.println(ansi().fg(Ansi.Color.BLUE).a(" ").fg(getAnsiColor(controller.getDraftPoolController().getDraftPool().get(index).getColor())).a(getDiceUnicode(controller.getDraftPoolController().getDraftPool().get(index).getTop())).fg(Ansi.Color.DEFAULT));

                        if(controller.getDraftPoolController().getDraftPool().get(index)!=null){
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
                                controller.getToolController().usePennelloPerPastaSaldaPlace(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()),controller.getDraftPoolController().getDraftPool().get(index), X, Y);
                                return true;
                            }else {
                                return false;
                            }
                        }
                    }catch (RemoteException | TimeExceededException | CannotPlaceDiceException e){
                        return false;
                    }
                }
            }

        }catch (RemoteException e){
            return false;
        }
    return false;
    }

    public void setPennelloPerPastaSalda1(String val){
        try {
            pennelloPerPastaSalda = Integer.parseInt(val.substring(val.length()-1));
            controller.getToolController().usePennelloPerPastaSaldaRoll(controller.getDraftPoolController().getDraftPool().get(pennelloPerPastaSalda));
            viewController.getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(controller.getDraftPoolController().getDraftPool());
            GUIStaticMethods.changeButtonGrid("pennelloPerPastaSalda2", viewController);viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona dove mettere dado sulla vetrata");

        }catch (RemoteException e){}

    }
    public void setPennelloPerPastaSalda2(int X, int Y){
        try {
            try{
                controller.getToolController().usePennelloPerPastaSaldaPlace(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()),controller.getDraftPoolController().getDraftPool().get(pennelloPerPastaSalda), X, Y);
                viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa completata, selezionare prossima mossa");
                viewController.getMainController().getPlayerHUD().getTools().setCardUsed(true);
            }catch (CannotPlaceDiceException e){
                viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa non completata, selezionare prossima mossa");
            }
            viewController.getMainController().getPlayerHUD().getPlayerFacade().updategrid(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
            viewController.getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(controller.getDraftPoolController().getDraftPool());
            GUIStaticMethods.setButtonFacadeNull(viewController);
            GUIStaticMethods.setButtonDraftPoolNull(viewController);
        }catch (RemoteException e){}
    }

}
