package Shared.Model.Schemes;

import Server.SchemeCardsHandler.SchemeCardMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SchemeCardMapperTest {
    private static SchemeCardMapper SCM;

    @Before
    public void getCardMapper() {
        SCM = SchemeCardMapper.getCardMapper();
        Assert.assertNotNull(SCM);
    }

    @Test
    public void readAllCards() {
        int count = 0;
        File file = new File("CustomCards/SchemeCardMap.txt");
        try(BufferedReader buffer = new BufferedReader(new FileReader(file))){
            for(String currentLine = buffer.readLine(); currentLine!=null; currentLine = buffer.readLine()){
                count++;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        Assert.assertTrue(count == SCM.readAllCards().size());
    }

    @Test
    public void saveNewCard() throws Exception {
        /*
        ArrayList<SchemeCard> result;
        String[] frontCellNumbers1 = new String[]{"0","0","1","0","0","1","0","3","0","2","0","5","4","6","0","0","0","5","0","0"};
        String[] frontCellColors1 = new String[]{Color.WHITE.toString(),Color.WHITE.toString(),Color.WHITE.toString(),Color.WHITE.toString(), Color.WHITE.toString(),Color.WHITE.toString(), Color.GREEN.toString(), Color.WHITE.toString(),Color.BLUE.toString(),Color.WHITE.toString(),Color.BLUE.toString(), Color.WHITE.toString(), Color.WHITE.toString(), Color.WHITE.toString(), Color.WHITE.toString(), Color.GREEN.toString(), Color.WHITE.toString(), Color.BLUE.toString(), Color.WHITE.toString(),Color.GREEN.toString(), Color.WHITE.toString()};
        String[] frontCellNumbers2 = new String[]{"0","1","0","0","4","6","0","2","5","0","1","0","5","3","0","0","0","0","0","0"};
        String[] frontCellColors2 = new String[]{Color.WHITE.toString(),Color.WHITE.toString(), Color.GREEN.toString(), Color.PURPLE.toString(), Color.WHITE.toString(), Color.WHITE.toString(), Color.PURPLE.toString(), Color.WHITE.toString(), Color.WHITE.toString(), Color.GREEN.toString(), Color.WHITE.toString(), Color.GREEN.toString(), Color.WHITE.toString(), Color.WHITE.toString(), Color.PURPLE.toString(),Color.WHITE.toString(),Color.WHITE.toString(),Color.WHITE.toString(),Color.WHITE.toString(),Color.WHITE.toString() };

        result = SCM.readAllCards();
        for (int i=0; i<result.size();i++){
            if(result.get(i).getFront().getName().equals("LuxMondi")&&result.get(i).getRear().getName().equals("LuxAstram")){
                Assert.assertTrue(false);
            }
        }
        Asker asker = new Asker(System.in, System.out);
        SCM.saveNewCard("LuxMondi","5", frontCellNumbers1,frontCellColors1,"LuxAstram", "3", frontCellNumbers2, frontCellColors2);

        boolean isOk=false;
        File file = new File("CustomCards/SchemeCardMap.txt");
        try(BufferedReader bufferedReader=new BufferedReader(new FileReader(file))){
            for(String line = bufferedReader.readLine();line!=null;line=bufferedReader.readLine()){
                if(line.equals("LuxMondi-LuxAstram")){
                    isOk=true;
                    break;
                }
            }
            Assert.assertTrue(isOk);
        }

        File file2 = new File("CustomCards/LuxMondi-LuxAstram.xml");
        Assert.assertTrue(file2.exists());


*/
    }

    @Test
    public void removeCardNamed() {
        String card = "LuxMondi-LuxAstram";

        SCM.removeCardNamed(card);

        File fileToDelete = new File("CustomCards/" + card + ".txt");
        Assert.assertTrue(!fileToDelete.exists());
    }

}