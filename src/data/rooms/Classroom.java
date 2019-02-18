package data.rooms;

import data.schedulerelated.Lesson;

import java.util.ArrayList;
import java.util.List;

public class Classroom extends Room {
    private List<Lesson> lessons;

    public Classroom(String name, int capacity) {
        super(name, capacity);
        this.lessons = new ArrayList<>();
    }

    public void addLesson(Lesson lesson) {
        this.lessons.add(lesson);
    }
}
