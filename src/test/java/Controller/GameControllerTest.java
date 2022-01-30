package Controller;

import Server.Game;
import Server.GameController;
import Shared.Player;
import org.junit.Test;

import java.util.ArrayList;

public class GameControllerTest {
    @Test
    public void playGameTest(){
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("carlo"));
        players.add(new Player("pippo"));
        players.add(new Player("gallo"));
        players.add(new Player("banana"));
        players.get(0).setAvailable(true);
        players.get(1).setAvailable(true);
        players.get(2).setAvailable(true);
        players.get(3).setAvailable(true);
        Game game = new Game();
        GameController gameController = new GameController(game);
        players.get(0).setEndTurn(true);
        players.get(1).setEndTurn(true);
        players.get(2).setEndTurn(true);
        players.get(3).setEndTurn(true);
        gameController.playGame(players);


    }
}
