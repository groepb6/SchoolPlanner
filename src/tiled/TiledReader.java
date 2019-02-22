package tiled;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.invoke.MethodHandles;

public class TiledReader {

    /**
     * Loads a TileMap from a json file
     *
     * @param fileName The file name of the json file
     * @return a JsonObject that can be used in the constructor of TileMap
     */
    public static JsonObject readTileMap(String fileName) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        //String loadPath = MethodHandles.lookup().lookupClass().getResource("tiled/tileMaps") + fileName + ".json";
        //String loadPath = classLoader.getResource("tiled/tileMaps") + fileName + ".json";
        String loadPath = TiledReader.class.getResource("tiled/tileMaps") + fileName + ".json";
        System.out.println("Attempting to json file from " + loadPath);
        try {
            File saveFile = new File(loadPath);
            FileInputStream fileInputStream = new FileInputStream(saveFile);
            JsonReader jsonReader = Json.createReader(fileInputStream);
            return (JsonObject) jsonReader.readObject();
        } catch (FileNotFoundException exception) {
            System.out.println("Save file not found!");
        }
        System.out.println("Loading has failed!");
        return null;
    }

    /**
     * Loads a TileMap from a json file that has the standard file name, should only be used by developers
     *
     * @return a JsonObject that can be used in the constructor of TileMap
     */
    public static JsonObject readTileMap() {
        return TiledReader.readTileMap("test.json");
    }

}
