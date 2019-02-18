package data.schoolrelated;

import data.persons.Student;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable {
    private String name;
    private ArrayList<Student> students;

    public Group(String name) {
        this.name = name;
        this.students = new ArrayList<>();
    }

    public void addStudents(Student student) {
        this.students.add(student);
    }

    public void addStudents(String name, int id) {
        this.students.add(new Student(name, id));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }
}
