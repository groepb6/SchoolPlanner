package tiled;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.InputStream;

/**
 * A static class used to obtain the .json file for the map out of the project resources.
 */
public class TiledReader {

    /**
     * Obtains the JsonObject from a .json file located in the project resources under /tiles/maps/...
     * The .json file should be a map file from Tiled.exe.
     * This JsonObject is used to create a Map object in the constructor of Map.
     *
     * @param fileName The file name of the json file
     * @return a JsonObject that can be used in the constructor of Map
     */
    public static JsonObject readMap(String fileName) {
        InputStream loadPath = TiledReader.class.getResourceAsStream("/tiles/maps/" + fileName);
        System.out.println("Attempting to load json file from " + loadPath);
        JsonReader jsonReader = Json.createReader(loadPath);
        return jsonReader.readObject();
    }

}
