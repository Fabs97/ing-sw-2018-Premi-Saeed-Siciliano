package Server.SchemeCardsHandler;

import Shared.Model.Schemes.SchemeCard;

import java.io.*;
import java.util.ArrayList;

public class  SchemeCardMapper {
    private static SchemeCardMapper instance;
    private final static String PATH = "src/main/resources/CustomCards/SchemeCardMap.txt";

    private SchemeCardHandler SCH;

    /**
     * Singleton constructor.
     * @author Fabrizio Siciliano
     * */
    private SchemeCardMapper(){
        this.SCH = new SchemeCardHandler();
    }

    /**
     * Singleton getter.
     * @author Fabrizio Siciliano
     * */
    public static SchemeCardMapper getCardMapper() {
        if(instance==null)
            instance = new SchemeCardMapper();
        return instance;
    }

    /**
     * reads all cards in CustomCardsMap.txt
     * @return list of cards from XML package
     * @author Fabrizio Siciliano
     * */
    public ArrayList<SchemeCard> readAllCards(){
        ArrayList<SchemeCard> result = new ArrayList<>();
        try(BufferedReader bufferReader = new BufferedReader(new FileReader(PATH ))){
            for (String currentSchemeFile = bufferReader.readLine(); currentSchemeFile != null; currentSchemeFile = bufferReader.readLine()) {
                //System.out.println(currentSchemeFile);
                result.add(SCH.readCustomCard(currentSchemeFile));
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * saves new custom card's XML
     * @param frontName first card's name to be saved
     * @param frontFavors first card's favors' value to be saved
     * @param frontCellsNumber first ordered list of numbers of cells to be saved
     * @param frontCellsColor first ordered list of colors of cells to be saved
     * @param rearName second card's name to be saved
     * @param rearFavors second card's favors' value to be saved
     * @param rearCellsNumber second ordered list of numbers of cells to be saved
     * @param rearCellsColor second ordered list of colors of cells to be saved
     * @see SchemeCardHandler#buildCustomCard(String, String, String[], String[], String, String, String[], String[])
     * @author Fabrizio Siciliano
     * */
    //checks if card doesn't already exists, if so inserts name in map and creates the new card
    public void saveNewCard(String frontName, String frontFavors, String[] frontCellsNumber, String[] frontCellsColor, String rearName, String rearFavors, String[] rearCellsNumber, String[] rearCellsColor) {
        if (!existsCardNamed(frontName + "-" + rearName)){
            try(FileWriter fileWriter = new FileWriter(PATH + "SchemeCardMap.txt", true);
                BufferedWriter bw = new BufferedWriter(fileWriter);
                PrintWriter out = new PrintWriter(bw)){

                //writes on file SchemeCardMap.txt
                out.println(frontName + "-" + rearName);
                SCH.buildCustomCard(frontName,frontFavors, frontCellsNumber, frontCellsColor, rearName, rearFavors, rearCellsNumber, rearCellsColor);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * checks if card's XML already exists
     * @param name name of the card to be checked
     * @author Fabrizio Siciliano
     * */
    private boolean existsCardNamed(String name){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH + "SchemeCardMap.txt"))){
            String currentCardName;
            while((currentCardName = bufferedReader.readLine())!=null){
                if(currentCardName.equals(name)){
                    System.out.println("found it! Name: " + name);
                    return true;
                }
            }
            return false;
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * removes card's XML from file txt
     * @param name name of the card to be checked
     * @see SchemeCardHandler#removeCustomCard(String)
     * @author Fabrizio Siciliano
     * */
    //removes card from CustomCardsMap and from package XML
    public void removeCardNamed(String name){
        File mapper = new File(PATH + "SchemeCardMap.txt");
        File temporary = new File(PATH + "temporary.txt");

        try(BufferedReader reader = new BufferedReader(new FileReader(mapper));
            BufferedWriter writer = new BufferedWriter(new FileWriter(temporary))){

            String currentCard;

            while ((currentCard = reader.readLine()) != null) {
                String trimmed = currentCard.trim();
                if (!trimmed.equals(name))
                    writer.write(currentCard + System.getProperty("line.separator"));
            }
            reader.close();
            writer.close();

            //copy content of temporary in CustomCardsMap
            try(BufferedWriter overwriter = new BufferedWriter(new FileWriter(mapper));
                BufferedReader overreader = new BufferedReader(new FileReader(temporary))){

                String toOverwrite;
                System.out.println("\nStarting to overwrite...\n\n");

                while((toOverwrite = overreader.readLine())!=null){
                    System.out.println(toOverwrite);
                    overwriter.write(toOverwrite + System.getProperty("line.separator"));
                }

                overreader.close();
                overwriter.close();

                //delete temporary, it becomes useless
                if(temporary.delete() && SCH.removeCustomCard(name)) {
                    System.out.println("Deletion of " + name + " has been successful");
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
}
