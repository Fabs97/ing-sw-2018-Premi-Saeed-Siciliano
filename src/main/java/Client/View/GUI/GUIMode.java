package Client.View.GUI;

import Client.Client;
import Client.View.ViewControllers.VIEWController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIMode {
    private VIEWController controller;
    private Stage stage;

    public GUIMode(VIEWController controller, Stage stage, boolean RMI){
        this.controller = controller;
        this.stage = stage;
        controller.getMainController().setUsingRMI(RMI);
    }

    /**
     * shows GUI for GUI or CLI play mode choice
     * @see GUIUsername for next possible choice
     * @see Client.View.CLI.CLIStaticMethods#playWithCLI(VIEWController) for next possible choice
     * @author Fabrizio Siciliano
     * */
    public void showGUI(){
        AnchorPane layer = new AnchorPane();
        layer.autosize();

        VBox box = new VBox(20);
        box.autosize();
        box.setAlignment(Pos.CENTER);

        Button GUIButton = new Button("GUI");
        GUIButton.autosize();
        GUIButton.setOnAction(e->{
            controller.getMainController().setUsingCLI(false);
            stage.setFullScreen(true);
            GUIUsername firstPane = new GUIUsername(controller);
            firstPane.showGUI();
        });
        GUIButton.setStyle("-fx-font-weight: bolder; -fx-background-color: #e88e40;");

        Button CLIButton = new Button("CLI");
        CLIButton.autosize();
        CLIButton.setOnAction(e->{
            controller.getMainController().setUsingCLI(true);
            Client.setController(controller);
            stage.close();
        });
        CLIButton.setStyle("-fx-font-weight: bolder; -fx-background-color: #e88e40;");

        box.getChildren().addAll(GUIButton, CLIButton);
        layer.getChildren().add(box);

        AnchorPane.setLeftAnchor(box, 0.0);
        AnchorPane.setRightAnchor(box, 0.0);
        AnchorPane.setTopAnchor(box, 0.0);
        AnchorPane.setBottomAnchor(box, 0.0);

        Client.getRootLayout().setCenter(layer);
    }

}
