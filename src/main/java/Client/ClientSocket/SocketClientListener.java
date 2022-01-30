package Client.ClientSocket;

import Client.View.ViewControllers.VIEWController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class SocketClientListener implements Runnable{
    private VIEWController controller;
    private BufferedReader in;
    private PrintWriter out;
    private static final String gameStarted = "gamestarted";
    private static final String playerNotAvailable = "playernotavailable";
    private static final String startTurn = "startturn";
    private static final String endTurn = "endturn";

    private static final String regex = "/";

    public SocketClientListener(VIEWController controller, BufferedReader in, PrintWriter out){
        this.controller = controller;
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        String command;
        String input;
        try {
            input = in.readLine();
            List<String> splitted = Arrays.asList(input.split("/"));
            command = splitted.get(0);
            switch (command){
                case gameStarted:
                    controller.getMainController().setGameStarted(true);
                    break;
                case playerNotAvailable:
                    controller.getTableController().playerNotAvailable(splitted.get(1));
                    break;
                case startTurn:
                    controller.getTurnController().startTurn();
                    break;
                case endTurn:
                    controller.getMainController().setMyTurn(false);
                    break;
                default:
                    System.out.println("Invalid command from server");
                    break;
            }
        } catch (IOException i){
            System.err.println("Server disconnected, closing application...");
            System.exit(0);
        }
    }
}
