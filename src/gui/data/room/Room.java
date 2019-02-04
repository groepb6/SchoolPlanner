package gui.data.room;

abstract public class Room {
    private String name;
    private int capacity;
    private boolean isAvailable;

    public Room(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
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
