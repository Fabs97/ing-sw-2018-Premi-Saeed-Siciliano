package Server.ToolCards;

import Shared.Color;
import Shared.Model.Dice.Dice;
import Shared.Model.Dice.DiceBag;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Schemes.SchemeCell;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DiluentePerPastaSaldaTest {
    DiceBag diceBag = new DiceBag();
    Dice dice1;
    Dice dice2;
    Dice dice3;
    Scheme scheme;
    SchemeCell[][] schemeCell= new SchemeCell[4][5];

    @Before
    public void setUp() throws Exception {
        while(diceBag.getDicesLeft()!=0){
            diceBag.extract();
        }
        for(int i=0; i<4;i++){
            for(int j=0; j<5;j++){
                schemeCell[i][j]=new SchemeCell();
            }
        }
        scheme = new Scheme("test", 5, schemeCell);
        dice1 = new Dice(Color.GREEN);
        dice1.setTop(1);
        dice2 = new Dice(Color.GREEN);
        dice2.setTop(2);
        dice3 = new Dice(Color.RED);
        dice3.setTop(3);



    }

    @Test
    public void changeDiceTop() {
        //try{
            //DiluentePerPastaSalda.changeDiceTop(diceBag, dice3, 5, scheme, 0 , 0);
            //DiluentePerPastaSalda.changeDiceTop(diceBag,dice2,7,scheme,0,0);
            Assert.assertEquals(5, scheme.getScheme()[0][0].getDado().getTop());
            Assert.assertEquals(Color.RED, scheme.getScheme()[0][0].getDado().getColor());
            int cont=0;
            for(int i=0; i<4;i++){
                for(int j=0; j<5; j++){
                    if(!scheme.getScheme()[i][j].isOccupied()){
                        Assert.assertEquals(Color.WHITE, scheme.getScheme()[i][j].getColor());
                        cont++;
                    }
                }
            }
            Assert.assertEquals(19, cont);


        /*}
        catch (WrongInputException e){
            Assert.assertEquals(5, scheme.getScheme()[0][0].getDado().getTop());
            Assert.assertEquals(Color.RED, scheme.getScheme()[0][0].getDado().getColor());
            int cont=0;
            for(int i=0; i<4;i++){
                for(int j=0; j<5; j++){
                    if(!scheme.getScheme()[i][j].isOccupied()){
                        Assert.assertEquals(Color.WHITE, scheme.getScheme()[i][j].getColor());
                        cont++;
                    }
                }
            }
            Assert.assertEquals(19, cont);
        }*/
        //catch (CannotPlaceDiceException e){}
    }
}