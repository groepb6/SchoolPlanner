package data.readwrite;
import data.schoolrelated.School;
import java.io.*;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Class to save objects to a file
 */
public class DataWriter {

    public static void writeSchool(School school) {
        String savePath = "saves/school/school.txt";
        try {
            File saveFile = new File(savePath);
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(school);
        } catch (Exception exception) { exception.printStackTrace(); }
    }
}
