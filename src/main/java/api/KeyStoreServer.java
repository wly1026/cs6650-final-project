package api;

import common.Response;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface KeyStoreServer extends Remote {
    Response get(String key) throws RemoteException;
    Response put(String key, String value) throws RemoteException;
    Response delete(String key) throws  RemoteException;
}
