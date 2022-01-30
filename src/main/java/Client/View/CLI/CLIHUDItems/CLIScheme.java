package Client.View.CLI.CLIHUDItems;

import Shared.Exceptions.TimeExceededException;
import Shared.Model.Schemes.Scheme;
import Client.View.CLI.CLI;
import Client.View.ViewControllers.VIEWController;
import org.fusesource.jansi.Ansi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class CLIScheme extends CLI{
    public static void showScheme(Scheme scheme){
        for (int i = 0; i < scheme.getScheme().length; i++) {
            if (i == 0) {
                System.out.print("  ");
                for (int k = 0; k < scheme.getScheme()[i].length; k++) {
                    System.out.print(k + " ");
                }
                System.out.println();
            }
            for (int j = 0; j < scheme.getScheme()[i].length; j++) {
                if (j == 0) {
                    System.out.print(i + " ");
                }

                if(!scheme.getScheme()[i][j].isOccupied()){
                    System.out.print(ansi().eraseScreen().fg(getAnsiColor(scheme.getScheme()[i][j].getColor())).a(scheme.getScheme()[i][j].toString()).fg(Ansi.Color.DEFAULT).a(" "));
                }
                else{
                    System.out.print(ansi().eraseScreen().fg(getAnsiColor(scheme.getScheme()[i][j].getDado().getColor())).a(scheme.getScheme()[i][j].getDado().getTop()).fg(Ansi.Color.DEFAULT).a(" "));
                }
                /*
                if (!scheme.getScheme()[i][j].isOccupied() && scheme.getScheme()[i][i].getColor().equals(Color.WHITE)) {
                    System.out.print(scheme.getScheme()[i][j].toString() + " ");
                } else {
                    if (!scheme.getScheme()[i][j].isOccupied() && !scheme.getScheme()[i][i].getColor().equals(Color.WHITE)) {
                        System.out.print(ansi().eraseScreen().fg(getAnsiColor(scheme.getScheme()[i][j].getColor())).a("0").fg(Ansi.Color.DEFAULT).a(" "));
                    } else {
                        if (scheme.getScheme()[i][j].isOccupied()) {
                            System.out.print(ansi().eraseScreen().fg(getAnsiColor(scheme.getScheme()[i][j].getDado().getColor())).a(scheme.getScheme()[i][j].getDado().getTop()).fg(Ansi.Color.DEFAULT).a(" "));
                        }
                    }

                }*/

            }
            System.out.println();
        }
    }

    public static int selectPositionRow(VIEWController controller, Scheme scheme)throws TimeExceededException{
        int row = -1;
        Scanner br = new Scanner(System.in);
            if(controller.getMainController().isMyTurn())
                do{
                    try {
                        System.out.println("Inserira riga in cui prendere dado oppure -1 per uscire: ");
                        row = Integer.parseInt(br.nextLine());
                        while(row<-1||row>=scheme.getScheme().length){
                            System.out.println("Inserira riga in cui prendere dado oppure -1 per uscire: ");
                            row = Integer.parseInt(br.nextLine());
                        }
                        break;
                    }catch (NumberFormatException e){
                        System.out.println("Input non valido, riprova");
                    }
                }while (true);

            else {
                throw new TimeExceededException();
            }


        if(controller.getMainController().isMyTurn()){
            return row;
        }else {
            throw new TimeExceededException();
        }

    }
    public static int selectPositionColumn(VIEWController controller, Scheme scheme)throws TimeExceededException{
        int column = -1;
        Scanner br = new Scanner(System.in);
            if(controller.getMainController().isMyTurn())
                do{
                    try {
                        System.out.println("Inserira colonna in cui prendere dado oppure -1 per uscire: ");
                        column = Integer.parseInt(br.nextLine());
                        while(column<-1||column>=scheme.getScheme()[0].length){
                            System.out.println("Inserira colonna in cui prendere dado oppure -1 per uscire: ");
                            column = Integer.parseInt(br.nextLine());
                        }
                        break;
                    }catch (NumberFormatException e){
                        System.out.println("Input non valido, riprova");
                    }
                }while (true);

            else {
                throw new TimeExceededException();
            }
        if(controller.getMainController().isMyTurn()){
            return column;
        }else {
            throw new TimeExceededException();
        }


    }
    public static  int insertPositionRow(VIEWController controller, Scheme scheme)throws TimeExceededException{
        int row = -1;
        Scanner br = new Scanner(System.in);
            if(controller.getMainController().isMyTurn())
                do{
                    try {
                        System.out.println("Inserira riga in cui inserire dado oppure -1 per uscire: ");
                        row = Integer.parseInt(br.nextLine());
                        while(row<-1||row>=scheme.getScheme().length){
                            System.out.println("Inserira riga in cui inserire dado oppure -1 per uscire: ");
                            row = Integer.parseInt(br.nextLine());
                        }
                        break;
                    }catch (NumberFormatException e){
                        System.out.println("Input non valido, riprova");
                    }
                }while (true);

            else {
                throw new TimeExceededException();
            }

        if(controller.getMainController().isMyTurn()){
            return row;
        }else {
            throw new TimeExceededException();
        }

    }
    public static int insertPositionColumn(VIEWController controller, Scheme scheme)throws TimeExceededException{
        int column = -1;
        Scanner br = new Scanner(System.in);

            if(controller.getMainController().isMyTurn())
                do{
                    try {
                        System.out.println("Inserira colonna in cui inserire dado oppure -1 per uscire: ");
                        column = Integer.parseInt(br.nextLine());
                        while(column<-1||column>=scheme.getScheme()[0].length){
                            System.out.println("Inserira colonna in cui inserire dado oppure -1 per uscire: ");
                            column = Integer.parseInt(br.nextLine());
                        }
                        break;
                    }catch (NumberFormatException e){
                        System.out.println("Input non valido, riprova");
                    }
                }while (true);

            else {
                throw new TimeExceededException();
            }

        if(controller.getMainController().isMyTurn()){
            return column;
        }else {
            throw new TimeExceededException();
        }


    }
}
