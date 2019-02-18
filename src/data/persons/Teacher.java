package data.persons;

import data.schoolrelated.Subject;

public class Teacher extends Person {
    private Subject subject;

    public Teacher(String name, int id) {
        super(name, id);
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
