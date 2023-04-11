package common;

import java.io.Serializable;

public class Response implements Serializable {

    public enum Status {
        SUCCESS,
        CONSENSUS_FAIL,
        KEY_NOT_FOUND;
    }

    private Id id;
    private Status status;
    private String value;

    public Response(Id id, Status status, String value) {
        this.id = id;
        this.status = status;
        this.value = value;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Response{" +
                "id=" + id +
                ", status=" + status +
                ", value='" + value + '\'' +
                '}';
    }
}
