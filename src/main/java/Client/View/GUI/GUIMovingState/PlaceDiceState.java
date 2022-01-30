package Client.View.GUI.GUIMovingState;

import Client.ClientRMI.RMIClientController;
import Client.View.GUI.GUIPlayerHUD;
import Client.View.ViewControllers.VIEWController;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;


public class PlaceDiceState implements State{
    @Override
    public void setActions(VIEWController controller) {
        GUIPlayerHUD hud = controller.getMainController().getPlayerHUD();

        ObservableList<Node> draftPoolchildren = hud.getDraftPool().getDraftPoolBox().getChildren();
        for(Node child : draftPoolchildren){
            ImageView view = (ImageView) child;

            view.setOnDragDetected(event -> {
                Dragboard db = view.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString(view.getId());
                db.setContent(content);
                event.consume();
            });

            view.setOnDragDone(event -> view.setImage(null));
        }

        ObservableList<Node> facadeChilds = hud.getPlayerFacade().getGridPane().getChildren();
        RMIClientController control;
        for(Node child : facadeChilds){
            ImageView view = (ImageView) child;

            view.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                boolean success = false;
               /* if(db.hasString()){
                    //TODO: complete it


                    success = controller.getToolController().getRMIController()(controller.getCardsController().getScheme(), controller.getTableController().getDraftPool().get(controller.getTableController().getDraftPool().), GridPane.getColumnIndex(child), GridPane.getRowIndex(child));
                    System.out.println("dado piazzato : " + success);
                }*/
                event.setDropCompleted(success);
                event.consume();
            });

        }
    }
}
