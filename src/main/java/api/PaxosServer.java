package api;

import common.Promise;
import common.Proposal;

import java.rmi.Remote;
import java.rmi.RemoteException;

//Todo add comments
public interface PaxosServer extends KeyStoreServer, Remote {
    Promise promise(Proposal proposal) throws RemoteException;
    boolean accept(Proposal proposal) throws  RemoteException;
    void commit(Proposal proposal) throws RemoteException;
}