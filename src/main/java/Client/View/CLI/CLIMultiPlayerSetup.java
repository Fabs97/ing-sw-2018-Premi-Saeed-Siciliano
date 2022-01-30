package Client.View.CLI;

import Client.View.ViewControllers.VIEWController;

public class CLIMultiPlayerSetup implements CLIInterface{

    private VIEWController controller;

    public CLIMultiPlayerSetup(VIEWController controller){
        this.controller = controller;
    }

    /**
     * shows multi player setup status
     * @author Fabrizio Siciliano
     * */
    public void showCLI(){
        //right now, nothing to show
    }
}
