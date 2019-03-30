package data.readwrite;

import data.sampledata.SampleData;
import data.schoolrelated.School;
import gui.settings.ApplicationSettings;
import javafx.scene.Node;

import java.io.*;

/**
 * Class to load objects saved to a file
 *
 * @author Hanno Brandwijk
 * @author Wout Stevens
 * @author Noah Walsmits
 */
public class DataReader {
    public static final int ROOMCOUNT = 6; //TODO: move

    /**
     * This method reads school.txt and returns a School-object when found.
     * It also prints custom messages to respresent the different exceptions that could occur.
     */
    public static School readSchool() {
        try {
            File saveFile = new File(ApplicationSettings.schoolPath);
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
        return DataReader.emergencySchool();
    }

    public static School readDefaults() {
        try {
            File saveFile = new File(ApplicationSettings.saveSlotPath);
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
        return DataReader.emergencySchool();
    }

    public static Node[][] readAllNodes() {
        try {
            File saveFile = new File(ApplicationSettings.saveNodePath);
            FileInputStream fileInputStream = new FileInputStream(saveFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (Node[][]) objectInputStream.readObject();
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
     * Checks if the School has the right amount of rooms.
     * @param school The School that is checked.
     * @return True if the School does have the right amount of rooms, and otherwise false.
     */
    public static boolean hasRooms(School school) {
        if (school.getRooms().size() == ROOMCOUNT) {
            return true;
        }
        return false;
    }

    /**
     * Gets a School object from SampleData. Used to create a School object when the saved object cannot be properly read.
     *
     * @return A School object from SampleData.
     */
    public static School emergencySchool() {
        System.out.println("No proper school could be loaded, creating a new one...");
        SampleData sampleData = new SampleData();
        return sampleData.getSchool();
    }
    //TODO: **IMPORTANT** check if succesfully loaded School has the classrooms with proper names!
}
