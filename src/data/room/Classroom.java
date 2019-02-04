package data.room;

import data.Group;
import data.Lesson;
import data.person.Teacher;
import java.time.LocalTime;
import java.util.ArrayList;

public class Classroom extends Room {

    private ArrayList<Lesson> lessons;

    public Classroom(String name, int capacity) {
        super(name, capacity);
        this.lessons = new ArrayList<>();
    }

    public void addLesson(Lesson lesson) {
        this.lessons.add(lesson);
    }

    public void addLesson(Teacher teacher, Group group, LocalTime startTime, LocalTime endTime) {
        this.lessons.add(new Lesson(teacher, group, startTime, endTime));
    }
}
