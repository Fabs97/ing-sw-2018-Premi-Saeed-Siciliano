package Server.RMIInterfaceImplementation;

import Shared.Exceptions.*;
import Shared.RMIInterface.ToolControllerInterface;
import Server.ToolCards.*;
import Server.Lobby.Lobby;
import Shared.Model.Dice.Dice;
import Shared.Model.Dice.DiceBag;
import Shared.Player;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Tools.ToolCard;
import Server.MainServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ToolController extends UnicastRemoteObject implements ToolControllerInterface {

    public ToolController() throws RemoteException {

    }

    @Override
    public int getFavours(String name)throws RemoteException {
        for(Player player: MainServer.getLobby().getPlayers()){
            if(player.getName().equals(name)){
                return player.getFavours();
            }
        }
        return -1;
    }



    @Override
    public void normalMove(Scheme scheme, Dice dice, int x, int y) throws CannotPlaceDiceException{
        for(Player player : MainServer.getLobby().getPlayers()){
            if(player.getScheme().getName().equals(scheme.getName())){
                try{
                    player.setScheme(PlaceDiceRestrictions.placeDice(scheme, dice, x, y));
                    ArrayList<Dice> draftPool= MainServer.getLobby().getGame().getDraftPool();
                    for (int i=0; i<draftPool.size();i++) {
                        if(draftPool.get(i).equals(dice)){
                            MainServer.getLobby().getGame().getDraftPool().remove(i);
                            break;
                        }
                    }
                }catch (CannotPlaceDiceException e){
                    player.setScheme(scheme);
                    throw new CannotPlaceDiceException("Errore nel piazzamento");
                }
                break;
            }
        }

    }

    @Override
    public void useTool(String name, String toolName){
        Lobby lobby = MainServer.getLobby();
        for(ToolCard toolCard : lobby.getGame().getTools()){
            if(toolCard.getName().equals(toolName))
                toolCard.useCard();
        }
        if(toolName.equals("Tenaglia a Rotelle")) {
            for (Player player : lobby.getPlayers()) {
                if (player.getName().equals(name))
                    player.setTenagliaARotelleUsed(true);
            }
        }
    }

    @Override
    public ArrayList<ToolCard> getToolCard() {
        return MainServer.getLobby().getGame().getTools();
    }

    @Override
    public void useAlesatorePerLaminaDiRame(Scheme scheme, int oldX, int oldY, int newX, int newY) throws WrongInputException, CannotPlaceDiceException {
        for(Player player : MainServer.getLobby().getPlayers()) {
            if (player.getScheme().getName().equals(scheme.getName())) {
                try{
                    player.setScheme(AlesatorePerLaminaDiRame.changeDicePosition(scheme, oldX, oldY, newX, newY));
                }catch (WrongInputException|CannotPlaceDiceException e){
                    player.setScheme(scheme);
                    if (e instanceof WrongInputException){
                        throw new WrongInputException("Input sbagliato");
                    }
                    if(e instanceof CannotPlaceDiceException    ){
                        throw new CannotPlaceDiceException("Impossibile piazzare il dado");
                    }
                }
                break;
            }
        }
    }
    @Override
    public void useDiluentePerPastaSaldaSwitch(int dicePosition){
        DiluentePerPastaSalda.switchDice(MainServer.getLobby().getGame().getDiceBag(), MainServer.getLobby().getGame().getDraftPool(),dicePosition);
    }
    @Override
    public void useDiluentePerPastaSaldaSetValue(Dice dice, int value){
        try{
            for(Dice selectedDice : MainServer.getLobby().getGame().getDraftPool()){
                if(selectedDice.equals(dice)){
                    DiluentePerPastaSalda.changeDiceTop(selectedDice, value);
                }
            }
        }catch (WrongInputException e){
        }

    }
    @Override
    public void useDiluentePerPastaSaldaPlace(Scheme scheme, Dice dice, int x, int y)throws CannotPlaceDiceException{
        try{
            normalMove(scheme, dice, x, y);
        }catch (CannotPlaceDiceException e){
            throw new CannotPlaceDiceException("Impossibile piazzare dado");
        }

    }
    @Override
    public void useLathekin(Scheme scheme, int firstDiceX, int firstDiceY, int firstX, int firstY, int secondDiceX, int secondDiceY, int secondX, int secondY) throws CannotPlaceDiceException{
        for(Player player : MainServer.getLobby().getPlayers()) {
            if (player.getScheme().getName().equals(scheme.getName())) {
                try{
                    player.setScheme(Lathekin.moveDices(scheme, firstDiceX, firstDiceY, firstX, firstY, secondDiceX, secondDiceY, secondX, secondY));
                }catch (CannotPlaceDiceException e){
                    player.setScheme(scheme);
                    throw new CannotPlaceDiceException("Impossibile piazzare i dadi");
                }
                break;
            }
        }

    }
    @Override
    public void useMartelletto(boolean firstTurn) throws CannotUseCardException {
        Martelletto.reRollDices(MainServer.getLobby().getGame().getDraftPool(), firstTurn);
    }
    @Override
    public void usePennelloPerEglomise(Scheme scheme, int oldX, int oldY, int newX, int newY) throws WrongInputException, CannotPlaceDiceException{
        for(Player player : MainServer.getLobby().getPlayers()) {
            if (player.getScheme().getName().equals(scheme.getName())) {
                try{
                    player.setScheme(PennelloPerEglomise.changeDicePosition(scheme, oldX, oldY, newX, newY));
                }catch (WrongInputException|CannotPlaceDiceException e){
                    player.setScheme(scheme);
                    if(e instanceof CannotPlaceDiceException){
                        throw new CannotPlaceDiceException("Impossibile piazzare il dado");
                    }
                    if(e instanceof WrongInputException){
                        throw new CannotPlaceDiceException("Input sbagliato");
                    }
                }
                break;
            }
        }

    }
    @Override
    public void usePennelloPerPastaSaldaRoll(Dice dice){
        for (Dice diceSelected : MainServer.getLobby().getGame().getDraftPool()){
            if(diceSelected.equals(dice)){
                PennelloPerPastaSalda.reRollDice(diceSelected);
                break;
            }
        }

    }
    @Override
    public void usePennelloPerPastaSaldaPlace(Scheme scheme,Dice dice, int x, int y)throws CannotPlaceDiceException{
        normalMove(scheme,dice,x,y);
    }
    @Override
    public void usePinzaSgrossatrice(Dice chosenDice, boolean increase)throws WrongInputException{
        ArrayList<Dice> draftPool= MainServer.getLobby().getGame().getDraftPool();
        for (int i=0; i<draftPool.size();i++) {
            if(draftPool.get(i).equals(chosenDice)){
                PinzaSgrossatrice.pickDiceTool(draftPool.get(i), increase);
                break;
            }
        }


    }
    @Override
    public void useRigaInSughero(Scheme scheme, Dice chosenDice, int x, int y) throws WrongInputException, CannotPlaceDiceException{
        for(Player player : MainServer.getLobby().getPlayers()){
            if(player.getScheme().getName().equals(scheme.getName())){
                try {
                    player.setScheme(RigaInSughero.setDice(scheme, chosenDice, x, y));
                    ArrayList<Dice> draftPool= MainServer.getLobby().getGame().getDraftPool();
                    for (int i=0; i<draftPool.size();i++) {
                        if(draftPool.get(i).equals(chosenDice)){
                            MainServer.getLobby().getGame().getDraftPool().remove(i);
                            break;
                        }
                    }
                    break;
                }catch (WrongInputException|CannotPlaceDiceException e){
                    player.setScheme(scheme);
                    if(e instanceof CannotPlaceDiceException){
                        throw new CannotPlaceDiceException("Impossibile piazzare il dado");
                    }
                    if(e instanceof WrongInputException){
                        throw new CannotPlaceDiceException("Input sbagliato");
                    }
                }

            }
        }

    }
    @Override
    public void useTaglierinaCircolare(Dice fromDraftPool, int round, int posInRound) throws IllegalRoundException {
        ArrayList<Dice> draftPool = MainServer.getLobby().getGame().getDraftPool();
        for(int i=0; i<draftPool.size();i++){
            if(draftPool.get(i).equals(fromDraftPool)){
                TaglierinaCircolare.switchDices(MainServer.getLobby().getGame().getDraftPool(), MainServer.getLobby().getGame().getTrace(),draftPool.get(i), round, posInRound);
                break;
            }
        }

    }
    @Override
    public void useTaglierinaManuale(Scheme scheme, int firstOldX, int firstOldY, int firstNewX, int firstNewY, int secondOldX, int secondOldY, int secondNewX, int secondNewY) throws IllegalRoundException, WrongInputException, CannotPlaceDiceException{
        for(Player player : MainServer.getLobby().getPlayers()) {
            if (player.getScheme().getName().equals(scheme.getName())) {
                try{
                    player.setScheme(TaglierinaManuale.moveDices(MainServer.getLobby().getGame().getTrace(),scheme, firstOldX, firstOldY, firstNewX, firstNewY, secondOldX, secondOldY, secondNewX, secondNewY));
                }catch (WrongInputException e){
                    player.setScheme(scheme);
                }
                break;
            }
        }

    }
    @Override
    public void useTamponeDiamantato(Dice dice){
        ArrayList<Dice> draftPool= MainServer.getLobby().getGame().getDraftPool();
        for (int i=0; i<draftPool.size();i++) {
            if(draftPool.get(i).equals(dice)){
                TamponeDiamantato.pickAndChangeTop(draftPool.get(i));
                break;
            }
        }

    }
    @Override
    public void useTenagliaARotelle(Scheme scheme, Dice dice, int x, int y) throws CannotPlaceDiceException{
        for(Player player : MainServer.getLobby().getPlayers()) {
            if (player.getScheme().getName().equals(scheme.getName())) {
                try{
                    player.setScheme(TenagliaARotelle.placeDice(scheme, dice, x, y));
                }catch (CannotPlaceDiceException e){
                    player.setScheme(scheme);
                    throw new CannotPlaceDiceException("Impossibile piazzare il dado");
                }
                break;
            }
        }

    }
}
