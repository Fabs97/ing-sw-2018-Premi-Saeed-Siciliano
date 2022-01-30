package Server;

import Server.RMIInterfaceImplementation.ConnectionController;
import Server.ServerSocket.ServerClient;
import Shared.Color;
import Shared.Model.Dice.Dice;
import Shared.Model.Dice.DiceBag;
import Shared.Model.ObjectiveCard.PrivateObjDeck;
import Shared.Model.ObjectiveCard.PublicObjDeck;
import Shared.Model.ObjectiveCard.PublicObjective;
import Shared.Model.RoundTrace.RoundTrace;
import Shared.Model.Schemes.SchemeCard;
import Shared.Model.Schemes.SchemeCardDeck;
import Shared.Model.Tools.ToolCard;
import Shared.Model.Tools.ToolDeck;
import Shared.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;


public class Game {
    volatile HashMap<String, Integer> ranking;

    volatile ArrayList<Dice> draftPool;

    volatile ArrayList<PublicObjective> publicObjectives;

    volatile ArrayList<ToolCard> tools;

    volatile RoundTrace roundTrace;

    volatile DiceBag diceBag;

    volatile ArrayList<SchemeCard> schemeCards;

    volatile SchemeCardDeck schemeCardDeck = new SchemeCardDeck();


    public Game(){
        schemeCardDeck.buildDeck();
        publicObjectives = new ArrayList<>();
        tools = new ArrayList<>();
        draftPool = new ArrayList<>();
        roundTrace = new RoundTrace();
        diceBag = new DiceBag();
        schemeCards = new ArrayList<>();
        ranking = new HashMap<>();
    }

    public DiceBag getDiceBag(){
        return diceBag;
    }

    public ArrayList<PublicObjective> getPublicObjectives() {
        return publicObjectives;
    }

    public ArrayList<ToolCard> getTools(){
        return tools;
    }

    public ArrayList<Dice> getDraftPool() {
        return draftPool;
    }

    public RoundTrace getTrace(){
        return roundTrace;
    }

    private void startGame(ArrayList<Player> players){
        for(Player player : players){
            try{
                ServerClient client = ConnectionController.getClient(player.getName());
                if(client.isRMI())
                    client.getRmiClient().startGame();
                else
                    client.getSocketClient().getConnectionHandler().startGame();
                player.setAvailable(true);
            } catch (RemoteException r){
                player.setAvailable(false);
            }
        }
    }

    public void setupMultiGame(ArrayList<Player> players){
        PrivateObjDeck privateObjDeck = new PrivateObjDeck();
        privateObjDeck.buildPrivate();
        privateObjDeck.shuffle();
        PublicObjDeck publicObjDeck = new PublicObjDeck();
        publicObjDeck.buildPublic();
        publicObjDeck.shuffle();
        publicObjectives.add(publicObjDeck.pick());
        publicObjectives.add(publicObjDeck.pick());
        publicObjectives.add(publicObjDeck.pick());
        ToolDeck toolDeck = new ToolDeck();
        toolDeck.shuffle();
        tools.add(toolDeck.pick());
        tools.add(toolDeck.pick());
        tools.add(toolDeck.pick());
        SchemeCardDeck schemeCardDeck = new SchemeCardDeck();
        schemeCardDeck.buildDeck();
        schemeCardDeck.shuffle();
        for (int i = 0; i<players.size(); i++) {
            players.get(i).setPrivateObjective1(privateObjDeck.pick());
            switch(i){
                case 0:
                    players.get(i).setColor(Color.RED);
                    break;
                case 1:
                    players.get(i).setColor(Color.BLUE);
                    break;
                case 2:
                    players.get(i).setColor(Color.GREEN);
                    break;
                case 3:
                    players.get(i).setColor(Color.PURPLE);
                    break;
                default:
                    break;
            }

            schemeCards.add(schemeCardDeck.pick());
            schemeCards.add(schemeCardDeck.pick());

        }
        startGame(players);
    }

    public void setupSingleGame(Player player, int difficult){
        PrivateObjDeck privateObjDeck = new PrivateObjDeck();
        privateObjDeck.buildPrivate();
        privateObjDeck.shuffle();
        PublicObjDeck publicObjDeck = new PublicObjDeck();
        publicObjDeck.buildPublic();
        publicObjDeck.shuffle();
        publicObjectives.add(publicObjDeck.pick());
        publicObjectives.add(publicObjDeck.pick());
        ToolDeck toolDeck = new ToolDeck();
        toolDeck.shuffle();
        for(int i = 0; i<difficult; i++){
            tools.add(toolDeck.pick());
        }
        player.setPrivateObjective1(privateObjDeck.pick());
        player.setPrivateObjective2(privateObjDeck.pick());
        player.setColor(Color.RED);

        SchemeCardDeck schemeCardDeck = new SchemeCardDeck();
        schemeCardDeck.buildDeck();
        schemeCardDeck.shuffle();
        schemeCards.add(schemeCardDeck.pick());
        schemeCards.add(schemeCardDeck.pick());

    }
    public void setRoundTrace(ArrayList<Dice> dices, int round){
        roundTrace.getTrace().get(round).addDicesToTrace(dices);
    }
    public void setDraftPool(ArrayList<Dice> draftPool) {
        this.draftPool = draftPool;
    }

    public void setSchemeCards(ArrayList<SchemeCard> schemeCards){
        this.schemeCards = schemeCards;
    }

    public ArrayList<SchemeCard> getSchemeCards(){
        return this.schemeCards;
    }

    public ArrayList<SchemeCard> getSchemeCardDeck(){
        return this.schemeCardDeck.getDeck();
    }

    public HashMap<String, Integer> getRanking() {
        return ranking;
    }

    public void setRanking(HashMap<String, Integer> ranking) {
        this.ranking = ranking;
    }
}