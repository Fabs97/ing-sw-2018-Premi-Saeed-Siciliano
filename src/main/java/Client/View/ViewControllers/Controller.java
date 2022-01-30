package Client.View.ViewControllers;

import Client.ClientRMI.RMIClient;
import Client.ClientSocket.SocketClient;
import Client.View.GUI.GUIPlayerHUD;


public class Controller {
    private boolean usingCLI;
    private boolean singlePlayer;
    private boolean usingRMI;
    private volatile boolean isMyTurn;
    private String playerName;
    private String idLobby;
    private boolean gameStarted;
    private boolean continueGame;
    private GUIPlayerHUD playerHUD;
    private RMIClient rmiClient;
    private SocketClient socketClient;

    public Controller(){
        this.usingCLI       = false;
        this.singlePlayer   = false;
        this.usingRMI       = false;
        this.isMyTurn       = false;
        this.playerName     = null;
        this.idLobby        = null;
        this.gameStarted    = false;
        this.continueGame   = false;
        this.playerHUD      = null;
        this.rmiClient      = null;
        this.socketClient   = null;
    }

    /**
     * @return this client
     * @see RMIClient
     * @author Fabrizio Siciliano
     * */
    public RMIClient getClient() {
        return rmiClient;
    }

    /**
     * @param rmiClient sets this client
     * @author Fabrizio Siciliano
     * */
    public void setClient(RMIClient rmiClient) {
        this.rmiClient = rmiClient;
    }

    /**
     * @param usingCLI sets using CLI or GUI usage
     * @author Fabrizio Siciliano
     * */
    public void setUsingCLI(boolean usingCLI) {
        this.usingCLI = usingCLI;
    }

    /**
     * @return CLI or GUI usage
     * @author Fabrizio Siciliano
     * */
    public boolean isUsingCLI() {
        return usingCLI;
    }

    public void setPlayerHUD(GUIPlayerHUD playerHUD) {
        this.playerHUD = playerHUD;
    }

    /**
     * @return client's player HUD
     * @see GUIPlayerHUD
     * @author Fabrizio Siciliano
     * */
    public GUIPlayerHUD getPlayerHUD(){
        return this.playerHUD;
    }

    /**
     * @param name name of the client
     * @author Fabrizio Siciliano
     * */
    public void insertUsername(String name){
        this.playerName = name;
    }

    /**
     * @return name of the client
     * @author Fabrizio Siciliano
     * */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * @param value single or multi player mode
     * @author Fabrizio Siciliano
     * */
    public void insertSinglePlayer(boolean value){
        singlePlayer = value;
    }

    /**
     * @return single or multi player mode
     * @author Fabrizio Siciliano
     * */
    public boolean isSinglePlayer() {
        return singlePlayer;
    }

    /**
     * @param usingRMI RMI or socket usage
     * @author Fabrizio Siciliano
     * */
    public void setUsingRMI(boolean usingRMI) {
        this.usingRMI = usingRMI;
    }

    /**
     * @return RMI or socket usage
     * @author Fabrizio Siciliano
     * */
    public boolean isUsingRMI() {
        return usingRMI;
    }

    /**
     * @param idLobby value of the lobby joined
     * @author Fabrizio Siciliano
     * */
    public void setIdLobby(String idLobby){
        this.idLobby = idLobby;
    }

    /**
     * @return value of the lobby joined
     * @author Fabrizio Siciliano
     * */
    public String getIdLobby(){
        return this.idLobby;
    }

    /**
     * @return value of the game started
     * @author Fabrizio Siciliano
     * */
    public boolean isGameStarted() {
        return gameStarted;
    }

    /**
     * @param gameStarted valued of the game started
     * @author Fabrizio Siciliano
     * */
    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    /**
     * @return boolean value for isMyTurn
     * @author Marco Premi
     */
    public  boolean isMyTurn() {
        return isMyTurn;
    }

    /**
     * @param myTurn boolean value for isMyTurn
     * @author Marco Premi
     */
    public void setMyTurn(boolean myTurn) {
        if(!usingCLI) {
            if(playerHUD.getButtonMoves()!=null)
                playerHUD.getButtonMoves().setTurnOnGoing(myTurn);
            else
                new Thread(() -> {
                    while(playerHUD.getButtonMoves() == null){}
                    playerHUD.getButtonMoves().setTurnOnGoing(myTurn);
                }).start();
        }
        isMyTurn = myTurn;
    }

    public SocketClient getSocketClient(){
        return socketClient;
    }

    public void setSocketClient(SocketClient client){
        this.socketClient = client;
    }

    public boolean canContinueGame() {
        return continueGame;
    }

    public void setContinueGame(boolean continueGame) {
        this.continueGame = continueGame;
    }

    public static void handleServerDisconnected(){
        int seconds = 3;
        System.out.println("Non sono riuscito a comunicare col server");
        System.out.println("Chiudo il programma tra " + seconds + " secondi");
        long elapsedTime;
        long waitBeforeClosing = seconds*1000L;
        long startTime = System.currentTimeMillis();
        do{
            elapsedTime = System.currentTimeMillis() - startTime;
        }while(elapsedTime >= waitBeforeClosing);
        System.exit(0);
    }
}
