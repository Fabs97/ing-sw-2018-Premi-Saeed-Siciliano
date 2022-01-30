package Shared.Model.Tools;

import Shared.Color;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ToolCardTest {

    private ToolCard card;

    @Before
    public void setUp() throws Exception {
        card = new ToolCard("Pinza Sgrossatrice", "Dopo aver scelto un dado, aumenta o diminuisci il valore del dado scelto di 1. Non puoi cambiare un 6 in 1 o un 1 in 6", 1, Color.PURPLE);
        System.out.println(card.toString());
    }

    @Test
    public void getName() {
        Assert.assertEquals("Pinza Sgrossatrice", card.getName());
    }

    @Test
    public void getDescription() {
        Assert.assertEquals("Dopo aver scelto un dado, aumenta o diminuisci il valore del dado scelto di 1. Non puoi cambiare un 6 in 1 o un 1 in 6", card.getDescription());
    }

    @Test
    public void getSerialNo() {
        Assert.assertEquals( 1, card.getSerialNo());
    }

    @Test
    public void getColor() {
        Assert.assertEquals(Color.PURPLE, card.getColor());
    }

    @Test
    public void isUsed() {
        Assert.assertFalse(card.isUsed());
    }

    @Test
    public void useCard() {
        card.useCard();
        Assert.assertTrue(card.isUsed());
    }

}