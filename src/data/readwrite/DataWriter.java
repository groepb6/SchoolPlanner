package data.readwrite;

import data.persons.Teacher;
import data.schedulerelated.Schedule;
import data.schoolrelated.School;

import java.io.*;
import java.util.List;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Class to save objects to a file
 */
public class DataWriter {

    /**
     * This method writes the given School-object to the file school.txt
     * It also prints custom messages to respresent the different exceptions that could occur.
     *
     * @param school
     */

    public static void writeSchool(School school) {
        String savePath = "saves/school/school.txt";
        System.out.println("Attempting to save object to " + savePath);
        try {
            File saveFile = new File(savePath);
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(school);
            System.out.println("Object saved successfully.");
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

    /*
    private static void writeObject(Object object, String fileName) {
        String savePath = getSaveDirectory(object) + fileName + ".txt";
        System.out.println("Attempting to save object to " + savePath);
        try {
            File saveFile = new File(savePath);
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
            System.out.println("Object saved successfully.");
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
    */

//    /**
//     * Saves a List of schedules to a file with the standard file name
//     *
//     * @param schedules The List of schedules that need to be saved
//     */
//
//    public static void writeScheduleList(List<Schedule> schedules) {
//        DataWriter.writeScheduleList(schedules, "scheduleTest1");
//    }
//
//    /**
//     * Saves a group of students to a file in the saves/groups directory
//     *
//     * @param group    The group that needs to be saved
//     * @param fileName The file name of the save file
//     */
//    public static void writeGroup(Group group, String fileName) {
//        String savePath = "saves/groups/" + fileName + ".txt";
//        System.out.println("Attempting to save group to " + savePath);
//        try {
//            File saveFile = new File(savePath);
//            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
//            objectOutputStream.writeObject(group);
//            System.out.println("group saved successfully.");
//        } catch (FileNotFoundException exception) {
//            System.out.println("Save destination not found!");
//            System.out.println("YOUR FILE MIGHT NOT HAVE BEEN SAVED!");
//            System.out.println("Please try again.");
//        } catch (IOException exception) {
//            System.out.println("An IOException has occurred!");
//            System.out.println("YOUR FILE MIGHT NOT HAVE BEEN SAVED!");
//            System.out.println("Please try again.");
//        }
//    }
//
//    /**
//     * Saves a group to a file with the standard file name
//     *
//     * @param group
//     */
//    public static void writeGroup(Group group) {
//        DataWriter.writeGroup(group, "groupTest1");
//    }
//
//    /**
//     * * Saves teacher to a file in the saves/teachers directory
//     *
//     * @param teacher  The teacher that needs to be saved
//     * @param fileName The file name of the save file
//     */
//    public static void writeTeacher(Teacher teacher, String fileName) {
//        String savePath = "saves/teachers/" + fileName + ".txt";
//        System.out.println("Attempting to save teacher to " + savePath);
//        try {
//            File saveFile = new File(savePath);
//            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
//            objectOutputStream.writeObject(teacher);
//            System.out.println("Object saved successfully.");
//        } catch (FileNotFoundException exception) {
//            System.out.println("Save destination not found!");
//            System.out.println("YOUR FILE MIGHT NOT HAVE BEEN SAVED!");
//            System.out.println("Please try again.");
//        } catch (IOException exception) {
//            System.out.println("An IOException has occurred!");
//            System.out.println("YOUR FILE MIGHT NOT HAVE BEEN SAVED!");
//            System.out.println("Please try again.");
//        }
//    }
//
//    /**
//     * Saves a teacher to a file with the standard file name
//     *
//     * @param teacher The teacher that needs to be saved
//     */
//    public static void writeTeacher(Teacher teacher) {
//        DataWriter.writeTeacher(teacher, "teacherTest1");
//    }


}
