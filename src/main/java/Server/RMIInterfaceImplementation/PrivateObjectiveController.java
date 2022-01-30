package Server.RMIInterfaceImplementation;

import Shared.RMIInterface.PrivateObjectiveInterface;
import Server.Lobby.Lobby;
import Shared.Model.ObjectiveCard.PrivateObjective;
import Shared.Player;
import Server.MainServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrivateObjectiveController extends UnicastRemoteObject implements PrivateObjectiveInterface {
    public PrivateObjectiveController() throws RemoteException {

    }

    @Override
    public PrivateObjective getPrivateObjective1(String name){
        Lobby lobby = MainServer.getLobby();
        for(Player player : lobby.getPlayers()){
            if(player.getName().equals(name))
                return player.getPrivateObjective1();
        }
        return null;
    }
    @Override
    public PrivateObjective getPrivateObjective2(String name){
        Lobby lobby = MainServer.getLobby();
        for(Player player : lobby.getPlayers()){
            if(player.getName().equals(name))
                return player.getPrivateObjective2();
        }
        return null;
    }
}
