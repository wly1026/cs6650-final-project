package client;

import api.PaxosServer;
import common.Response;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientApp {
    public static void main(String[] args) {
        int[] ports = new int[]{9091, 9092, 9093, 9094, 9095};
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            Runnable r = () -> {
                try {
                    Registry registry = LocateRegistry.getRegistry(ports[finalI]);
                    PaxosServer stub = (PaxosServer) registry.lookup("PaxosServer");
                    Response res = stub.get("aa");
                    System.out.println(res);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            new Thread(r).start();
        }
    }
}
