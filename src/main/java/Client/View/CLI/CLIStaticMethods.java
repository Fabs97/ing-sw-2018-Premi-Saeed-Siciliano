package Client.View.CLI;

import Client.View.ViewControllers.VIEWController;

import java.util.Date;

public class CLIStaticMethods {
    private CLIStaticMethods(){ }

    /**
     * @param viewController main client controller
     * @author Fabrizio Siciliano
     * */
    public static void playWithCLI(VIEWController viewController){
        CLI context = new CLI();

        context.setStrategy(new CLIUsername(viewController));
        context.executeStrategy();

        context.setStrategy(new CLIMainMenu(viewController));
        context.executeStrategy();

        if(viewController.getMainController().isSinglePlayer()){
            context.setStrategy(new CLISinglePlayerSetup(viewController));
        }
        else{
            context.setStrategy(new CLIMultiPlayerSetup(viewController));
        }
        context.executeStrategy();

        if(!continueGame(viewController)) {
            context.setStrategy(new CLIPlayerSetup(viewController));
            context.executeStrategy();
            syncToGame(viewController);
        }

        context.setStrategy(new CLITurnMenu(viewController));
        context.executeStrategy();

        context.setStrategy(new CLIEndGame(viewController));
        context.executeStrategy();

        closeGame();


        System.exit(0);
    }

    /**
     * closing CLI state
     * @author Fabrizio Siciliano
     * */
    private static void closeGame(){
        long MAX_TIME_WAITING = 10*1000L; //10 seconds waiting before closing application
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0L;

        while (elapsedTime < MAX_TIME_WAITING) {
            elapsedTime = (new Date()).getTime() - startTime;
        }
    }

    private static void syncToGame(VIEWController controller){
        long MAX_TIME_WAITING = 10*1000L;
        long startTime = System.currentTimeMillis();
        while (!controller.getTurnController().arePlayersReady()) {
            if (System.currentTimeMillis() - startTime >= MAX_TIME_WAITING) {
                System.out.println("Aspetto di sincronizzarmi");
                startTime = System.currentTimeMillis();
            }
        }
    }


    /**
     * shows lobby sync status
     * @author Fabrizio Siciliano
     * */
    private static boolean continueGame(VIEWController viewController) {
        long MAX_TIME_WAITING = 10*1000;
        long startTime = System.currentTimeMillis();
        while (!viewController.getMainController().isGameStarted() && !viewController.getMainController().canContinueGame()) {
            if (System.currentTimeMillis() - startTime >= MAX_TIME_WAITING) {
                System.out.println("Aspetto che la lobby sia pronta");
                startTime = System.currentTimeMillis();
            }
        }

        if(viewController.getMainController().canContinueGame())
            return true;
        else
            return false;
    }
}
