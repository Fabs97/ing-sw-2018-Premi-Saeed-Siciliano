package Client.View.CLI.CLIHUDItems;

import Shared.Model.ObjectiveCard.PrivateObjective;
import Client.View.CLI.CLI;

public class CLIPrivateCards extends CLI {
    public static void showMultiPrivateCard(PrivateObjective privateObjective){
        System.out.println(privateObjective.toString());
    }
    public static void showSinglePrivateCard(PrivateObjective privateObjective1, PrivateObjective privateObjective2){
        System.out.println(privateObjective1.toString());
        System.out.println(privateObjective2.toString());
    }
}
