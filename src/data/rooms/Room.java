package data.rooms;

import data.schedulerelated.Hour;

import java.io.Serializable;
import java.util.ArrayList;

abstract public class Room implements Serializable {

    private String name;
    private ArrayList<Hour> hours;
    private int capacity;
    private boolean isAvailable;

    public Room(String name) {
        this.name = name;
        this.hours = new ArrayList<>();
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

    public ArrayList<Hour> getHours() {
        return hours;
    }

    public void setHours(ArrayList<Hour> hours) {
        this.hours = hours;
    }
}
