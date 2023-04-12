package common;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public class Id implements Serializable, Comparable<Id> {
    private int sequenceId;
    private int serverId;

    public Id(int id, int serverId) {
        this.sequenceId = id;
        this.serverId = serverId;
    }

    public int getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(int id) {
        this.sequenceId = id;
    }


    @Override
    public int compareTo(Id o) {
        if (this.sequenceId == o.sequenceId) {
            return this.serverId - o.serverId;
        } else {
            return this.sequenceId - o.sequenceId;
        }
    }

    @Override
    public String toString() {
        return "Id{" +
                "id=" + sequenceId +
                ", serverId=" + serverId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Id id1 = (Id) o;
        return sequenceId == id1.sequenceId && serverId == id1.serverId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sequenceId, serverId);
    }
}
