package Server.ToolCards;

import Shared.Color;
import Shared.Model.Dice.Dice;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Schemes.SchemeCell;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class PennelloPerPastaSaldaTest {
    Scheme scheme;
    SchemeCell[][] schemeCell= new SchemeCell[4][5];
    ArrayList<Dice> draftpool;
    Dice dice1;
    Dice dice2;
    Dice dice3;

    @Before
    public void setUp() throws Exception {
        for(int i=0; i<4;i++){
            for(int j=0; j<5;j++){
                schemeCell[i][j]=new SchemeCell();
            }
        }
        scheme = new Scheme("test", 5, schemeCell);
        draftpool = new ArrayList<>();
        dice1 = new Dice(Color.PURPLE);
        dice1.setTop(3);
        dice2 = new Dice(Color.GREEN);
        dice2.setTop(2);
        dice3 = new Dice(Color.YELLOW);
        dice3.setTop(5);
        draftpool.add(0,dice1);
        draftpool.add(1, dice2);
        draftpool.add(2, dice3);
    }

    @Test
    public void reRollDice() {

        //first test
        //PennelloPerPastaSalda.reRollDice(scheme,draftpool,dice3,0,0);
        Assert.assertEquals(Color.YELLOW, scheme.getScheme()[0][0].getDado().getColor());
        int cont=0;
        for(int i=0; i<4; i++){
            for(int j=0; j<5;j++){
                if(!scheme.getScheme()[i][j].isOccupied()){
                   Assert.assertEquals(Color.WHITE,scheme.getScheme()[i][j].getColor());
                   cont++;
                }
            }
        }
        Assert.assertEquals(19,cont);
        Assert.assertEquals(2, draftpool.size());
        Assert.assertEquals(dice1, draftpool.get(0));
        Assert.assertEquals(dice2, draftpool.get(1));

        //second test
        //PennelloPerPastaSalda.reRollDice(scheme,draftpool,dice2,3,3);
        cont=0;
        for(int i=0; i<4; i++){
            for(int j=0; j<5;j++){
                if(!scheme.getScheme()[i][j].isOccupied()){
                    Assert.assertEquals(Color.WHITE,scheme.getScheme()[i][j].getColor());
                    cont++;
                }
            }
        }
        Assert.assertEquals(19,cont);
        Assert.assertEquals(2, draftpool.size());
        Assert.assertEquals(dice1, draftpool.get(0));
        Assert.assertEquals(dice2, draftpool.get(1));



    }
}