package simulation;

import data.schedulerelated.Hour;
import org.jfree.fx.FXGraphics2D;

/**
 * Tracks the hours and minutes of the simulation.
 *
 * @author Noah Walmits
 */
public class SimTime {
    private int startingHour;
    private int hours;
    private double minutes;
    private double speed;
    private boolean updated;
    private boolean updatesEnabled;
    private boolean speedUpdated;

    public static final double DEFAULTSPEED = 1.0;
    public static final double MINSPEED = 0.1;
    public static final double MAXSPEED = 10;

    /**
     * Creates a SimTime object. The starting hour can be decided, but the starting minute cannot.
     *
     * @param startingHour The hour this SimTime object should start at
     */
    public SimTime(int startingHour) {
        this.startingHour = startingHour;
        this.hours = startingHour;
        this.minutes = 0;
        this.speed = this.DEFAULTSPEED;
        this.updated = true;
        this.updatesEnabled = true;
        this.speedUpdated = true;
    }

    /**
     * Updates the time of this objects. 60 minutes or more become 1 and add an hour. 24 hours or more become 0.
     * With a speed of 1, every real-time second would be a minute.
     *
     * @param deltaTime The difference of time, given my the animation timer.
     */
    public void update(double deltaTime) {
        this.minutes += deltaTime * this.speed;
        if (this.minutes >= 60.0) {
            if (this.updatesEnabled) {
                this.updated = true;
            }
            this.hours++;
            this.minutes = this.minutes % 60.0;
            this.hours = this.hours % 24;
        }
    }

    public void reset() { //TODO: test proper reset procedures
        this.hours = this.startingHour;
        this.minutes = 0.0;
        this.speed = 1;
        this.updated = true;
    }

    /**
     * Changes the speed of SimTime. The speed cannot be decreased under the minimum speed as set by the constant MINSPEED.
     * The speed cannot be increased above the maximum as set by the constant MAXSPEED. Any speed change that causes the speed
     * to go over/under the maximum/minimum will be reverted, in order to prevent the speed settings from getting out of sync.
     *
     * @param speedChange A positive or negative double that will be added to the current speed.
     */
    public void changeSpeed(double speedChange) {
        this.speed += speedChange;
        if (this.speed < this.MINSPEED) {
            this.speed -= speedChange;
        } else if (this.speed > this.MAXSPEED) {
            this.speed -= speedChange;
        }
        this.speedUpdated = true;
    }

    public int getHours() {
        return this.hours;
    }

    public int getMinutes() {
        return (int) this.minutes;
    }

    public void minSpeed() {
        this.speed = MINSPEED;
    }

    public void maxSpeed() {
        this.speed = MAXSPEED;
    }

    /**
     * Used by a non-SimTime object to set updated to false.
     */
    public void updateReceived() {
        this.updated = false;
    }

    public boolean isUpdated() {
        return this.updated;
    }

    /**
     * Allows updated to be set to true again and sets updated to true.
     */
    public void enableUpdates() {
        this.updated = true;
        this.updatesEnabled = true;
    }

    /**
     * Stops updated from being set to true.
     */
    public void disableUpdates() {
        this.updatesEnabled = false;
    }

    public double getSpeed() {
        return speed;
    }

    /**
     * Finds a value from Hour that fits the current time in the simulation.
     *
     * @return A value from the Hour enum.
     */
    public Hour getTimeSlot() {
        if (this.hours < 8) {
            return Hour.NONE;
        }
        if (this.hours < 9) {
            return Hour.FIRST;
        }
        if (this.hours < 10) {
            return Hour.SECOND;
        }
        if (this.hours < 11) {
            return Hour.THIRD;
        }
        if (this.hours < 12) {
            return Hour.FOURTH;
        }
        if (this.hours < 13) {
            return Hour.FIFTH;
        }
        if (this.hours < 14) {
            return Hour.SIXTH;
        }
        if (this.hours < 15) {
            return Hour.SEVENTH;
        }
        if (this.hours < 16) {
            return Hour.EIGHTH;
        }
        if (this.hours < 17) {
            return Hour.NINTH;
        }
        if (this.hours < 18) {
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
