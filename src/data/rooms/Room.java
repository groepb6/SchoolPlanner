package data.rooms;

import data.objects.Chair;
import data.schedulerelated.Hour;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hanno Brandwijk
 * @author Wout Stevens
 */

abstract public class Room implements Serializable {

    private String name;
    private ArrayList<Hour> hours;
    private int capacity;
    private List<Chair> seats;
    private boolean isAvailable;

    public Room(String name) {
        this.name = name;
        this.hours = new ArrayList<>();
        this.capacity = capacity;
        this.seats = new ArrayList<>();
        this.addSeats();
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

    public List<Chair> getSeats() {
        return this.seats;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean available) {
        isAvailable = available;
    }

    protected void addSeats() {
        for (int i = 0; i < this.capacity; i++) {
            this.seats.add(new Chair());
        }
    }

    public ArrayList<Hour> getHours() {
        return hours;
    }

    public void setHours(ArrayList<Hour> hours) {
        this.hours = hours;
    }
}
