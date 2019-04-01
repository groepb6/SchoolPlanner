package data.readwrite;

import data.schoolrelated.School;
import gui.settings.ApplicationSettings;
import javafx.scene.Node;

import java.io.*;

/**
 * Class to save objects to a file
 * <p>
 * @author Hanno Brandwijk
 * @author Wout Stevens
 * @author Noah Walsmits
 */
public class DataWriter {

    /**
     * This method writes the given School-object to the file school.txt
     * It also prints custom messages to respresent the different exceptions that could occur.
     *
     * @param school School object that contains all plans and schedules.
     */

    public static void writeSchool(School school) {
        try {
            File saveFile = new File(ApplicationSettings.schoolPath);
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(school);
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

    public static void writeDefaults(School school) {
        try {
            File saveFile = new File(ApplicationSettings.saveSlotPath);
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(school);
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

    public static void writeAllNodes(Node[][] allNodes) {
        try {
            File saveFile = new File(ApplicationSettings.saveNodePath);
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(allNodes);
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
}