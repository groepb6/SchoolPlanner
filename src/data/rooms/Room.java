package data.rooms;

import java.io.Serializable;

abstract public class Room implements Serializable {

    private String name;
    private int capacity;
    private boolean isAvailable;

    public Room(String name) {
        this.name = name;
        //his.capacity = capacity;
        this.isAvailable = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
