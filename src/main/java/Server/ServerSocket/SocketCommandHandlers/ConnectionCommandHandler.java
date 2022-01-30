package Server.ServerSocket.SocketCommandHandlers;

import Server.MainServer;
import Server.RMIInterfaceImplementation.ConnectionController;
import Server.ServerSocket.SocketClientHandler;
import Shared.Color;
import Shared.Exceptions.LobbyFullException;
import Shared.Exceptions.ObjectNotFoundException;

import java.io.PrintWriter;
import java.util.Map;

public class ConnectionCommandHandler {
    private ConnectionController controller;
    private PrintWriter out;

    public ConnectionCommandHandler(PrintWriter out){
        this.controller = MainServer.getConnectionController();
        this.out = out;
    }

    public void login(SocketClientHandler client, String name){
        out.println(controller.login(name, client));
    }

    public void joinGame(String name){
        try {
            out.println(controller.joinGame(name, false));
        } catch (ObjectNotFoundException o) {
            out.println("ObjectNotFoundException");
        } catch (LobbyFullException l) {
            out.println("LobbyFullException");
        }
    }

    public void getRanking(){
        Map<String, Integer> map = controller.getRanking();
        String toSend = "";
        if(map!=null){
            for(String key : map.keySet()){
                toSend += key + ":" + map.get(key) + "/";
            }
        } else{
            toSend = "null";
        }
        out.println(toSend);
    }

    public void getColor(String name){
        Color color = controller.getPlayerColor(name);
        if (color == null)
            out.println("null");
        else
            out.println(color.toString());
    }

    public void startGame(){
        out.println("gamestarted");
    }

    public void playerNotAvailable(String name){
        out.println("playernotavailable" +
                SocketClientHandler.getRegex() + name);

    }

    public void notifyStartTurn(){
        out.println("startturn");
    }

    public void notifyEndTurn(){
        out.println("endturn");
    }
}
