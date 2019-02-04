package data;

import data.person.Teacher;

import java.io.Serializable;
import java.time.LocalTime;

public class Lesson implements Serializable {
    private Teacher teacher;
    private Group group;
    private Period period;

    public Lesson(Teacher teacher, Group group, LocalTime startTime, LocalTime endTime) {
        this.teacher = teacher;
        this.group = group;
        this.period = new Period(startTime, endTime);
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}
