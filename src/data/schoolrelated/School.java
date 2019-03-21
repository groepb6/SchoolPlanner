package data.schoolrelated;

import data.persons.Person;
import data.persons.Teacher;
import data.rooms.Classroom;
import data.rooms.Room;
import data.schedulerelated.Schedule;

import java.util.ArrayList;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * Finds all of the schedules that belong to a certain group, and puts those in a HashMap.
     * @return A SchoolMap that has every Group of the School as key, and a List of Schedule objects as value.
     */
    public Map<Group, List<Schedule>> findGroupSchedules() {
        Map<Group, List<Schedule>> groupSchedules = new HashMap<>();
        for (Group group : this.groups) {
            List<Schedule> foundSchedules = new ArrayList<>();
            for (Schedule schedule : this.schedules) {
                if (schedule.getGroup().equals(group)) {
                    foundSchedules.add(schedule);
                }
            }
            groupSchedules.put(group, foundSchedules);
        }
        return groupSchedules;
    }

    public boolean hasData() {
        return (rooms.size()+teachers.size()+groups.size()+subjects.size())>0;
    }

    public void clearAll() {
        rooms.clear();
        teachers.clear();
        groups.clear();
        schedules.clear();
        subjects.clear();
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
