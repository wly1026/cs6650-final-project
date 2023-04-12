package server;

import api.KeyStoreServer;
import api.PaxosServer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerApp {
    public static void main(String[] args) {
        int[] ports = new int[]{9091, 9092, 9093, 9094, 9095};
        KeyStoreServer[] servers = new KeyStoreServer[5];
        List<Thread> serverThreads = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            Runnable r = () -> {
                PaxosServer server = new PaxosServerImpl(finalI, ports);
                int port = ports[finalI];
                servers[finalI] = server;
                try {
                    PaxosServer stub = (PaxosServer) UnicastRemoteObject.exportObject(server, 0);
                    Registry registry = LocateRegistry.createRegistry(port);
                    registry.bind("PaxosServer", stub);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.printf("Server starts at port %d\n", port);
            };
            Thread t = new Thread(r);
            serverThreads.add(t);
        }

        // Periodically choose at most 2 servers to kill and restart.
        while (true) {


            // recover all state information from a peer server
        }
    }
}
