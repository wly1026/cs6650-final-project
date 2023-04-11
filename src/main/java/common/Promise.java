package common;

import java.io.Serializable;

public class Promise implements Serializable {
    public enum Status {
        // The acceptor has accepted one earlier proposal
        ACCEPTED,
        // The acceptor has accepted later proposal
        REJECTED,
        // The acceptor has never accepted any other proposal
        AGREE,
    }
    private Proposal proposal;
    private Status status;

    public Promise(Proposal proposal, Status status) {
        this.proposal = proposal;
        this.status = status;
    }
}
