package data.schoolrelated;

import data.persons.Person;
import data.persons.Student;
import data.persons.Teacher;
import data.rooms.Room;
import data.schedulerelated.Schedule;

import java.io.Serializable;
import java.util.*;

/**
 * @author Hanno Brandwijk
 * @author Wout Stevens
 */

public class School implements Serializable {
    private String name;
    private Set<Room> rooms;
    private Set<Teacher> teachers;
    private Set<Group> groups;
    private Set<Schedule> schedules;
    private Set<Subject> subjects;
    private Set<Person> people;

    public School(String name) {
        this.name = name;
        this.rooms = new HashSet<>();
        this.teachers = new HashSet<>();
        this.groups = new HashSet<>();
        this.schedules = new HashSet<>();
        this.subjects = new HashSet<>();
        this.people = new HashSet<>();
    }

//    /**
//     * Finds all of the schedules that belong to a certain Group, and puts those in a HashMap.
//     * @return A SchoolMap that has every Group of the School as key, and a List of Schedule objects as value.
//     */
//    public SchoolMap<Group, Set<Schedule>> findGroupSchedules() {
//        SchoolMap<Group, Set<Schedule>> groupSchedules = new HashMap<>();
//        for (Group group : this.groups) {
//            Set<Schedule> foundSchedules = new HashSet<>();
//            for (Schedule schedule : this.schedules) {
//                if (schedule.getGroup().equals(group)) {
//                    foundSchedules.add(schedule);
//                }
//            }
//            groupSchedules.put(group, foundSchedules);
//        }
//        return groupSchedules;
//    }
//
//    /**
//     * Finds all of the schedules that belong to a certain Teacher, and puts those in a HashMap.
//     * @return A SchoolMap that has every Teacher of the School as key, and a List of Schedule objects as value.
//     */
//    public SchoolMap<Teacher, Set<Schedule>> findTeacherSchedules() {
//        SchoolMap<Teacher, Set<Schedule>> teacherSchedules = new HashMap<>();
//        for (Teacher teacher : this.teachers) {
//            Set<Schedule> foundSchedules = new HashSet<>();
//            for (Schedule schedule : this.schedules) {
//                if (schedule.getTeacher().equals(teacher)) {
//                    foundSchedules.add(schedule);
//                }
//            }
//            teacherSchedules.put(teacher, foundSchedules);
//        }
//        return teacherSchedules;
//    }

    /**
     * Adds Student objects to each Group in the school if they don't have any. Executes the refreshPeople method after adding the students.
     * @param studentsPerGroup The amount of students a group should have.
     */
    public void createStudents(int studentsPerGroup) {
        for (Group group : this.groups) {
            if (group.getStudents().size() < 1) {
                for (int i = 0; i < studentsPerGroup; i++) {
                    group.addStudent(new Student(""));
                }
            }
        }
    }

    /**
     * Used to add any Person objects in students and teachers that are not yet contained in people.
     */
    public void refreshPeople() {
        this.people.addAll(this.teachers);
        for (Group group : this.groups) {
            this.people.addAll(group.getStudents());
        }
    }

    public void sort() {
        //todo: sorting on name.
    }

    public boolean hasData() {
        return (teachers.size()+groups.size()+subjects.size())>0;
    }

    public void clearAll() {
        teachers.clear();
        groups.clear();
        schedules.clear();
        subjects.clear();
        people.clear();
    }

//    public void addClassroom(String name, int capacity) {
//        this.rooms.add(new Classroom(name));
//    }
//
//    public void addClassroom(Classroom classroom) {
//        this.rooms.add(classroom);
//    }
//
//    public void addTeacher(String name, int id, Subject subject) {
//        this.teachers.add(new Teacher(name));
//    }
//
//    public void addTeacher(Teacher teacher) {
//        this.teachers.add(teacher);
//    }
//
//    public void addGroup(Group group) {
//        this.groups.add(group);
//    } TODO: remove unused methods

    public void addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
        this.rooms.add(schedule.getRoom());
        this.teachers.add(schedule.getTeacher());
        this.groups.add(schedule.getGroup());
        this.subjects.add(schedule.getSubject());
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public Set<Group> getGroups() {
        return groups;
    }

//    public ArrayList<String> groupsToString(){
//        ArrayList<String> groupString = new ArrayList<>();
//        for (Group g : groups) {
//            groupString.add(g.getName());
//        }
//        return groupString;
//    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }

    /**
     * A getter for people that executes refreshPeople.
     * @return A Set with all of the Person objects contained within the School.
     */
    public Set<Person> getPeople() {
        this.refreshPeople();
        return people;
    }

}
