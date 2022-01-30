package Client.View.CLI.CLIHUDItems;

import Shared.Exceptions.TimeExceededException;
import Shared.Model.Dice.Dice;
import Client.View.CLI.CLI;
import Client.View.ViewControllers.VIEWController;
import org.fusesource.jansi.Ansi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class CLIDraftPool extends CLI{

    public static void showDraftPool(ArrayList<Dice> draftPool){
        for(int i=0; i<draftPool.size(); i++){
            System.out.println(ansi().fg(Ansi.Color.BLUE).a(i + ") ").fg(getAnsiColor(draftPool.get(i).getColor())).a(getDiceUnicode(draftPool.get(i).getTop())).fg(Ansi.Color.DEFAULT));
        }
    }

    public static Dice selectDiceFromDraftpool(ArrayList<Dice> draftPool, VIEWController viewController)throws TimeExceededException {
        Scanner br = new Scanner(System.in);
        int index=-1;

            showDraftPool(draftPool);
            while (true) {
                if(viewController.getMainController().isMyTurn()){
                    do{
                        try{
                            System.out.println(ansi().fg(Ansi.Color.BLUE).a("Inserire indice dado da prendere oppure " + draftPool.size() +" per uscire dalla selezione").fg(Ansi.Color.DEFAULT));
                            index = Integer.parseInt(br.nextLine());
                            break;
                        }catch (NumberFormatException e){
                            System.out.println("Input non valido, riprova");
                        }
                    }while(true);
                }else {
                    throw new TimeExceededException();
                }

                if (index == draftPool.size()) {
                    return null;
                } else if (index >= 0 && index<draftPool.size()) {
                    System.out.println(ansi().fg(Ansi.Color.GREEN).a("Hai scelto il dado: ").fg(Ansi.Color.DEFAULT).a(draftPool.get(index).toString()));
                    break;
                } else {
                    System.out.println(ansi().fg(Ansi.Color.RED).a("Numero indice dado sbagliato").fg(Ansi.Color.DEFAULT));
                }
            }

        if(viewController.getMainController().isMyTurn()){
            return draftPool.get(index);
        }else {
            throw new TimeExceededException();
        }

    }
    public static int selectIndexDiceFromDraftpool(ArrayList<Dice> draftPool, VIEWController viewController)throws TimeExceededException{
        Scanner br = new Scanner(System.in);
        int index=-1;
            showDraftPool(draftPool);
            while (true) {
                if(viewController.getMainController().isMyTurn()){
                    do{
                        try{
                            System.out.println(ansi().fg(Ansi.Color.BLUE).a("Inserire indice dado da prendere oppure " + draftPool.size() +" per uscire dalla selezione").fg(Ansi.Color.DEFAULT));
                            index = Integer.parseInt(br.nextLine());
                            break;
                        }catch (NumberFormatException e){
                            System.out.println("Input non valido, riprova");
                        }
                    }while(true);
                }else {
                    throw new TimeExceededException();
                }

                if (index == draftPool.size()) {
                    return -1;
                } else if (index >= 0 && index<draftPool.size()) {
                    System.out.println(ansi().fg(Ansi.Color.GREEN).a("Hai scelto il dado: ").fg(Ansi.Color.DEFAULT).a(draftPool.get(index).toString()));
                    break;
                } else {
                    System.out.println(ansi().fg(Ansi.Color.RED).a("Numero indice dado sbagliato").fg(Ansi.Color.DEFAULT));
                }
            }

        if(viewController.getMainController().isMyTurn()){
            return index;
        }else {
            throw new TimeExceededException();
        }

    }
}
