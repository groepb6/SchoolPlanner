package data.schoolrelated;

import data.persons.Student;
import data.schedulerelated.Hour;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Hanno Brandwijk
 * @author Wout Stevens
 */

public class Group implements Serializable {
    private String name;
    private Set<Student> students;
    private Set<Hour> hours;

    public Group(String name) {
        this.name = name;
        this.students = new HashSet<>();
        this.hours = new HashSet<>();
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public String getName() {
        return name;
    }

    public Set<Student> getStudents() {
        return this.students;
    }

//    public void setStudents(Set<Student> students) {
//        this.students = students;
//    } todo: remove unused methods

    public Set<Hour> getHours() {
        return hours;
    }

//    public void setHours(Set<Hour> hours) {
//        this.hours = hours;
//    }
}
