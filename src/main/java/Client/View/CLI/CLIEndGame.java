package Client.View.CLI;

import Client.View.ViewControllers.VIEWController;

import java.util.Map;

import static org.fusesource.jansi.Ansi.ansi;

public class CLIEndGame extends CLI implements CLIInterface {

    private VIEWController controller;

    public CLIEndGame(VIEWController controller){
        this.controller = controller;
    }

    /**
     * shows end game state
     * @author Fabrizio Siciliano
     * */
    public void showCLI(){
        System.out.println(ansi().fg(getOptionColor()));
        System.out.println("****************************************************************************");
        System.out.println("*                                                                          *");
        System.out.println("*                     ECCO I RISULTATI DELLA PARTITA                       *");
        System.out.println("*                                                                          *");
        System.out.println("****************************************************************************\n");
        Map<String, Integer> ranking = controller.getSetupController().getRanking();
        if(ranking!=null) {
            int rankingPosition = 1;
            for (String player : ranking.keySet()) {
                System.out.println(ansi().fg(getOptionColor()).a(rankingPosition + ") " + player + ": " + ranking.get(player)).fg(getDefaultColor()));
                rankingPosition++;
            }
        }
        else{
            System.err.println("Errore in ranking catching");
        }

        System.out.println(ansi().fg(getAcceptedColor()).a("La partita è finita. Chiudo la partita tra pochi secondi"));

    }
}
