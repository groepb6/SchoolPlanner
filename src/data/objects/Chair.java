package data.objects;

/**
 * @author Hanno Brandwijk
 * @author Wout Stevens
 */

public class Chair {
    public boolean isAvailable;
    public enum Direction {
        LEFT, RIGHT, UP, DOWN, TOILET
    }
    public Direction direction;
    public int x;
    public int y;
    public boolean isChair = false;
    public Chair() {
        this.isAvailable = true;
    }
    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void setDir(Direction dir) {
        this.direction = dir;
    }
}
