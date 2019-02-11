package data;

import gui.Schedule;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class DataReader {

    public static void readObject() throws IOException, FileNotFoundException, ClassNotFoundException {

        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;

        try {
            fileInputStream = new FileInputStream("saves/schedulesTest2.txt");
            objectInputStream = new ObjectInputStream(fileInputStream);

            ArrayList<Schedule> schedules = (ArrayList<Schedule>) objectInputStream.readObject();

            for (Schedule s : schedules) {
                System.out.println(s);
            }

        } finally {
            if (objectInputStream != null) {
                objectInputStream.close();
            }
        }
    }
}
