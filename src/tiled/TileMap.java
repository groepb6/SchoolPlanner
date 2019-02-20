package tiled;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class TileMap {
    private int imageHeight;
    private int imageWidth;
    private List<Tile> tiles;

    public TileMap(String fileName) {
        this.readTileMap(fileName);
    }

    public String toString() {
        return imageHeight + "";
    }

    private void readTileMap(String fileName) {
        try {
            String filePath = TileMap.class.getResource("/tiled/" + fileName + ".json").toString();
            File file = new File(filePath);
            InputStream input = new FileInputStream(file);
            JsonReader jsonReader = Json.createReader(input);
            JsonObject tileMap = jsonReader.readObject();
            this.imageHeight = tileMap.getInt("imagewidth", 10);
        } catch (Exception e) {
            System.out.println("dommet");
        }
    }


}
