package data.schoolrelated;

import data.persons.Student;
import data.schedulerelated.Hour;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Group implements Serializable {
    private String name;
    private Set<Student> students;
    private Set<Hour> hours;

    public Group(String name) {
        this.name = name;
        this.students = new HashSet<>();
        this.hours = new HashSet<>();
    }

    public void addStudents(Student student) {
        this.students.add(student);
    }

    public void addStudents(String name, int id) {
        this.students.add(new Student(name));
    }

    public String getName() {
        return name;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<Hour> getHours() {
        return hours;
    }

    public void setHours(Set<Hour> hours) {
        this.hours = hours;
    }
}
