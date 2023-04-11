package common;

import java.io.Serializable;

public class Proposal implements Serializable {
    private Id id;
    private Operation operation;
    private String key;
    private String value;

    public enum Operation {
        PUT,
        GET,
        ADD;
    }

    public Proposal(Id id, Operation operation, String key, String value) {
        this.id = id;
        this.operation = operation;
        this.key = key;
        this.value = value;
    }
}
