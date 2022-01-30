package Client.View.GUI;

import Client.Client;
import Client.View.ViewControllers.VIEWController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.util.ArrayList;

public class GUIEndGame{
    private VIEWController controller;

    public GUIEndGame(VIEWController controller){
        this.controller = controller;
    }

    public void showGUI() {
        Client.setBackgroundImage(Client.getMainBackground());

        AnchorPane layer = new AnchorPane();
        layer.autosize();

        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);

        ArrayList<String> ranking = null; // TODO: get ranking from server!
        if(ranking!=null){
            for(int i=0; i<ranking.size(); i++){
                TextField field = new TextField();
                field.setDisable(true);
                field.setOpacity(2.0);
                field.setMinWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3);
                field.setMaxWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3);
                field.setPrefWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3);
                //field.setText(); TODO: get ranking from server
            }
        }
        Button exitButton = new Button("ESCI");
        exitButton.setOnAction(e->System.exit(0));

        box.getChildren().add(exitButton);
        AnchorPane.setLeftAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4);
        AnchorPane.setRightAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4);
        AnchorPane.setBottomAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2);
        AnchorPane.setTopAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2);

        //add labels to pane
        layer.getChildren().addAll(box);
        //pane to layout
        Client.getRootLayout().setCenter(layer);
    }
}
