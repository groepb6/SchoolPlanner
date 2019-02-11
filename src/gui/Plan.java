package gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.*;

public class Plan implements Serializable {
    private SimpleStringProperty time;
    private SimpleStringProperty group;
    private SimpleStringProperty location;
    private SimpleStringProperty teacher;
    private SimpleStringProperty subject;

    public Plan(String time, String group, String location, String teacher, String subject) {
        this.time = new SimpleStringProperty(time);
        this.group = new SimpleStringProperty(group);
        this.location = new SimpleStringProperty(location);
        this.teacher = new SimpleStringProperty(teacher);
        this.subject = new SimpleStringProperty(subject);
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public void setGroup(String group) {
        this.group.set(group);
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public void setTeacher(String teacher) {
        this.teacher.set(teacher);
    }

    public void setSubject(String subject) {
        this.subject.set(subject);
    }

    public String getTime() {
        return this.time.get();
    }

    public String getGroup() {
        return this.group.get();
    }

    public String getLocation() {
        return this.location.get();
    }

    public String getTeacher() {
        return this.teacher.get();
    }

    public String getSubject() {
        return this.subject.get();
    }

    @Override
    public String toString() {
        return (time.get() + group.get() + location.get() + teacher.get() + subject.get());
    }

    /**
     * Method to save the Plan to a file
     * @param fileName
     */
    public void saveToFile(String fileName) {
        String savePath = "saves/" + fileName + ".txt";
        System.out.println("Attempting to save schedule to " + savePath);
        try {
            File saveFile = new File(savePath);
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            System.out.println("Plan saved successfully.");
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
     * Method to save this Plan to the save test file
     */
    public void saveToFile() {
        this.saveToFile("planTest1");
    }

}
