package data.readwrite;

import data.schedulerelated.Schedule;
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
     * CURRENTLY DYSFUNCTIONAL
     * Method to save an object to a file
     *
     * @param object   Has to implement Saveable to save successfully
     * @param fileName The file name for the save file
     */
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

    /**
     * CURRENTLY DYSFUNCTIONAL
     * Saves object to a file with a standard file name, should only be used by developers
     *
     * @param object Has to implement Saveable to save successfully
     */
    /*
    private static void writeObject(Object object) {
        DataWriter.writeObject(object, "testSave");
    }
    */

    /**
     * CURRENTLY DYSFUNCTIONAL
     * Checks if the parameter is either a List with Saveable objects or a Saveable object, then gives the right save directory
     *
     * @param object Has to implement Saveable or be a list containing objects implementing Saveable
     * @return The save directory.
     */
    /*
    private static String getSaveDirectory(Object object) {
        if (object.getClass().equals(List.class)) {
            try {
                List<Saveable> list = (List<Saveable>) object;
                if (list.get(0).getClass().equals(Schedule.class)) {
                    return list.get(0).getPath();
                }
            } catch (Exception exception) {
//                throw new Exception("You can't save an ArrayList not filled with classes implementing Saveable");
                System.out.println("You can't save a List not filled with classes implementing Saveable!");
                System.out.println("YOUR FILE COULD NOT BE SAVED!");
            }
        }
        if (object.getClass().equals(Saveable.class)) {
            Saveable saveable = (Saveable) object;
            return saveable.getPath();
        }
//        throw new Exception("You tried to save something that doesn't implement Saveable");
        System.out.println("You tried to save something that doesn't implement Saveable!");
        System.out.println("YOUR FILE COULD NOT BE SAVED!");
        return "saves/junk/";
    }
    */

    /**
     * Saves a List of schedules to a file
     * @param schedules The List of schedules that need to be saved
     * @param fileName The file name of the save file
     */
    public static void writeScheduleList(List<Schedule> schedules, String fileName) {
        String savePath = "saves/schedules/" + fileName + ".txt";
        System.out.println("Attempting to save object to " + savePath);
        try {
            File saveFile = new File(savePath);
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(schedules);
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

    /**
     * Saves a List of schedules to a file with the standard file name
     *
     * @param schedules The List of schedules that need to be saved
     */
    public static void writeScheduleList(List<Schedule> schedules) {
        DataWriter.writeScheduleList(schedules, "scheduleTest1");
    }

}
