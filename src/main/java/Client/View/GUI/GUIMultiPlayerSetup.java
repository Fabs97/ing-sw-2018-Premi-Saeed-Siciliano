package Client.View.GUI;

import Client.View.ViewControllers.VIEWController;

public class GUIMultiPlayerSetup {
    private VIEWController controller;

    public GUIMultiPlayerSetup(VIEWController controller){
        this.controller = controller;
    }

    /**
     * shows GUI for MultiPlayer setup
     * @see GUIWaitForLobbyReady for next screen
     * @author Fabrizio Siciliano
     * */
    public void showGUI(){
        GUIWaitForLobbyReady fourthPane = new GUIWaitForLobbyReady(controller);
        fourthPane.showGUI();
    }
}
