package data.persons;

import data.schedulerelated.Hour;
import data.schoolrelated.Subject;

import java.io.Serializable;
import java.util.ArrayList;

public class Teacher extends Person implements Serializable {

    private Subject subject;
    private ArrayList<Hour> hours;

    public Teacher(String name) {
        super(name);
        this.subject = subject;
        this.hours = new ArrayList<>();
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public ArrayList<Hour> getHours() {
        return hours;
    }

    public void setHours(ArrayList<Hour> hours) {
        this.hours = hours;
    }
}
