package data;

import java.io.Serializable;

public class Toilet implements Serializable {
    private int id;
    private boolean isAvailable;

    public Toilet(int id, boolean isAvailable) {
        this.id = id;
        this.isAvailable = isAvailable;
    }
}
