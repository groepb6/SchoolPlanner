package data;

import gui.Schedule;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataWriter {

    public static void writeObject(ArrayList<Schedule> schedules) throws FileNotFoundException, IOException {

        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream("saves/schedulesTest2.txt");
            objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(schedules);
        } finally {
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
        }
    }
}
