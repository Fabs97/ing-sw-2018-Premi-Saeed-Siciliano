package Shared.RMIInterface;

import Shared.Model.ObjectiveCard.PrivateObjective;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrivateObjectiveInterface extends Remote {
    PrivateObjective getPrivateObjective1(String name) throws RemoteException;
    PrivateObjective getPrivateObjective2(String name) throws RemoteException;

}
