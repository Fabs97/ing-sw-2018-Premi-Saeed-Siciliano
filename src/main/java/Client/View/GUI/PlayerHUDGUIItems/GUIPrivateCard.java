package Client.View.GUI.PlayerHUDGUIItems;

import Shared.Model.ObjectiveCard.PrivateObjective;
import Client.View.GUI.GUIAlertBox;
import Client.View.GUI.GUIPlayerHUD;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.awt.*;

public class GUIPrivateCard {
    private HBox box;
    private final static double hSpacing = 5;
    private final static double imageWidth = GUIPlayerHUD.getBoxWidht();
    private final static double imageHeigth = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 4;

    public GUIPrivateCard(){
        box = new HBox();
        box.setSpacing(hSpacing);
        box.setAlignment(Pos.CENTER);
        box.autosize();
    }

    public HBox createPrivateCardBox(PrivateObjective card1){
        String url = "Images/ObjectiveCards/Private/private" + card1.getColor().toString() +".jpg";
        ImageView newPrivateCard = new ImageView(new Image(url));
        newPrivateCard.autosize();
        newPrivateCard.setFitHeight(imageHeigth);
        newPrivateCard.setFitWidth(imageWidth / 3);
        newPrivateCard.setOnMouseClicked(e-> GUIAlertBox.displayCard("Carta privata" , url));
        box.getChildren().add(newPrivateCard);
        return box;
    }

}
