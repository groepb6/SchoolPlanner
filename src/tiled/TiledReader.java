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
     * Loads a TileSet from a json file
     *
     * @param fileName The file name of the json file
     * @return a JsonObject that can be used in the constructor of TileSet
     */
    public static JsonObject readTileSet(String fileName) {
        //ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        //String loadPath = MethodHandles.lookup().lookupClass().getResource("tiled/tilesets/") + fileName + ".json";
        //String loadPath = classLoader.getResource("tiled/tilesets/") + fileName + ".json";
        //String loadPath = TiledReader.class.getResource("tiled/tilesets/") + fileName + ".json"; //todo: fix
        String loadPath = "D:\\School\\Avans jaar 1\\Periode 3\\Proftaak\\SchoolPlanner\\resources\\tiled\\tilesets\\Testj.json";
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
     * Loads a TileSet from a json file that has the standard file name, should only be used by developers
     *
     * @return a JsonObject that can be used in the constructor of TileSet
     */
    public static JsonObject readTileSet() {
        return TiledReader.readTileSet("test.json");
    }

    /**
     * Loads a Map from a json file
     *
     * @param fileName The file name of the json file
     * @return a JsonObject that can be used in the constructor of Map
     */
    public static JsonObject readMap(String fileName) {
        String loadPath = TiledReader.class.getResource("tiled/maps/") + fileName + ".json"; //todo: fix
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
     * Loads a Map from a json file that has the standard file name, should only be used by developers
     *
     * @return a JsonObject that can be used in the constructor of Map
     */
    public static JsonObject readMap() {
        return TiledReader.readTileSet("test.json");
    }

    /**
     * Loads a Layer from a json file
     *
     * @param fileName The file name of the json file
     * @return a JsonObject that can be used in the constructor of Layer
     */
    public static JsonObject readLayer(String fileName) {
        String loadPath = TiledReader.class.getResource("tiled/error/") + fileName + ".json"; //todo: fix, decide load path
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
     * Loads a Layer from a json file that has the standard file name, should only be used by developers
     *
     * @return a JsonObject that can be used in the constructor of Layer
     */
    public static JsonObject readlayer() {
        return TiledReader.readTileSet("test.json");
    }

}
