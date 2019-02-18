package data.schedulerelated;

import gui.assistclasses.Plan;

import data.readwrite.Saveable;

import java.io.Serializable;

public class Schedule implements Serializable, Saveable {

    private String time;
    private String group;
    private String location;
    private String teacher;
    private String subject;

    public Schedule(Plan plan) {
        this.time = plan.getTime();
        this.group = plan.getGroup();
        this.location = plan.getLocation();
        this.teacher = plan.getTeacher();
        this.subject = plan.getSubject();
    }

    public Plan getPlan() {
        return new Plan(this.time, this.group, this.location, this.teacher, this.subject);
    }

    public String getTime() {
        return time;
    }

    public String getGroup() {
        return group;
    }

    public String getLocation() {
        return location;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getSubject() {
        return subject;
    }

    /**
     * Gets the path to the directory that Schedule objects should be saved to
     *
     * @return "saves/schedules/" which is the directory where the schedules should be saved
     */
    public String getPath() {
        return "saves/schedules/";
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
