package data.persons;

import data.schoolrelated.Subject;

import java.io.Serializable;

public class Teacher extends Person implements Serializable {

    private Subject subject;

    public Teacher(String name) {
        super(name);
        this.subject = subject;
    }
}
