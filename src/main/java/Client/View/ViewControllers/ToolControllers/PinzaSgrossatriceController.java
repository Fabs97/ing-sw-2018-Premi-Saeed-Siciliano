package Client.View.ViewControllers.ToolControllers;

import Shared.Exceptions.TimeExceededException;
import Shared.Exceptions.WrongInputException;
import Shared.Model.Dice.Dice;
import Client.ClientRMI.RMIClientController;
import Client.View.CLI.CLIHUDItems.CLIDraftPool;
import Client.View.GUI.GUIAlertBox;
import Client.View.GUI.GUIStaticMethods;
import Client.View.ViewControllers.VIEWController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.Scanner;

public class PinzaSgrossatriceController implements ToolCardStrategyInterface{


    private VIEWController viewController;
    private RMIClientController controller;
    public PinzaSgrossatriceController(VIEWController viewController, RMIClientController controller){
        this.controller = controller;
        this.viewController = viewController;
    }
    protected int pinzaSgrossatrice;
    public boolean useToolCard() {
        try{
            if((viewController.getToolController().getToolCardUsed("Pinza Sgrossatrice")&&controller.getToolController().getFavours(viewController.getMainController().getPlayerName())>=2)||(!viewController.getToolController().getToolCardUsed("Pinza Sgrossatrice")&&controller.getToolController().getFavours(viewController.getMainController().getPlayerName())>=1)){
                if (!viewController.getMainController().isUsingCLI()) {
                    viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Scegli un dado nella riserva");
                    GUIStaticMethods.changeButtonDraftPool("pinzaSgrossatrice1",viewController);
                } else {
                    try {
                        Dice dice = CLIDraftPool.selectDiceFromDraftpool(controller.getDraftPoolController().getDraftPool(),viewController);
                        if(dice!=null){
                            Scanner br = new Scanner(System.in);
                            String line = null;
                            if(viewController.getMainController().isMyTurn()){
                                do{
                                    try {
                                        System.out.println("Inserisci 1 per aumentare o 0 per diminuire");
                                        line = br.nextLine();
                                    }catch (NumberFormatException e){
                                        System.out.println("Valore inserito errato, riprova");
                                    }
                                }while (line!=null);

                            }else {
                                return false;
                            }

                            while (!line.equals("0") && !line.equals("1")) {
                                System.out.println("Valore inserito errato, riprovare");
                                if(viewController.getMainController().isMyTurn()){
                                    line = br.nextLine();
                                }else {
                                    return false;
                                }
                            }
                            boolean increase = false;
                            if (line != null && line.equals("1")) {
                                increase = true;
                            } else {
                                if (line != null && line.equals("0")) {
                                    increase = false;
                                } else {
                                }
                            }
                            if(viewController.getMainController().isMyTurn()){
                                controller.getToolController().usePinzaSgrossatrice(dice, increase);
                                return true;
                            }else {
                                return false;
                            }
                        }
                    } catch (IOException | WrongInputException | TimeExceededException e) {
                        return false;
                    }
                }
            }

        }catch (RemoteException e){
            return false;
        }
    return false;
    }

    public void setPinzaSgrossatrice1(String val){
        pinzaSgrossatrice = Integer.parseInt(val.substring(val.length()-1));
        GUIAlertBox.showIncreaseOrDecrease(viewController);
    }
    public void setPinzaSgrossatrice2(boolean increase){
        try{
            try {
                controller.getToolController().usePinzaSgrossatrice(controller.getDraftPoolController().getDraftPool().get(pinzaSgrossatrice),increase);
                viewController.getMainController().getPlayerHUD().getTools().setCardUsed(true);
                viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa completata, seleziona la tua prossima mossa");
            }catch (WrongInputException e){
                viewController.getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa non completata, seleziona la tua prossima mossa");

            }
            viewController.getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(controller.getDraftPoolController().getDraftPool());
            GUIStaticMethods.setButtonDraftPoolNull(viewController);
        }catch (RemoteException e){}

    }
}
