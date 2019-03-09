package tiled;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TiledReader {

    /**
     * Loads a Map from a json file
     *
     * @param fileName The file name of the json file
     * @return a JsonObject that can be used in the constructor of Map
     */
    public static JsonObject readMap(String fileName) {
        String loadPath = TiledReader.class.getResource("/tiles/maps/") + fileName + ".json"; //todo: fix
        //loadPath = TiledReader.class.getResourceAsStream("/tiles/maps/" + fileName + ".json").toString();
        System.out.println("Attempting to json file from " + loadPath);
        try {
            File saveFile = new File(loadPath);
            System.out.println(saveFile.canRead()); //test code
            System.out.println(saveFile.getAbsolutePath()); //test code
            FileInputStream fileInputStream = new FileInputStream(saveFile);
            JsonReader jsonReader = Json.createReader(fileInputStream);
            return (JsonObject) jsonReader.readObject();
        } catch (FileNotFoundException exception) {
            System.out.println("Save file not found!");
            exception.printStackTrace();
        }
        System.out.println("Loading has failed!");
        return null;
    }

}
