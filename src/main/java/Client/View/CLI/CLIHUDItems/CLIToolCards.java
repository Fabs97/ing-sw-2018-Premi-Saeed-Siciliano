package Client.View.CLI.CLIHUDItems;

import Shared.Model.Tools.ToolCard;
import Client.View.ViewControllers.VIEWController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CLIToolCards {
    public static void showToolCards(ArrayList<ToolCard> toolCards){
        for(ToolCard toolCard : toolCards){
            System.out.println(toolCard.toString());
        }
    }

    public static boolean useToolCards(ArrayList<ToolCard> toolCards, VIEWController controller){
        boolean isPresent = false;

        for(ToolCard toolCard : toolCards){
            System.out.println("Nome Tool Card: " + toolCard.getName());
        }
        System.out.println("Inserisci il nome della carta da utilizzare: ");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        try{
            line = br.readLine();
            while(!isPresent){
                for(int i=0; i<toolCards.size();i++){
                    if(line.equals(toolCards.get(i).getName())){
                        isPresent=true;
                    }
                }
                if(!isPresent){
                    System.out.println("La carte inserita non esiste, inserire nuovo valore: ");
                    line=br.readLine();
                }
            }
            System.out.println("Hai scelto la carta: "+line);
            return controller.getToolController().useToolCard(line);
        }catch (IOException e){
            return false;
        }
    }

    public static boolean useNormalMove(VIEWController controller){
         return controller.getToolController().useToolCard("Normal Move");
    }

}
