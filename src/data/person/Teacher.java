package data.person;

import data.Subject;

import java.io.*;

public class Teacher extends Person {

    private Subject subject;

    public Teacher(String name, int id, String subjectName) {
        super(name, id);
        this.subject = new Subject(subjectName);
    }

    /**
     * Save this Teacher to a file, possible use in a dropdown list with existing teachers to select
     * @param fileName
     */
    public void saveToFile(String fileName) {
        String savePath = "saves/teachers/" + fileName + ".txt";
        System.out.println("Attempting to save teacher to " + savePath);
        try {
            File saveFile = new File(savePath);
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            System.out.println("teacher saved successfully.");
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

    /**
     * Method to save this Teacher to the save test file
     */
    public void saveToFile() {
        this.saveToFile("teacherTest1");
    }

}
