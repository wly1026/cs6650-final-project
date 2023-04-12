package common;

import java.io.Serializable;

public class Response implements Serializable {

    public enum Status {
        SUCCESS,
        CONSENSUS_FAIL,
        KEY_NOT_FOUND;
    }

    private Status status;
    private String value;

    public Response(Status status, String value) {
        this.status = status;
        this.value = value;
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
                "status=" + status +
                ", value='" + value + '\'' +
                '}';
    }
}
