package Shared.RMIInterface;

import Shared.Model.Dice.Dice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface DraftPoolInterface extends Remote {
    ArrayList<Dice> getDraftPool() throws RemoteException;
    ArrayList<Dice> rollDiceInDraftpool(int index)throws RemoteException;
}