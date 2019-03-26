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
        try {
            File saveFile = new File(loadPath);
            FileInputStream fileInputStream = new FileInputStream(saveFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (School) objectInputStream.readObject();
        } catch (Exception exception) { exception.printStackTrace(); }
        return null;
    }
}
