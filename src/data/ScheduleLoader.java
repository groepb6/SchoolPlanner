package data;

import java.io.*;

/**
 * Class to load new schedules
 */
public class ScheduleLoader {

    /**
     * Method to load a ScheduleTemp from a file
     * Returns a new ScheduleTemp when loading has failed
     * @param fileName
     * @return
     */
    public static ScheduleTemp loadFile(String fileName) {
        String loadPath = fileName + ".txt";
        System.out.println("Attempting to load schedule from " + loadPath);
        try {
            File saveFile = new File(loadPath);
            FileInputStream fileInputStream = new FileInputStream(saveFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (ScheduleTemp) objectInputStream.readObject();
        } catch (FileNotFoundException exception) {
            System.out.println("Save file not found!");
        } catch (IOException exception) {
            System.out.println("An IOException has occurred!");
        } catch (ClassNotFoundException exception) {
            System.out.println("No object found in file!");
        }
        System.out.println("Loading has failed!");
        return new ScheduleTemp(null);
    }

    /**
     * Method to load the ScheduleTemp from the test save file
     * @return
     */
    public static ScheduleTemp loadFile() {
        return ScheduleLoader.loadFile("testSave");
    }

}
