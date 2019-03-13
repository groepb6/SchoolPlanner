package tiled;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.InputStream;

public class TiledReader {

    /**
     * Loads a Map from a json file
     *
     * @param fileName The file name of the json file
     * @return a JsonObject that can be used in the constructor of Map
     */
    public static JsonObject readMap(String fileName) {
        InputStream loadPath = TiledReader.class.getResourceAsStream("/tiles/maps/" + fileName); //todo: fix
        System.out.println("Attempting to load json file from " + loadPath);
        JsonReader jsonReader = Json.createReader(loadPath);
        return jsonReader.readObject();
    }

}
