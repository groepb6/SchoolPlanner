package gui.assistclasses;

import gui.components.frames.FancyView;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

/**
 * @author Dustin Hendriks
 * @since 10-02-2019
 * <p>
 * The plan class uses SimpleStringProperty's to enable easy reading by the JavaFX TableView. This was not an option using normal Strings. Also SimpleStringProperty's are not serializable, which means the Schedule class had to be added as between method.
 */

public class Plan {
    private SimpleStringProperty time;
    private SimpleStringProperty group;
    private SimpleStringProperty location;
    private SimpleStringProperty teacher;
    private SimpleStringProperty subject;

    /**
     * The constructor sets all attributes to the values given as parameter
     *
     * @param time     Defines the time (format "ab:cd - ef:fh").
     * @param group    Defines the group string, can be anything except null.
     * @param location Defines the location string, can be anything except null.
     * @param teacher  Defines the teacher string, can be anything including null.
     * @param subject  The subject String, can be anything except null.
     */

    public Plan(String time, String group, String location, String teacher, String subject) {
        if (time == null) throw new IllegalArgumentException("Time may not be null");
        if (group == null) throw new IllegalArgumentException("Group may not be null");
        if (location == null) throw new IllegalArgumentException("Location may not be null");
        if (subject == null) throw new IllegalArgumentException("Subject may not be null");

        this.time = new SimpleStringProperty(time);
        this.group = new SimpleStringProperty(group);
        this.location = new SimpleStringProperty(location);
        this.teacher = new SimpleStringProperty(teacher);
        this.subject = new SimpleStringProperty(subject);
    }

    /**
     * @return time string.
     */

    public String getTime() {
        return this.time.get();
    }

    public void setTime(String time) {
        this.time = new SimpleStringProperty(time);
    }

    /**
     * @return group string.
     */

    public String getGroup() {
        return this.group.get();
    }

    /**
     * @return location string.
     */

    public String getLocation() {
        return this.location.get();
    }

    /**
     * @return teacher string.
     */

    public String getTeacher() {
        return this.teacher.get();
    }

    /**
     * @return subject string.
     */

    public String getSubject() {
        return this.subject.get();
    }

    /**
     * Return if the plan is equal to another plan.
     * @param plan Plan you want to compare with this. plan object.
     * @return True if the same, else false.
     */

    public boolean isEqualTo(Plan plan) {
        return (plan.getGroup().equals(getGroup()) && plan.getTime().equals(getTime()) && plan.getSubject().equals(getSubject()) && plan.getLocation().equals(getLocation()) && plan.getTeacher().equals(getTeacher()));
    }

    /**
     * @return Better readable string for the user (which is a sentence that tells the data).
     */

    public boolean isEqualToExceptTime(Plan plan) {
        boolean endTimeIsBeginTime;
        endTimeIsBeginTime =((FancyView.testDataToBeginTime(plan.getTime())==FancyView.testDataToEndTime(this.getTime())) || (FancyView.testDataToEndTime(plan.getTime())==FancyView.testDataToBeginTime(this.getTime())));
        return (plan.getGroup().equals(getGroup()) && plan.getSubject().equals(getSubject()) && plan.getLocation().equals(getLocation()) && plan.getTeacher().equals(getTeacher()) && endTimeIsBeginTime);
    }

    @Override
    public String toString() {
        return ("Group " + group.get() + " from " + time.get() + " at " + location.get() + " subject " + subject.get()) + " by " + teacher.get();
    }
}
