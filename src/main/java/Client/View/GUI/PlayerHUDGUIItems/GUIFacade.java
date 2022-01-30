package Client.View.GUI.PlayerHUDGUIItems;

import Shared.Color;
import Shared.Model.Schemes.Scheme;
import Client.View.GUI.GUIPlayerHUD;
import Client.View.ViewControllers.VIEWController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.awt.*;

public class GUIFacade {
    private   VIEWController viewController;

    private VBox box;
    private Color facadeColor;
    private GridPane gridPane;

    private static final double gridWidht = GUIPlayerHUD.getBoxWidht() - GUIPlayerHUD.getBoxWidht() /4;

    private static StringProperty favorsString;

    public GUIFacade(Color color, VIEWController controller){
        this.viewController = controller;
        box = new VBox();
        box.autosize();

        //create grid
        gridPane = new GridPane();
        gridPane.autosize();
        gridPane.setPrefSize(gridWidht, gridWidht);
        gridPane.setMinSize(gridWidht, gridWidht);
        gridPane.setMaxSize(gridWidht, gridWidht);

        facadeColor = color;
    }

    public VBox createFacade(Scheme scheme){
        //create background
        ImageView imageView = new ImageView(new Image("Images/Facade/facciataHalf" + facadeColor.toString() +".png"));
        imageView.setFitWidth(gridWidht);
        imageView.setFitHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/ 2.5);
        imageView.autosize();

        for(int i=0; i<scheme.getScheme().length; i++){
            for(int j=0; j<scheme.getScheme()[i].length; j++){
                ImageView newImageView = new ImageView();
                String url;
                if (!scheme.getScheme()[i][j].getColor().equals(Color.WHITE)) {
                    url = "Images/Dice/" + scheme.getScheme()[i][j].getColor().toString() + "0.png";
                } else {
                  url = "Images/Dice/W" + scheme.getScheme()[i][j].getNum() + ".png";
                }
                newImageView.setImage(new Image(url));
                newImageView.setFitHeight(gridWidht/scheme.getScheme().length);
                newImageView.setFitWidth(gridWidht/scheme.getScheme()[i].length);
                newImageView.autosize();

                newImageView.setOnMouseClicked(null);

                gridPane.add(newImageView, j, i);
            }
        }

        //create favors Label
        TextField textField = new TextField("Favori: NA");
        textField.autosize();
        favorsString = new SimpleStringProperty("Favors: " + viewController.getCardsController().getScheme().getFavors());
        textField.textProperty().bind(favorsString);
        textField.setOpacity(1);
        textField.setStyle("-fx-font-weight: bolder");
        textField.setAlignment(Pos.CENTER);
        textField.setDisable(true);
        textField.setMinWidth(gridWidht);
        textField.setMaxWidth(gridWidht);
        textField.setPrefWidth(gridWidht);

        box.getChildren().addAll(imageView, gridPane, textField);
        return box;
    }

    public void updateFavors(int newFavors){
        favorsString = new SimpleStringProperty("Favors: " + newFavors);
    }

    public void updategrid(Scheme scheme){
        ObservableList<Node> children = gridPane.getChildren();
        for(Node child : children){
            String url = "Images/Dice/";
            ImageView view = (ImageView) child;
            if(scheme.getScheme()[GridPane.getRowIndex(child)][GridPane.getColumnIndex(child)].isOccupied()){
                url += scheme.getScheme()[GridPane.getRowIndex(child)][GridPane.getColumnIndex(child)].getDado().getColor().toString() + scheme.getScheme()[GridPane.getRowIndex(child)][GridPane.getColumnIndex(child)].getDado().getTop() + ".png";
            }
            else{
                url += scheme.getScheme()[GridPane.getRowIndex(child)][GridPane.getColumnIndex(child)].getColor().toString() + scheme.getScheme()[GridPane.getRowIndex(child)][GridPane.getColumnIndex(child)].getNum() + ".png";
            }
            view.setImage(new Image(url));
        }
    }
    public GridPane getGridPane() {
        return gridPane;
    }
}
