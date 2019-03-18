package data.schoolrelated;

import data.persons.Person;
import data.persons.Student;
import data.schedulerelated.Hour;
import sun.nio.cs.ArrayEncoder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Group implements Serializable {
    private String name;
    private ArrayList<Student> students;
    private ArrayList<Hour> hours;

    public Group(String name) {
        this.name = name;
        this.students = new ArrayList<>();
        this.hours = new ArrayList<>();
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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public ArrayList<Hour> getHours() {
        return hours;
    }

    public void setHours(ArrayList<Hour> hours) {
        this.hours = hours;
    }
}
