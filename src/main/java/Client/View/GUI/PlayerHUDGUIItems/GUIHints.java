package Client.View.GUI.PlayerHUDGUIItems;

import Client.View.GUI.GUIPlayerHUD;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;

import java.awt.*;

public class GUIHints {
    private StringProperty string;
    private TextField field;

    private static final double fieldWidht = GUIPlayerHUD.getBoxWidht();
    private static final double fieldHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 5;

    public GUIHints(){
        field = new TextField();
        field.autosize();
        field.setDisable(true);
        string = new SimpleStringProperty("Benvenuto in Sagrada!");
    }

    public TextField createScreen(){
        field.textProperty().bind(string);
        field.setOpacity(2.0);
        field.setStyle("-fx-font-weight: bolder; -fx-text-alignment: center; -fx-column-halignment: center;");
        field.setMaxSize(fieldWidht, fieldHeight);
        field.setPrefSize(fieldWidht, fieldHeight);
        field.setMinSize(fieldWidht, fieldHeight);

        return field;
    }

    public void updateScreen(String message){
        Platform.runLater(()-> string.setValue(message));
    }
}
