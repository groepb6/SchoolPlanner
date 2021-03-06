package data.persons;

import data.schedulerelated.Hour;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Hanno Brandwijk
 * @author Wout Stevens
 */

public class Teacher extends Person implements Serializable {
    private ArrayList<Hour> hours;

    public Teacher(String name) {
        super(name);
        this.hours = new ArrayList<>();
    }

    public ArrayList<Hour> getHours() {
        return hours;
    }
    public void setHours(ArrayList<Hour> hours) {
        this.hours = hours;
    }
}
