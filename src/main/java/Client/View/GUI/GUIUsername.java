package Client.View.GUI;

import Client.Client;
import Client.View.ViewControllers.VIEWController;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.awt.*;

public class GUIUsername{
    private VIEWController controller;

    private static final double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    public GUIUsername(VIEWController controller){
        this.controller = controller;
    }

    /**
     * shows GUI for user's name input
     * @see GUIMainMenu for next possible choice
     * @author Fabrizio Siciliano
     * */
    public void showGUI(){
        AnchorPane layer = new AnchorPane();

        Client.setBackgroundImage(Client.getMainBackground());

        VBox box = new VBox(10);
        box.autosize();
        box.setAlignment(Pos.CENTER);

        TextField textField = new TextField("Inserisci il tuo username e premi INVIO");
        textField.setOpacity(1.0);
        textField.autosize();
        textField.setMaxWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2);
        textField.setDisable(true);
        textField.setStyle("-fx-font-weight: bolder; ");

        TextField username = new TextField();
        username.autosize();
        username.setPromptText("Scrivi qua il tuo username");
        username.setAlignment(Pos.CENTER);
        username.setOnAction(e-> {
            controller.getMainController().insertUsername(username.getText());
            if(controller.getSetupController().login()) {
                GUIMainMenu secondPane = new GUIMainMenu(controller);
                secondPane.showGUI();
            } else{
                username.setText("");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("LOGIN ERROR");
                alert.setContentText("Login andato male, prova con un altro username");
                alert.showAndWait();
            }
        });

        box.getChildren().addAll(textField, username);
        layer.getChildren().add(box);
        AnchorPane.setLeftAnchor(box, screenHeight/2);
        AnchorPane.setRightAnchor(box, screenHeight/2);
        AnchorPane.setTopAnchor(box, 0.0);
        AnchorPane.setBottomAnchor(box, 0.0);

        Client.getRootLayout().setCenter(layer);
    }
}
