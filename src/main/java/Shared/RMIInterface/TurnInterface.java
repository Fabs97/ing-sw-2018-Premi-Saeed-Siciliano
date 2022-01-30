package Shared.RMIInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TurnInterface extends Remote{
    void endMyTurn(String name) throws RemoteException;
    boolean isGameEnded() throws RemoteException;
    int round() throws  RemoteException;
    boolean isClockwise() throws RemoteException;
}

