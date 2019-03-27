package data.objects;

/**
 * @author Hanno Brandwijk
 * @author Wout Stevens
 * */

public class Chair {
    private boolean isAvailable;

    public Chair() {
        this.isAvailable = true;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
