package server;

import api.PaxosServer;
import common.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
public class PaxosServerImpl implements PaxosServer {

    private static final int maxAttempts = 10;
    private Map<String, String> persist;
    private int[] allPorts;
    private int serverId;
    // It stores any promised proposal's id.
    private Id maxId;
    private Proposal accepted;
    private Log log;

    public PaxosServerImpl(int myPortIdx, int[] allPorts) {
        this.persist = new ConcurrentHashMap<>();
        this.allPorts = allPorts;
        this.serverId = myPortIdx;
        this.maxId = new Id(0, myPortIdx);
        this.log = new Log(allPorts[myPortIdx], "Server");
    }

    @Override
    public Response get(String key) throws RemoteException {
        log.Info("Receives request get key: %s", key);
        int attempts = 0;
        while (attempts < maxAttempts) {
            Id id = generateMaxId();
            Proposal proposal = new Proposal(id, Proposal.Operation.GET, key, "");
            if (runPaxos(proposal)) {
                if (persist.containsKey(key)) {
                    return new Response(Response.Status.SUCCESS, persist.get(key));
                } else {
                    return new Response(Response.Status.KEY_NOT_FOUND, "");
                }
            }
            attempts++;
        }
        return new Response(Response.Status.CONSENSUS_FAIL, "");
    }

    @Override
    public Response put(String key, String value) throws RemoteException {
        log.Info("Receives request put key: %s value: %s", key, value);
        int attempts = 0;
        while (attempts < maxAttempts) {
            Id id = generateMaxId();
            Proposal proposal = new Proposal(id, Proposal.Operation.PUT, key, value);
            if (runPaxos(proposal)) {
                return new Response(Response.Status.SUCCESS, "");
            }
            attempts++;
        }
        return new Response(Response.Status.CONSENSUS_FAIL, "");
    }

    @Override
    public Response delete(String key) throws RemoteException {
        log.Info("Receives request delete key: %s", key);
        int attempts = 0;
        while (attempts < maxAttempts) {
            Id id = generateMaxId();
            Proposal proposal = new Proposal(id, Proposal.Operation.DELETE, key, "");
            if (runPaxos(proposal)) {
                return new Response(Response.Status.SUCCESS, "");
            }
            attempts++;
        }
        return new Response(Response.Status.CONSENSUS_FAIL, "");
    }

    @Override
    public Promise promise(Proposal proposal) throws RemoteException {
//        log.Info("Receives a promise message, proposal: %s", proposal);

        // mimic failure like, the network failure
        if (Math.random() <= 0.1) {
            log.Error("Random failure");
            return null;
        }

        // The proposal is later.
        if (proposal.getId().compareTo(this.maxId) >= 0) {
            // update the maxId if it meets with later id.
            this.maxId = proposal.getId();
            if (this.accepted != null) {
                return new Promise(this.accepted, Promise.Status.ACCEPTED);
            } else {
                return new Promise(null, Promise.Status.AGREE);
            }
        }
        // The proposal is too early.
        else {
            // TODO the larger Id can be returned to tell the proposal to restart with higher id efficiently.
            return new Promise(null, Promise.Status.REJECTED);
        }
    }

    @Override
    public boolean accept(Proposal proposal) throws RemoteException {
//        log.Info("Receives a accept message, proposal: %s", proposal);
        if (proposal.getId().compareTo(this.maxId) >= 0) {
            this.accepted = proposal;
            this.maxId = proposal.getId();
            return true;
        }
        return false;
    }

    @Override
    public void commit(Proposal proposal) throws RemoteException {
//        log.Info("Receives a commit message, proposal: %s", proposal);
        if (proposal.getOperation() == Proposal.Operation.DELETE) {
            this.persist.remove(proposal.getKey());
        } else if (proposal.getOperation() == Proposal.Operation.PUT) {
            this.persist.put(proposal.getKey(), proposal.getValue());
        }
    }

    /**
     * Avoid that threads will propose a new value simultaneously.
     * @param proposal
     * @return
     */
    private synchronized boolean runPaxos(Proposal proposal) {
        log.Info("Running Paxos, proposal: %s", proposal);
        // get all acceptors' objects
        List<PaxosServer> acceptors = new ArrayList<>();
        for (int i = 0; i < this.allPorts.length; i++) {
            try {
                Registry registry = LocateRegistry.getRegistry("localhost", allPorts[i]);
                PaxosServer acceptor = (PaxosServer) registry.lookup("PaxosServer");
                acceptors.add(acceptor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int promised = 0;
        int half = Math.floorDiv(acceptors.size(), 2) + 1;

        // phase 1: send prepare
        List<Proposal> earlierAcceptedProposals = new ArrayList<>();
        for (PaxosServer acceptor: acceptors) {
            try {
                Promise promise = acceptor.promise(proposal);
                if (promise == null || promise.getStatus() == Promise.Status.REJECTED) {
                    continue;
                }
                promised++;
                if (promise.getStatus() == Promise.Status.ACCEPTED) {
                    earlierAcceptedProposals.add(promise.getProposal());
                }

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (promised < half) {
            log.Info("Promise failure, proposal: %s", proposal);
            return false;
        }

        // If any acceptedValue attached, replace the proposal's command with the latest accepted proposal.
        if (earlierAcceptedProposals.size() > 0) {
            Proposal latestProposal = Proposal.getMostLatestAcceptedProposal(earlierAcceptedProposals);
            proposal.updateCommand(latestProposal.getOperation(), latestProposal.getKey(), latestProposal.getValue());
        }

        // phase 2: send accept
        int accepted = 0;
        for (PaxosServer acceptor: acceptors) {
            try {
                if (acceptor.accept(proposal)) {
                    accepted++;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (accepted < half) {
            log.Info("Accept failure, proposal: %s", proposal);
            return false;
        }

        // phase 3: send commit
        for (PaxosServer acceptor: acceptors) {
            try {
                 acceptor.commit(proposal);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        log.Info("Run Paxos successfully, proposal %s", proposal);
        return true;
    }

    private synchronized Id generateMaxId() {
        try {
            Thread.sleep(new Random().nextInt(5));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.maxId = new Id(this.maxId.getSequenceId() + 1, this.serverId);
        return this.maxId;
    }
}
