package data.schoolrelated;

import data.persons.Person;
import data.persons.Teacher;
import data.rooms.Classroom;
import data.rooms.Room;
import data.schedulerelated.Schedule;

import java.util.ArrayList;
import java.io.*;
import java.util.List;

public class School implements Serializable {
    private String name;
    private ArrayList<Room> rooms;
    private ArrayList<Person> teachers;
    private ArrayList<Group> groups;
    private ArrayList<Schedule> schedules;
    private ArrayList<Subject> subjects;

    public School(String name) {
        this.name = name;
        this.rooms = new ArrayList<>();
        this.teachers = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.schedules = new ArrayList<>();
        this.subjects = new ArrayList<>();
    }

    public void addClassroom(String name, int capacity) {
        this.rooms.add(new Classroom(name));
    }

    public void addClassroom(Classroom classroom) {
        this.rooms.add(classroom);
    }

    public void addTeacher(String name, int id, Subject subject) {
        this.teachers.add(new Teacher(name));
    }

    public void addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
    }

    public void addGroup(Group group) {
        this.groups.add(group);
    }

    public void addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public ArrayList<Person> getTeachers() {
        return teachers;
    }

    public ArrayList<Group> getGroups() {

        return groups;
    }

    /**
     * This method was used for testing whether a list had groups in them.
     * @return
     */

    public ArrayList<String> groupsToString(){
        ArrayList<String> groupString = new ArrayList<>();
        for (Group g : groups) {
            groupString.add(g.getName());
        }
        return groupString;
    }

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void setSchedules(ArrayList<Schedule> schedules) {
        this.schedules = schedules;
    }
}
