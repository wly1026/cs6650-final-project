package common;

import java.io.Serializable;

public class Id implements Serializable {
    private int id;
    private int serverId;

    public Id(int id, int serverId) {
        this.id = id;
        this.serverId = serverId;
    }

    /**
     *
     * @param other
     * @return true if Id is strictly larger than other id, meaning either the id is larger,
     * or ids are same but the serverId is larger.
     */
    public boolean isLater(Id other) {
        if (this.id == other.id) {
            return serverId > other.serverId;
        } else {
            return this.id > other.id;
        }
    }
}
