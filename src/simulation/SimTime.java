package simulation;

import data.schedulerelated.Hour;
import org.jfree.fx.FXGraphics2D;

/**
 * Handles the time of the simulation
 */
public class SimTime {
    private int startingHour;
    private int hours;
    private double minutes;
    private double speed;
    private boolean updated;

    /**
     * Creates a SimTime object. The starting hour can be decided, but the starting minute cannot.
     * @param startingHour The hour this SimTime object should start at
     * @param speed        The starting speed of this SimTime object.
     */
    public SimTime(int startingHour, double speed) {
        this.startingHour = startingHour;
        this.hours = startingHour;
        this.minutes = 0;
        this.speed = speed;
        this.updated = true;
    }

    /**
     * Updates the time of this objects. 60 minutes or more become 1 and add an hour. 24 hours or more become 0.
     * With a speed of 1, every real-time second would be a minute.
     * @param deltaTime The difference of time, given my the animation timer.
     */
    public void update(double deltaTime) {
        this.minutes += deltaTime * this.speed;
        if (this.minutes >= 60.0) {
            this.setUpdated(true);
            this.hours++;
            this.minutes = 0;
            if (this.hours >= 24) {
                this.hours = 0;
            }
        }
    }

    public void draw(FXGraphics2D graphics) {

    }

    public void reset() { //TODO: test proper reset procedures
        this.hours = this.startingHour;
        this.minutes = 0.0;
        this.speed = 1;
        this.updated = true;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getHours() {
        return this.hours;
    }

    public int getMinutes() {
        return (int) this.minutes;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    public boolean isUpdated() {
        return this.updated;
    }

    /**
     * Finds a value from Hour that fits the current time in the simulation.
     * @return A value from the Hour enum.
     */
    public Hour getTimeSlot() {
        if (this.hours <= 8) {
            return Hour.NONE;
        } if (this.hours <= 9) {
            return Hour.FIRST;
        } if (this.hours <= 10) {
            return Hour.SECOND;
        } if (this.hours <= 11) {
            return Hour.THIRD;
        } if (this.hours <= 12) {
            return Hour.FOURTH;
        }  if (this.hours <= 13) {
            return Hour.FIFTH;
        } if (this.hours <= 14) {
            return Hour.SIXTH;
        } if (this.hours <= 15) {
            return Hour.SEVENTH;
        }  if (this.hours <= 16) {
            return Hour.EIGHTH;
        } if (this.hours <= 17) {
            return Hour.NINTH;
        } if (this.hours <= 18) {
            return Hour.TENTH;
        } else {
            return Hour.NONE;
        }
    }

    @Override
    public String toString() {
        return "SimTime{" +
                "hours=" + hours +
                ", minutes=" + minutes +
                ", speed=" + speed +
                ", updated=" + updated +
                '}';
    }
}
