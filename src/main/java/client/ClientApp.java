package client;

import api.PaxosServer;
import common.Log;
import common.Response;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClientApp {
    private static Random random = new Random();
    private static Log log = new Log(0, "Client");
    public static void main(String[] args) {
        // get all proposers
        int[] ports = new int[]{9091, 9092, 9093, 9094, 9095};
        List<PaxosServer> proposers = new ArrayList<>();
        for (int i = 0; i < ports.length; i++) {
            try {
                Registry registry = LocateRegistry.getRegistry(ports[i]);
                PaxosServer proposer = (PaxosServer) registry.lookup("PaxosServer");
                proposers.add(proposer);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        // randomly choose on proposer to send the query.
        String[] queries = new String[]{"PUT KEY1 2", "PUT KEY2 4", "PUT KEY3 HELLO", "PUT KEY4 World", "PUT KEY5 5",
                "GET KEY1", "GET KEY2", "GET KEY3", "DELETE KEY1", "GET KEY1", "DELETE KEY2", "GET KEY2",
                "DELETE KEY3", "DELETE KEY4", "DELETE KEY6"};
        log.Info("Starting...");
        for (int i = 0; i < queries.length; i++) {
            int finalI = i;
            Runnable r = () -> {
                try {
                    PaxosServer server = proposers.get(random.nextInt(5));
                    log.Info("Starts %s", queries[finalI]);

                    String[] input = queries[finalI].split("\\s+");
                    Response res = null;
                    switch (input[0]) {
                        case "PUT":
                            res = server.put(input[1], input[2]);
                            break;
                        case "GET":
                            res = server.get(input[1]);
                            break;
                        case "DELETE":
                            res = server.delete(input[1]);
                            break;
                    }
                    log.Info("Finishes, query: %s, response: %s", queries[finalI], res);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            };

            new Thread(r).start();
        }
    }
}
