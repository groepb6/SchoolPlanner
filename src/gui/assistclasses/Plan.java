package gui.assistclasses;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class Plan {
    private SimpleStringProperty time;
    private SimpleStringProperty group;
    private SimpleStringProperty location;
    private SimpleStringProperty teacher;
    private SimpleStringProperty subject;

    public Plan(String time, String group, String location, String teacher, String subject) {
        this.time = new SimpleStringProperty(time);
        this.group = new SimpleStringProperty(group);
        this.location = new SimpleStringProperty(location);
        this.teacher = new SimpleStringProperty(teacher);
        this.subject = new SimpleStringProperty(subject);
    }

    public void setTime(final String time) {
        this.time.set(time);
    }

    public void setGroup(final String group) {
        this.group.set(group);
    }

    public void setLocation(final String location) {
        this.location.set(location);
    }

    public void setTeacher(final String teacher) {
        this.teacher.set(teacher);
    }

    public void setSubject(final String subject) {
        this.subject.set(subject);
    }

    public String getTime() {
        return this.time.get();
    }

    public String getGroup() {
        return this.group.get();
    }

    public String getLocation() {
        return this.location.get();
    }

    public String getTeacher() {
        return this.teacher.get();
    }

    public String getSubject() {
        return this.subject.get();
    }

    public boolean isEqualTo(Plan plan) {
        return (plan.getGroup().equals(getGroup()) && plan.getTime().equals(getTime()) && plan.getSubject().equals(getSubject()) && plan.getLocation().equals(getLocation()) && plan.getTeacher().equals(getTeacher()));
    }

    @Override
    public String toString() {
        return ("Group " + group.get() + " from " + time.get() + " at " + location.get() + " subject " + subject.get()) + " by " + teacher.get();
    }
}
