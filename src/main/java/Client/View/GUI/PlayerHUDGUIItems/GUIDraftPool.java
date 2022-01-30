package Client.View.GUI.PlayerHUDGUIItems;

import Shared.Model.Dice.Dice;
import Client.View.GUI.GUIPlayerHUD;
import Client.View.ViewControllers.VIEWController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.awt.*;
import java.util.ArrayList;

public class GUIDraftPool {

    private HBox draftPoolBox;
    private static final double viewSize = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 5;
    private static final double viewWidht = GUIPlayerHUD.getBoxWidht();

    private VIEWController viewController;

    public GUIDraftPool(ArrayList<Dice> draftPool, VIEWController viewController){
        this.viewController = viewController;
        if(draftPoolBox==null)
            draftPoolBox = new HBox(viewWidht/(draftPool.size()*2));
        draftPoolBox.setAlignment(Pos.CENTER);
        draftPoolBox.autosize();

        for(int i=0; i<draftPool.size(); i++){
            ImageView imageView = new ImageView(new Image("Images/Dice/W0.png"));
            imageView.autosize();
            imageView.setId(draftPool.get(i).getColor().toString() + draftPool.get(i).getTop());
            imageView.setFitHeight(viewWidht/(draftPool.size()*2));
            imageView.setFitWidth(viewWidht/(draftPool.size()*2));

            draftPoolBox.getChildren().add(imageView);
        }
    }

    public HBox updateDraftPoolBox(ArrayList<Dice> draftPool){
        Platform.runLater(()-> {
            if(draftPoolBox==null)
                draftPoolBox = new HBox(viewWidht/(draftPool.size()*2));
            int i = 0;
            ImageView actualView;
            ObservableList<Node> children = draftPoolBox.getChildren();
            for(Node node : children){
                ((ImageView)node).setOnMouseClicked(null);
                ((ImageView)node).setImage(null);
            }
            if (draftPool != null) {
                for (Dice dice : draftPool) {
                    actualView = (ImageView) children.get(i);
                    actualView.setImage(new Image("Images/Dice/" + dice.getColor().toString() + dice.getTop() + ".png"));
                    actualView.setId("draftpoolButton"+i);
                    i++;
                }
            }
        });
        return draftPoolBox;
    }

    public void setButtonDraftPoolNull(){
        for(Node child : draftPoolBox.getChildren()){
            ((Button)child).setOnAction(null);
        }
    }

    public HBox getDraftPoolBox() {
        return draftPoolBox;
    }

    public static double getHeight(){
        return viewSize;
    }
}
