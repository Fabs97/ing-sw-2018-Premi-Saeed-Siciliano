package Client.View.GUI;

import Client.View.GUI.GUIWIP.GUISettingsMenu;
import Shared.Exceptions.LobbyFullException;
import Shared.Exceptions.ObjectNotFoundException;
import Client.Client;
import Client.View.ViewControllers.VIEWController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.rmi.RemoteException;

public class GUIMainMenu{
    private VIEWController controller;

    public GUIMainMenu(VIEWController controller){
        this.controller = controller;
    }

    /**
     * shows Main Menu
     * @see GUISinglePlayerSetup for next possible choice
     * @see GUIMultiPlayerSetup for next possible choice
     * @see GUISettingsMenu for next possible choice
     * @author Fabrizio Siciliano
     * */
    public void showGUI(){
        Client.setBackgroundImage(Client.getMainBackground());

        AnchorPane layer = new AnchorPane();
        layer.autosize();

        HBox mainBox = new HBox(20);
        mainBox.setAlignment(Pos.CENTER);

        Button singlePlayerButton = new Button("SinglePlayer");
        singlePlayerButton.autosize();
        singlePlayerButton.setOnAction(e-> {
            controller.getMainController().insertSinglePlayer(true);
            GUISinglePlayerSetup thirdPane = new GUISinglePlayerSetup(controller);
            thirdPane.showGUI();
        });

        Button multiPlayerButton = new Button("MultiPlayer");
        multiPlayerButton.autosize();
        multiPlayerButton.setOnAction(e-> {
            controller.getMainController().insertSinglePlayer(false);
            try {
                controller.getSetupController().joinGame();
            } catch (RemoteException | LobbyFullException | ObjectNotFoundException l ){
                if(l instanceof LobbyFullException){
                    GUIAlertBox.display("LobbyFullException", "La lobby è piena\nIl gioco verrà chiuso");
                    System.exit(0);
                } else if(l instanceof LobbyFullException){
                    GUIAlertBox.display("PlayerNotFoundException", "Non ho trovato il giocatore\nIl gioco verrà chiuso");
                    System.exit(0);
                } else{
                    GUIAlertBox.display("RemoteException", "Errore di comunicazione col server");
                }
            }
            GUIMultiPlayerSetup thirdPane = new GUIMultiPlayerSetup(controller);
            thirdPane.showGUI();
        });

        Button settingsButton = new Button("Settings");
        settingsButton.autosize();
        settingsButton.setOnAction(e->{
            //TODO: must be linked with settings
        });

        Button exitButton = new Button("Exit");
        exitButton.setAlignment(Pos.CENTER);
        exitButton.autosize();
        exitButton.setOnAction(e-> System.exit(0));


        mainBox.getChildren().addAll(singlePlayerButton, multiPlayerButton, settingsButton, exitButton);

        layer.getChildren().add(mainBox);
        AnchorPane.setLeftAnchor(mainBox, 0.0);
        AnchorPane.setRightAnchor(mainBox, 0.0);
        AnchorPane.setTopAnchor(mainBox, 0.0);
        AnchorPane.setBottomAnchor(mainBox, 0.0);

        Client.getRootLayout().setCenter(layer);
    }
}
