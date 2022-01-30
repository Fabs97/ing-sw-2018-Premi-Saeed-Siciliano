package Shared.RMIInterface;

import Shared.Model.Schemes.Scheme;
import Shared.Model.Schemes.SchemeCard;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SchemeInterface extends Remote{
    SchemeCard getSchemeCard() throws RemoteException;
    void setScheme(String name, String schemeName) throws RemoteException;
    Scheme getScheme(String name) throws RemoteException;
    int getPlayerFavours(String name) throws RemoteException;
    void saveCardOnServer(String[] names, String[] favors, String[] scheme1, String[] scheme2) throws RemoteException;

}
