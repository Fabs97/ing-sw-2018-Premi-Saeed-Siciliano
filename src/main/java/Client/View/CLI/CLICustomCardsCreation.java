package Client.View.CLI;

import Client.View.ViewControllers.VIEWController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static org.fusesource.jansi.Ansi.ansi;

public class CLICustomCardsCreation extends CLI{
    private VIEWController controller;

    public CLICustomCardsCreation(VIEWController controller){
        this.controller = controller;
    }

    /**
     * shows custom cards creation state
     * @author Fabrizio Siciliano
     * */
    public void createNewScheme(){

        System.out.println(ansi().eraseScreen().fg(getOptionColor()));
        System.out.println("****************************************************************************");
        System.out.println("*                                                                          *");
        System.out.println("*               BENVENUTO NELLA CREAZIONE DEGLI SCHEMI CUSTOM              *");
        System.out.println("*                                                                          *");
        System.out.println("****************************************************************************\n");
        String[] names = new String[2];
        String[] favors = new String[2];

        System.out.println(ansi().fg(getOptionColor()).a("Comincia con la prima carta"));
        names[0] = nameScheme();
        ArrayList<String> fromFunction1 = createScheme();
        favors[0] = giveFavors();

        System.out.println(ansi().fg(getOptionColor()).a("Continua con la seconda carta"));
        names[1] = nameScheme();
        ArrayList<String> fromFunction2 = createScheme();
        favors[1] = giveFavors();

        String[] scheme1 = new String[fromFunction1.size()];
        fromFunction1.toArray(scheme1);
        String[] scheme2 = new String[fromFunction2.size()];
        fromFunction2.toArray(scheme2);
        System.out.println(ansi().fg(getOptionColor()).a("Perfetto! Salvo la carta, la ritroverai durante le prossime partite"));

        controller.getSetupController().saveCustomCard(names, favors, scheme1, scheme2);
    }

    private String giveFavors(){
        String favors = "";
        String choice = "";
        do {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.println(ansi().fg(getOptionColor()).a("Inserisci il numero di favori dello schema"));
                favors = br.readLine();

                System.out.println(ansi().fg(getAcceptedColor()).a("Favori inseriti: " + favors).fg(getDefaultColor()));
                System.out.println(ansi().fg(getOptionColor()).a("Va bene questo valore? Scrivi \"SI\" oppure \"NO\"").fg(getDefaultColor()));

                choice = br.readLine();
            } catch (IOException i) {
                System.err.println("IOException in CLICustomCardsCreation");
            }
        }while(choice.equals("SI") || choice.equals("NO"));

        return favors;
    }

    private String nameScheme(){
        String name = "";
        String choice = "";
        do {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.println(ansi().fg(getOptionColor()).a("Inserisci il nome dello schema"));
                name = br.readLine();

                System.out.println(ansi().fg(getAcceptedColor()).a("Nome inserito: " + name).fg(getDefaultColor()));
                System.out.println(ansi().fg(getOptionColor()).a("Va bene questo schema? Scrivi \"SI\" oppure \"NO\"").fg(getDefaultColor()));

                choice = br.readLine();
            } catch (IOException i) {
                System.err.println("IOException in CLICustomCardsCreation");
            }
        }while(choice.equals("SI") || choice.equals("NO"));

        return name;
    }

    private ArrayList<String> createScheme(){
        boolean accepted = false;
        ArrayList<String> newArray = null;
        do {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                System.out.println(ansi().fg(getOptionColor()).a("Quante righe deve avere la nuova carta schema?"));
                int rows = Integer.parseInt(br.readLine());

                System.out.println(ansi().fg(getOptionColor()).a("Quante colonne deve avere la nuova carta schema?"));
                int columns = Integer.parseInt(br.readLine());

                String[][] newSchemeCells = new String[rows][columns];
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++) {
                        while (true) {
                            System.out.println(ansi().fg(getOptionColor()).a("Le celle possono essere:").fg(getDefaultColor()).a("\nB0\tG0\tP0\tR0\tY0\tW0\n\nW1\tW2\tW3\tW4\tW5\tW6"));
                            System.out.println(ansi().fg(getOptionColor()).a("Inserisci il valore della cella " + i + " " + j).fg(getDefaultColor()));

                            String chosenValue = br.readLine();
                            if (chosenValue.equals("B0") || chosenValue.equals("G0") || chosenValue.equals("P0")
                                    || chosenValue.equals("R0") || chosenValue.equals("BY0") || chosenValue.equals("W0")
                                    || chosenValue.equals("W1") || chosenValue.equals("W2") || chosenValue.equals("W3")
                                    || chosenValue.equals("W4") || chosenValue.equals("W5") || chosenValue.equals("W6")) {

                                newSchemeCells[i][j] = chosenValue;
                                System.out.println(ansi().fg(getAcceptedColor()).a("Valore inserito: " + chosenValue).fg(getDefaultColor()));
                                break;
                            } else {
                                System.out.println(ansi().fg(getWarningColor()).a("Opzione non valida, ritenta"));
                            }
                        }
                    }
                }

                if (acceptNewScheme(newSchemeCells, rows, columns)) {
                    accepted = true;
                    newArray = new ArrayList<>(rows + columns);
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < columns; j++) {
                            newArray.add(newSchemeCells[i][j]);
                        }
                    }
                } else {
                    System.out.println(ansi().fg(getWarningColor()).a("Non va bene lo schema eh? Inseriscine uno nuovo allora!"));
                }

            } catch (IOException i) {
                System.err.println("IOException in CLICustomCardsCreation");
            }
        }while(accepted);

        return newArray;
    }

    private boolean acceptNewScheme(String[][] schemeCells, int rows, int columns) {

        System.out.println(ansi().eraseScreen().fg(getOptionColor()));
        System.out.println("****************************************************************************");
        System.out.println("*                                                                          *");
        System.out.println("*                     QUESTO È LO SCHEMA CHE HAI CREATO                    *");
        System.out.println("*                                                                          *");
        System.out.println("****************************************************************************\n");

        //TODO: NOT WORKING! FIX IT!
        for(int i = 0; i< rows; i++){
            for(int j=0; j< columns; j++){
                System.out.print(ansi().fg(getOptionColor()).a(schemeCells[i][j] + "\t"));
            }
            System.out.println();
        }

        try{
            while(true){
                System.out.println(ansi().fg(getOptionColor()).a("Va bene questo schema? Scrivi \"SI\" oppure \"NO\"").fg(getDefaultColor()));
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String choice = br.readLine();
                if(choice.equals("SI")){
                    return true;
                }
                else if(choice.equals("NO")){
                    return false;
                }
                else{
                    System.out.println(ansi().fg(getWarningColor()).a("Opzione non valida"));
                }
            }
        }
        catch (IOException i){ }

        //never reached!
        return false;
    }
}
