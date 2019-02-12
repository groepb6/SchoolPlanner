package data.schedulerelated;

import gui.assistclasses.Plan;

import java.io.Serializable;

public class Schedule implements Serializable {

    private transient Plan plan;
    private String time;
    private String group;
    private String location;
    private String teacher;
    private String subject;

    public Schedule(Plan plan) {
        this.plan = plan;
        this.time = this.plan.getTime();
        this.group = this.plan.getGroup();
        this.location = this.plan.getLocation();
        this.teacher = this.plan.getTeacher();
        this.subject = this.plan.getSubject();
    }

    public Plan getPlan() {
        return new Plan(this.time, this.group, this.location, this.teacher, this.subject);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "time='" + time + '\'' +
                ", group='" + group + '\'' +
                ", location='" + location + '\'' +
                ", teacher='" + teacher + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
