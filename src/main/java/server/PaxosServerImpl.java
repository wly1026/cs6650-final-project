package server;

import api.PaxosServer;
import common.Id;
import common.Promise;
import common.Proposal;
import common.Response;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PaxosServerImpl implements PaxosServer {
    private Map<String, String> persist;
    private int myPort;
    private int[] allPorts;

    public PaxosServerImpl(int myPortIdx, int[] allPorts) {
        this.persist = new ConcurrentHashMap<>();
        this.myPort = allPorts[myPortIdx];
        this.allPorts = allPorts;
    }

    @Override
    public Response get(String key) throws RemoteException {
        return new Response(new Id(1, 1), Response.Status.SUCCESS, "I am port" + myPort);
    }

    @Override
    public Response put(String key, String value) throws RemoteException {
        return null;
    }

    @Override
    public Response delete(String key) throws RemoteException {
        return null;
    }

    @Override
    public Promise promise(Proposal proposal) throws RemoteException {
        return null;
    }

    @Override
    public boolean accept(Proposal proposal) throws RemoteException {
        return false;
    }

    @Override
    public void commit(Proposal proposal) throws RemoteException {

    }
}
