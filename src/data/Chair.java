package data;

import java.io.Serializable;

public class Chair implements Serializable {
    private int id;
    private boolean isAvailable;

    public Chair(int id, boolean isAvailable) {
        this.id = id;
        this.isAvailable = isAvailable;
    }
}
