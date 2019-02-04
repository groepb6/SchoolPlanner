package data;

import data.person.Teacher;
import data.room.Classroom;
import data.room.Room;
import java.util.ArrayList;

public class School {

    private String name;
    private ArrayList<Room> rooms;
    private ArrayList<Teacher> teachers;
    private ArrayList<Group> groups;

    public School(String name) {
        this.name = name;
        this.rooms = new ArrayList<>();
        this.teachers = new ArrayList<>();
        this.groups = new ArrayList<>();
    }

    public void addClassroom(String name, int capacity) {
        this.rooms.add(new Classroom(name, capacity));
    }

    public void addClassroom(Classroom classroom) {
        this.rooms.add(classroom);
    }

    public void addTeacher(String name, int id, String subjectName) {
        this.teachers.add(new Teacher(name, id, subjectName));
    }

    public void addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
    }

    public void addGroup(Group group) {
        this.groups.add(group);
    }
}
