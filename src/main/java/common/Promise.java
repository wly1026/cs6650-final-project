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

    public Proposal getProposal() {
        return proposal;
    }

    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
