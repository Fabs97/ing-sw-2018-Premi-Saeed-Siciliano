package Shared.RMIInterface;

import Shared.Model.RoundTrace.RoundTrace;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RoundTraceInterface extends Remote {
    RoundTrace getRoundTrace() throws RemoteException;
}
