package Server;

import Server.Configuration.ConfigurationLoader;
import Server.RMIInterfaceImplementation.ConnectionController;
import Server.ServerSocket.ServerClient;
import Shared.Model.ObjectiveCard.PublicObjective;
import Shared.Model.Schemes.Scheme;
import Shared.Player;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class GameController {
    private Game game;
    private int round;
    private boolean clockwise;
    private int starterPlayerPos;
    private long turnTimer;
    private int numberOfPlayersAvailable;
    public GameController(Game game){
        round = 0;
        clockwise = true;
        starterPlayerPos = 0;
        this.game = game;
        try{
            ConfigurationLoader configurationLoader = new ConfigurationLoader();
            turnTimer = configurationLoader.getTurnTimer();
        }
        catch (IOException i){
            System.err.println("[Main Server]\tIOException in main\n" + i.getMessage());
        }
    }
    public void playGame(ArrayList<Player> players){
        numberOfPlayersAvailable = players.size();
        if(players.size() == 1){
            playSingleGame(players.get(0));
        }
        else {
            playMultiGame(players);
            for(Player player : players){
                if(player.isAvailable())
                    player.setPoints(calculatePointMulti(player));
                else
                    //if the player is disconneted he will get 0 point
                    player.setPoints(-99);
            }
            orderRanking(players);
        }
    }

    private void playSingleGame(Player player){
        for(int i = 0; i<10; i++) {
            game.setDraftPool(game.getDiceBag().extractNDices(4));
            //playTurn(player);
            //playTurn(player);
            game.setRoundTrace(game.getDraftPool(),i);
        }
    }

    private void playMultiGame(ArrayList<Player> players){
        while(round<10){
            game.setDraftPool(game.getDiceBag().extractNDices((players.size()*2)+1));
            if (starterPlayerPos==players.size())
                starterPlayerPos = 0;
            playRound(players, starterPlayerPos);
            if(numberOfPlayersAvailable<=1)
                return;
            game.setRoundTrace(game.getDraftPool(),round);
            starterPlayerPos++;
            round++;

        }
        starterPlayerPos--;
    }

    private void playRound(ArrayList<Player> players, int i){

        for(int j = 0; j<players.size() - 1 ; j++){
            if(players.get(i).isAvailable()) {
                playTurn(players.get(i), players);
            }
            if(numberOfPlayersAvailable<=1)
                return;
            if(i<players.size()-1)
                i++;
            else
                i=0;
        }
        playTurn(players.get(i), players);
        if(numberOfPlayersAvailable<=1)
            return;
        clockwise = false;
        for(int j = 0; j<players.size(); j++){
            if(!players.get(i).isTenagliaARotelleUsed() && players.get(i).isAvailable()) {
                playTurn(players.get(i), players);
                if(numberOfPlayersAvailable<=1)
                    return;
            }
            //it mean that in the last turn this player have used this tool, so now he can't play this turn
            else
                players.get(i).setTenagliaARotelleUsed(false);
            if(i == 0)
                i = players.size() - 1;
            else
                i--;
        }
    }

    private void playTurn(Player player, ArrayList<Player> players){
        ServerClient client = ConnectionController.getClient(player.getName());
        long elapsedTime = 0L;
        long startTime;
        numberOfPlayersAvailable = controllPlayersAvailable(players);
        if(numberOfPlayersAvailable<=1)
            return;
        if(player.isAvailable()){
            try{
                if(client.isRMI()){
                    client.getRmiClient().notifyStartTurn();
                }
                else{
                    client.getSocketClient().getConnectionHandler().notifyStartTurn();
                }
                for(Player p : MainServer.getLobby().getPlayers()) {
                    if (p.isAvailable()) {
                        if(client.isRMI()) {
                            client.getRmiClient().update();
                        } else {
                            //socket client
                            client.getSocketClient().getTableHandler().updateDraftPool(game.getDraftPool());
                            client.getSocketClient().getTableHandler().updateRoundTrace(game.getTrace());
                        }
                    }
                }
                startTime = System.currentTimeMillis();
                while(elapsedTime<turnTimer){
                    if(player.isEndTurn()){
                        break;
                    }else{
                        elapsedTime=System.currentTimeMillis()-startTime;
                    }
                }
                if(player.isEndTurn()){
                    player.setEndTurn(false);
                }else{
                    if(client.isRMI())
                        client.getRmiClient().notifyEndTurn();
                    else
                        client.getSocketClient().getConnectionHandler().notifyEndTurn();
                }
            }catch (RemoteException e){
               MainServer.getLobby().removePlayerFromLobby(player.getName());
               MainServer.getLobby().clientNotAvailable(player.getName());
               ConnectionController.removeClient(player.getName());
               numberOfPlayersAvailable--;
            }
        }
    }


    private void calculatePointSingle(Player player){
        //objective point
        //select pv

    }
    private int calculatePointMulti(Player player){
        int point = 0;
        Scheme schema = player.getScheme();
        //public objective point
        for(PublicObjective publicObjective : game.getPublicObjectives()){
            point += publicObjective.calculatePoints(schema);
        }
        //private objective point
        int privateObjectivePoints = player.getPrivateObjective1().calculatePoints(schema);
        point += privateObjectivePoints;
        player.setPrivateObjectivePoints(privateObjectivePoints);
        //remaining favours
        point += player.getFavours();
        //blank cell in the scheme
        int blank = 0;
        for(int i=0; i<schema.getScheme().length; i++){
            for(int j=0; j<schema.getScheme()[i].length; j++){
                if(!schema.getScheme()[i][j].isOccupied())
                    blank++;
            }
        }
        point -= blank;
        return point;
    }

    public int getRound() {
        return round + 1;
    }

    public boolean isClockwise() {
        return clockwise;
    }

    public int getStarterPlayerPos() {
        return starterPlayerPos;
    }

    private void orderRanking(ArrayList<Player> players){
        int max;
        int i;
        int pos = 0;
        int size = players.size();
        for (int j = 0; j < size; j++){
            max = -999;
            i = 0;
            for (Player player : players) {
                if (player.getPoints() > max) {
                    max = player.getPoints();
                    pos = i;
                }
                //if they have the same point we will look to their private objectve points
                else if (player.getPoints() == max){
                    if(player.getPrivateObjectivePoints() > players.get(pos).getPrivateObjectivePoints() ){
                        pos = i;
                    }
                    //in the case that also the objective pounts are same we have to control the favours point
                    else if(player.getPrivateObjectivePoints() == players.get(pos).getPrivateObjectivePoints() ){
                        if(player.getFavours() > players.get(pos).getFavours()){
                            pos = i;
                        }
                        //if also the favours points are same, the winner will who occupies the lowest position of the last round.
                        else if (player.getFavours() == players.get(pos).getFavours()){
                            int starterPos = MainServer.getLobby().getGameController().getStarterPlayerPos();
                            for(int k = 0; k<size; k++ ){
                                if(pos == starterPos) {
                                    pos = i;
                                    break;
                                }
                                else if(i == starterPos)
                                    break;
                                if(starterPos == (size - 1))
                                    starterPos = 0;
                                else
                                    starterPos++;
                            }
                        }
                    }
                }
                i++;
            }
            game.getRanking().put(players.get(pos).getName(), max);
            players.remove(pos);
        }
    }

    private int controllPlayersAvailable(ArrayList<Player> players){
        int available = 0;
        int pos = 0;
        for(Player player : MainServer.getLobby().getPlayers()) {
            try {
                if(player.isAvailable() && ConnectionController.getClient(player.getName()).isRMI()) {
                    ConnectionController.getClient(player.getName()).getRmiClient().ping();
                    if (!players.get(pos).isAvailable()) {
                        ConnectionController.getClient(player.getName()).getRmiClient().playerAgainAvailable(player.getName());
                        players.get(pos).setAvailable(true);
                    }
                    available++;
                }
                //TODO else case (socket)
            } catch (RemoteException r) {
                MainServer.getLobby().removePlayerFromLobby(player.getName());
                MainServer.getLobby().clientNotAvailable(player.getName());
                ConnectionController.removeClient(player.getName());
                players.get(pos).setAvailable(false);
            }
            pos++;
        }
        return available;
    }

}
