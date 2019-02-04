package gui.data;

import java.io.*;

public class Schedule implements Serializable {

    public Schedule() {

    }

    public void saveToFile(String fileName) throws Exception {
        String savePath = fileName + ".txt";
        File saveFile = new File(savePath);
        FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);
    }

}
