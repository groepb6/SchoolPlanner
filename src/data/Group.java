package data;

import data.person.Student;
import java.io.*;
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

    /**
     * Saves this Group to a file
     * @param fileName
     */
    public void saveToFile(String fileName) {
        String savePath = "saves/groups/" + fileName + ".txt";
        System.out.println("Attempting to save group to " + savePath);
        try {
            File saveFile = new File(savePath);
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            System.out.println("group saved successfully.");
        } catch (FileNotFoundException exception) {
            System.out.println("Save destination not found!");
            System.out.println("YOUR FILE MIGHT NOT HAVE BEEN SAVED!");
            System.out.println("Please try again.");
        } catch (IOException exception) {
            System.out.println("An IOException has occurred!");
            System.out.println("YOUR FILE MIGHT NOT HAVE BEEN SAVED!");
            System.out.println("Please try again.");
        }
    }

}
