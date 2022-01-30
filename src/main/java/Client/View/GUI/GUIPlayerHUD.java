package Client.View.GUI;


import Client.Client;
import Client.View.GUI.PlayerHUDGUIItems.*;
import Client.View.ViewControllers.VIEWController;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GUIPlayerHUD {
    private GUIPlayersBox playersSchemes;
    private GUIFacade playerFacade;
    private GUIDraftPool draftPool;
    private GUIRoundTrace roundTrace;
    private GUIToolCards tools;
    private GUIPublicCards publicCards;
    private GUIPrivateCard privateCards;
    private GUIButtonMoves buttonMoves;
    private GUIHints hintsScreen;

    private BooleanProperty gameStarted;
    private BooleanProperty gameEnded;

    private VIEWController controller;

    private static final double boxWidht = Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3;
    private static final double verticalSpacing = 20;

    public GUIPlayerHUD(VIEWController controller){
        gameStarted = new SimpleBooleanProperty();
        gameEnded = new SimpleBooleanProperty();
        buttonMoves = new GUIButtonMoves(controller);
        this.controller = controller;
    }

    /**
     * shows GUI player's HUD
     * @author Fabrizio Siciliano
     * */
    public void showGUI() {
        if(!gameStarted.getValue()) {
            Client.setBackgroundImage(Client.getPlayingBackground());

            AnchorPane syncLayer = new AnchorPane();
            syncLayer.autosize();

            VBox syncBox = new VBox(50);
            syncBox.autosize();
            syncBox.setAlignment(Pos.CENTER);

            ProgressIndicator indicator = new ProgressIndicator();
            indicator.setProgress(-1.0);
            indicator.autosize();

            TextField field = new TextField("Aspetto il sync con la lobby");
            field.autosize();
            field.setDisable(true);
            field.setOpacity(2.0);
            field.setPrefWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3);
            field.setMaxWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3);
            field.setMinWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3);
            field.setStyle("-fx-font-weight: bolder; ");

            syncBox.getChildren().addAll(field, indicator);
            syncLayer.getChildren().add(syncBox);
            AnchorPane.setTopAnchor(syncBox, 0.0);
            AnchorPane.setBottomAnchor(syncBox, 0.0);
            AnchorPane.setRightAnchor(syncBox, 0.0);
            AnchorPane.setLeftAnchor(syncBox, 0.0);
            Client.getRootLayout().setCenter(syncLayer);
        }
        else{
            createPlayerHUD(controller);
        }

        gameStarted.addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                createPlayerHUD(controller);
            }
        });

        gameEnded.addListener(((observable, oldValue, newValue) -> {
            if(controller.getTurnController().isGameEnded()){
                GUIEndGame pane = new GUIEndGame(controller);
                pane.showGUI();
            }
        }));
    }

    private void createPlayerHUD(VIEWController controller){
        Platform.runLater(()-> {
            AnchorPane layer = new AnchorPane();
            layer.autosize();

            //--------------------------------------------------create HUD elements--------------------------------------------------
            roundTrace = new GUIRoundTrace(controller);
            HBox roundTraceBox = roundTrace.getRoundTrace();
            roundTraceBox.setAlignment(Pos.CENTER);

            draftPool = new GUIDraftPool(controller.getTableController().getDraftPool(), controller);
            HBox draftPoolBox = draftPool.updateDraftPoolBox(controller.getTableController().getDraftPool());
            draftPoolBox.setAlignment(Pos.TOP_CENTER);

            tools = new GUIToolCards();
            HBox toolCardsBox = tools.createToolCardsBox(controller);
            toolCardsBox.setAlignment(Pos.BOTTOM_CENTER);

            hintsScreen = new GUIHints();
            TextField hintsField = hintsScreen.createScreen();
            hintsField.setAlignment(Pos.BOTTOM_CENTER);

            publicCards = new GUIPublicCards();
            HBox publiCardsBox = publicCards.createPublicCardsBox(controller.getCardsController().getPublicObjectives());
            publiCardsBox.setAlignment(Pos.TOP_CENTER);

            HBox buttonMovesBox = buttonMoves.createButtonBox();
            buttonMovesBox.setAlignment(Pos.CENTER);

            privateCards = new GUIPrivateCard();
            HBox privateCardsBox = privateCards.createPrivateCardBox(controller.getCardsController().getPrivateObjective());
            privateCardsBox.setAlignment(Pos.BOTTOM_CENTER);

            playersSchemes = new GUIPlayersBox(controller);
            HBox playersBox = playersSchemes.createPlayersBox(controller.getTableController().getOtherPlayersNames());
            playersBox.setAlignment(Pos.TOP_CENTER);

            playerFacade = new GUIFacade(controller.getSetupController().getPlayerColor(), controller);
            VBox playerBox = playerFacade.createFacade(controller.getCardsController().getScheme());
            playerBox.setAlignment(Pos.CENTER);
            //--------------------------------------------------create HUD elements--------------------------------------------------

            VBox leftBox = new VBox(verticalSpacing);
            leftBox.setAlignment(Pos.CENTER);
            leftBox.getChildren().addAll(roundTraceBox, draftPoolBox, toolCardsBox, hintsField);

            VBox centerBox = new VBox(verticalSpacing);
            centerBox.setAlignment(Pos.CENTER);
            centerBox.getChildren().addAll(publiCardsBox, buttonMovesBox, privateCardsBox);

            VBox rightBox = new VBox(verticalSpacing);
            rightBox.setAlignment(Pos.CENTER);
            rightBox.getChildren().addAll(playersBox, playerBox);

            HBox mainBox = new HBox(20);
            mainBox.autosize();
            mainBox.setAlignment(Pos.CENTER);
            mainBox.getChildren().addAll(leftBox, centerBox, rightBox);

            //add everything to layer
            layer.getChildren().addAll(mainBox);

            //add layer to pane
            Client.getRootLayout().setCenter(layer);

            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(() -> {
                if(controller.getTurnController().isGameEnded()){
                    GUIEndGame pane = new GUIEndGame(controller);
                    pane.showGUI();
                }
            }, 0, 8, TimeUnit.SECONDS);
        });
    }

    /**
     * //TODO: complete JavaDoc
     * @author Fabrizio Siciliano
     * */
    public void setGameEnded(boolean value){
        Platform.runLater(()->{
            if(gameEnded==null){
                gameEnded = new SimpleBooleanProperty();
            }
            gameEnded.setValue(value);
        });
    }

    /**
     * @return {@link GUIPlayersBox} for further players' schemes updates
     * @author Fabrizio Siciliano
     * */
    public GUIPlayersBox getPlayersBox() {
        return playersSchemes;
    }

    /**
     * @return {@link GUIFacade} for further player's scheme and favors updates
     * @author Fabrizio Siciliano
     * */
    public GUIFacade getPlayerFacade() {
        return playerFacade;
    }

    /**
     * @return {@link GUIDraftPool} for further draftPool updates
     * @author Fabrizio Siciliano
     * */
    public GUIDraftPool getDraftPool() {
        return draftPool;
    }

    /**
     * @return {@link GUIRoundTrace} for further round trace updates
     * @author Fabrizio Siciliano
     * */
    public GUIRoundTrace getRoundTrace() {
        return roundTrace;
    }

    /**
     * @return {@link GUIToolCards} for further tool cards updates
     * @author Fabrizio Siciliano
     * */
    public GUIToolCards getTools() {
        return tools;
    }

    /**
     * @return {@link GUIButtonMoves} for further possible moves updates
     * @author Fabrizio Siciliano
     * */
    public GUIButtonMoves getButtonMoves() {
        return buttonMoves;
    }

    /**
     * @return {@link GUIPrivateCard} for further private card updates
     * @author Fabrizio Siciliano
     * */
    public GUIPrivateCard getPrivateCards() {
        return privateCards;
    }

    /**
     * @return {@link GUIPublicCards} for further public cards updates
     * @author Fabrizio Siciliano
     * */
    public GUIPublicCards getPublicCards() {
        return publicCards;
    }

    /**
     * @return {@link GUIHints} for further hints on screen updates
     * @author Fabrizio Siciliano
     * */
    public GUIHints getHintsScreen() {
        return hintsScreen;
    }

    /**
     * @return value for single box width
     * @author Fabrizio Siciliano
     * */
    public static double getBoxWidht() {
        return boxWidht;
    }

    public void setGameStarted(boolean value){
        if(gameStarted == null)
            gameStarted = new SimpleBooleanProperty();
        gameStarted.set(value);
    }

    public void resetEndTurn(){
        tools.setCardUsed(false);
        buttonMoves.setDiceMoved(false);
        GUIStaticMethods.setButtonFacadeNull(controller);
        GUIStaticMethods.setButtonDraftPoolNull(controller);
        GUIStaticMethods.setRoundTraceButtonNull(controller);
        hintsScreen.updateScreen("Aspetta che gli altri giocatori finiscano i rispettivi turni");
    }
}
