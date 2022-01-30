package Shared.RMIInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote {

    /**
     * server to client interface comms
     * @author Fabrizio Siciliano
     * */
    void ping() throws RemoteException;
    String getName() throws RemoteException;
    void startGame() throws RemoteException;
    void notifyStartTurn() throws RemoteException;
    void notifyEndTurn() throws RemoteException;
    void playerNotAvailable(String name) throws RemoteException;
    void update() throws RemoteException;
    void playerAgainAvailable(String name) throws RemoteException;
    void continuePlaying() throws RemoteException;
}
