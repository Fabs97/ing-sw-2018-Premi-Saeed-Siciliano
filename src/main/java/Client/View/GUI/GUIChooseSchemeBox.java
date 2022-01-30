package Client.View.GUI;


import Shared.Color;
import Shared.Model.ObjectiveCard.PrivateObjective;
import Shared.Model.ObjectiveCard.PublicObjective;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Schemes.SchemeCard;
import Client.Client;
import Client.View.ViewControllers.VIEWController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GUIChooseSchemeBox {

    private VIEWController viewController;

    private static final double gridSize = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 4 + 10;

    private static final double buttonSize = gridSize / 5;

    private final static double imageWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 10;
    private final static double imageHeigth = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 6;

    public GUIChooseSchemeBox(VIEWController viewController){
        this.viewController = viewController;
    }

    public void showGUI(){
        Client.setBackgroundImage(Client.getPlayingBackground());

        AnchorPane layer = new AnchorPane();
        layer.autosize();
        PrivateObjective privateObjectives = viewController.getCardsController().getPrivateObjective();
        ArrayList<PublicObjective> publicObjectives = viewController.getCardsController().getPublicObjectives();
        SchemeCard schemeCard1 = viewController.getCardsController().getSchemeCard();
        SchemeCard schemeCard2 = viewController.getCardsController().getSchemeCard();

        HBox box = new HBox(buttonSize*2);
        box.setAlignment(Pos.CENTER);

        VBox boxRight = new VBox(buttonSize);
        boxRight.autosize();

        Button rightButton = new Button("Scegli questo");
        rightButton.setId(schemeCard1.getFront().getName());
        rightButton.setOnAction(e -> {
            viewController.getCardsController().setScheme(rightButton.getId());
            GUIPlayerHUD pane = new GUIPlayerHUD(viewController);
            viewController.getMainController().setPlayerHUD(pane);
            pane.showGUI();
        });

        Button rightButton2 = new Button("Scegli questo");
        rightButton2.setId(schemeCard1.getRear().getName());
        rightButton2.setOnAction(e -> {
            viewController.getCardsController().setScheme(rightButton2.getId());
            GUIPlayerHUD pane = new GUIPlayerHUD(viewController);
            viewController.getMainController().setPlayerHUD(pane);
            pane.showGUI();
        });

        VBox boxLeft = new VBox(buttonSize);
        Button leftButton;
        boxLeft.autosize();
        leftButton = new Button("Scegli questo");
        leftButton.setId(schemeCard2.getFront().getName());
        leftButton.setOnAction(e -> {
            viewController.getCardsController().setScheme(leftButton.getId());
            GUIPlayerHUD pane = new GUIPlayerHUD(viewController);
            viewController.getMainController().setPlayerHUD(pane);
            pane.showGUI();
        });

        Button leftButton2 = new Button("Scegli questo");
        leftButton2.setId(schemeCard2.getRear().getName());
        leftButton2.setOnAction(e -> {
            viewController.getCardsController().setScheme(leftButton2.getId());
            GUIPlayerHUD pane = new GUIPlayerHUD(viewController);
            viewController.getMainController().setPlayerHUD(pane);
            pane.showGUI();
        });

        GridPane gridPaneRight = new GridPane();
        GridPane gridPaneRight2 = new GridPane();
        GridPane gridPaneLeft = new GridPane();
        GridPane gridPaneLeft2 = new GridPane();

        createGrid(gridPaneRight, schemeCard1.getFront());
        createGrid(gridPaneRight2, schemeCard1.getRear());
        createGrid(gridPaneLeft, schemeCard2.getFront());
        createGrid(gridPaneLeft2, schemeCard2.getRear());

        boxRight.getChildren().addAll(rightButton,  gridPaneRight, rightButton2, gridPaneRight2);
        boxRight.setAlignment(Pos.CENTER);

        boxLeft.getChildren().addAll(leftButton, gridPaneLeft, leftButton2, gridPaneLeft2);
        boxLeft.setAlignment(Pos.CENTER);

        //------------------------------------Private anc Public cards show ---------------------------
        VBox objectBox = new VBox((Toolkit.getDefaultToolkit().getScreenSize().getHeight() - (imageHeigth*4))/4);
        objectBox.setAlignment(Pos.CENTER);

        //private cards setup
        String privateURL = "Images/ObjectiveCards/Private/private"+ privateObjectives.getColor().toString() +".jpg";
        Image privateObjectImage = new Image(privateURL,imageWidth, imageHeigth, false, false);
        ImageView privateObjectImageView = new ImageView(privateObjectImage);
        privateObjectImageView.setOnMouseClicked(e-> GUIAlertBox.displayCard("Carta pubblica", privateURL));
        privateObjectImageView.autosize();
        objectBox.getChildren().addAll(privateObjectImageView);

        //public cards setup
        for (PublicObjective publicObjective : publicObjectives) {
            String url = "Images/ObjectiveCards/Public/" + publicObjective.getName() + ".jpg";
            Image publicCardImage = new Image(url, imageWidth, imageHeigth, false, false);
            ImageView newPublicCard = new ImageView(publicCardImage);
            newPublicCard.autosize();
            newPublicCard.setOnMouseClicked(e -> GUIAlertBox.displayCard("Carta pubblica", url));
            objectBox.getChildren().add(newPublicCard);
        }


        box.getChildren().addAll(boxLeft, boxRight, objectBox);
        layer.getChildren().add(box);

        AnchorPane.setBottomAnchor(box, 0.0);
        AnchorPane.setTopAnchor(box, 0.0);
        AnchorPane.setRightAnchor(box, 0.0);
        AnchorPane.setLeftAnchor(box, 0.0);

        Client.getRootLayout().setCenter(layer);
        //waits for all players to be ready
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            if(viewController.getTurnController().arePlayersReady()) {
                viewController.getMainController().getPlayerHUD().setGameStarted(true);
                scheduler.shutdown();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void createGrid(GridPane grid, Scheme scheme) {
        try {
            //need to throw IOException if scheme's image does not exist in package
            ImageIO.read(new File("Images/Facade/" + scheme.getName() + ".png"));

            Image image = new Image("Images/Facade/" + scheme.getName() + ".png");
            grid.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, false))));
        } catch (IOException e) {
            for (int i = 0; i < scheme.getScheme().length; i++) {
                for (int j = 0; j < scheme.getScheme()[i].length; j++) {
                    Image image;
                    if (!scheme.getScheme()[i][j].getColor().equals(Color.WHITE)) {
                        image = new Image("Images/Dice/" + scheme.getScheme()[i][j].getColor().toString() + "0.png");
                    } else {
                        image = new Image("Images/Dice/W" + scheme.getScheme()[i][j].getNum() + ".png");
                    }
                    ImageView newPane = new ImageView(image);
                    newPane.setFitHeight(buttonSize);
                    newPane.setFitWidth(buttonSize);

                    newPane.autosize();
                    grid.add(newPane, j, i);
                }
            }
            BackgroundFill filling = new BackgroundFill(javafx.scene.paint.Color.rgb(216, 163, 67), null, null);
            grid.setBackground(new Background(filling));
            grid.setHgap(gridSize - ((scheme.getScheme().length +1)*buttonSize));
            grid.setVgap(gridSize - (scheme.getScheme()[0].length*buttonSize));
        }
        finally {
            grid.setPrefHeight(gridSize);
            grid.setPrefWidth(gridSize);
            grid.autosize();
            grid.setAlignment(Pos.CENTER);
        }
    }
}
