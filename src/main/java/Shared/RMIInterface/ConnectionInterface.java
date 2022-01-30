package Shared.RMIInterface;

import Shared.Exceptions.LobbyFullException;
import Shared.Exceptions.ObjectNotFoundException;
import Shared.Color;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

public interface ConnectionInterface extends Remote {
    boolean login(String newName, Object client) throws RemoteException;

    //setup the server-client connection
    void areYouAlive(String name) throws RemoteException;
    void setDifficulty(int difficult) throws RemoteException;
    void removePlayer(String name) throws ObjectNotFoundException, RemoteException;
    //String joinLobby(String name, boolean RMI, boolean singlePlayer) throws LobbyFullException, PlayerNameAlreadyInLobbyException, RemoteException;
    //ArrayList<Lobby> getLobbies() throws RemoteException;
    ArrayList<String> getOtherPlayerName(String actualPlayerName) throws  RemoteException;
    Color getPlayerColor(String playerName) throws RemoteException;
    boolean arePlayersReady() throws RemoteException;

    String joinGame(String playerName, boolean RMI) throws LobbyFullException, ObjectNotFoundException, RemoteException;

    Map<String, Integer> getRanking() throws RemoteException;
}