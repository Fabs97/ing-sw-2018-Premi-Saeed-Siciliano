package Server.Lobby;

import Shared.Exceptions.ObjectNotFoundException;

import java.util.ArrayList;

public class LobbyList {
    private ArrayList<Lobby> lobbies;

    private int actualLobbies;

    public LobbyList(){
        this.lobbies = new ArrayList<>();
    }

    public void addLobby(Lobby lobby){
        lobbies.add(lobby);
        actualLobbies++;
    }

    public void removeLobby(Lobby lobby) throws ObjectNotFoundException{
        if(lobbies.contains(lobby)){
            lobbies.remove(lobby);
            actualLobbies--;
        }
        else
            throw new ObjectNotFoundException("Lobby " + lobby.toString() + " non trovata");
    }

    public int getActualLobbies() {
        return actualLobbies;
    }

    public Lobby getLobby(int pos) {
        if(pos<=lobbies.size())
            return lobbies.get(pos);
        else
            return null;
    }

    @Override
    public String toString(){
        String toReturn = new String("");
        for (Lobby lobby : lobbies) {
            toReturn = toReturn.concat(lobby.toString() + "\n");
        }
        return toReturn;
    }
}
