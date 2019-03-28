package data.readwrite;


import data.sampledata.SampleData;
import data.schoolrelated.School;

import java.io.*;

/**
 * Class to load objects saved to a file
 */
public class DataReader {

    /**
     * This method reads school.txt and returns a School-object when found.
     * It also prints custom messages to respresent the different exceptions that could occur.
     */

    public static School readSchool() {
        String loadPath = "saves/school/school.txt";
//        System.out.println("Attempting to load schedule from " + loadPath);
        try {
            File saveFile = new File(loadPath);
            FileInputStream fileInputStream = new FileInputStream(saveFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (School) objectInputStream.readObject();
        } catch (FileNotFoundException exception) {
//            System.out.println("Save file not found!");
//            System.out.println("YOUR FILE COULD NOT BE LOADED!");
        } catch (IOException exception) {
//            System.out.println("An IOException has occurred!");
//            System.out.println("YOUR FILE COULD NOT BE LOADED!");
        } catch (ClassNotFoundException exception) {
//            System.out.println("No object found in file!");
//            System.out.println("YOUR FILE COULD NOT BE LOADED!");
        }
//        System.out.println("Loading has failed!");
//        System.out.println("The program will probably stop working now.");
//        return null;
        return DataReader.emergencySchool();
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
        return DataReader.emergencySchool();
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
