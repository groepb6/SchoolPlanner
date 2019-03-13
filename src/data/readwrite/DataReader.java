package data.readwrite;

import data.persons.Teacher;
import data.schedulerelated.Schedule;
import data.schoolrelated.School;

import java.io.*;
import java.util.List;

/**
 * Class to load objects saved to a file
 */
public class DataReader {

    public static School readSchool() {
        String loadPath = "saves/school/school.txt";
        System.out.println("Attempting to load schedule from " + loadPath);
        try {
            File saveFile = new File(loadPath);
            FileInputStream fileInputStream = new FileInputStream(saveFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (School) objectInputStream.readObject();
        } catch (FileNotFoundException exception) {
            System.out.println("Save file not found!");
            System.out.println("YOUR FILE COULD NOT BE LOADED!");
        } catch (IOException exception) {
            System.out.println("An IOException has occurred!");
            System.out.println("YOUR FILE COULD NOT BE LOADED!");
        } catch (ClassNotFoundException exception) {
            System.out.println("No object found in file!");
            System.out.println("YOUR FILE COULD NOT BE LOADED!");
        }
        System.out.println("Loading has failed!");
        System.out.println("The program will probably stop working now.");
        return null;
    }

    public static School readPreset() {
        String loadPath = "saves/school/preset.txt";
        System.out.println("Attempting to load schedule from " + loadPath);
        try {
            File saveFile = new File(loadPath);
            FileInputStream fileInputStream = new FileInputStream(saveFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (School) objectInputStream.readObject();
        } catch (FileNotFoundException exception) {
            System.out.println("Save file not found!");
            System.out.println("YOUR FILE COULD NOT BE LOADED!");
        } catch (IOException exception) {
            System.out.println("An IOException has occurred!");
            System.out.println("YOUR FILE COULD NOT BE LOADED!");
        } catch (ClassNotFoundException exception) {
            System.out.println("No object found in file!");
            System.out.println("YOUR FILE COULD NOT BE LOADED!");
        }
        System.out.println("Loading has failed!");
        System.out.println("The program will probably stop working now.");
        return null;
    }


    /**
     * CURRENTLY DYSFUNCTIONAL
     * Loads an object saved to a file
     *
     * @param object   Has to implement Saveable to save successfully
     * @param fileName The file name for the save file
     * @return An object that has to be casted to the intended class;
     */
    /*
    private static Object readObject(Object object, String fileName) {
        String loadPath = DataWriter.getSaveDirectory(object) + fileName + ".txt";
        System.out.println("Attempting to load schedule from " + loadPath);
        try {
            File saveFile = new File(loadPath);
            FileInputStream fileInputStream = new FileInputStream(saveFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return objectInputStream.readObject();
        } catch (FileNotFoundException exception) {
            System.out.println("Save file not found!");
            System.out.println("YOUR FILE COULD NOT BE LOADED!");
        } catch (IOException exception) {
            System.out.println("An IOException has occurred!");
            System.out.println("YOUR FILE COULD NOT BE LOADED!");
        } catch (ClassNotFoundException exception) {
            System.out.println("No object found in file!");
            System.out.println("YOUR FILE COULD NOT BE LOADED!");
        }
        System.out.println("Loading has failed!");
        System.out.println("The program will probably stop working now.");
        return null;
    }
    */
}
