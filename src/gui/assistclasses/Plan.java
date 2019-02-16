package gui.assistclasses;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Dustin Hendriks
 * @since 11-02-2019
 *
 * The Plan class exists to let Classes easily read data, especially the TableView, which needs SimpleStringProperty's, else it can not read and place the data in its columns.
 */

public class Plan {
    private SimpleStringProperty time;
    private SimpleStringProperty group;
    private SimpleStringProperty location;
    private SimpleStringProperty teacher;
    private SimpleStringProperty subject;

    /**
     * The constructor sets all Plan information, such as time, group, location, teacher and subject.
     * @param time Time string, needs format "ab:cd - ef:gh".
     * @param group Group string, can be anything, however spaces can make searching hard when a strict searching method is used (when .trim() is not performed).
     * @param location Location string, can be anything.
     * @param teacher Teacher string, can be anything.
     * @param subject Subject string, can be anything.
     */

    public Plan(String time, String group, String location, String teacher, String subject) {
        this.time = new SimpleStringProperty(time);
        this.group = new SimpleStringProperty(group);
        this.location = new SimpleStringProperty(location);
        this.teacher = new SimpleStringProperty(teacher);
        this.subject = new SimpleStringProperty(subject);
    }

    /**
     * @return Receive the time String.
     */

    public String getTime() {
        return this.time.get();
    }

    /**
     * @return Receive the group string.
     */

    public String getGroup() {
        return this.group.get();
    }

    /**
     * @return Receive the location String.
     */

    public String getLocation() {
        return this.location.get();
    }

    /**
     * @return Receive the teacher String.
     */

    public String getTeacher() {
        return this.teacher.get();
    }

    /**
     * @return Receive the subject String.
     */

    public String getSubject() {
        return this.subject.get();
    }

    /**
     * Check if a plan is equal to another plan.
     * @param plan Plan you want to compare with -this- plan object.
     * @return Receive a true or false value that tells if there is any difference.
     */

    public boolean isEqualTo(Plan plan) {
        return (plan.getGroup().equals(getGroup()) && plan.getTime().equals(getTime()) && plan.getSubject().equals(getSubject()) && plan.getLocation().equals(getLocation()) && plan.getTeacher().equals(getTeacher()));
    }

    /**
     * @return String that places all variables in a nice sentence, so it is comfortable for the user to read.
     */

    @Override
    public String toString() {
        return ("Group " + group.get() + " from " + time.get() + " at " + location.get() + " subject " + subject.get()) + " by " + teacher.get();
    }
}
