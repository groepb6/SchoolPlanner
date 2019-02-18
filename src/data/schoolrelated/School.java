package data.schoolrelated;

import data.persons.Person;
import data.persons.Teacher;
import data.rooms.Classroom;
import data.rooms.Room;

import java.util.ArrayList;
import java.io.*;
import java.util.List;

public class School implements Serializable {
    private String name;
    private List<Room> rooms;
    private List<Person> teachers;
    private List<Group> groups;

    public School(String name) {
        this.name = name;
        this.rooms = new ArrayList<>();
        this.teachers = new ArrayList<>();
        this.groups = new ArrayList<>();
    }

    public void addClassroom(Classroom classroom) {
        this.rooms.add(classroom);
    }

    public void addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
    }

    public void addGroup(Group group) {
        this.groups.add(group);
    }
}
