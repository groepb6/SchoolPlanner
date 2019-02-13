package data.readwrite;

import data.schedulerelated.Schedule;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class DataReader {

    public static ArrayList<Schedule> readObject() throws IOException, FileNotFoundException, ClassNotFoundException {
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream = null;
        try {
            fileInputStream = new FileInputStream("saves/schedulesData.txt");
            objectInputStream = new ObjectInputStream(fileInputStream);
            return (ArrayList<Schedule>) objectInputStream.readObject();
        } finally {
            if (objectInputStream != null) {
                objectInputStream.close();
            }
        }
    }
}
