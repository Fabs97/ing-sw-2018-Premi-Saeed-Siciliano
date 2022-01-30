package Shared.Model.Schemes;

import Shared.Color;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SchemeTest {

    private Scheme scheme;
    @Before
    public void setUp() throws Exception {
        SchemeCell[][] schemeCells = new SchemeCell[4][5];
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++){
                schemeCells[i][j]=new SchemeCell(i);
            }
        }
        scheme = new Scheme("Lux Mondi", 4, schemeCells);
    }

    @Test
    public void getName() {
        Assert.assertEquals("Lux Mondi", scheme.getName());
    }

    @Test
    public void getFavors() {
        Assert.assertEquals(4, scheme.getFavors());
    }

    @Test
    public void getScheme() {
        for(int i=0; i<4; i++){
            for(int j=0; j<5; j++){
                Assert.assertEquals(i, scheme.getScheme()[i][j].getNum());
                Assert.assertNull(scheme.getScheme()[i][j].getDado());
                Assert.assertTrue(scheme.getScheme()[i][j].getColor().equals(Color.WHITE));
            }
        }
    }

    @Test
    public void equals() {
        SchemeCell[][] fakeCells = new SchemeCell[4][5];
        for (int i=0; i<4; i++){
            for(int j=0; j<5; j++){
                fakeCells[i][j] = new SchemeCell();
            }
        }
        Scheme fakeScheme = new Scheme("Lux Mondi", 4, fakeCells);
        Assert.assertFalse(scheme.equals(fakeScheme));

        Scheme fakeScheme2 = new Scheme("Pippo" , 5, fakeCells);
        Assert.assertFalse(scheme.equals(fakeScheme2));

        SchemeCell[][] trueCells = new SchemeCell[4][5];
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++){
                trueCells[i][j]=new SchemeCell(i);
            }
        }

        Scheme trueScheme = new Scheme("Lux Mondi" , 4, trueCells);
        Assert.assertTrue(this.scheme.equals(trueScheme));
    }
}