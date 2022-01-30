package Shared.RMIInterface;

import Shared.Model.ObjectiveCard.PublicObjective;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface PublicObjectiveInterface extends Remote {
    ArrayList<PublicObjective> getPublicObjective() throws RemoteException;
}
