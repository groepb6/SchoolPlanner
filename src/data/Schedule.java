package data;

import java.io.*;

/**
 *
 */
public class Schedule implements Serializable {

    public Schedule() {

    }

    /**
     * Method to save the Schedule to a file
     * @param fileName
     */
    public void saveToFile(String fileName) {
        String savePath = fileName + ".txt";
        System.out.println("Attempting to save schedule to " + savePath);
        try {
            System.out.println("Schedule saved successfully.");
            File saveFile = new File(savePath);
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
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
