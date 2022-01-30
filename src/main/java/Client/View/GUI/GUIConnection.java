package Client.View.GUI;

import Client.Client;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIConnection {
    private Stage stage;

    public GUIConnection(Stage stage){
        this.stage = stage;
    }

    /**
     * shows GUI for RMI or Socket choice
     * @see GUIServerSetup for next possible choice
     * @author Fabrizio Siciliano
     * */
    public void showGUI(){
        Client.setBackgroundImage(Client.getSetupBackeground());

        AnchorPane layer = new AnchorPane();
        layer.autosize();
        VBox box = new VBox(20);
        box.autosize();
        box.setAlignment(Pos.CENTER);

        Button RMIButton = new Button("RMI");
        RMIButton.autosize();
        RMIButton.setOnAction(e->{
            GUIServerSetup pane = new GUIServerSetup(stage);
            pane.showGUI(true);
        });
        RMIButton.setStyle("-fx-background-color: #e88e40; -fx-font-weight: bolder");

        Button socketButton = new Button("Socket");
        socketButton.autosize();
        socketButton.setStyle("-fx-background-color: #e88e40; -fx-font-weight: bolder");
        socketButton.setOnAction(e->{
            GUIServerSetup pane = new GUIServerSetup(stage);
            pane.showGUI(false);
        });

        box.getChildren().addAll(RMIButton, socketButton);
        layer.getChildren().add(box);

        AnchorPane.setLeftAnchor(box, 0.0);
        AnchorPane.setRightAnchor(box, 0.0);
        AnchorPane.setTopAnchor(box, 0.0);
        AnchorPane.setBottomAnchor(box, 0.0);
        Client.getRootLayout().setCenter(layer);
    }
}
