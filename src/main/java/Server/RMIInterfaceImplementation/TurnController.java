package Server.RMIInterfaceImplementation;

import Shared.RMIInterface.TurnInterface;
import Server.Lobby.Lobby;
import Shared.Player;
import Server.MainServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class TurnController extends UnicastRemoteObject implements TurnInterface {

    public TurnController() throws RemoteException {

    }

    @Override
    public void endMyTurn(String playerName){
        Lobby lobby = MainServer.getLobby();
        for(Player player : lobby.getPlayers()){
            if(player.getName().equals(playerName)) {
                player.setEndTurn(true);
                break;
            }
        }
    }

    @Override
    public boolean isGameEnded() {
        return MainServer.getLobby().isGameEnded();
    }

    /*@Override
    public boolean isMyTurn(String name, String idLobby) {
        Lobby lobby = MainServer.getCurrentLobby(idLobby);
        for (Player player : lobby.getGame().getPlayers()) {
            if (player.getName().equals(name))
                return player.isStartTurn();
        }
        return false;
    }
    @Override
    public void endMyTurn(String name, String idLobby) {
        Lobby lobby = MainServer.getCurrentLobby(idLobby);
        for (Player player : lobby.getGame().getPlayers()) {
            if (player.getName().equals(name)) {
                player.setEndTurn(true);
                player.setStartTurn(false);
                break;
            }
        }
    } */

    @Override
    public int round()
    {
       return MainServer.getLobby().getGameController().getRound();
    }

    @Override
    public boolean isClockwise(){
        return MainServer.getLobby().getGameController().isClockwise();
    }
}
