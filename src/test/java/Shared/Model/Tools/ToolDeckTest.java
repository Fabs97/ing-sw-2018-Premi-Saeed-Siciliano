package Shared.Model.Tools;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ToolDeckTest {

    private ToolDeck deck;

    @Before
    public void setUp() throws Exception {
        deck = new ToolDeck();
        Assert.assertNotNull(deck);
        for(int i=0; i<deck.getMazzo().size(); i++){
            Assert.assertNotNull(deck.getMazzo().get(i));
            Assert.assertEquals(i+1, deck.getMazzo().get(i).getSerialNo());
        }
    }

    @Test
    public void getMazzo() {
        Assert.assertNotNull(deck.getMazzo());
    }

    @Test
    public void shuffle() {
        ArrayList<ToolCard> beforeShuffle = new ArrayList<>();
        beforeShuffle = (ArrayList<ToolCard>) deck.getMazzo().clone();
        Assert.assertTrue(deck.getMazzo().size()==12);
        Assert.assertTrue(beforeShuffle.size()==12);

        deck.shuffle();

        Assert.assertTrue(deck.getMazzo().size()==12);
        Assert.assertTrue(deck.getMazzo().containsAll(beforeShuffle));

    }

    @Test
    public void pick() {
        int beforePicking = deck.getMazzo().size();
        ToolCard firstExtracted = deck.pick();

        Assert.assertFalse(deck.getMazzo().contains(firstExtracted));
        Assert.assertTrue(deck.getMazzo().size() != beforePicking);

        ToolCard secondExtracted = deck.pick();

        Assert.assertNotEquals(firstExtracted, secondExtracted);
        Assert.assertEquals(beforePicking - 2, deck.getMazzo().size());
    }
}