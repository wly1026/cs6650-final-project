package common;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Proposal implements Serializable {
    private Id id;
    private Operation operation;
    private String key;
    private String value;

    public enum Operation {
        PUT,
        GET,
        DELETE;
    }

    public Proposal(Id id, Operation operation, String key, String value) {
        this.id = id;
        this.operation = operation;
        this.key = key;
        this.value = value;
    }

    public Id getId() {
        return id;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void updateCommand(Operation op, String key, String value) {
        this.operation = op;
        this.key = key;
        this.value = value;
    }

    public static Proposal getMostLatestAcceptedProposal(List<Proposal> earlierAcceptedProposals) {
        return earlierAcceptedProposals.stream().max(Comparator.comparing(Proposal::getId)).get();
    }

    @Override
    public String toString() {
        return "Proposal{" +
                "id=" + id +
                ", operation=" + operation +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proposal proposal = (Proposal) o;
        return Objects.equals(id, proposal.id) && operation == proposal.operation && Objects.equals(key, proposal.key) && Objects.equals(value, proposal.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, operation, key, value);
    }
}
