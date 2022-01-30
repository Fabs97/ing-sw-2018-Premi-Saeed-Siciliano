package Shared.Model.RoundTrace;

import Shared.Exceptions.IllegalColorException;
import Shared.Asker;
import Shared.Color;
import Shared.Model.Dice.Dice;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class RoundTraceCellTest {
    private RoundTraceCell cell;

    @Before
    public void setUp() throws Exception {
        cell = new RoundTraceCell();
        Assert.assertNotNull(cell.getCell());
    }

    @Test
    public void getCell() {
        Assert.assertNotNull(cell.getCell());
    }

    @Test
    public void addDicesToTrace() {
        ArrayList<Dice> dicesToAdd = new ArrayList<>();
        try {
            dicesToAdd.add(new Dice(Color.PURPLE));
            dicesToAdd.add(new Dice(Color.YELLOW));
            dicesToAdd.add(new Dice(Color.BLUE));
            dicesToAdd.add(new Dice(Color.RED));
            dicesToAdd.add(new Dice(Color.GREEN));
        }
        catch (IllegalColorException e){/*never reached*/}

        cell.addDicesToTrace(dicesToAdd);

        Assert.assertTrue(cell.getCell().containsAll(dicesToAdd));
    }

    @Test
    public void switchDices() {
        ArrayList<Dice> dicesToAdd = new ArrayList<>();
        try {
            Dice toPop = new Dice(Color.BLUE);
            dicesToAdd.add(new Dice(Color.PURPLE));
            dicesToAdd.add(new Dice(Color.YELLOW));
            dicesToAdd.add(toPop);
            dicesToAdd.add(new Dice(Color.RED));
            dicesToAdd.add(new Dice(Color.GREEN));

            cell.addDicesToTrace(dicesToAdd);

            Dice toPush = new Dice(Color.YELLOW);
            Asker asker = new Asker(System.in, System.out);

            Assert.assertEquals(toPop, cell.switchDices(toPush, toPop));

            Dice wrongDice = new Dice(Color.RED);

            Assert.assertEquals(wrongDice, cell.switchDices(toPush, wrongDice));
        }
        catch(IllegalColorException e){/*never reached*/}
    }
}