package Client.View.CLI;

import Shared.Exceptions.LobbyFullException;
import Shared.Exceptions.ObjectNotFoundException;
import Client.View.ViewControllers.VIEWController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class CLIMainMenu extends CLI implements CLIInterface{


    private VIEWController controller;

    public CLIMainMenu(VIEWController controller){
        this.controller = controller;
    }

    private final static int singlePlayerOption = 1;
    private final static int multiPlayerOption = 2;
    private final static int settingsOption = 3;
    private final static int exitOption = 4;

    /**
     * shows main menu insertion status
     * @author Fabrizio Siciliano
     * */
    public void showCLI(){
        System.out.println(ansi().eraseScreen().fg(getOptionColor()));
        System.out.println("                    ____                            _       ");
        System.out.println("                   / ___|  __ _  __ _ _ __ __ _  __| | __ _ ");
        System.out.println("                   \\___ \\ / _` |/ _` | '__/ _` |/ _` |/ _` |");
        System.out.println("                    ___) | (_| | (_| | | | (_| | (_| | (_| |");
        System.out.println("                   |____/ \\__,_|\\__, |_|  \\__,_|\\__,_|\\__,_|");
        System.out.println("                                |___/                       ");
        System.out.println();

        System.out.println("****************************************************************************");
        System.out.println("*                                                                          *");
        System.out.println("*                  SCEGLI UNA VOCE DAL MENU PRINCIPALE                     *");
        System.out.println("*                                                                          *");
        System.out.println("****************************************************************************\n");
        try {
            Scanner br = new Scanner(System.in);
            /*while(br.hasNext())
                br.next();*/
            String line;
            while(true) {
                System.out.println(ansi().fg(getOptionColor()).a(singlePlayerOption + ") ").fg(getDefaultColor()).a("SinglePlayer"));
                System.out.println(ansi().fg(getOptionColor()).a(multiPlayerOption + ") ").fg(getDefaultColor()).a("MultiPlayer"));
                System.out.println(ansi().fg(getOptionColor()).a(settingsOption + ") ").fg(getDefaultColor()).a("Settings"));
                System.out.println(ansi().fg(getOptionColor()).a(exitOption + ") ").fg(getDefaultColor()).a("Exit"));


                int optionChosen = br.nextInt();

                switch (optionChosen) {
                    case singlePlayerOption:
                        System.out.println(ansi().fg(getAcceptedColor()).a("Hai scelto di giocare in SinglePlayer").fg(getDefaultColor()));
                        //TODO: must be linked with its real thingy!
                        break;
                    case multiPlayerOption:
                        System.out.println(ansi().fg(getAcceptedColor()).a("Hai scelto di giocare in MultiPlayer").fg(getDefaultColor()));
                        controller.getMainController().insertSinglePlayer(false);
                        try {
                            controller.getSetupController().joinGame();
                        } catch (LobbyFullException | ObjectNotFoundException l){
                            //TODO: must be changed with multiple lobby attendance
                            if(l instanceof LobbyFullException) {
                                System.out.println(ansi().fg(getWarningColor()).a("La lobby è piena.\nChiudo il programma, ma ritenta più tardi!"));
                                System.exit(0);
                            }
                            if(l instanceof ObjectNotFoundException){
                                System.out.println("Non sono riuscito ad aggiungere il player.\nChiudo il programma, ma ritenta più tardi!");
                                System.exit(0);
                            }
                        }
                        break;
                    case settingsOption:
                        CLISettingsMenu settingsMenu = new CLISettingsMenu(controller);
                        settingsMenu.showCLI();
                        break;
                    case exitOption:
                        System.exit(0);
                        break;
                    default:
                        System.out.println(ansi().fg(getWarningColor()).a("Non è un'opzione valida").fg(getDefaultColor()));
                        break;
                }
                if(optionChosen>=singlePlayerOption && optionChosen<=exitOption && optionChosen!=settingsOption)
                    break;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
