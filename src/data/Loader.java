package data;

import java.io.*;

/**
 * Class to load saved objects
 */
public class Loader {

    /**
     * Method to load a Schedule from a file
     * Returns a new Schedule when loading has failed
     * @param fileName
     * @return
     */
    public static Schedule loadFile(String fileName) {
        String loadPath = "saves/schedules/" + fileName + ".txt";
        System.out.println("Attempting to load schedule from " + loadPath);
        try {
            File saveFile = new File(loadPath);
            FileInputStream fileInputStream = new FileInputStream(saveFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (Schedule) objectInputStream.readObject();
        } catch (FileNotFoundException exception) {
            System.out.println("Save file not found!");
        } catch (IOException exception) {
            System.out.println("An IOException has occurred!");
        } catch (ClassNotFoundException exception) {
            System.out.println("No object found in file!");
        }
        System.out.println("Loading has failed!");
        return new Schedule(null);
    }

    /**
     * Method to load the Schedule from the test save file
     * @return
     */
    public static Schedule loadFile() {
        return Loader.loadFile("testSave");
    }

}
