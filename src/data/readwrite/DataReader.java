package data.readwrite;

import data.rooms.Classroom;
import data.rooms.Room;
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
        System.out.println("The program will probably stop working now.");
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
        System.out.println("The program will probably stop working now.");
        return DataReader.emergencySchool();
    }

    /**
     * Creates a School with the rooms needed for the simulation and writes it to the save file.
     *
     * @return A School object with 6 rooms and nothing else.
     */
    public static School emergencySchool() {
        System.out.println("No proper school could be loaded, creating a new one...");
        School school = new School("School");
        school.getRooms().add(new Classroom("LA001"));
        school.getRooms().add(new Classroom("LA002"));
        school.getRooms().add(new Classroom("LA003"));
        school.getRooms().add(new Classroom("LA004"));
        school.getRooms().add(new Classroom("LA005"));
        school.getRooms().add(new Classroom("LA006"));
        DataWriter.writeSchool(school);
        return school;
    }

}
