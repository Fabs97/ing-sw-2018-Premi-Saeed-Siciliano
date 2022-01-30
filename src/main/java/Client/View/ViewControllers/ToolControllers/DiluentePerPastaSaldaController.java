package Client.View.ViewControllers.ToolControllers;

import Client.ClientRMI.RMIClientController;
import Client.View.CLI.CLIHUDItems.CLIDraftPool;
import Client.View.CLI.CLIHUDItems.CLIScheme;
import Client.View.GUI.GUIAlertBox;
import Client.View.GUI.GUIStaticMethods;
import Client.View.ViewControllers.VIEWController;
import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Exceptions.TimeExceededException;
import Shared.Model.Dice.Dice;
import org.fusesource.jansi.Ansi;

import java.rmi.RemoteException;
import java.util.Scanner;

import static Client.View.CLI.CLI.getAnsiColor;
import static Client.View.CLI.CLI.getDiceUnicode;
import static org.fusesource.jansi.Ansi.ansi;

public class DiluentePerPastaSaldaController implements ToolCardStrategyInterface {
    private VIEWController viewController;
    private RMIClientController controller;

    protected int diluentePerPastaSalda;

    public DiluentePerPastaSaldaController(VIEWController viewController, RMIClientController controller){
        this.controller = controller;
        this.viewController = viewController;
    }

    @Override
    public boolean useToolCard() {
        try{
            if((viewController.getToolController().getToolCardUsed("Diluente per Pasta Salda")&&controller.getToolController().getFavours(viewController.getMainController().getPlayerName())>=2)||(!viewController.getToolController().getToolCardUsed("Diluente per Pasta Salda")&&controller.getToolController().getFavours(viewController.getMainController().getPlayerName())>=1)){
                if(!viewController.getMainController().isUsingCLI()){
                    viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona il dado sulla vetrata da prendere");
                    GUIStaticMethods.changeButtonDraftPool("diluentePerPastaSalda", viewController);
                }else{
                   try{
                       Scanner br = new Scanner(System.in);
                       int index = CLIDraftPool.selectIndexDiceFromDraftpool(controller.getDraftPoolController().getDraftPool(), viewController);
                       controller.getToolController().useDiluentePerPastaSaldaSwitch(index);
                       System.out.println(ansi().fg(Ansi.Color.BLUE).a(" ").fg(getAnsiColor(controller.getDraftPoolController().getDraftPool().get(index).getColor())).a(getDiceUnicode(controller.getDraftPoolController().getDraftPool().get(index).getTop())).fg(Ansi.Color.DEFAULT));
                       System.out.println("Selezionare il valore del dado: valora tra 1 e 6");
                       int value = -1;
                       value = br.nextInt();
                       while (value<1||value>6){
                           System.out.println("Valore inserito non valido, inserire nuovo valore");
                           value=br.nextInt();
                       }
                       controller.getToolController().useDiluentePerPastaSaldaSetValue(controller.getDraftPoolController().getDraftPool().get(index), value);
                       System.out.println(ansi().fg(Ansi.Color.BLUE).a(" ").fg(getAnsiColor(controller.getDraftPoolController().getDraftPool().get(index).getColor())).a(getDiceUnicode(controller.getDraftPoolController().getDraftPool().get(index).getTop())).fg(Ansi.Color.DEFAULT));
                       CLIScheme.showScheme(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));

                       int X = CLIScheme.insertPositionRow(viewController, controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
                       if(X==-1){
                           return false;
                       }
                       int Y = CLIScheme.insertPositionColumn(viewController,controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
                       if(Y==-1){
                           return false;
                       }
                       if(viewController.getMainController().isMyTurn()){
                           controller.getToolController().usePennelloPerPastaSaldaPlace(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()), controller.getDraftPoolController().getDraftPool().get(index), X, Y);
                           return true;
                       }else {
                           return false;
                       }
                   }catch (CannotPlaceDiceException e){
                       System.out.println("Impossibile piazzare il dado");
                   }

                }
            }
            return true;
        }catch(RemoteException | TimeExceededException e){
            return false;
        }

    }
    public void setDiluentePerPastaSalda(String val) {
        try{
            diluentePerPastaSalda = Integer.parseInt(val.substring(val.length()-1));
            controller.getToolController().useDiluentePerPastaSaldaSwitch(diluentePerPastaSalda);
            GUIAlertBox.setDiceValue(viewController);
        }catch (RemoteException e){}

    }
    public void setDiluentePerPastaSalda2(int value){
        try{
            controller.getToolController().useDiluentePerPastaSaldaSetValue(controller.getDraftPoolController().getDraftPool().get(diluentePerPastaSalda),value);
            viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Scegliere dove mettere dado sulla vetrata");
            GUIStaticMethods.changeButtonGrid("diluentePerPastaSalda2", viewController);
        }catch (RemoteException e){}

    }
    public void setDiluentePerPastaSalda3(int x, int y){
        try {
            try{
                controller.getToolController().usePennelloPerPastaSaldaPlace(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()),controller.getDraftPoolController().getDraftPool().get(diluentePerPastaSalda), x, y );
                viewController.getMainController().getPlayerHUD().getTools().setCardUsed(true);
                viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa completata, selezionare prossima mossa");

            }catch (CannotPlaceDiceException e2){
                viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa non completata, selezionare prossima mossa");

            }
            viewController.getMainController().getPlayerHUD().getPlayerFacade().updategrid(controller.getSchemeController().getScheme(viewController.getMainController().getPlayerName()));
            viewController.getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(controller.getDraftPoolController().getDraftPool());
            GUIStaticMethods.setButtonFacadeNull(viewController);
            GUIStaticMethods.setButtonDraftPoolNull(viewController);
        }catch (RemoteException e ){}
    }

}
