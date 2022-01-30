package Client.View.CLI;


import Client.View.ViewControllers.VIEWController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.fusesource.jansi.Ansi.ansi;

public class CLISettingsMenu extends CLI {
    private VIEWController controller;

    public CLISettingsMenu(VIEWController controller){
        this.controller = controller;
    }

    private static final int option1 = 1;
    private static final int option2 = 2;

    public void showCLI(){
        System.out.println(ansi().eraseScreen().fg(getOptionColor()));
        System.out.println("****************************************************************************");
        System.out.println("*                                                                          *");
        System.out.println("*                           MENU' IMPOSTAZIONI                             *");
        System.out.println("*                                                                          *");
        System.out.println("****************************************************************************\n");

        //show options
        System.out.println(ansi().fg(getOptionColor()).a(option1 + ") ").fg(getDefaultColor()).a("Crea uno schema nuovo"));


        System.out.println(ansi().fg(getOptionColor()).a(option2 + ") ").fg(getDefaultColor()).a("Torna alla schermata principale"));

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                int chosenOption = Integer.parseInt(br.readLine());
                switch (chosenOption){
                    case option1:
                        CLICustomCardsCreation customCardsCreation = new CLICustomCardsCreation(controller);
                        customCardsCreation.createNewScheme();
                        break;
                    case option2:
                        return;
                    default:
                        System.out.println(ansi().fg(getWarningColor()).a("Non è un'opzione valida").fg(getDefaultColor()));
                        break;
                }

                if(chosenOption == option1 || chosenOption == option2)
                    break;
            }
        }catch (IOException i){  }
    }
}
