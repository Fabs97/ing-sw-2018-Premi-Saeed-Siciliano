package Client.View.CLI;

import Client.View.ViewControllers.VIEWController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.fusesource.jansi.Ansi.ansi;

public class CLISinglePlayerSetup extends CLI implements CLIInterface {
    private VIEWController controller;

    public CLISinglePlayerSetup(VIEWController controller){
        this.controller = controller;
    }

    /**
     * shows single player setup state
     * @author Fabrizio Siciliano
     * */
    public void showCLI() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println(ansi().eraseScreen().fg(getOptionColor()));
        System.out.println("****************************************************************************");
        System.out.println("*                                                                          *");
        System.out.println("*                   IMPOSTA LA DIFFICOLTA: valore tra 1 e 5                *");
        System.out.println("*                                                                          *");
        System.out.println("****************************************************************************\n");
        int difficulty;

        try {
            difficulty = Integer.parseInt(br.readLine());
            while (difficulty < 1 || difficulty > 5) {
                System.out.println(ansi().fg(getWarningColor()).a("Il valore inserito non è valido, ritenta\n"));
                difficulty = Integer.parseInt(br.readLine());
            }
            System.out.println(ansi().fg(getAcceptedColor()).a("La difficoltà inserita è: ").fg(getDefaultColor()).a(difficulty));
            /*
             * TODO: set difficulty on server and connect to it
             */
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
