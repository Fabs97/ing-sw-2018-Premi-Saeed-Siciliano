package Client.View.GUI.PlayerHUDGUIItems;

import Shared.Model.ObjectiveCard.PublicObjective;
import Client.View.GUI.GUIAlertBox;
import Client.View.GUI.GUIPlayerHUD;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.util.ArrayList;

public class GUIPublicCards {
    private HBox box;

    private final static double hSpacing = 5;
    private final static double imageWidth = GUIPlayerHUD.getBoxWidht();
    private final static double imageHeigth = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 4;

    public GUIPublicCards(){
        box = new HBox();
        box.setSpacing(hSpacing);
        box.setAlignment(Pos.CENTER);
        box.autosize();
    }

    public HBox createPublicCardsBox(ArrayList<PublicObjective> publicCards){
        for(int i=0; i<publicCards.size(); i++){
            String url = "Images/ObjectiveCards/Public/" + publicCards.get(i).getName() +".jpg";
            ImageView newPublicCard = new ImageView(new Image(url));
            newPublicCard.autosize();
            newPublicCard.setFitWidth(imageWidth/publicCards.size());
            newPublicCard.setFitHeight(imageHeigth);
            newPublicCard.setOnMouseClicked(e-> GUIAlertBox.displayCard("Carta pubblica" , url));
            box.getChildren().add(newPublicCard);
        }
        return box;
    }
}
