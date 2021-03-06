package data.rooms;

import data.objects.Toilet;

/**
 * @author Hanno Brandwijk
 * @author Wout Stevens
 */

public class Bathroom extends Room {

    public Bathroom(String name, int capacity) {
        super(name);
    }

    @Override
    protected void addSeats() {
        for (int i = 0; i < super.getCapacity(); i++) {
            super.getSeats().add(new Toilet());
        }
    }
}
