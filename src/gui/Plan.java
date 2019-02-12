package gui;

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

    public void setTime(String time) {
        this.time.set(time);
    }

    public void setGroup(String group) {
        this.group.set(group);
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public void setTeacher(String teacher) {
        this.teacher.set(teacher);
    }

    public void setSubject(String subject) {
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

    @Override
    public String toString() {
        return ("Group " + group.get() + " from "+time.get() + " at "+location.get()  + " subject "+subject.get()) + " by "+teacher.get();
    }

    public static ArrayList getTestData() {
        ArrayList list = new ArrayList();
        list.add(new Plan("08:45 - 09:45", "23TIVT1B6", "LA424", "Johan", "OGP"));
        list.add(new Plan("10:00 - 12:00", "23TIVT1B6", "LA218", "Pieter", "Maths"));
        list.add(new Plan("13:30 - 14:00", "23TIVT1B6", "LA672", "Genevieve", "P&OC"));
        list.add(new Plan("15:00 - 16:30", "23TIVT1B6", "LD726", "Jan", "OOM"));
        list.add(new Plan("17:00 - 20:00", "23TIVT1B6", "HA300", "Peter", "Hardware"));

        return list;
    }
}
