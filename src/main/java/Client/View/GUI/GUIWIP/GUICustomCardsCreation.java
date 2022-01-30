package Client.View.GUI.GUIWIP;

/*
public class GUICustomCardsCreation{
    private static final AnchorPane anchorPane;

    static {
        anchorPane = new AnchorPane();
        anchorPane.autosize();
    }

    private static int columnInput;
    private static int rowInput;

    public static void showCustomCardCreationMenu(){

        VBox vbox = new VBox(10);
        vbox.autosize();
        vbox.setAlignment(Pos.CENTER);

        //first box for columns' input
        VBox columnsBox = new VBox(10);
        columnsBox.autosize();

        TextField columnText = new TextField("Inserisci il numero di colonne del nuovo schema");
        columnText.autosize();
        columnText.setAlignment(Pos.CENTER);
        columnText.setDisable(true);
        columnText.setOpacity(1);

        TextField columnField = new TextField();
        columnField.autosize();
        columnField.setAlignment(Pos.CENTER);
        columnField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) {
                if(!newValue.equals(""))
                    columnInput = Integer.parseInt(newValue);
            } else {
                if(oldValue!=null)
                    columnField.setText(oldValue);
                else
                    columnField.setText("");
            }
        });

        //adding children to first box
        columnsBox.getChildren().addAll(columnText, columnField);

        //second box for rows' input
        VBox rowsBox = new VBox(10);
        rowsBox.autosize();

        TextField rowText = new TextField("Inserisci il numero di righe del nuovo schema");
        rowText.autosize();
        rowText.setAlignment(Pos.CENTER);
        rowText.setDisable(true);
        rowText.setOpacity(1);

        TextField rowField = new TextField();
        rowField.autosize();
        rowField.setAlignment(Pos.CENTER);
        rowField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) {
                if(!newValue.equals(""))
                rowInput = Integer.parseInt(newValue);
            } else {
                if(oldValue!=null)
                    rowField.setText(oldValue);
                else
                    rowField.setText("");
            }
        });

        //adding children to second box
        rowsBox.getChildren().addAll(rowText, rowField);

        Button createNewGridButton = new Button("CREA");
        createNewGridButton.autosize();
        createNewGridButton.setAlignment(Pos.CENTER);
        createNewGridButton.setOnAction(e->{
            String col = columnField.getText();
            String row = rowField.getText();
            if(!col.equals("") && !row.equals("")){
                columnInput = (Integer.parseInt(col));
                rowInput = Integer.parseInt(row);

                anchorPane.getChildren().removeAll(vbox);
                createNewGrid();
            }
        });

        vbox.getChildren().addAll(columnsBox, rowsBox, createNewGridButton);


        anchorPane.getChildren().addAll(vbox);
        AnchorPane.setRightAnchor(columnsBox, Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4);
        AnchorPane.setLeftAnchor(columnsBox, Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4);


        AnchorPane.setRightAnchor(rowsBox, Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4);
        AnchorPane.setRightAnchor(rowsBox, Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4);


        AnchorPane.setBottomAnchor(vbox, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/4);
        AnchorPane.setTopAnchor(vbox, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/4);
        AnchorPane.setLeftAnchor(vbox, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
        AnchorPane.setRightAnchor(vbox, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);

        getRootLayout().setCenter(anchorPane);
    }

    private static void createNewGrid(){
        VBox box = new VBox(20);
        box.autosize();
        box.setAlignment(Pos.CENTER);

        GridPane grid = new GridPane();
        grid.autosize();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setMaxHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
        grid.setMaxWidth(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
        grid.setPrefHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
        grid.setPrefWidth(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);

        for(int i=0; i<columnInput; i++){
            for(int j=0; j<rowInput; j++){
                Button newButton = new Button();
                newButton.autosize();
                newButton.setId("background not yet chosen");
                newButton.setAlignment(Pos.CENTER);
                newButton.setOnAction(e->{
                    GUIAlertBox.showCells("Scegli la cella che vuoi inserire", "Images/Dice/");
                    String chosen = SettingsController.getChosenCustomCardsCell();
                    if(chosen != null){
                    BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
                    Background buttonBackground = new Background(new BackgroundImage(new Image(chosen), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bSize));

                    newButton.setId(chosen);
                    newButton.setBackground(buttonBackground);
                    }
                });

                grid.add(newButton, i, j);
            }
        }

        Button createCardButton = new Button("FINITO");
        createCardButton.autosize();
        createCardButton.setAlignment(Pos.CENTER);
        createCardButton.setOnAction(e-> {
            ArrayList<String> toController = new ArrayList<>();
            for(Node child : grid.getChildren()){
                toController.add(child.getId());
            }
            SettingsController.saveCardOnServer(toController, "Images/Dice/");
        });

        box.getChildren().addAll(grid, createCardButton);
        anchorPane.getChildren().addAll(box);
        AnchorPane.setBottomAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
        AnchorPane.setTopAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
        AnchorPane.setLeftAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/4);
        AnchorPane.setRightAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/4);
    }
}*/
