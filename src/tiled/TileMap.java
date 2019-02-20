package tiled;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import javax.json.*;

public class TileMap {
    private List<Tile> tiles;

    public TileMap(String fileName) {

        try {
            File file = new File(fileName);
            InputStream input = new FileInputStream(file);
            JsonReader jsonReader = Json.createReader(input);
            JsonObject tileMap = jsonReader.readObject();
        } catch (Exception e) {

        }

    }


}
