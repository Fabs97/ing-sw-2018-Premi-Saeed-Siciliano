package Shared.Model;

import Server.Game;
import Shared.Player;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class GameTest {
    @Test
    public void setUpSingleGameTester(){
        String name = "pippo";
        int difficult = 3;
        Player player = new Player(name);
        Game game = new Game();
        game.setupSingleGame(player,difficult);
        Assert.assertEquals("pippo", player.getName());
    }
    @Test
    public void setUpMultiGameTester(){
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("carlo"));
        players.add(new Player("pippo"));
        players.add(new Player("gallo"));
        players.add(new Player("banana"));
        Game game = new Game();
        game.setupMultiGame(players);
        Assert.assertNotNull(game.getSchemeCards());
    }
}
