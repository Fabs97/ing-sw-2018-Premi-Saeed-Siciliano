package Shared.Model.Schemes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SchemeCardTest {

    private SchemeCard card;

    @Before
    public void setUp() throws Exception {
        SchemeCell[][] frontCells = new SchemeCell[4][5];
        SchemeCell[][] rearCells = new SchemeCell[4][5];
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++){
                frontCells[i][j]=new SchemeCell();
                rearCells[i][j]=new SchemeCell();
            }
        }
        card = new SchemeCard(new Scheme("LuxMondi",4,frontCells),new Scheme("LuxAstram", 5, rearCells));
        System.out.println(card.toString());
    }

    @Test
    public void getFront() {
        Assert.assertTrue(card.getFront().getName().equals("LuxMondi"));
        Assert.assertTrue(card.getFront().getFavors() == 4);
        Assert.assertNotNull(card.getFront());
    }

    @Test
    public void getRear() {
        Assert.assertNotNull(card.getRear());
        Assert.assertTrue(card.getRear().getFavors()==5);
        Assert.assertTrue(card.getRear().getName().equals("LuxAstram"));
    }

    @Test
    public void equals() {
        SchemeCell[][] fakeFrontCells = new SchemeCell[4][5];
        SchemeCell[][] fakeRearCells = new SchemeCell[4][5];
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++){
                fakeFrontCells[i][j]=new SchemeCell();
                fakeRearCells[i][j]=new SchemeCell();
            }
        }
        SchemeCard fakeCard1 = new SchemeCard(new Scheme("LuxMondi", 4, fakeFrontCells), new Scheme("LuxAstram", 5, fakeRearCells));
        Assert.assertTrue(fakeCard1.equals(card));

        SchemeCard fakeCard2 = new SchemeCard(null, null);
        Assert.assertFalse(fakeCard2.equals(card));

        SchemeCard fakeCard3 = new SchemeCard(new Scheme("LuxMondi", 4, fakeFrontCells),null);
        Assert.assertFalse(fakeCard3.equals(card));

        SchemeCard fakeCard4 = new SchemeCard(null, new Scheme("LuxMondi", 4, fakeRearCells));
        Assert.assertFalse(fakeCard4.equals(card));
    }
}