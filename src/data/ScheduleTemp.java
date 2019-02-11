package data;

import java.util.ArrayList;
import java.io.*;

public class ScheduleTemp implements Serializable {

    private String name;
    private ArrayList<Room> rooms;
    private ArrayList<Person> teachers;
    private ArrayList<Group> groups;

    public ScheduleTemp(String name) {
        this.name = name;
        this.rooms = new ArrayList<>();
        this.teachers = new ArrayList<>();
        this.groups = new ArrayList<>();
    }

    public void addClassroom(String name, int capacity) {
        this.rooms.add(new Classroom(name, capacity));
    }

    public void addClassroom(Classroom classroom) {
        this.rooms.add(classroom);
    }

    public void addTeacher(String name, int id, String subjectName) {
        this.teachers.add(new Teacher(name, id, subjectName));
    }

    public void addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
    }

    public void addGroup(Group group) {
        this.groups.add(group);
    }

    /**
     * Method to save the ScheduleTemp to a file
     * @param fileName
     */
    public void saveToFile(String fileName) {
        String savePath = fileName + ".txt";
        System.out.println("Attempting to save schedule to " + savePath);
        try {
            File saveFile = new File(savePath);
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            System.out.println("ScheduleTemp saved successfully.");
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
     * Method to save this ScheduleTemp to the save test file
     */
    public void saveToFile() {
        this.saveToFile("testSave");
    }

}
