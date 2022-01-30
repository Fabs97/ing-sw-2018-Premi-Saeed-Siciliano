package Client.View.GUI;

import Client.View.ViewControllers.VIEWController;
import Shared.Exceptions.IllegalRoundException;
import Shared.Model.RoundTrace.RoundTrace;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.rmi.RemoteException;

public class GUIAlertBox {

    public static void display(String title, String message) {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Chiudi questa finestra");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void displayCard(String title, String url){
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        Image image = new Image(url, Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2, false, false);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight((Toolkit.getDefaultToolkit().getScreenSize().getHeight()/3)*2);
        imageView.setFitWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3);
        imageView.autosize();

        Button closeButton = new Button("Chiudi questa finestra");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(imageView, closeButton);
        layout.setAlignment(Pos.CENTER);
        layout.autosize();

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.centerOnScreen();
        window.showAndWait();
    }

    public static void showIncreaseOrDecrease(VIEWController controller) {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Aumenta o diminuisci?");

        Button increase = new Button("Aumenta di 1");
        increase.setOnAction(e->{
            controller.getToolController().getPinzaSgrossatriceController().setPinzaSgrossatrice2(true);
            window.close();
        });
        Button decrease = new Button("Diminuisci di 1");
        decrease.setOnAction(e->{
            controller.getToolController().getPinzaSgrossatriceController().setPinzaSgrossatrice2(false);
            window.close();
        });
        Button close = new Button("Chiudi la finestra");
        close.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(increase,decrease,close);
        layout.setAlignment(Pos.CENTER);
        layout.autosize();

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void showSingleRound(int round, VIEWController controller){
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Round: "+round);

        RoundTrace roundTrace = controller.getTableController().getRoundTrace();
        VBox layout = new VBox(10);
        for(int i=0; i< roundTrace.getTrace().get(round-1).getCell().size(); i++){
            ImageView view = new ImageView(new Image("Images/Dice/" + roundTrace.getTrace().get(round-1).getCell().get(i).getColor() + roundTrace.getTrace().get(round-1).getCell().get(i).getTop() + ".png"));

            layout.getChildren().add(view);
        }

        Button close = new Button("Chiudi la finestra");
        close.setOnAction(e -> window.close());

        layout.getChildren().add(close);
        layout.setAlignment(Pos.CENTER);
        layout.autosize();

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void showAllRounds(String toolUsed, VIEWController controller){
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Round Trace");

        RoundTrace roundTrace = controller.getTableController().getRoundTrace();

        HBox layout = new HBox();
        for(int i=0; i<roundTrace.getTrace().size();i++){
            VBox roundBox = new VBox();
            for(int j=0; j<roundTrace.getTrace().get(i).getCell().size();j++){
                Button newButton = new Button();
                newButton.setId("ROUND"+i+j);
                Image buttonBackgroundImage = new Image("Images/Dice/" + roundTrace.getTrace().get(i).getCell().get(j).getColor() + roundTrace.getTrace().get(i).getCell().get(j).getTop() + ".png");
                BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
                Background buttonBackground = new Background(new BackgroundImage(buttonBackgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bSize));
                newButton.setBackground(buttonBackground);
                switch (toolUsed){
                    case "taglierinaCircolare2" : newButton.setOnAction(e->{
                        controller.getToolController().getTaglierinaCircolareController().setTaglierinaCircolare2(newButton.getId());
                        window.close();
                    });
                    break;
                    case "normalView" : newButton.setOnAction(null);
                    break;
                }

                roundBox.getChildren().add(newButton);
            }
            layout.getChildren().add(roundBox);
        }
        layout.setAlignment(Pos.CENTER);
        layout.autosize();

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void setDiceValue(VIEWController controller){
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Seleziona valore dado");


        VBox layout = new VBox(10);


        for(int i=1; i<=6; i++){
            final int j=i;
            Button newButton = new Button("Valore: "+i);
            newButton.setId(""+i);
            newButton.setOnAction(e->{
                controller.getToolController().getDiluentePerPastaSaldaController().setDiluentePerPastaSalda2(j);
                window.close();
            });
            layout.getChildren().add(newButton);
        }

        Button close = new Button("Chiudi la finestra");
        close.setOnAction(e -> window.close());

        layout.getChildren().add(close);

        layout.setAlignment(Pos.CENTER);
        layout.autosize();

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}