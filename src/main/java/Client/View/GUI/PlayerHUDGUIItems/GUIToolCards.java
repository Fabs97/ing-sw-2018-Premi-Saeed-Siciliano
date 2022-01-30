package Client.View.GUI.PlayerHUDGUIItems;

import Client.View.GUI.GUIPlayerHUD;
import Client.View.ViewControllers.VIEWController;
import Shared.Model.Tools.ToolCard;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;


public class GUIToolCards {
    private HBox box;
    private static final int hSpacing = 5;
    private static final double imageWidth = GUIPlayerHUD.getBoxWidht() / 3;
    private static final double imageHeigth = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 5;
    private BooleanProperty cardUsed;

    public GUIToolCards(){
        cardUsed = new SimpleBooleanProperty();
        box = new HBox();
        box.setSpacing(hSpacing);
        box.setAlignment(Pos.CENTER);
        box.autosize();
    }

    public HBox createToolCardsBox(VIEWController controller) {
        for (ToolCard card : controller.getCardsController().getToolCards()) {
            String imageURL = "Images/Tools/";
            switch (card.getName()) {
                case "Pinza Sgrossatrice": {
                    imageURL += "pinzaSgrossatrice.jpg";
                    break;
                }
                case "Pennello per Eglomise": {
                    imageURL += "pennelloPerEglomise.jpg";
                    break;
                }
                case "Alesatore per lamina di rame": {
                    imageURL += "alesatorePerLaminaDiRame.jpg";
                    break;
                }
                case "Lathekin": {
                    imageURL += "lathekin.jpg";
                    break;
                }
                case "Taglierina circolare": {
                    imageURL += "taglierinaCircolare.jpg";
                    break;
                }
                case "Pennello per pasta salda": {
                    imageURL += "pennelloPerPastaSalda.jpg";
                    break;
                }
                case "Martelletto": {
                    imageURL += "martelletto.jpg";
                    break;
                }
                case "Tenaglia a Rotelle": {
                    imageURL += "tenagliaARotelle.jpg";
                    break;
                }
                case "Riga in Sughero": {
                    imageURL += "rigaInSughero.jpg";
                    break;
                }
                case "Tampone Diamantato": {
                    imageURL += "tamponeDiamantato.jpg";
                    break;
                }
                case "Diluente per Pasta Salda": {
                    imageURL += "diluentePerPastaSalda.jpg";
                    break;
                }
                case "Taglierina Manuale": {
                    imageURL += "taglierinaManuale.jpg";
                    break;
                }
                default:
                    imageURL += "toolCardBack.jpg";
                    break;
            }
            Image toolCardBackground;
            try {
                toolCardBackground = new Image(imageURL);
            } catch (IllegalArgumentException i){
                toolCardBackground = new Image("Images/Tools/toolCardBack.jpg");
            }
            ImageView toolCard = new ImageView(toolCardBackground);
            toolCard.setFitHeight(imageHeigth);
            toolCard.setFitWidth(imageWidth);
            String url = imageURL;
            toolCard.setOnMouseClicked(e -> showToolCard("Carta utensile", url, card.getName(), controller));

            box.getChildren().add(toolCard);
        }
        return box;
    }

    public void showToolCard(String title, String url, String cardName, VIEWController controller){
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        //bottoni per usare la carta
        Button useToolButton = new Button("Usa la carta");
        useToolButton.visibleProperty().bind(cardUsed);
        useToolButton.setOnAction(e-> {
            controller.getToolController().useToolCard(cardName);
            window.close();
        });

        Button closeButton = new Button("Chiudi questa finestra");
        closeButton.setOnAction(e -> window.close());

        TextField field = new TextField();
        for(ToolCard card : controller.getCardsController().getToolCards()) {
            if (!card.getName().equals(cardName)) {
                if (!card.isUsed())
                    field.setText("Costo: 1 favore");
                else
                    field.setText("Costo: 2 favori");
                field.setDisable(true);
                field.autosize();
                field.setOpacity(2.0);
                field.setStyle("-fx-font-weight: bolder; -fx-text-alignment: center; ");
            }
        }

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(field, useToolButton, closeButton);
        buttonBox.setAlignment(Pos.CENTER);

        //imageView per la carta
        ImageView imageView = new ImageView(new Image(url));
        imageView.setFitHeight((Toolkit.getDefaultToolkit().getScreenSize().getHeight()/3)*2);
        imageView.setFitWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3);
        imageView.autosize();

        VBox layout = new VBox(10);
        layout.getChildren().addAll(imageView, buttonBox);
        layout.setAlignment(Pos.CENTER);
        layout.autosize();

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public void setCardUsed(boolean cardUsed) {
        Platform.runLater(()-> this.cardUsed.setValue(!cardUsed));
    }
}
