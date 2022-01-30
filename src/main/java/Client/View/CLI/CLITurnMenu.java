package Client.View.CLI;

import Client.View.CLI.CLIHUDItems.*;
import Client.View.ViewControllers.VIEWController;

import java.io.IOException;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class CLITurnMenu extends CLI implements CLIInterface{

    private VIEWController controller;

    public CLITurnMenu(VIEWController controller){
        this.controller = controller;
    }

    //to be updated every time a new option is added!
    private static final int MAX_OPTIONS = 10; //it is equal to last option + 2! (ex: option6 + 2 = 8)
    private static final int option1 = 1;
    private static final int option2 = 2;
    private static final int option3 = 3;
    private static final int option4 = 4;
    private static final int option5 = 5;
    private static final int option6 = 6;
    private static final int option7 = 7;
    private static final int option8 = 8;
    private boolean waitForNextRound(){
        long MAX_TIME_WAITING = 5*1000;
        long startTime = System.currentTimeMillis();
        while(!controller.getMainController().isMyTurn()){
            if(controller.getTurnController().isGameEnded()){
                return false;
            }
            if(System.currentTimeMillis() - startTime >= MAX_TIME_WAITING){
                System.out.println(ansi().fg(getOptionColor()).a("Aspetto che gli altri client finiscano i rispettivi turni"));
                startTime = System.currentTimeMillis();
            }
        }
        return true;
    }

    public void showCLI(){
        do{
            if(waitForNextRound()){
                Thread thread = new Thread(new CLIThread());
                thread.start();
                while(!thread.isInterrupted()){ }
            }
        } while(!controller.getTurnController().isGameEnded());
    }

    private class Background implements Runnable{
        private boolean isTerminated;
        private Thread parent;
        public Background(Thread parent){
            this.parent = parent;
            this.isTerminated = false;
        }

        public void setTerminated(boolean terminated) {
            this.isTerminated = terminated;
        }

        @Override
        public void run() {
            while(controller.getMainController().isMyTurn()&&!isTerminated){}
            if(!isTerminated){
                parent.interrupt();
                System.out.println("Hai terminato il turno");
            }else{
                System.out.println("Hai finito il tempo a disposizione");
            }
        }
    }

    private class CLIThread implements Runnable{
        @Override
        public void run() {
            boolean normalMoveUsed = false;
            boolean toolUsed = false;
            if(!controller.getTurnController().isGameEnded()) {
                Scanner scanner = new Scanner(System.in);
                Background background = new Background(Thread.currentThread());
                Thread backgroundThread = new Thread(background);
                backgroundThread.start();

                normalMoveUsed = false;
                toolUsed = false;
                System.out.println(ansi().fg(getOptionColor()).a("E' il tuo turno! Decidi cosa vuoi fare nei prossimi 40 secondi"));

                //option 1
                System.out.println(ansi().a(option1 + ") ").fg(getDefaultColor()).a("Mostra le carte pubbliche").fg(getOptionColor()));

                //option 2
                System.out.println(ansi().a(option2 + ") ").fg(getDefaultColor()).a("Mostra le carte utensili").fg(getOptionColor()));

                //option 3
                if (controller.getMainController().isSinglePlayer())
                    System.out.println(ansi().a(option3 + ") ").fg(getDefaultColor()).a("Mostra le carte private").fg(getOptionColor()));
                else
                    System.out.println(ansi().a(option3 + ") ").fg(getDefaultColor()).a("Mostra la carta privata").fg(getOptionColor()));

                //option 4
                System.out.println(ansi().a(option4 + ") ").fg(getDefaultColor()).a("Mostra lo schema attuale").fg(getOptionColor()));

                //option 5
                System.out.println(ansi().a(option5 + ") ").fg(getDefaultColor()).a("Utilizza una carta tool").fg(getOptionColor()));

                //option 6
                System.out.println(ansi().a(option6 + ") ").fg(getDefaultColor()).a("Prendi un dado e piazzalo sullo schema").fg(getOptionColor()));

                //option 7
                System.out.println(ansi().a(option7 + ") ").fg(getDefaultColor()).a("Mostra draftpool").fg(getOptionColor()));

                //option 8
                System.out.println(ansi().a(option7 + ") ").fg(getDefaultColor()).a("Mostra tracciato dei round").fg(getOptionColor()));

                //option MAX_OPTIONS - 1
                if (!controller.getMainController().isSinglePlayer())
                    System.out.println(ansi().a(MAX_OPTIONS - 1 + ") ").fg(getDefaultColor()).a("Mostra gli schemi degli altri giocatori").fg(getOptionColor()));

                //last option (10)
                System.out.println(ansi().a(MAX_OPTIONS + ") ").fg(getDefaultColor()).a("Passa il turno").fg(getOptionColor()));

                String line;
                int option;
                try {
                    while (true) {

                        if(controller.getMainController().isMyTurn())
                            line = scanner.nextLine();
                        else {
                            System.out.println(ansi().fg(getWarningColor()).a("E' scaduto il tempo, aspetta il prossimo turno").fg(getDefaultColor()));
                            break;
                        }
                        while (!line.equals("1") && !line.equals("2") && !line.equals("3") && !line.equals("4") && !line.equals("5") && !line.equals("6") && !line.equals("7") && !line.equals("8") && !line.equals("9")&&!line.equals("10")) {
                            System.out.println("Scelta inserita errata, inserire nuova scelta!");
                            line = scanner.nextLine();
                        }
                        option = Integer.parseInt(line);

                        switch (option) {
                            case option1:
                                CLIPublicCards.showPublicCards(controller.getCardsController().getPublicObjectives());
                                break;
                            case option2:
                                CLIToolCards.showToolCards(controller.getCardsController().getToolCards());
                                break;
                            case option3:
                                CLIPrivateCards.showMultiPrivateCard(controller.getCardsController().getPrivateObjective());
                                break;
                            case option4:
                                CLIScheme.showScheme(controller.getCardsController().getScheme());
                                break;
                            case option5:
                                if(toolUsed){
                                    System.out.println("Hai già usato una tool card in questo turno!");
                                }else{
                                    if(CLIToolCards.useToolCards(controller.getCardsController().getToolCards(), controller)){
                                        toolUsed=true;
                                    }else {
                                        toolUsed=false;
                                    }

                                }
                                break;
                            case option6:
                                if(normalMoveUsed){
                                    System.out.println("Hai      già piazzato un dado in questo turno!");
                                }else{
                                    if(CLIToolCards.useNormalMove(controller)){
                                        normalMoveUsed=true;
                                        System.out.println("Sono riuscito a piazzare il dado!");
                                    }else {
                                        System.out.println("Non sono riuscito a piazzare il dado!");
                                        normalMoveUsed=false;
                                    }

                                }
                                break;
                            case option7:
                                CLIDraftPool.showDraftPool(controller.getTableController().getDraftPool());
                                break;
                            case option8:
                                CLIRoundTrace.showRoundTrace(controller.getTableController().getRoundTrace());
                                break;
                            case MAX_OPTIONS - 1: {
                                controller.getTableController().showOtherPlayerSchemes();
                                break;
                            }
                            case MAX_OPTIONS:
                                System.out.println(ansi().fg(getWarningColor()).a("Passo il turno...").fg(getDefaultColor()));
                                controller.getMainController().setMyTurn(false);
                                controller.getTurnController().endMyTurn();
                                background.setTerminated(true);
                                Thread.currentThread().interrupt();
                                break;
                        }
                        //exit from menu = pass the turn
                        if (option == MAX_OPTIONS)
                            break;
                    }
                } catch (IOException i) {
                    System.err.println("Errore dalla CLITurnMenu");
                }
            }
        }
    }
}