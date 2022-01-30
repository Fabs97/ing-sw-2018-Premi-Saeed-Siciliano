package Shared.Model.Dice;

import Shared.Color;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DiceTest {

    private Dice dadoVerde;
    private Dice dadoGiallo;
    private Dice dadoViola;
    private Dice dadoBlu;
    private Dice dadoRosso;

    @Before
    public void setUp() throws Exception {

        this.dadoBlu = new Dice(Color.BLUE);
        this.dadoGiallo = new Dice(Color.YELLOW);
        this.dadoRosso = new Dice(Color.RED);
        this.dadoViola = new Dice(Color.PURPLE);
        this.dadoVerde = new Dice(Color.GREEN);
    }

    @Test
    public void getColor() {
        Assert.assertEquals(Color.GREEN, dadoVerde.getColor());
        Assert.assertEquals(Color.BLUE, dadoBlu.getColor());
        Assert.assertEquals(Color.RED, dadoRosso.getColor());
        Assert.assertEquals(Color.PURPLE, dadoViola.getColor());
        Assert.assertEquals(Color.YELLOW, dadoGiallo.getColor());
    }

    @Test
    public void getTop() {
        Assert.assertEquals(1, dadoVerde.getTop());
        Assert.assertEquals(1, dadoBlu.getTop());
        Assert.assertEquals(1, dadoRosso.getTop());
        Assert.assertEquals(1, dadoViola.getTop());
        Assert.assertEquals(1, dadoGiallo.getTop());
    }

    @Test
    public void setTop() {
        dadoVerde.setTop(2);
        dadoGiallo.setTop(3);
        dadoViola.setTop(4);
        dadoBlu.setTop(5);
        dadoRosso.setTop(6);
        Assert.assertEquals(2, dadoVerde.getTop());
        Assert.assertEquals(3, dadoGiallo.getTop());
        Assert.assertEquals(4, dadoViola.getTop());
        Assert.assertEquals(5, dadoBlu.getTop());
        Assert.assertEquals(6, dadoRosso.getTop());
    }

    @Test
    public void roll() {
        dadoRosso.roll();
        dadoBlu.roll();
        dadoVerde.roll();
        dadoViola.roll();
        dadoGiallo.roll();
        Assert.assertTrue(dadoRosso.getTop()>= 1 && dadoRosso.getTop()<=6);
        Assert.assertTrue(dadoBlu.getTop()>= 1 && dadoBlu.getTop()<=6);
        Assert.assertTrue(dadoVerde.getTop()>= 1 && dadoVerde.getTop()<=6);
        Assert.assertTrue(dadoGiallo.getTop()>= 1 && dadoGiallo.getTop()<=6);
        Assert.assertTrue(dadoViola.getTop()>= 1 && dadoViola.getTop()<=6);
    }

    @Test
    public void equals() {
        Assert.assertFalse(dadoRosso.equals(dadoBlu));
        Assert.assertFalse(dadoGiallo.equals(dadoRosso));
        Assert.assertFalse(dadoViola.equals(dadoGiallo));
        Assert.assertFalse(dadoVerde.equals(dadoViola));
        Assert.assertFalse(dadoBlu.equals(dadoVerde));
    }
}