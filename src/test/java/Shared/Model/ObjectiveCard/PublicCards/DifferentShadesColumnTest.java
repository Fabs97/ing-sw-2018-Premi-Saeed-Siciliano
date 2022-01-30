package Shared.Model.ObjectiveCard.PublicCards;

import Shared.Exceptions.IllegalColorException;
import Shared.Color;
import Shared.Model.Dice.Dice;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Schemes.SchemeCell;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DifferentShadesColumnTest {

    private DifferentShadesColumn card;
    @Before
    public void setUp() throws Exception {
        card = new DifferentShadesColumn("Sfumature diverse - Colonna", "Colonne senza sfumature ripetute",4);
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
            Dice yellowDice = new Dice(Color.YELLOW);
            yellowDice.setTop(1);
            Dice greenDice = new Dice(Color.GREEN);
            greenDice.setTop(2);
            Dice redDice = new Dice(Color.RED);
            redDice.setTop(3);
            Dice blueDice = new Dice(Color.BLUE);
            blueDice.setTop(4);
            Dice purpleDice = new Dice(Color.PURPLE);
            purpleDice.setTop(5);

            schemeCell[0][0].setDado(yellowDice);
            schemeCell[1][0].setDado(greenDice);
            schemeCell[2][0].setDado(redDice);
            schemeCell[3][0].setDado(blueDice);

            schemeCell[0][2].setDado(blueDice);
            schemeCell[1][2].setDado(yellowDice);
            schemeCell[2][2].setDado(yellowDice);
            schemeCell[3][2].setDado(greenDice);

            schemeCell[0][1].setDado(greenDice);
            schemeCell[2][1].setDado(blueDice);
            schemeCell[3][1].setDado(redDice);

            schemeCell[0][3].setDado(greenDice);
            schemeCell[1][3].setDado(blueDice);
            schemeCell[2][3].setDado(redDice);
            schemeCell[3][3].setDado(redDice);

            schemeCell[0][4].setDado(blueDice);
            schemeCell[1][4].setDado(redDice);
            schemeCell[2][4].setDado(purpleDice);
            schemeCell[3][4].setDado(blueDice);

            Scheme scheme = new Scheme("test", 5, schemeCell);
            Assert.assertEquals(4, card.calculatePoints(scheme));
        }
        catch(IllegalColorException e){/*never reached*/}
    }
}