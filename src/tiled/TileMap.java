package tiled;

import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TileMap {
    private BufferedImage image;
    private int imageHeight;
    private int imageWidth;
    private int margin;
    private String name;
    private int spacing;
    private int tileCount;
    private int tileHeight;
    private int tileWidth;
    private List<Tile> tiles;

    public TileMap(JsonObject jsonTileMap) {
        try {
            File imageFile = new File(this.getClass().getResource("tiled/tilemaps/") + jsonTileMap.getString("image"));
            this.image = ImageIO.read(imageFile);
        } catch (IOException exception) {
            System.out.println("Image loading failed!");
        }
        this.imageHeight = jsonTileMap.getInt("imageheight");
        this.imageWidth = jsonTileMap.getInt("imagewidth");
        this.margin = jsonTileMap.getInt("margin");
        this.name = jsonTileMap.getString("name");
        this.spacing = jsonTileMap.getInt("spacing");
        this.tileCount = jsonTileMap.getInt("tilecount");
        this.tileHeight = jsonTileMap.getInt("tileheight");
        this.tileWidth = jsonTileMap.getInt("tilewidth");
        this.addTiles();
    }

    private void addTiles() {
        
    }


}
