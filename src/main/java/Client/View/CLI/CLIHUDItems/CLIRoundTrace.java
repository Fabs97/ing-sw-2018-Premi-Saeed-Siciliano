package Client.View.CLI.CLIHUDItems;

import Shared.Exceptions.TimeExceededException;
import Shared.Model.RoundTrace.RoundTrace;
import Client.View.CLI.CLI;
import Client.View.ViewControllers.VIEWController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class CLIRoundTrace extends CLI {
    public static void showRoundTrace(RoundTrace roundTrace){
        for(int i=0; i<roundTrace.getTrace().size();i++){
            int j=i+1;
            System.out.println("Round: "+j);
            for(int k=0; k<roundTrace.getTrace().get(i).getCell().size();k++){
                System.out.println("Indice: "+k+"Dado: "+roundTrace.getTrace().get(i).getCell().get(k).toString());
            }
        }
    }

    public static int selectRound(RoundTrace roundTrace, VIEWController viewController) throws TimeExceededException {
        Scanner br = new Scanner(System.in);
        int round=-1;
            if(viewController.getMainController().isMyTurn()){
                do{
                    try{
                        System.out.println("In che round vuoi prendere il dado? Inserire -1 per uscire");
                        round = Integer.parseInt(br.nextLine());
                        break;
                    }catch (NumberFormatException e){
                        System.out.println("Valore inserito non valido, riprova");
                    }
                }while (true);
            }else {
                throw new TimeExceededException();
            }
            if(round==-1){
                return round;
            }

            System.out.println("Valore inserito errato, inserire nuovo valore oppure -1 per uscire");
            while ((round<=0||round>roundTrace.getTrace().size())&&round!=-1){
                if(viewController.getMainController().isMyTurn()){
                    do{
                        try{
                            System.out.println("In che round vuoi prendere il dado? Inserire -1 per uscire");
                            round = Integer.parseInt(br.nextLine());
                            break;
                        }catch (NumberFormatException e){
                            System.out.println("Valore inserito non valido, riprova");
                        }
                    }while (true);
                }else {
                    throw new TimeExceededException();
                }
            }

        if(viewController.getMainController().isMyTurn()){
            return round;
        }else {
            throw new TimeExceededException();
        }

    }

    public static int selectIndex(RoundTrace roundTrace, int ound, VIEWController viewController) throws TimeExceededException{
        System.out.println("In che indice vuoi prendere il dado?");
        Scanner br = new Scanner(System.in);
        int index=-1;
            if(viewController.getMainController().isMyTurn()){
                do{
                    try{
                        System.out.println("In che indice vuoi prendere il dado? Inserire -1 per uscire");
                        index = Integer.parseInt(br.nextLine());
                        break;
                    }catch (NumberFormatException e){
                        System.out.println("Valore inserito non valido, riprova");
                    }
                }while (true);
            }else {
                throw new TimeExceededException();
            }
            if(index==-1){
                return index;
            }

            System.out.println("Valore inserito errato, inserire nuovo valore oppure -1 per uscire");
            while ((index<=0||index>roundTrace.getTrace().size())&&index!=-1){
                if(viewController.getMainController().isMyTurn()){
                    do{
                        try{
                            System.out.println("In che indice vuoi prendere il dado? Inserire -1 per uscire");
                            index = Integer.parseInt(br.nextLine());
                            break;
                        }catch (NumberFormatException e){
                            System.out.println("Valore inserito non valido, riprova");
                        }
                    }while (true);
                }else {
                    throw new TimeExceededException();
                }
            }

        if(viewController.getMainController().isMyTurn()){
            return index;
        }else {
            throw new TimeExceededException();
        }


    }
}
