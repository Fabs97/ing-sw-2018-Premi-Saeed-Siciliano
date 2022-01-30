package Client.View.GUI.PlayerHUDGUIItems;

import Client.View.ViewControllers.VIEWController;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;

public class GUIButtonMoves {
    private VIEWController controller;

    private Button placeDiceButton;
    private Button passTurnButton;
    private BooleanProperty turnOnGoing;
    private BooleanProperty diceMoved;

    public GUIButtonMoves(VIEWController controller){
        if(turnOnGoing==null)
            turnOnGoing = new SimpleBooleanProperty();
        this.controller = controller;
        this.diceMoved = new SimpleBooleanProperty();

    }

    public HBox createButtonBox(){
        HBox box = new HBox(10);
        box.autosize();

        ProgressIndicator indicator = new ProgressIndicator();
        indicator.setProgress(-1.0);
        indicator.setVisible(false);

        placeDiceButton = new Button("DADO");
        placeDiceButton.autosize();
        placeDiceButton.setOnAction(e->controller.getToolController().useToolCard("Normal Move"));

        passTurnButton = new Button("PASSO");
        passTurnButton.autosize();
        passTurnButton.setOnAction(e->{
            controller.getTurnController().endMyTurn();
            controller.getMainController().setMyTurn(false);
        });
        if(turnOnGoing.getValue().equals(Boolean.TRUE)){
            box.getChildren().addAll(placeDiceButton,passTurnButton);
        }

        turnOnGoing.addListener((observable, oldValue, newValue) -> {
            if(newValue){
                diceMoved.setValue(false);
                box.getChildren().removeAll(indicator);
                box.getChildren().addAll(placeDiceButton,passTurnButton);
                controller.getMainController().getPlayerHUD().getHintsScreen().updateScreen("E' il tuo turno. Siamo al round " + controller.getTurnController().whatRoundIs());
            } else{
                box.getChildren().removeAll(placeDiceButton,passTurnButton);
                box.getChildren().addAll(indicator);
                controller.getMainController().getPlayerHUD().resetEndTurn();
            }
        });

        diceMoved.addListener((observable, oldValue, newValue) -> {
            if(!oldValue&&newValue){
                box.getChildren().remove(placeDiceButton);
            }
        });
        return box;
    }

    public void setTurnOnGoing(boolean value){
        Platform.runLater(()->{
            if(turnOnGoing==null){
                turnOnGoing = new SimpleBooleanProperty();
            }
            turnOnGoing.setValue(value);
        });
    }

    public void setDiceMoved(boolean value) {
        Platform.runLater(()->diceMoved.setValue(value));
    }
}
