package Client.View.GUI;

import Client.Client;
import Client.ClientRMI.RMIClient;
import Client.ClientSocket.SocketClient;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.rmi.RemoteException;

public class GUIServerSetup {

    private final static double screenWidht = Toolkit.getDefaultToolkit().getScreenSize().getWidth();

    private Stage stage;

    public GUIServerSetup(Stage stage) {
        this.stage = stage;
    }

    /**
     * shows GUI for server's adress and port input, starts client
     * @param RMI boolean value for RMI or Socket connection
     * @see GUIMode for next possible choice
     * @author Fabrizio Siciliano
     * */
    public void showGUI(boolean RMI){
        Client.setBackgroundImage(Client.getSetupBackeground());

        AnchorPane layer = new AnchorPane();
        layer.autosize();

        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);

        TextField addressField = new TextField();
        addressField.setPromptText("Inserisci l'indirizzo del server");
        addressField.setStyle("-fx-text-alignment: center; ");
        addressField.setMaxWidth(200);

        TextField portField = new TextField();
        portField.setPromptText("Inserisci la porta del server");
        portField.setStyle("-fx-text-alignment: center; ");
        portField.setMaxWidth(200);

        Button connectButton = new Button("CONNETTIMI");
        connectButton.setOnAction(e->{
            try {
                String serverAddress = addressField.getText();
                int serverPort = Integer.parseInt(portField.getText());
                if(RMI) {
                    RMIClient client = new RMIClient(serverAddress, serverPort);
                    client.startRMICLient();
                    GUIMode pane = new GUIMode(client.getMainController(), stage, true);
                    pane.showGUI();
                }
                else{
                    SocketClient client = new SocketClient(serverAddress, serverPort);
                    client.startSocketClient();
                    GUIMode pane = new GUIMode(client.getMainController(), stage, false);
                    pane.showGUI();
                }
            } catch (NumberFormatException n){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                portField.setText("");
                alert.setHeaderText("NUMBER FORMAT ERROR");
                alert.setContentText("Hai messo una porta non idonea, rimettila!");
                alert.showAndWait();
            } catch (RemoteException r){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                addressField.setText("");
                portField.setText("");
                alert.setHeaderText("REMOTE EXCEPTION ERROR");
                alert.setContentText("Non sono riuscito a connettermi al server\nProva con un altro server");
                alert.showAndWait();
            } catch (IOException i){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                addressField.setText("");
                portField.setText("");
                alert.setHeaderText("IO EXCEPTION ERROR");
                alert.setContentText("Non sono riuscito a connettermi al server\nProva con un altro server");
                alert.showAndWait();
            }
        });
        connectButton.setStyle("-fx-font-weight: bolder; -fx-background-color: #e88e40;");

        box.getChildren().addAll(addressField, portField, connectButton);
        layer.getChildren().add(box);
        AnchorPane.setLeftAnchor(box, 0.0);
        AnchorPane.setRightAnchor(box, 0.0);
        AnchorPane.setTopAnchor(box, 0.0);
        AnchorPane.setBottomAnchor(box, 0.0);

        Client.getRootLayout().setCenter(layer);
    }
}
