package Client.View.CLI;

import Shared.Model.ObjectiveCard.PrivateObjective;
import Shared.Model.ObjectiveCard.PublicObjective;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Schemes.SchemeCard;
import Client.View.CLI.CLIHUDItems.CLIScheme;
import Client.View.ViewControllers.VIEWController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static org.fusesource.jansi.Ansi.ansi;

public class CLIPlayerSetup extends CLI implements CLIInterface {

    private VIEWController controller;

    public CLIPlayerSetup(VIEWController controller){
        this.controller = controller;
    }

    /**
     * shows player's scheme setup state
     * @author Marco Premi
     * */
    public void showCLI() {
        try {
            ArrayList<PrivateObjective> privateObjectives = new ArrayList<>();
            privateObjectives.add(controller.getCardsController().getPrivateObjective());
            SchemeCard schemeCard1 = controller.getCardsController().getSchemeCard();
            SchemeCard schemeCard2 = controller.getCardsController().getSchemeCard();
            Scheme scheme1 = schemeCard1.getFront();
            Scheme scheme2 = schemeCard1.getRear();
            Scheme scheme3 = schemeCard2.getFront();
            Scheme scheme4 = schemeCard2.getRear();

            System.out.println();
            for (PrivateObjective privateObjective: privateObjectives) {
                System.out.println("Obiettivo privato: "+privateObjective.toString());
            }

            ArrayList<PublicObjective> objectives = controller.getCardsController().getPublicObjectives();

            System.out.println();
            for(int i=0; i<objectives.size(); i++){
                System.out.println(i+": "+ objectives.get(i).toString());
            }

            System.out.println(ansi().fg(getOptionColor()).a("Fronte della prima carta: ").fg(getDefaultColor()).a(scheme1.getName()));
            System.out.println(ansi().fg(getOptionColor()).a("Favori: ").fg(getDefaultColor()).a(scheme4.getFavors()));

            CLIScheme.showScheme(schemeCard1.getFront());

            System.out.println();

            System.out.println(ansi().fg(getOptionColor()).a("Retro della prima carta: ").fg(getDefaultColor()).a(scheme2.getName()));
            System.out.println(ansi().fg(getOptionColor()).a("Favori: ").fg(getDefaultColor()).a(scheme4.getFavors()));
            CLIScheme.showScheme(schemeCard1.getRear());
            System.out.println();

            System.out.println(ansi().fg(getOptionColor()).a("Fronte della seconda carta: ").fg(getDefaultColor()).a(scheme3.getName()));
            System.out.println(ansi().fg(getOptionColor()).a("Favori: ").fg(getDefaultColor()).a(scheme4.getFavors()));
            CLIScheme.showScheme(schemeCard2.getFront());
            System.out.println();

            System.out.println(ansi().fg(getOptionColor()).a("Retro della seconda carta: ").fg(getDefaultColor()).a(scheme4.getName()));
            System.out.println(ansi().fg(getOptionColor()).a("Favori: ").fg(getDefaultColor()).a(scheme4.getFavors()));
            CLIScheme.showScheme(schemeCard2.getRear());
            System.out.println();

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println(ansi().fg(getDefaultColor()).a("Digitare nome schema scelto: "));
            String chosenSchemeCardName;
            chosenSchemeCardName = br.readLine();
            while(!chosenSchemeCardName.equals(scheme1.getName()) && !chosenSchemeCardName.equals(scheme2.getName()) && !chosenSchemeCardName.equals(scheme3.getName())&&!chosenSchemeCardName.equals(scheme4.getName())) {
                System.out.println(ansi().fg(getWarningColor()).a("Nome inserito non valido").fg(getDefaultColor()));
                chosenSchemeCardName = br.readLine();
            }
            System.out.println(ansi().fg(getAcceptedColor()).a("Hai scelto: ").fg(getDefaultColor()).a(chosenSchemeCardName));

            //sends chosen card to server
            controller.getCardsController().setScheme(chosenSchemeCardName);
        }catch (IOException e){}
    }
}
