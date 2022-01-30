package Server.RMIInterfaceImplementation;

import Server.Lobby.Lobby;
import Server.MainServer;
import Server.ServerSocket.ServerClient;
import Server.ServerSocket.SocketClientHandler;
import Shared.Color;
import Shared.Exceptions.LobbyFullException;
import Shared.Exceptions.ObjectNotFoundException;
import Shared.Player;
import Shared.RMIInterface.ConnectionInterface;
import Shared.RMIInterface.RMIClientInterface;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ConnectionController extends UnicastRemoteObject implements ConnectionInterface {

    private volatile static ArrayList<ServerClient> allClients;

    public ConnectionController() throws RemoteException { }

    public static List<ServerClient> getClients(){
        return allClients;
    }

    public static ServerClient getClient(String name){
        for(ServerClient client : allClients){
            if(client.getUsername().equals(name))
                return client;
        }
        return null;
    }

    public static void removeClient(String name){
        try {
            for (ServerClient client : allClients) {
                if (client.getUsername().equals(name)) {
                    allClients.remove(client);
                    return;
                }
            }
        } catch (ConcurrentModificationException c){
            System.err.println("[Server]\tCatched ConcurrentModificationException");
        }
    }

    /**
     * pings all client in map
     * @author Fabrizio Siciliano
     * */
    public void pingAll() {
        Iterator iterator = allClients.iterator();
        while(iterator.hasNext()){
            ServerClient client = (ServerClient) iterator.next();
            if(client.isRMI()){
                try{
                    client.getRmiClient().ping();
                } catch (RemoteException r){
                    System.out.println("[Server]\tFailed to ping " + client.getUsername() + ", disconnecting");
                    iterator.remove();
                    if(MainServer.getLobby()!=null)
                        MainServer.getLobby().removePlayerFromLobby(client.getUsername());
                }
            }
            else{
                //TODO: ping socket clients for connection check
            }
        }
        /*for(ServerClient client : allClients){
            if(client.isRMI()){
                try{
                    client.getRmiClient().ping();
                } catch (RemoteException r){
                    System.out.println("[Server]\tFailed to ping " + client.getUsername() + ", disconnecting");
                    allClients.remove(client);
                    try{
                        MainServer.getLobby().removePlayerFromLobby(client.getUsername());
                    } catch (ObjectNotFoundException o) {
                        System.out.println("Il player con nome " + client + " non è stato trovato");
                    }
                }
            }
            else{
                //TODO: ping socket clients for connection check
            }
        }*/
    }


    /**
     * @param newName name of the client to be added to map
     * @param newClient client to be added to map
     * @return true if login is successful
     * @author Fabrizio Siciliano
     * */
    public boolean login(String newName, Object newClient) {
        if(allClients == null)
            allClients = new ArrayList<>();
        //ping all clients to check valid connections
        pingAll();

        //checks unique name on server
        for(ServerClient client : allClients){
            if(client.getUsername().equals(newName))
                return false;
            else
                break;
        }
        if(newClient instanceof RMIClientInterface) {
            allClients.add(new ServerClient(newName, (RMIClientInterface) newClient));
        } else if(newClient instanceof SocketClientHandler){
            allClients.add(new ServerClient(newName, (SocketClientHandler) newClient));
        }
        System.out.println("[RMI Server]\tClient " + newName + " has entered server");
        return true;
    }

    @Override
    public void areYouAlive(String name){

    }

    @Override
    public void setDifficulty( int difficult){
        MainServer.getLobby().setDifficulty(difficult);
    }

    @Override
    public boolean arePlayersReady(){
        return MainServer.getLobby().arePlayersReady();
    }

    @Override
    public void removePlayer(String name) throws ObjectNotFoundException{
        Lobby lobby = MainServer.getLobby();
        for(Player player : lobby.getPlayers()){
            if(player.getName().equals(name)){
                lobby.removePlayerFromLobby(player.getName());
                break;
            }
        }
    }

   /* public String joinLobby(String playerName, boolean RMI, boolean singlePlayer, String idLobby) throws LobbyFullException, PlayerNameAlreadyInLobbyException {
        if(idLobby!=null){
            for(Lobby lobby : MainServer.getLobbyList()){
                if (lobby.getLobbyId().equals(idLobby)) {

                    //Checks if player with that name is already in the lobby
                    for(NetPlayer player : lobby.getNetPlayers()){
                        if(player.getId().equals(playerName)){
                            throw new PlayerNameAlreadyInLobbyException();
                        }
                    }
                    //if comes out of the for loop, player is not in the lobby, check if it is full
                    if (lobby.getGame().getPlayers().size() < 4) {
                        lobby.addPlayerToLobby(new NetPlayer(playerName, RMI));
                        lobby.setSinglePlayer(singlePlayer);
                        break;
                    }
                    else
                        throw new LobbyFullException("Lobby con id " + idLobby + " piena");
                }
            }
            return idLobby;
        }
        else{
            //creates a new random lobby
            String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
            StringBuilder salt = new StringBuilder();
            Random rnd = new Random();
            while (salt.length() < 18) { // length of the random string.
                int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                salt.append(SALTCHARS.charAt(index));
            }
            String randomString = salt.toString();
            //add to the end so it can immediately be accessed
            MainServer.getLobbyList().add(new Lobby(randomString));
            MainServer.getCurrentLobby(randomString).addPlayerToLobby(new NetPlayer(playerName, RMI));
            MainServer.getCurrentLobby(randomString).setSinglePlayer(singlePlayer);
            return randomString;
        }

    }*/

    /**
     * @param playerName name of the player joining
     * @param RMI boolean for using RMI or socket
     * @author Fabrizio Siciliano
     * */
    @Override
    public String joinGame(String playerName, boolean RMI) throws LobbyFullException, ObjectNotFoundException {
        if(MainServer.getLobby() == null){
            //TODO: set new lobbyID
            MainServer.setLobby(new Lobby("Partita 1"));
            new Thread(MainServer.getLobby()).start();
        }

        MainServer.getLobby().addPlayerToLobby(playerName);

        return MainServer.getLobby().getLobbyId();
    }


    /*public ArrayList<Lobby> getLobbies(){
        return MainServer.getLobbyList();
    }*/

    public Color getPlayerColor(String playerName){
        for(Player player : MainServer.getLobby().getPlayers())
            if(player.getName().equals(playerName))
                return player.getColor();
  
        return null;
    }

    @Override
    public ArrayList<String> getOtherPlayerName(String actualPlayerName){
        ArrayList<String> otherPlayerName = new ArrayList<>();
        Lobby lobby = MainServer.getLobby();

        for(Player player : lobby.getPlayers()) {
            if (!player.getName().equals(actualPlayerName))
                otherPlayerName.add(player.getName());
        }
        return otherPlayerName;
    }

    @Override
    public Map<String, Integer> getRanking() {
        return MainServer.getLobby().getGame().getRanking();
    }

    public static void playerAgainAvailable(String name){
        for(ServerClient client : allClients){
            if(client.isRMI()) {
                try {
                    if(!client.getUsername().equals(name)) {
                        client.getRmiClient().playerAgainAvailable(name);
                    } else {
                        client.getRmiClient().continuePlaying();
                    }
                } catch (RemoteException r){
                    MainServer.getLobby().removePlayerFromLobby(client.getUsername());
                    removeClient(client.getUsername());
                }
            }
            else{
                //TODO: socket else
            }
        }
    }
}
