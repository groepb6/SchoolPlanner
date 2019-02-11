package gui;

import java.io.*;

public class GuiLoader {

    /**
     * Method to load a Schedule from a file
     * Returns a new Schedule when loading has failed
     * @param fileName
     * @return
     */
    public static Plan loadPlan(String fileName) {
        String loadPath = "saves/" + fileName + ".txt";
        System.out.println("Attempting to load plan from " + loadPath);
        try {
            File saveFile = new File(loadPath);
            FileInputStream fileInputStream = new FileInputStream(saveFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (Plan) objectInputStream.readObject();
        } catch (FileNotFoundException exception) {
            System.out.println("Save file not found!");
        } catch (IOException exception) {
            System.out.println("An IOException has occurred!");
        } catch (ClassNotFoundException exception) {
            System.out.println("No object found in file!");
        }
        System.out.println("Loading has failed!");
        return new Plan(null, null, null, null, null);
    }

    /**
     * Method to load the Schedule from the test save file
     * @return
     */
    public static Plan loadPlan() {
        return GuiLoader.loadPlan("planTest1");
    }


}
