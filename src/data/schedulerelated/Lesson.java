package data.schedulerelated;

import data.persons.Teacher;
import data.rooms.Classroom;
import data.schoolrelated.Group;

public class Lesson {
    private Teacher teacher;
    private Group group;
    private Classroom classroom;
    private Period period;

    public Lesson(Teacher teacher, Group group, Classroom classroom, Period period) {
        this.teacher = teacher;
        this.group = group;
        this.classroom = classroom;
        this.period = period;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Group getGroup() {
        return group;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public Period getPeriod() {
        return period;
    }
}
