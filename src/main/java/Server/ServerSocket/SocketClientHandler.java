package Server.ServerSocket;

import Server.ServerSocket.SocketCommandHandlers.ConnectionCommandHandler;
import Server.ServerSocket.SocketCommandHandlers.TableCommandHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class SocketClientHandler {
    private BufferedReader inputFromClient;
    private PrintWriter outputToClient;

    /*-----------------------------Static attributes---------------------------------------*/
    private static final String loginCommand = "login";
    private static final String joinGameCommand = "joingame";
    private static final String getRankingCommand = "getranking";
    private static final String getColorCommand = "getcolor";
    private static final String getPrivateObjectiveCommand = "getprivateobjective";
    private static final String getSchemeCardCommand = "getschemecard";

    private static final String regex = "/";
    private ConnectionCommandHandler connectionHandler;
    private TableCommandHandler tableHandler;
    /*-------------------------------------------------------------------------------------*/


    /**
     * @param inputStream input of the server
     * @param outputStream output of the server
     * @author Fabrizio Siciliano
     * */
    public SocketClientHandler(BufferedReader inputStream, PrintWriter outputStream){
        inputFromClient = inputStream;
        outputToClient = outputStream;
        connectionHandler = new ConnectionCommandHandler(/*inputFromClient, */outputToClient);
        tableHandler = new TableCommandHandler(outputToClient);
    }

    /**
     * handles IO buffers for the server
     * @author Fabrizio Siciliano
     * */
    public boolean handleClient(){
        List<String> input;
        String command = "InitialValue";

        while(!command.equals("disconnect")) {
            try {
                String stringInput = inputFromClient.readLine();
                input = Arrays.asList(stringInput.split(regex));
                command = input.get(0);
                switch (command){
                    case loginCommand:
                        connectionHandler.login(this, input.get(1));
                        break;
                    case joinGameCommand:
                        connectionHandler.joinGame(input.get(1));
                        break;
                    case getRankingCommand:
                        connectionHandler.getRanking();
                        break;
                    case getColorCommand:
                        connectionHandler.getColor(input.get(1));
                        break;
                    case getPrivateObjectiveCommand:
                        tableHandler.getPrivateObjective(input.get(1));
                        break;
                    case getSchemeCardCommand:
                        tableHandler.getSchemeCard();
                        break;
                    default:
                        System.out.println("[Socket Server]\tInput command not defined: " + command);
                        break;
                }

            } catch (IOException i) {
                //TODO: Exception catched, handle client disconnection!
            }
        }
        //TODO: to be changed
        return false;
    }

    public ConnectionCommandHandler getConnectionHandler() {
        return connectionHandler;
    }

    public TableCommandHandler getTableHandler(){
        return tableHandler;
    }

    public static String getRegex(){
        return regex;
    }
}
