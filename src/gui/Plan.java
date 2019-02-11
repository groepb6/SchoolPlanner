package gui;

import javafx.beans.property.SimpleStringProperty;

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
        return (time.get() + group.get() + location.get() + teacher.get() + subject.get());
    }
}
