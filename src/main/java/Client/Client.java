package Client;

import Client.View.CLI.CLIStaticMethods;
import Client.View.GUI.GUIConnection;
import Client.View.ViewControllers.VIEWController;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Client extends Application {

    private static Stage primaryStage;
    private static BorderPane rootLayout;
    private static VIEWController controller;

    private static final String setupBackeground = "Images/Tools/toolCardBack.jpg";
    private final static String mainBackground = "Images/Sagrada.jpg";
    private final static String playingBackground = "Images/woodBackground.jpg";

    /**
     * main application
     * @author Fabrizio Siciliano
     * */
    public static void main(String[] args) {
        Application.launch(args);
        CLIStaticMethods.playWithCLI(controller);
    }

    /**
     * @author Fabrizio Siciliano
     * */
    @Override
    public void start(Stage primaryStage){
        startGUI(primaryStage);
    }

    /**
     * starts GUI
     * @param primary main stage
     * @author Fabrizio Siciliano
     * */
    public void startGUI(Stage primary){
        primaryStage = primary;
        primaryStage.setTitle("Sagrada");
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        initRootLayout();
        GUIConnection zeroPane = new GUIConnection(primaryStage);
        zeroPane.showGUI();
    }

    /**
     * init root layout and primary stage
     * @author Fabrizio Siciliano
     * */
    private void initRootLayout(){
        rootLayout = new BorderPane();
        Image sagradaImage = new Image(setupBackeground);
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        Background sagradaBackground = new Background(new BackgroundImage(sagradaImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bSize));

        rootLayout.setBackground(sagradaBackground);
        primaryStage.setScene(new Scene(rootLayout));
        primaryStage.setWidth(400);
        primaryStage.setHeight(590);
        FadeTransition ft = new FadeTransition(Duration.millis(3000), rootLayout);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
        primaryStage.show();
    }

    public static Stage getMainStage(){
        return primaryStage;
    }

    /**
     * @return root layout of GUI
     * @author Fabrizio Siciliano
     * */
    public static BorderPane getRootLayout(){
        return rootLayout;
    }

    /**
     * @return global value for main background
     * @author Fabrizio Siciliano
     * */
    public static String getMainBackground() {
        return mainBackground;
    }

    /**
     * @return global value for playing background
     * @author Fabrizio Siciliano
     * */
    public static String getPlayingBackground(){
        return playingBackground;
    }

    /**
     * @return global value for setup background
     * @author Fabrizio Siciliano
     * */
    public static String getSetupBackeground() {return setupBackeground;}

    /**
     * @param image URL for image to be set as background of rootLayout
     * @author Fabrizio Siciliano
     * */
    public static void setBackgroundImage(String image){
        Image sagradaImage = new Image(image);
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        Background sagradaBackground = new Background(new BackgroundImage(sagradaImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bSize));
        rootLayout.setBackground(sagradaBackground);
    }

    /**
     * @param controller1 main client's controller
     * @author Fabrizio Siciliano
     * */
    public static void setController(VIEWController controller1) {
        controller = controller1;
    }
}
