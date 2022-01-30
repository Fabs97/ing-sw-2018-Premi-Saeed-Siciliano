package Client.View.GUI.PlayerHUDGUIItems;

import Shared.Model.Dice.Dice;
import Shared.Model.RoundTrace.RoundTrace;
import Client.View.GUI.GUIAlertBox;
import Client.View.GUI.GUIPlayerHUD;
import Client.View.ViewControllers.VIEWController;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.util.List;

public class GUIRoundTrace {
    private static HBox roundTrace;
    private static final double viewHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 5;
    private static final double viewWidht = GUIPlayerHUD.getBoxWidht();

    private VIEWController viewController;

    public GUIRoundTrace(VIEWController viewController){
        this.viewController=viewController;
        roundTrace = new HBox(1);
        roundTrace.setAlignment(Pos.CENTER);
        roundTrace.autosize();

        for(int i=1; i<=10; i++){
            int j=i;
            //TODO: must set an image
            ImageView newView = new ImageView(new Image("Images/RoundTrace/RT" + i + ".png"));
            newView.setFitWidth(viewWidht/10);
            newView.setFitHeight(viewWidht/10);
            newView.setOnMouseClicked(e->GUIAlertBox.showSingleRound(j, viewController));
            roundTrace.getChildren().add(newView);
        }
    }

    public void updateRoundTrace(RoundTrace trace){
        Platform.runLater(()->{
            String url;
            for (int i = 0; i < 10; i++) {
                List<Dice> cell = trace.getTrace().get(i).getCell();
                if (cell != null) {
                    for (Dice dice : cell) {
                        url = "Images/Dice/" + dice.getColor().toString() + dice.getTop() + ".png";
                        ImageView view = (ImageView) roundTrace.getChildren().get(i);
                        view.setImage(new Image(url));
                    }
                }
            }
        });
    }

    public HBox getRoundTrace(){
        return roundTrace;
    }
}
