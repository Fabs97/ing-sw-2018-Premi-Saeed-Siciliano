package Client.View.GUI;

import Client.Client;
import Client.View.ViewControllers.VIEWController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.awt.*;


public class GUISinglePlayerSetup{
    private VIEWController controller;

    public GUISinglePlayerSetup(VIEWController controller){
        this.controller = controller;
    }

    public void showGUI(){
        Client.setBackgroundImage(Client.getMainBackground());
        AnchorPane layer = new AnchorPane();

        HBox box = new HBox(20);
        box.setAlignment(Pos.CENTER);

        TextField field = new TextField("Inserisci a che difficoltà vuoi giocare");
        field.setDisable(true);
        field.setOpacity(2.0);
        field.setMinSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/5);
        field.setMaxSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/5);
        field.setPrefSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/5);

        Slider difficultySliderBar = new Slider();
        difficultySliderBar.setMin(1.0);
        difficultySliderBar.setMax(5.0);
        difficultySliderBar.setSnapToTicks(true);
        difficultySliderBar.setMajorTickUnit(1);
        difficultySliderBar.setMinorTickCount(0);
        difficultySliderBar.setShowTickLabels(true);
        difficultySliderBar.setShowTickMarks(true);

        Button playButton = new Button("Gioca");
        playButton.setOnAction(e-> {
            int difficulty = (int) difficultySliderBar.getValue();
            //TODO: send difficulty to server and connect
            GUIWaitForLobbyReady fourthPane = new GUIWaitForLobbyReady(controller);
            fourthPane.showGUI();
        });


        box.getChildren().addAll(field, difficultySliderBar, playButton);
        layer.getChildren().add(box);
        AnchorPane.setTopAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
        AnchorPane.setBottomAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
        AnchorPane.setLeftAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/4);
        AnchorPane.setRightAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/4);
        Client.getRootLayout().setCenter(layer);
    }
}
