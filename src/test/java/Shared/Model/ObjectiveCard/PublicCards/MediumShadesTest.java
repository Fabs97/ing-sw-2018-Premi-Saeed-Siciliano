package Shared.Model.ObjectiveCard.PublicCards;

import Shared.Exceptions.IllegalColorException;
import Shared.Color;
import Shared.Model.Dice.Dice;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Schemes.SchemeCell;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MediumShadesTest {

    private MediumShades card;
    @Before
    public void setUp() throws Exception {
        card = new MediumShades("Sfumature Medie", "Set di 3 & 4 ovunque", 2);
        Assert.assertNotNull(card);
    }

    @Test
    public void calculatePoints() {
        SchemeCell[][] schemeCell = new SchemeCell[4][5];

        for(int i=0; i<4;i++){
            for(int j=0; j<5;j++){
                schemeCell[i][j]=new SchemeCell();
            }
        }
        try {
            Dice dice1 = new Dice(Color.YELLOW);
            dice1.setTop(1);
            Dice dice2 = new Dice(Color.GREEN);
            dice2.setTop(3);
            Dice dice3 = new Dice(Color.RED);
            dice3.setTop(5);
            Dice dice4 = new Dice(Color.BLUE);
            dice4.setTop(4);
            schemeCell[0][0].setDado(dice1);
            schemeCell[0][1].setDado(dice2);
            schemeCell[0][2].setDado(dice4);
            schemeCell[0][3].setDado(dice2);
            schemeCell[0][4].setDado(dice4);
            schemeCell[1][0].setDado(dice1);
            schemeCell[1][1].setDado(dice3);
            schemeCell[1][2].setDado(dice1);
            schemeCell[1][3].setDado(dice4);
            schemeCell[1][4].setDado(dice3);
            schemeCell[2][0].setDado(dice2);
            schemeCell[2][1].setDado(dice4);
            schemeCell[2][2].setDado(dice1);
            schemeCell[2][3].setDado(dice3);
            schemeCell[2][4].setDado(dice1);
            schemeCell[3][0].setDado(dice4);
            schemeCell[3][1].setDado(dice3);
            schemeCell[3][2].setDado(dice2);
            schemeCell[3][3].setDado(dice1);
            schemeCell[3][4].setDado(dice3);
            Scheme scheme = new Scheme("test",5, schemeCell);
            Assert.assertEquals(8,card.calculatePoints(scheme));

            schemeCell[3][0].setDado(dice3);
            Scheme scheme1 = new Scheme("test", 5, schemeCell);
            Assert.assertEquals(8, card.calculatePoints(scheme));
        }
        catch (IllegalColorException e){/*never reached*/}
    }
}