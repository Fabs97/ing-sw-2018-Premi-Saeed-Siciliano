package Server.Lobby;

import Server.GameController;
import Server.ServerSocket.ServerClient;
import Shared.Exceptions.LobbyFullException;
import Shared.Exceptions.ObjectNotFoundException;
import Server.Game;
import Shared.Player;
import Server.MainServer;
import Server.RMIInterfaceImplementation.ConnectionController;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Lobby extends Thread {

    private int difficult;

    ArrayList<Player> players;

    private String lobbyId;

    private Game game;

    private GameController gameController;

    private boolean singlePlayer;

    private boolean gameStarted;

    private boolean gameEnded;

    private boolean playersReady;

    private final static int MAX_PLAYERS_LOBBY = 4;

    /**
     * @param lobbyId id of the lobby
     * @author Fabrizio Siciliano
     * */
    public Lobby(String lobbyId){
        this.players = new ArrayList<>();
        this.lobbyId = lobbyId;
        this.singlePlayer = false;
        this.game = null;
        this.playersReady = false;
        this.gameStarted = false;
        this.gameEnded = false;
        this.gameController = null;
    }

    @Override
    public void run() {
        //start waiting timer
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0L;
        while (elapsedTime < MainServer.getLobbyTimer()) {
            System.out.flush();
            if(players.size() == MAX_PLAYERS_LOBBY)
                break;
            else if(players.size()<2)
                startTime = System.currentTimeMillis();
            else if(players.size()>=2)
                elapsedTime = System.currentTimeMillis() - startTime;
        }
        //if we get out from the while it means that at least 2 players have joined the lobby
        game = new Game();
        if(!singlePlayer) {
            game.setupMultiGame(players);
        }
        else{
            game.setupSingleGame(players.get(0),difficult);
        }
        gameStarted = true;
        gameController = new GameController(game);
        System.out.print("[RMI Server]\tStarting game with players: " );
        players.forEach(player-> System.out.print(player.getName() + "\t"));

        waitPlayersSetup();
        playersReady = true;
        gameController.playGame(players);
        gameEnded = true;


    }

    /**
     * @return waits for clients' scheme choice
     * @author Fabrizio Siciliano
     * */
    private void waitPlayersSetup(){
        for(Player player : players){
            while(player.getScheme()==null){
                System.out.flush();
            }
        }
    }

    /**
     * @param name name of the player to be added
     * @exception LobbyFullException if lobby's size reaches maximum
     * @exception ObjectNotFoundException if player is not found in lobby
     * @author Fabrizio Siciliano
     * */
    public void addPlayerToLobby(String name) throws LobbyFullException {
        if(!gameStarted) {
            if (players.size() < MAX_PLAYERS_LOBBY) {
                players.add(new Player(name));
            } else
                throw new LobbyFullException("La lobby è piena!");
        } else{
            for(Player player : players){
                if(player.getName().equals(name)){
                    player.setAvailable(true);
                    ConnectionController.playerAgainAvailable(name);
                    return;
                }
            }
        }
    }

    /**
     * @param name name of the player to be removed
     * @exception ObjectNotFoundException if player is not found in lobby
     * @author Fabrizio Siciliano
     * */
    public void removePlayerFromLobby(String name) {
        for(Player player : players){
            if(player.getName().equals(name)) {
                if(!gameStarted)
                    players.remove(player);
                else
                    player.setAvailable(false);
                System.out.println("[Server]\tRemoved the player :" + name );
                return;
            }
        }
    }

    public void clientNotAvailable(String name){
        for(Player player : players){
            try {
                if(player.isAvailable()) {
                    ServerClient client = ConnectionController.getClient(player.getName());
                    if(client.isRMI())
                        client.getRmiClient().playerNotAvailable(name);
                    else
                        client.getSocketClient().getConnectionHandler().playerNotAvailable(name);
                }
            } catch (RemoteException r){ }
        }
    }

    public String getLobbyId(){
        return lobbyId;
    }

    public Game getGame() {
        return game;
    }

    public boolean arePlayersReady(){
        return playersReady;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public GameController getGameController() {
        return gameController;
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    /*--------------------------Single Player methods------------------------------*/

    public void setDifficulty(int difficult) {
        this.difficult = difficult;
    }

    public boolean isSinglePlayer() {
        return singlePlayer;
    }

    public void setSinglePlayer(boolean singlePlayer) {
        this.singlePlayer = singlePlayer;
    }

    public int getDifficult() {
        return difficult;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder("Lobby" + lobbyId + ". Players: :\n\t");
        for(int i =0; i<players.size(); i++)
            builder.append(i +": " + players.get(i).getName() + "\n\t");
        return builder.toString();
    }
}

