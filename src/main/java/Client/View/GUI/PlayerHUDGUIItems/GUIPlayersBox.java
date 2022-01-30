package Client.View.GUI.PlayerHUDGUIItems;

import Client.View.GUI.GUIPlayerHUD;
import Client.View.ViewControllers.VIEWController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class GUIPlayersBox {
    private HBox playersBox;
    private static final double buttonWidth = GUIPlayerHUD.getBoxWidht();
    private VIEWController viewController;

    public GUIPlayersBox(VIEWController viewController){
        this.viewController = viewController;

        playersBox = new HBox(20);
        playersBox.autosize();
        playersBox.setAlignment(Pos.CENTER);
    }

    public HBox createPlayersBox(ArrayList<String> players){
        for(int i=0; i<players.size(); i++){
            int j = i;
            Button newButton = new Button(players.get(i) + "'s scheme");
            newButton.autosize();
            newButton.setMaxWidth((buttonWidth/players.size()) - 20);
            newButton.setMinWidth((buttonWidth/players.size()) - 20);
            newButton.setPrefWidth((buttonWidth/players.size()) - 20) ;
            /*newButton.addEventHandler(MouseEvent.MOUSE_PRESSED,
                    e -> {
                        boolean clicked = false;
                        if(!clicked){
                            PlayersBoxController.setNotVisible(players.get(j));
                            clicked=true;
                        }else{
                            PlayersBoxController.setVisible(players.get(j));
                            clicked=false;
                        }
                    });*/

            playersBox.getChildren().add(newButton);
        }
        playersBox.setAlignment(Pos.BOTTOM_CENTER);
        return playersBox;
    }
}
