package gui.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class ScheduleLoader {

    public Schedule loadFile(String fileName) throws Exception {
        String loadPath = fileName + ".txt";
        File saveFile = new File(loadPath);
        FileInputStream fileInputStream = new FileInputStream(saveFile);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        return (Schedule)objectInputStream.readObject();
    }

}
