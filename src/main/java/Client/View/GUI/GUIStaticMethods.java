package Client.View.GUI;

import Client.View.ViewControllers.VIEWController;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class GUIStaticMethods {
    private GUIStaticMethods(){ }

    //It changes button SetOnAction for different tools
    public static void changeButtonGrid(String toolUsed, VIEWController viewController){
        for(Node child : viewController.getMainController().getPlayerHUD().getPlayerFacade().getGridPane().getChildren()){
            switch (toolUsed){
                case "diluentePerPastaSalda2" : (child).setOnMouseClicked(e-> viewController.getToolController().getDiluentePerPastaSaldaController().setDiluentePerPastaSalda3(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "normalMove2" : (child).setOnMouseClicked(e-> viewController.getToolController().getNormalMoveController().setNormalMove2(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "pennelloPerEglomise1" : (child).setOnMouseClicked(e-> viewController.getToolController().getPennelloPerEglomiseController().setPennelloPerEglomise1(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "pennelloPerEglomise2" :  (child).setOnMouseClicked(e-> viewController.getToolController().getPennelloPerEglomiseController().setPennelloPerEglomise2(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "alesatorePerLaminaDiRame1" :  (child).setOnMouseClicked(e-> viewController.getToolController().getAlesatorePerLaminaDiRameController().setAlesatorePerLaminaDiRame1(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "alesatorePerLaminaDiRame2" : (child).setOnMouseClicked(e-> viewController.getToolController().getAlesatorePerLaminaDiRameController().setAlesatorePerLaminaDiRame2(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "lathekin1" : (child).setOnMouseClicked(e-> viewController.getToolController().getLathekinController().setLathekin1(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "lathekin2" : (child).setOnMouseClicked(e-> viewController.getToolController().getLathekinController().setLathekin2(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "lathekin3" : (child).setOnMouseClicked(e-> viewController.getToolController().getLathekinController().setLathekin3(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "lathekin4" : (child).setOnMouseClicked(e-> viewController.getToolController().getLathekinController().setLathekin4(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "rigaInSughero2" :  (child).setOnMouseClicked(e-> viewController.getToolController().getRigaInSugheroController().setRigaInSughero2(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "taglierinaManuale1" :  (child).setOnMouseClicked(e-> viewController.getToolController().getTaglierinaManualeController().setTaglierinaManuale1(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "taglierinaManuale2" : (child).setOnMouseClicked(e-> viewController.getToolController().getTaglierinaManualeController().setTaglierinaManuale2(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "taglierinaManuale3" :  (child).setOnMouseClicked(e-> viewController.getToolController().getTaglierinaManualeController().setTaglierinaManuale3(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "taglierinaManuale4" :(child).setOnMouseClicked(e-> viewController.getToolController().getTaglierinaManualeController().setTaglierinaManuale4(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "tenagliaARotelle2" :(child).setOnMouseClicked(e-> viewController.getToolController().getTenagliaARotelleController().setTenagliaARotelle2(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "pennelloPerPastaSalda2" :(child).setOnMouseClicked(e-> viewController.getToolController().getPennelloPerPastaSaldaController().setPennelloPerPastaSalda2(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
            }
        }
    }
    public static void changeButtonDraftPool(String toolUsed, VIEWController viewController){
        for(Node child : viewController.getMainController().getPlayerHUD().getDraftPool().getDraftPoolBox().getChildren()){
            switch (toolUsed){
                case "normalMove1": (child).setOnMouseClicked(e-> viewController.getToolController().getNormalMoveController().setNormalMove1(((child).getId())));
                    break;
                case "rigaInSughero1": (child).setOnMouseClicked(e-> viewController.getToolController().getRigaInSugheroController().setRigaInSughero1(((child).getId())));
                    break;
                case "taglierinaCircolare1": (child).setOnMouseClicked(e-> viewController.getToolController().getTaglierinaCircolareController().setTaglierinaCircolare1(((child).getId())));
                    break;
                case "diluentePerPastaSalda": (child).setOnMouseClicked(e-> viewController.getToolController().getDiluentePerPastaSaldaController().setDiluentePerPastaSalda(((child).getId())));
                    break;
                case "tamponeDiamantato": (child).setOnMouseClicked(e-> viewController.getToolController().getTamponeDiamantatoController().setTamponeDiamantato(((child).getId())));
                    break;
                case "tenagliaARotelle1": (child).setOnMouseClicked(e-> viewController.getToolController().getTenagliaARotelleController().setTenagliaARotelle1(((child).getId())));
                    break;
                case "pinzaSgrossatrice1": (child).setOnMouseClicked(e-> viewController.getToolController().getPinzaSgrossatriceController().setPinzaSgrossatrice1(((child).getId())));
                    break;
                case "pennelloPerPastaSalda1": (child).setOnMouseClicked(e-> viewController.getToolController().getPennelloPerPastaSaldaController().setPennelloPerPastaSalda1(((child).getId())));
                    break;
            }
        }
    }


    public static void setButtonDraftPoolNull(VIEWController viewController){
        for(Node child : viewController.getMainController().getPlayerHUD().getDraftPool().getDraftPoolBox().getChildren()){
            (child).setOnMouseClicked(null);
        }
    }
    public static void setButtonFacadeNull(VIEWController viewController){
        for(Node child : viewController.getMainController().getPlayerHUD().getPlayerFacade().getGridPane().getChildren()){
            (child).setOnMouseClicked(null);
        }
    }

    public static void setRoundTraceButtonNull(VIEWController viewController){ // non so se serve davvero questa funzione, vedo se cancellarla
        ObservableList<Node> children = viewController.getMainController().getPlayerHUD().getRoundTrace().getRoundTrace().getChildren();
        for (int i=0; i<children.size(); i++){
            final int j=i;
            children.get(i).setOnMouseClicked(e->GUIAlertBox.showSingleRound(j, viewController));
        }
    }
}
