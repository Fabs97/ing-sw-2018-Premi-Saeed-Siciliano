package Server.ToolCards;

import Shared.Exceptions.CannotUseCardException;
import Shared.Color;
import Shared.Model.Dice.Dice;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class MartellettoTest {
    ArrayList<Dice> draftpool;

    @Before
    public void setUp() throws Exception {
        draftpool = new ArrayList<>();
        Dice dice1 = new Dice(Color.PURPLE);
        dice1.setTop(3);
        Dice dice2 = new Dice(Color.GREEN);
        dice2.setTop(2);
        Dice dice3 = new Dice(Color.YELLOW);
        dice3.setTop(5);
        draftpool.add(0,dice1);
        draftpool.add(1, dice2);
        draftpool.add(2, dice3);
    }

    @Test
    public void reRollDices() {

        //first test
        try{
            Martelletto.reRollDices(draftpool, true);
            Assert.assertEquals(Color.PURPLE, draftpool.get(0).getColor());
            Assert.assertEquals(Color.GREEN, draftpool.get(1).getColor());
            Assert.assertEquals(Color.YELLOW, draftpool.get(2).getColor());
        }catch (CannotUseCardException e){}

        //second test
        try{
            Martelletto.reRollDices(draftpool, false);
            Assert.assertEquals(Color.PURPLE, draftpool.get(0).getColor());
            Assert.assertEquals(3, draftpool.get(0).getTop());
            Assert.assertEquals(Color.GREEN, draftpool.get(1).getColor());
            Assert.assertEquals(2, draftpool.get(0).getTop());
            Assert.assertEquals(Color.YELLOW, draftpool.get(2).getColor());
            Assert.assertEquals(5, draftpool.get(0).getTop());

        }catch (CannotUseCardException e){}


    }
}